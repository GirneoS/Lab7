package org.example.controller.commands;

import org.example.controller.ExecutableCommand;
import org.example.models.DataBaseHandler;
import org.example.models.MainCollection;
import org.example.models.basics.Dragon;

import java.io.Serializable;
import java.util.Arrays;

public class RemoveFirstCommand implements ExecutableCommand, Serializable {
    private static final long serialVersionUID = 12L;
    private String[] cmd;

    /**
     * This method adds "remove_first" command. Here the program removes the first element in the main PriorityQueue.
     *
     * @param userName
     * @param password
     */
    @Override
    public String execute(String userName, String password) {
        Dragon dragon = MainCollection.getQueue().peek();
        if(dragon != null){
            DataBaseHandler handler = new DataBaseHandler();
            handler.connectToDataBase();

            int userID = handler.getUserIdByName(userName);

            if(handler.userOwnerOfDragon(dragon.getId(),userID)){
                handler.deleteDragonById(dragon.getId());
                MainCollection.getQueue().remove();
                HistoryCommand.UpdateHistory("remove_first");
                return "";
            }else{
                return "\u001B[31m" + "Вы не можете удалить дракона, который вам не принадлежит!" + "\u001B[0m";
            }

        }else {
            return "\u001B[31m" + "Нельзя выполнить \"remove_first\", т. к. коллекция пустая!" + "\u001B[0m";
        }
    }

    /**
     * This method validates an arguments for "remove_first" command.
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
