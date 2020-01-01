package com.iatrikhplhroforia.emf.medCase;

import org.springframework.stereotype.Component;

@Component
public class MedCaseFormatter {

    public MedCaseResponse formatMedCase(MedCase medCase){
        return new MedCaseResponse(
                medCase.getIssue(),
                medCase.getDate()
        );
    }
}
