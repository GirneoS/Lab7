package org.example.controller.commands;

import org.example.controller.ExecutableCommand;

import java.io.*;
import java.util.Arrays;

public class ExitCommand implements ExecutableCommand, Serializable {
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
}
