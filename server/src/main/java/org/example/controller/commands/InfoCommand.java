package org.example.controller.commands;

import org.example.controller.ExecutableCommand;
import org.example.models.MainCollection;

import java.io.Serializable;
import java.util.Arrays;

public class InfoCommand implements ExecutableCommand, Serializable {
    private static final long serialVersionUID = 9L;
    private String[] cmd;

    /**
     * This method contains the logic for "info" command. Here the program prints type of main collection in app, date and time of initialization and number of dragons in collection.
     *
     * @param command  command with arguments from the console
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
     * @param command command with arguments from the console
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
}
