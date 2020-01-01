package com.iatrikhplhroforia.emf.medCase;

import com.iatrikhplhroforia.emf.person.PersonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MedCaseController {

    @Autowired
    MedCaseService service;

    @GetMapping("/allPatients_History")
    public List< Related<PersonResponse, List<MedCaseResponse> > > getHistoryFromAll(){
        return service.history();
    }

}
