package com.iatrikhplhroforia.emf.person;

import com.iatrikhplhroforia.emf.EmfApplication;
import com.iatrikhplhroforia.emf.medCase.PostPutDeleteMedCaseController;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PostPutDeletePersonController {

    private String url = EmfApplication.getUrl();

    private String id;
    private String fName;
    private String lName;
    private String father;
    private String address;

    int isId;   // variable to check if given id in the Requests being made is valid with Int.parseInt(request_sId) method

    @PostMapping("/")
    public String insert(@RequestBody Map<String, String> body) {

        getRequestBodyData(body);

        try {
            isId = Integer.parseInt(id);

            try {
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement statement = conn.prepareStatement("INSERT INTO " +
                        "PERSON (Id,FirstName,LastName,Father,Address) VALUES(?,?,?,?,?)");

                statement.setInt(1, isId);
                statement.setString(2, fName);
                statement.setString(3, lName);
                statement.setString(4, father);
                statement.setString(5, address);

                statement.execute();
                conn.close();
            } catch (SQLException e) {
                if (e.getMessage().contains("constraint violation"))
                    return "This Id already exists";

                return e.getClass().toString() + "\r\n" + e.getMessage();
            }
        } catch (NumberFormatException e) {
            return "Id must be an Integer";
        }
        return "submit OK";
    }

    @PutMapping("/")
    public Map<String, String> update(@RequestBody Map<String, String> body) {

        getRequestBodyData(body);

        try {
            isId = Integer.parseInt(id);

            String responseFromIdCheck= thereIsId(isId);

            if (responseFromIdCheck.equals("YES")) {
                try {
                    Connection conn = DriverManager.getConnection(url);
                    PreparedStatement statement = conn.prepareStatement("UPDATE " +
                            "PERSON SET FirstName=?,LastName=?,Father=?,Address=? WHERE Id=?");

                    statement.setString(1, fName);
                    statement.setString(2, lName);
                    statement.setString(3, father);
                    statement.setString(4, address);
                    statement.setInt(5, isId);

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
            } else if(responseFromIdCheck.equals("NO"))
                return new HashMap<String, String>() {{
                    put("message", "this Id does NOT exist");
                }};
            else
                return new HashMap<String, String>() {{
                    put("message", responseFromIdCheck);
                }};
        }catch (NumberFormatException e) {
            return new HashMap<String, String>() {{
                put("message", "Id must be an Integer");
            }};
        }
    }

    private void getRequestBodyData(Map<String, String> body){
        id = body.get("id");
        fName = body.get("fName");
        lName = body.get("lName");
        father = body.get("father");
        address = body.get("address");
    }

    // TODO: implement consecutive data deletion from different tables
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable String id){

        try {
            isId = Integer.parseInt(id);

            String responseFromIdCheck = thereIsId(isId);

            if (responseFromIdCheck.equals("YES")) {
                try {
                    Connection conn = DriverManager.getConnection(url);

                    PreparedStatement deletePerson = conn.prepareStatement("DELETE FROM " +
                            "PERSON WHERE Id=?");

                    deletePerson.setInt(1, isId);
                    deletePerson.execute();

                    PreparedStatement deletePerson_sCases = conn.prepareStatement("DELETE FROM " +
                            "HISTORY WHERE Id=?");
                    deletePerson_sCases.setInt(1, isId);
                    deletePerson_sCases.execute();

                    conn.close();
                } catch (SQLException e) {
                    return e.getClass().toString() + "\r\n" + e.getMessage();
                }
                return "delete OK";
            }else if(responseFromIdCheck.equals("NO"))
                return "This Id does NOT exist";
            else
                return responseFromIdCheck;
        }catch(NumberFormatException e){
                return "Id must be an Integer";
        }
    }

    private String thereIsId(int id){
        try {
            Connection conn = DriverManager.getConnection(url);

            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT Id FROM PERSON");

            while (results.next()) {
                if(results.getInt("Id")==id)
                    return "YES";
            }
            conn.close();
            return "NO";
        }catch (SQLException e) {
            return e.getClass().toString() + "\r\n" + e.getMessage();
        }
    }
}
