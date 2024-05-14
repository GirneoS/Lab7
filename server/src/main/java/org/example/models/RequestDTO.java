package org.example.models;

import org.example.controller.ExecutableCommand;

import java.io.Serializable;

public class RequestDTO implements Serializable {
    private static final long serialVersionUID = 17L;
    private final ExecutableCommand command;
    private final String userName;
    private final String password;

    public RequestDTO(ExecutableCommand command, String userName, String password) {
        this.command = command;
        this.userName = userName;
        this.password = password;
    }

    public ExecutableCommand getCommand() {
        return command;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
