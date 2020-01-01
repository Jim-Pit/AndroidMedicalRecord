package com.iatrikhplhroforia.emf.user;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RegisteredUser {
    @Id
    private String uid;
    private String email;

    public RegisteredUser() {
    }

    public RegisteredUser(String uid, String email) {
        this.uid = uid;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }
}
