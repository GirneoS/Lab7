package org.example.controller.commands;

import org.example.controller.ExecutableCommand;
import org.example.models.MainCollection;

import java.io.Serializable;
import java.util.Arrays;

public class RemoveFirstCommand implements ExecutableCommand, Serializable {
    private static final long serialVersionUID = 12L;
    private String[] cmd;

    /**
     * This method adds "remove_first" command. Here the program removes the first element in the main PriorityQueue.
     * @param command command with arguments from the console.
     */
    @Override
    public String execute() {
            if(MainCollection.getQueue().isEmpty()){
                return "\u001B[31m" + "Нельзя выполнить \"remove_first\", т. к. коллекция пустая!" + "\u001B[0m";
            }else {
                MainCollection.getQueue().remove();
                HistoryCommand.UpdateHistory("remove_first");
                return "";
            }
    }

    /**
     * This method validates an arguments for "remove_first" command.
     * @param command command with arguments from the console
     * @return returns true if user not entered arguments and false if he entered some.
     */
    @Override
    public boolean validate() {
        if(cmd.length==1){
            return true;
        }else{
            System.out.println("\u001B[31m" + "У команды \"remove_first\" нет аргументов!" + "\u001B[0m");
            return false;
        }
    }

    @Override
    public void setCmd(String[] cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "RemoveFirstCommand{" +
                "cmd=" + Arrays.toString(cmd) +
                '}';
    }
}
