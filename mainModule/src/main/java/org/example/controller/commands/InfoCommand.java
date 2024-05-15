package org.example.controller.commands;

import org.example.controller.ExecutableCommand;
import org.example.models.MainCollection;

import java.io.Serializable;
import java.util.Arrays;

public class InfoCommand implements ExecutableCommand, Serializable {
    private String type = "common";
    private String userName;
    private String password;
    private static final long serialVersionUID = 9L;
    private String[] cmd;

    /**
     * This method contains the logic for "info" command. Here the program prints type of main collection in app, date and time of initialization and number of dragons in collection.
     *
     * @param userName
     * @param password
     */
    @Override
    public String execute(String userName, String password) {
            HistoryCommand.UpdateHistory("info");
            return "Тип: PriorityQueue\nДата инициализации: " + MainCollection.getInitDate() + "\nКоличество элементов: " + MainCollection.getQueue().size();
    }

    /**
     * This method validates an arguments for "info" command.
     * @return returns true if user not entered arguments and false if he entered some.
     */
    @Override
    public boolean validate() {
        if (cmd.length == 1) {
            return true;
        } else {
            System.out.println("\u001B[31m" + "У команды info нет аргументов!" + "\u001B[0m");
            return false;
        }
    }

    @Override
    public void setCmd(String[] cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "InfoCommand{" +
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
