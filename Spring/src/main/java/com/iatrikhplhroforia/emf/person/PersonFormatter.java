package com.iatrikhplhroforia.emf.person;

import org.springframework.stereotype.Component;

@Component
public class PersonFormatter {

    public PersonResponse formatPerson(Person person){
        return new PersonResponse(
                getFullName(person),
                person.getFather()
        );
    }

    private String getFullName(Person person) {
        return person.getFirstName()+" "+person.getLastName();
    }
}
