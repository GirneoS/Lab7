package org.example.controller.commands;

import org.example.controller.ExecutableCommand;

import java.io.Serializable;
import java.util.Arrays;

public class ExitCommand implements ExecutableCommand, Serializable {
    private String type = "common";
    private String userName;
    private String password;
    private static final long serialVersionUID = 4L;
    private String[] cmd;

    /**
     * This method contains logic for "exit" command. Here the program exits from app.
     */
    @Override
    public String execute(String userName, String password) {
        SaveCommand saveCommand = new SaveCommand();
        var savedStatus = saveCommand.execute(userName, password);

        System.out.println(savedStatus);

        return "exit";

    }

    /**
     * This method validates an arguments for "exit" command
     * @return returns true if arguments was entered correctly and false if it was entered incorrectly
     */
    @Override
    public boolean validate() {
        if(cmd.length==1){
            return true;
        }else{
            System.out.println("\u001B[31m" + "У команды exit нет аргументов!" + "\u001B[0m");
            return false;
        }
    }

    @Override
    public void setCmd(String[] cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "ExitCommand{" +
                "cmd=" + Arrays.toString(cmd) +
                '}';
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
