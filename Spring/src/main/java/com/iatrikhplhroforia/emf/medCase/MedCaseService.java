package com.iatrikhplhroforia.emf.medCase;

import com.iatrikhplhroforia.emf.EmfApplication;
import com.iatrikhplhroforia.emf.person.Person;
import com.iatrikhplhroforia.emf.person.PersonFormatter;
import com.iatrikhplhroforia.emf.person.PersonResponse;
import com.iatrikhplhroforia.emf.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MedCaseService {

    @Autowired
    PersonFormatter personFormatter;

    @Autowired
    MedCaseFormatter medCaseFormatter;

    private String url = EmfApplication.getUrl();



    public List< Related<PersonResponse, List<MedCaseResponse> > > history() {

        List< Related< PersonResponse, List<MedCaseResponse> > > list = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();

            ResultSet results = statement.executeQuery("SELECT * FROM HISTORY INNER JOIN PERSON ON PERSON.Id=HISTORY.Id ORDER BY PERSON.Id");
            //ResultSet res = statement.executeQuery("SELECT Issue,Date FROM HIST WHERE HIST.Id=1");

            int id = 0;

            PersonResponse person = new PersonResponse();
            List<MedCaseResponse> cases = new ArrayList<>();

            while (results.next()) {
                if(id==0){
                    id = results.getInt("Id");

                    Person who = new Person(
                            results.getInt("Id"),
                            results.getString("FirstName"),
                            results.getString("LastName"),
                            results.getString("Father"),
                            results.getString("Address")
                    );
                    person = personFormatter.formatPerson(who);
                    cases = new ArrayList<>();
                    cases.add(medCaseFormatter.
                            formatMedCase(new MedCase(
                                    results.getInt("Id"),
                                    results.getString("Issue"),
                                    results.getString("Date"),
                                    results.getString("Comments")
                            )   )   );
                }
                else if (id != results.getInt("Id")) {
                    Related< PersonResponse, List<MedCaseResponse> > related = new Related<>(person,cases);
                    list.add(related);

                    id = results.getInt("Id");

                    Person who = new Person(
                            results.getInt("Id"),
                            results.getString("FirstName"),
                            results.getString("LastName"),
                            results.getString("Father"),
                            results.getString("Address")
                    );
                    person = personFormatter.formatPerson(who);
                    cases = new ArrayList<>();
                    cases.add(medCaseFormatter.
                            formatMedCase(new MedCase(
                                    results.getInt("Id"),
                                    results.getString("Issue"),
                                    results.getString("Date"),
                                    results.getString("Comments")
                    )   )   );
                }
                else
                {
                    cases.add(medCaseFormatter.
                            formatMedCase(new MedCase(
                                    results.getInt("Id"),
                                    results.getString("Issue"),
                                    results.getString("Date"),
                                    results.getString("Comments")
                            )   )   );
                }
            }
            // Add last patient with his history
            Related< PersonResponse, List<MedCaseResponse> > related = new Related<>(person,cases);
            list.add(related);

            conn.close();
        }
        catch (SQLException e) {
        }
        return list;
    }


}
