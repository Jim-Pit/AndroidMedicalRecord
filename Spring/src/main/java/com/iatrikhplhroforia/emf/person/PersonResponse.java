package com.iatrikhplhroforia.emf.person;

public class PersonResponse {

    //private int id;
    private String name;
    private String father;

    public PersonResponse() {
    }

    public PersonResponse(String name, String father) {
        //this.id = id;
        this.name = name;
        this.father = father;
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
}
