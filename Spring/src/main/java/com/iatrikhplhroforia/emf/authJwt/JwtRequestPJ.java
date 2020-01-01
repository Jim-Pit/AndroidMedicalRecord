package com.iatrikhplhroforia.emf.authJwt;

import java.io.Serializable;

public class JwtRequestPJ implements Serializable {

    private static final long serialVersionUID = 0x1370cc7fL;

    private String email;
    private String password;

    public JwtRequestPJ() {
    }

    public JwtRequestPJ(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
