package org.example.controller.commands;

import org.example.controller.ExecutableCommand;
import org.example.models.DataBaseHandler;
import org.example.models.MainCollection;

import java.io.Serializable;
import java.util.Arrays;

public class ClearCommand implements ExecutableCommand, Serializable {
    private static final long serialVersionUID = 2L;
    private String[] cmd;

    /**
     * This method contains logic for "clear" command. Here the program makes PriorityQueue empty.
     */
    @Override
    public String execute(String userName, String password) {
            int sizeCollection = MainCollection.getQueue().size();
            if(sizeCollection==0){
                return "\u001B[31m" + "Коллекция уже пустая!" + "\u001B[0m";
            }else {
                for (int i = 0; i < sizeCollection; i++) {
                    DataBaseHandler handler = new DataBaseHandler();
                    handler.clearDB();
                    MainCollection.getQueue().remove();
                }
                HistoryCommand.UpdateHistory("clear");
                return "\033[0;34m" + "Очистка коллекции..." + "\u001B[0m";
            }

    }

    /**
     * This method validates an arguments for "clear" command
     * @return returns true if arguments was entered correctly and false if it was entered incorrectly
     */
    @Override
    public boolean validate() {
        if(cmd.length==1){
            return true;
        }else{
            System.out.println("\u001B[31m" + "У команды save не должно быть аргументов!" + "\u001B[0m");
            return false;
        }
    }
    public void setCmd(String[] cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "ClearCommand{" +
                "cmd=" + Arrays.toString(cmd) +
                '}';
    }
}
