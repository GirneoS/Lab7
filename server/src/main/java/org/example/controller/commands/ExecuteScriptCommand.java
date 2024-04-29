package org.example.controller.commands;

import org.example.controller.ExecutableCommand;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ExecuteScriptCommand implements ExecutableCommand, Serializable {
    private static final long serialVersionUID = 3L;
    private ArrayList<String> fileNames = new ArrayList<>();
    private String[] cmd;
    private static final HashMap<String, ExecutableCommand> commands = new HashMap<>();
    static{
        commands.put("help", new HelpCommand());
        commands.put("exit", new ExitCommand());
        commands.put("execute_script", new ExecuteScriptCommand());
        commands.put("show", new ShowCommand());
        commands.put("add", new AddCommand());
        commands.put("remove_first", new RemoveFirstCommand());
        commands.put("remove_head", new RemoveHeadCommand());
        commands.put("filter_starts_with_name", new FilterStartsWithNameCommand());
        commands.put("filter_by_wingspan", new FilterByWingspanCommand());
        commands.put("remove_by_id", new RemoveByIdCommand());
        commands.put("clear", new ClearCommand());
        commands.put("print_descending", new PrintDescendingCommand());
        commands.put("history", new HistoryCommand());
        commands.put("info", new InfoCommand());
    }

    /**
     * This method contains logic for "execute_script" command. Here the program reading commands from file.
     */
    @Override
    public String execute() {
        ArrayList<String> executedCommandResult = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(cmd[1]))){
            var line = reader.readLine();
            while(line!=null){
                if(line.split(" ")[0].equals("execute_script")){
                    if(fileNames.contains(line.split(" ")[1])){
                        return "\u001B[31m" + "В файле не может быть команды \"execute_script\", которая вызывает изначальный файл, т. к. это приведет к рекурсии!" + "\u001B[0m";
                    }
                    fileNames.add(line.split(" ")[1]);
                }

                executedCommandResult.add(invokeCommand(line.split(" ")));
                line = reader.readLine();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        HistoryCommand.UpdateHistory("execute_script");
        return String.join("\n\n",executedCommandResult);
    }

    /**
     * This method validates an arguments for "execute_script" command
     * @return returns true if arguments was entered correctly and false if it was entered incorrectly
     */
    @Override
    public boolean validate() {
        if(cmd.length==2){
            if(cmd[1].startsWith(" ")){
                System.out.println("\u001B[31m" + "Имя файла не может начинаться или заканчиваться с пробела!" + "\u001B[0m");
                return false;
            }
            if(cmd[1].startsWith(".")){
                System.out.println("\u001B[31m" + "Имя файла не может начинаться или заканчиваться с точки!" + "\u001B[0m");
                return false;
            }
            return true;
        }else{
            System.out.println("\u001B[31m" + "У команды execute_script должен быть 1 аргумент(название файла должно быть в одно слово)!" + "\u001B[0m");
            return false;
        }
    }

    public void setCmd(String[] cmd) {
        this.cmd = cmd;
    }

    public String[] getCmd() {
        return cmd;
    }

    @Override
    public String toString() {
        return "ExecuteScriptCommand{" +
                "cmd=" + Arrays.toString(cmd) +
                '}';
    }
    private String invokeCommand(String[] cmd){
        if(commands.containsKey(cmd[0])){
            ExecutableCommand executableCommand = commands.get(cmd[0]);
            executableCommand.setCmd(cmd);

            if(executableCommand.validate()){
                return executableCommand.execute();
            }else{
                return "";
            }
        }else{
            return "\u001B[31m" + "Команда"+cmd[0]+ "не найдена!" + "\u001B[0m";
        }
    }
}
