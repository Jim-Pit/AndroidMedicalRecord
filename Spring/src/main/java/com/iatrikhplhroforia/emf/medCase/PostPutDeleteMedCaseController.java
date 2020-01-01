package com.iatrikhplhroforia.emf.medCase;

import com.iatrikhplhroforia.emf.EmfApplication;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PostPutDeleteMedCaseController {

    private String url = EmfApplication.getUrl();

    private int isId;

    @PostMapping("/case")
    public Map<String, String> insertCase(@RequestBody Map<String, String> body) {
        String id = body.get("id");
        String issue = body.get("issue");
        String date = body.get("date");
        String comments = body.get("comments");

        try {
            isId = Integer.parseInt(id);

            try {
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement statement = conn.prepareStatement("INSERT INTO " +
                        "HISTORY (Id,Issue,Date,Comments) VALUES(?,?,?,?)");

                statement.setInt(1, isId);
                statement.setString(2, issue);
                statement.setString(3, date);
                statement.setString(4, comments);

                statement.execute();
                conn.close();
            } catch (SQLException e) {
                return new HashMap<String, String>() {{
                    put("message", e.getMessage());
                }};
            }
        } catch (NumberFormatException e) {
            return new HashMap<String, String>() {{
                put("message", "Id must be an Integer");
            }};
        }
        return new HashMap<String, String>() {{
            put("message", "submit OK");
        }};
    }

    // TODO: must change db design in order to update comments correctly
    @PutMapping("/case/{id}/{comment}")
    public Map<String, String> update(@PathVariable String id,@PathVariable String comment) {
        if(!comment.isEmpty()) {
            try {
                isId = Integer.parseInt(id);

                String comments = commentRetrieval(isId);

                // if isId exists in HISTORY table and there are comments about its case
                boolean addNewLine = !comments.equals("NO") && !comments.equals("EMPTY");

                // if existing comments are empty or not:
                if (!comments.equals("NO") && !comments.startsWith("sqlExceptionError")) {
                    try {
                        Connection conn = DriverManager.getConnection(url);
                        PreparedStatement statement = conn.prepareStatement("UPDATE " +
                                "HISTORY SET Comments=? WHERE Id=?");

                        if (addNewLine)
                            statement.setString(1, comments + "\r\n" + comment);
                        else
                            statement.setString(1, comment);
                        statement.setString(2, ((Integer) isId).toString());

                        statement.execute();
                        conn.close();
                    } catch (SQLException e) {
                        return new HashMap<String, String>() {{
                            put("message", e.getClass().toString() + "\r\n" + e.getMessage());
                        }};
                    }
                    // if all good:
                    return new HashMap<String, String>() {{
                        put("message", "update OK");
                    }};
                } else if (comments.equals("NO"))
                    return new HashMap<String, String>() {{
                        put("message", "this Id does NOT exist");
                    }};
                else
                    return new HashMap<String, String>() {{
                        put("message", comments);
                    }};
            } catch (NumberFormatException e) {
                return new HashMap<String, String>() {{
                    put("message", "Id must be an Integer");
                }};
            }
        } else
            return new HashMap<String, String>() {{
                put("message", "no comments");
            }};
    }

    private String commentRetrieval(int id){
        String comments = "";
        boolean found = false;

        try {
            Connection conn = DriverManager.getConnection(url);

            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM HISTORY");

            while (results.next()) {
                if(results.getInt("Id")==id) {
                    String insertedComments = results.getString("Comments");
                    // NullPointerException will be produced if there are no already inserted comments
                    /*
                    if ( insertedComments != null || !insertedComments.isEmpty() )
                        comments += insertedComments;
                    else
                        comments += "";
                     */
                    if ( insertedComments == null || insertedComments.isEmpty() )
                        comments += "";
                    else
                        comments += insertedComments;
                    found=true;
                }
            }
            conn.close();
        }catch (SQLException e) {
            return "sqlExceptionError: " + e.getClass().toString() + "\r\n" + e.getMessage();
        }

        if(found) {
            if (comments.isEmpty())
                return "EMPTY";
            return comments;
        }
        return "NO";
    }

    @DeleteMapping("/case/delete/{id}")
    public String delete(@PathVariable String id) {

        try {
            isId = Integer.parseInt(id);

            String responseFromIdCheck = thereisId(isId);

            if (responseFromIdCheck.equals("YES")) {
                try {
                    Connection conn = DriverManager.getConnection(url);
                    PreparedStatement statement = conn.prepareStatement("DELETE FROM " +
                            "HISTORY WHERE Id=?");

                    statement.setInt(1, isId);
                    statement.execute();
                    conn.close();
                } catch (SQLException e) {
                    return e.getClass().toString() + "\r\n" + e.getMessage();
                }
                return "delete OK";
            } else if (responseFromIdCheck.equals("NO"))
                return "This Id does NOT exist";
            else
                return responseFromIdCheck;
        } catch (NumberFormatException e) {
            return "Id must be an Integer";
        }
    }

    private String thereisId(int id){
        try {
            Connection conn = DriverManager.getConnection(url);

            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT Id FROM HISTORY");

            while (results.next()) {
                if(results.getInt("Id")==id) {
                    // if we don't close connection we will stumble upon an SQLiteException Error
                    // [SQLITE_BUSY]  The database file is locked (database is locked)
                    conn.close();
                    return "YES";
                }
            }
            conn.close();
            return "NO";
        }catch (SQLException e) {
            return e.getClass().toString() + "\r\n" + e.getMessage();
        }
    }
}
