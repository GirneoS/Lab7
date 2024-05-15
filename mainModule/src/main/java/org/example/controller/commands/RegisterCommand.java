package org.example.controller.commands;

import org.example.controller.ExecutableCommand;

import java.util.Scanner;

public class RegisterCommand implements ExecutableCommand {
    private String type = "register";
    private String userName;
    private String password;
    private String[] cmd;
    @Override
    public String execute(String userName, String password) {
        return null;
    }

    @Override
    public boolean validate() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите никнейм: ");
        String enteredName = scanner.nextLine();
        System.out.print("Придумайте пароль: ");
        String enteredPass = scanner.nextLine();

        this.userName = enteredName;
        this.password =  enteredPass;
        return cmd.length == 1;
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