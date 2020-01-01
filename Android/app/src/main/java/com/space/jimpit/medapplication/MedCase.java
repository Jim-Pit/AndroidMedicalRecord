package com.space.jimpit.medapplication;

import org.json.JSONArray;
import org.json.JSONObject;

public class MedCase {
    private String medCase;
    private String date;

    public MedCase(JSONObject data) {
        try {
            this.medCase = data.get("medCase").toString();
            this.date = data.get("date").toString();
        }catch (Exception e){}
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

    @Override
    public String toString(){
        return medCase + " at " + date ;
    }

}
