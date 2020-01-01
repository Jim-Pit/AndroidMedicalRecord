package com.space.jimpit.medapplication;

import org.json.JSONArray;
import org.json.JSONObject;

public class Person {

    //private int id;
    private String name;
    private String father;

    public Person() {
    }

    public Person(JSONObject data) {
        try {
            this.name = data.get("name").toString();
            this.father = data.get("father").toString();
        }catch (Exception e){}
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    @Override
    public String toString(){
        return name + ", father: " + father;
    }
}
