package com.iatrikhplhroforia.emf.person;

public class Person {

    private int id;
    private String firstName;
    private String lastName;
    private String father;
    private String address;

    public Person(int id, String firstName, String lastName, String father, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.father = father;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
