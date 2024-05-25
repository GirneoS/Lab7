package org.example.controller.commands;

import org.example.controller.ExecutableCommand;
import org.example.models.DataBaseHandler;
import org.example.models.MainCollection;
import org.example.models.basics.Dragon;

import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

public class RemoveByIdCommand implements ExecutableCommand, Serializable {
    private String type = "common";
    private String userName;
    private String password;
    private static final long serialVersionUID = 11L;
    private final Lock writeLock = MainCollection.getLock().writeLock();
    private String[] cmd;

    /**
     * This method adds "remove_by_id" command. Here the program removes an element from main PriorityQueue by its id.
     *
     * @param userName of user who is going to execute this command
     * @param password of user who is going to execute this command
     */
    @Override
    public String execute(String userName, String password) {
        int dragonID = Integer.parseInt(cmd[1]);
        Dragon dragon = MainCollection.getQueue().stream()
                .filter(x -> x.getId() == dragonID)
                .findFirst().orElse(null);

        if(dragon == null)
            return "\u001B[31m" + "Дракона с таким id не существует в коллекции!" + "\u001B[0m";
        else {
            DataBaseHandler handler = new DataBaseHandler();
            handler.connectToDataBase();
            int userID = handler.getUserIdByName(userName);

            if (handler.userOwnerOfDragon(dragon.getId(), userID)){
                handler.deleteDragonById(dragon.getId());
                writeLock.lock();
                try {
                    MainCollection.getQueue().remove(dragon);
                }finally {
                    writeLock.unlock();
                }
                HistoryCommand.UpdateHistory("remove_by_id");
                return "Вы удалили дракона с id = " + dragon.getId();
            }else
                return "Вы не имеете права удалять дракона с id: " + dragon.getId();
        }
    }

    /**
     * This method validates an arguments for "remove_by_id" command
     * @return returns true if arguments was entered correctly and false if it was entered incorrectly
     */
    @Override
    public boolean validate() {
        if(cmd.length==2){
            try{
                Integer.parseInt(cmd[1]);
                return true;
            }catch(NumberFormatException e){
                System.out.println("\u001B[31m" + "id должен быть указан числом!" + "\u001B[0m");
                return false;
            }
        }else{
            System.out.println("\u001B[31m" + "В команде должен remove_by_id быть 1 аргумент!" + "\u001B[0m");
            return false;
        }
    }

    @Override
    public void setCmd(String[] cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "RemoveByIdCommand{" +
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
