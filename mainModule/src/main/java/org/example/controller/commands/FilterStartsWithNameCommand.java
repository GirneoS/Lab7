package org.example.controller.commands;

import org.example.controller.ExecutableCommand;
import org.example.models.MainCollection;
import org.example.models.basics.Dragon;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.locks.Lock;

public class FilterStartsWithNameCommand implements ExecutableCommand, Serializable {
    private String type = "common";
    private String userName;
    private String password;
    private static final long serialVersionUID = 6L;
    private final Lock readLock = MainCollection.getLock().readLock();
    private String[] cmd;
    @Override
    public String execute(String userName, String password) {
        String subName = cmd[1];
        final String[] answer = {""};

        readLock.lock();
        try {
            Optional<Dragon> dragon = MainCollection.getQueue().stream()
                    .filter(x -> x.getName().startsWith(subName))
                    .findFirst();

            dragon.ifPresentOrElse(
                    v -> {
                        HistoryCommand.UpdateHistory("filter_starts_with_name");
                        answer[0] += dragon.toString();
                    },
                    () -> answer[0] += "\u001B[31m" + "В коллекции нет драконов, у которых имя начинается с указанной подстроки!" + "\u001B[0m");
        }finally {
            readLock.unlock();
        }

        return answer[0];
    }

    @Override
    public boolean validate() {
        if(cmd.length==2)
            return true;
        else {
            System.out.println("\u001B[31m" + "У команды filter_starts_with_name должен быть 1 аргумент!" + "\u001B[0m");
            return false;
        }
    }

    @Override
    public void setCmd(String[] cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "FilterStartsWithNameCommand{" +
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
