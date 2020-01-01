package com.iatrikhplhroforia.emf.person;

import com.iatrikhplhroforia.emf.EmfApplication;
import com.iatrikhplhroforia.emf.person.Person;
import com.iatrikhplhroforia.emf.person.PersonFormatter;
import com.iatrikhplhroforia.emf.person.PersonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    private String url = EmfApplication.getUrl();

    @Autowired
    PersonFormatter formatter;

    public String getPersonsText(){
            StringBuilder builda = new StringBuilder();

            try {
                Connection conn = DriverManager.getConnection(url);
                Statement statement = conn.createStatement();

                //Retrieve persons from db
                ResultSet res = statement.executeQuery("SELECT * FROM PERSON");

                while (res.next()) {
                    builda.append(res.getString("Id"));
                    builda.append(" -> ");
                    builda.append(res.getString("FirstName"));
                    builda.append(" ");
                    builda.append(res.getString("LastName"));
                    builda.append(": ");
                    builda.append(res.getString("Address"));

                    builda.append("<br/>");
                }
                conn.close();
            } catch (SQLException e) {
                builda.append(e.getMessage());
            }
            return builda.toString();
        }

    // Get data in Json form
    public List<PersonResponse> getPersonsList() {
        List<PersonResponse> all = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(url);

            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM PERSON");

            while (results.next()) {
                Person person = new Person(
                        results.getInt("Id"),
                        results.getString("FirstName"),
                        results.getString("LastName"),
                        results.getString("Father"),
                        results.getString("Address")
                        );

                all.add(formatter.formatPerson(person));
            }
            conn.close();
        } catch (SQLException e) {
            all.add(new PersonResponse("Connection Problem","ERROR"));
        }
        return all;
    }

    public String issues() {
        StringBuilder builda = new StringBuilder();

        try {
            Connection conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();

            ResultSet res = statement.executeQuery("SELECT FirstName, LastName, Issue FROM HISTORY INNER JOIN PERSON ON PERSON.Id=HISTORY.Id");
            //ResultSet res = statement.executeQuery("SELECT Issue,Date FROM HIST WHERE HIST.Id=1");

            while (res.next()) {
                builda.append(res.getString("FirstName"));
                builda.append('\t');
                builda.append(res.getString("LastName"));
                builda.append(":\t");
                builda.append(res.getString("Issue"));
                builda.append('\t');
//                builda.append(res.getString("Date"));

                builda.append("<br/>");
            }
            conn.close();
        } catch (SQLException e) {
            builda.append(e.getMessage());
        }
        return builda.toString();
    }

}
