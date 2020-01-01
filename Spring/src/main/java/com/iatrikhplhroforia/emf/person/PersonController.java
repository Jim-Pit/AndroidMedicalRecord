package com.iatrikhplhroforia.emf.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    PersonService personService;

    @RequestMapping("/persons")
    public String getAllPersonsString(){
        return personService.getPersonsText();
    }

    @RequestMapping("/personsJS")
    public List<PersonResponse> getAllPersonsList(){
        return personService.getPersonsList();
    }

    @RequestMapping("/issues")
    public String getIssues(){
        return personService.issues();
    }
}
