package org.example.controller.commands;

import org.example.controller.ExecutableCommand;
import org.example.models.MainCollection;
import org.example.models.basics.Dragon;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

public class ShowCommand implements ExecutableCommand, Serializable {
    private String type = "common";
    private String userName;
    private String password;
    private static final long serialVersionUID = 15L;
    private final Lock readLock = MainCollection.getLock().readLock();
    private String[] cmd;

    /**
     * This method contains the logic for "show" command. Here the program saves the collection in file "SavedCollection".
     *
     * @param userName
     * @param password
     */
    @Override
    public String execute(String userName, String password) {
        List<String> result;

        readLock.lock();
        try {
            result = MainCollection.getQueue().stream()
                    .map(Dragon::toString)
                    .collect(Collectors.toList());
        }finally {
            readLock.unlock();
        }
        HistoryCommand.UpdateHistory("show");
        return String.join("\n",result);
    }

    /**
     * This method validates an arguments for "show" command.
     * @return returns true if user not entered arguments and false if he entered some.
     */
    @Override
    public boolean validate() {
        if(cmd.length==1){
            return true;
        }else{
            System.out.println("\u001B[31m" + "У команды \"show\" нет аргументов!" + "\u001B[0m");
            return false;
        }

    }

    @Override
    public void setCmd(String[] cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "ShowCommand{" +
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
