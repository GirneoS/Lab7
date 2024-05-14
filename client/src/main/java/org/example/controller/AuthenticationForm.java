package org.example.controller;

import java.io.Serializable;

public class AuthenticationForm  implements Serializable {
    private static final long serialVersionUID = 16L;
    private String form;
    private String userName;
    private String password;

    public AuthenticationForm(String form) {
        this.form = form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getForm() {
        return form;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
