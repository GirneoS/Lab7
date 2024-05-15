package org.example.controller.commands;

import org.example.controller.ExecutableCommand;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Arrays;

/**
 * This class contains a history of the last 13 commands used
 */
public class HistoryCommand implements ExecutableCommand, Serializable {
    private String type = "common";
    private String userName;
    private String password;
    private static final long serialVersionUID = 8L;
    private String[] cmd;
    private static ArrayDeque<String> history = new ArrayDeque<>();

    /**
     * Method for adding a new command to the list of used commands
     * @param cmd the command just used
     */
    public static void UpdateHistory(String cmd){
        if(history.size()>=13){
            history.removeFirst();
        }else {
            history.addLast(cmd);
        }
    }

    /**
     * Special method that adds command "history" to the list of last 13 used commands
     */
    public static void AddHistoryInHistory(){
        history.addLast("history");
    }

    /**
     * Method that prints all the list of last 13 used commands
     */
    public static String PrintHistory(){
        String str_history = "";
        for (String s : history) {
            if(str_history.length()==0) {
                str_history += s;
            }else{
                str_history += "\n"+s;
            }
        }
        return str_history;
    }

    /**
     * This method adds "history" command to collection that contains all last 13 used commands and prints it.
     *
     * @param command  command with arguments from the console.
     * @param userName
     * @param password
     */
    @Override
    public String execute(String userName, String password) {
            AddHistoryInHistory();
            return PrintHistory();
    }

    /**
     * This method validates an arguments for "history" command.
     * @param command command with arguments from the console
     * @return returns true if user not entered arguments and false if he entered some.
     */
    @Override
    public boolean validate() {
        if(cmd.length==1){
            return true;
        }else {
            System.out.println("\u001B[31m" + "У команды \"history\" нет аргументов!" + "\u001B[0m");
            return false;
        }
    }

    @Override
    public void setCmd(String[] cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "HistoryCommand{" +
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
