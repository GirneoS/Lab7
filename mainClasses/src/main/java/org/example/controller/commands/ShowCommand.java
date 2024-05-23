package org.example.controller.commands;

import org.example.controller.ExecutableCommand;
import org.example.models.MainCollection;
import org.example.models.basics.Dragon;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ShowCommand implements ExecutableCommand, Serializable {
    private static final long serialVersionUID = 15L;
    private String[] cmd;

    /**
     * This method contains the logic for "show" command. Here the program saves the collection in file "SavedCollection".
     *
     * @param command  command with arguments from the console.
     * @param userName
     * @param password
     */
    @Override
    public String execute(String userName, String password) {
        List<String> result = MainCollection.getQueue().stream()
                        .map(Dragon::toString)
                        .collect(Collectors.toList());

        HistoryCommand.UpdateHistory("show");
        return String.join("\n",result);
    }

    /**
     * This method validates an arguments for "show" command.
     * @param command command with arguments from the console
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
}
