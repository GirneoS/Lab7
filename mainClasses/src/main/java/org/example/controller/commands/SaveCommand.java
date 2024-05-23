package org.example.controller.commands;

import org.example.controller.ExecutableCommand;
import org.example.models.MainCollection;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;

public class SaveCommand implements ExecutableCommand, Serializable {
    private static final long serialVersionUID = 14L;
    private String[] cmd;

    /**
     * This method contains the logic for "save" command. Here the program saves the collection in file "SavedCollection".
     */
    @Override
    public String execute(String userName, String password) {
        File file = new File("SavedApp");
        if(Files.isWritable(file.toPath())){
            try(FileWriter writer = new FileWriter(file)) {
                writer.write("name,age,id,wingspan,DragonType"+"\n");

                MainCollection.getQueue().forEach(dragon -> {
                    try {
                        writer.write(dragon.getName() + ","+dragon.getAge()+","+dragon.getId()+","+dragon.getWingspan()+","+dragon.getType() + "\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                return "\033[0;34m" + "Коллекция была сохранена в файл!" + "\u001B[0m";
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }else{
            return "\u001B[31m" + "У вас нет прав для сохранения файла" + "\u001B[0m";
        }
        }
    /**
     * This method validates an arguments for "save" command.
     * @return returns true if user not entered arguments and false if he entered some.
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

    @Override
    public void setCmd(String[] cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "SaveCommand{" +
                "cmd=" + Arrays.toString(cmd) +
                '}';
    }
}
