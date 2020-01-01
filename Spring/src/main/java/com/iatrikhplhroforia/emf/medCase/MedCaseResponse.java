package com.iatrikhplhroforia.emf.medCase;

public class MedCaseResponse {
    private String medCase;
    private String date;

    public MedCaseResponse(String medCase, String date) {
        this.medCase = medCase;
        this.date = date;
    }

    public String getMedCase() {
        return medCase;
    }

    public void setMedCase(String medCase) {
        this.medCase = medCase;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
