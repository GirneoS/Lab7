package org.example.controller.commands;

import org.example.controller.ExecutableCommand;
import org.example.models.MainCollection;
import org.example.models.basics.Dragon;

import java.io.Serializable;
import java.nio.charset.MalformedInputException;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.locks.Lock;

public class FilterByWingspanCommand implements ExecutableCommand, Serializable {
    private String type = "common";
    private String userName;
    private String password;
    private static final long serialVersionUID = 5L;
    private final Lock readLock = MainCollection.getLock().readLock();
    private String[] cmd;

    /**
     * This method contains logic for "filter_by_wingspan" command. Here the program filtering elements from PriorityQueue and print them.
     */
    @Override
    public String execute(String userName, String password) {
        float goal = Float.parseFloat(cmd[1]);
        Optional<Dragon> dragon = Optional.empty();
        final String[] answer = {""};

        readLock.lock();
        try {
            dragon = MainCollection.getQueue().stream()
                    .filter(x -> x.getWingspan() == goal)
                    .findFirst();
        }catch(NullPointerException ignored){

        }finally {
            readLock.unlock();
        }


        Optional<Dragon> finalDragon = dragon;
        dragon.ifPresentOrElse(
                v -> {HistoryCommand.UpdateHistory("filter_starts_with_name");
                    answer[0] += finalDragon.toString();},
                () -> answer[0] += "\u001B[31m В коллекции нет драконов, с указанным размахом крыльев! \u001B[0m");

        return answer[0];
    }

    /**
     * This method validates an arguments for "filter_by_wingspan" command
     * @return returns true if arguments was entered correctly and false if it was entered incorrectly
     */
    @Override
    public boolean validate() {
        if(cmd.length==2){
            try{
                Float.parseFloat(cmd[1]);
                return true;
            }catch(NumberFormatException e){
                System.out.println("\u001B[31m" + "Первый аргумент команды может быть задан только дробным числом!" + "\u001B[0m");
                return false;
            }
        }else {
            System.out.println("\u001B[31m" + "У команды filter_by_wingspan должен быть 1 аргумент!" + "\u001B[0m");
            return false;
        }
    }

    @Override
    public void setCmd(String[] cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "FilterByWingspanCommand{" +
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
