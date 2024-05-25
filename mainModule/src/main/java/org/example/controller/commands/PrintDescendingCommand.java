package org.example.controller.commands;

import org.example.controller.ExecutableCommand;
import org.example.models.MainCollection;
import org.example.models.basics.Dragon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class PrintDescendingCommand implements ExecutableCommand, Serializable {
    private String type = "common";
    private String userName;
    private String password;
    private static final long serialVersionUID = 10L;
    private final Lock readLock = MainCollection.getLock().readLock();
    private String[] cmd;

    /**
     * This method adds "print_descending" command. Here the program prints all elements of main PriorityQueue in reverse order.
     *
     * @param userName
     * @param password
     */
    @Override
    public String execute(String userName, String password) {
        List<String> reverseDragons;
        readLock.lock();
        try {
             reverseDragons = MainCollection.getQueue().stream()
                    .map(Dragon::toString)
                    .toList();
        }finally {
            readLock.unlock();
        }
        HistoryCommand.UpdateHistory("print_descending");
        return String.join("\n", reverseDragons);
    }

    /**
     * This method validates an arguments for "print_descending" command.
     * @return returns true if user not entered arguments and false if he entered some.
     */
    @Override
    public boolean validate() {
        if(cmd.length==1)
            return true;
        else{
            System.out.println("\u001B[31m" + "У команды print_descending нет аргументов!" + "\u001B[0m");
            return false;
        }
    }

    @Override
    public void setCmd(String[] cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "PrintDescendingCommand{" +
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
