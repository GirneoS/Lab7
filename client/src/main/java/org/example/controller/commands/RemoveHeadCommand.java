package org.example.controller.commands;

import org.example.controller.ExecutableCommand;
import org.example.models.DataBaseHandler;
import org.example.models.MainCollection;
import org.example.models.basics.Dragon;

import java.io.Serializable;
import java.util.Arrays;

public class RemoveHeadCommand implements ExecutableCommand, Serializable {
    private static final long serialVersionUID = 13L;
    private String[] cmd;
    /**
     * This method contains the logic for "remove_head" command. Here the program prints first element in the PriorityQueue and delete it.
     */
    @Override
    public String execute(String userName, String password) {
        Dragon dragon = MainCollection.getQueue().peek();
        if(dragon!=null){
            DataBaseHandler handler = new DataBaseHandler();

            int userID = handler.getUserIdByName(userName);

            if(handler.userOwnerOfDragon(dragon.getId(), userID)){
                HistoryCommand.UpdateHistory("remove_head");
                handler.deleteDragonById(dragon.getId());
                return MainCollection.getQueue().poll().toString();
            }else
                return "\u001B[31m" + "Вы не можете удалить дракона, который вам не принадлежит!" + "\u001B[0m";

        }else {
            return "\u001B[31m" + "Нельзя выполнить \"remove_head\", т. к. коллекция пустая!" + "\u001B[0m";
        }
    }
    /**
     * This method validates an arguments for "remove_head" command.
     * @return returns true if user not entered arguments and false if he entered some.
     */
    @Override
    public boolean validate() {
        if(cmd.length==1){
            return true;
        }else{
            System.out.println("\u001B[31m" + "У команды нет аргументов!" + "\u001B[0m");
            return false;
        }
    }

    @Override
    public void setCmd(String[] cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "RemoveHeadCommand{" +
                "cmd=" + Arrays.toString(cmd) +
                '}';
    }
}
