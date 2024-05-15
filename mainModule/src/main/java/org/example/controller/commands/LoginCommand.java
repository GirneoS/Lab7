package org.example.controller.commands;

import org.example.controller.ExecutableCommand;

public class LoginCommand implements ExecutableCommand {
    private String type = "login";
    private String userName;
    private String password;
    private String[] cmd;
    @Override
    public String execute(String userName, String password) {
        return null;
    }

    @Override
    public boolean validate() {
        return cmd.length==1;
    }

    @Override
    public void setCmd(String[] cmd) {
        this.cmd = cmd;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }


}
