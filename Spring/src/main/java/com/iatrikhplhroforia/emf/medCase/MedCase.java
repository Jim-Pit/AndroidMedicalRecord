package com.iatrikhplhroforia.emf.medCase;

public class MedCase {

    private int id;
    private String issue;
    private String date;
    private String details;

    public MedCase(int id, String issue, String date, String details) {
        this.id = id;
        this.issue = issue;
        this.date = date;
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
