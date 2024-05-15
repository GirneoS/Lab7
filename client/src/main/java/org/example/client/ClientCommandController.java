package org.example.client;

import org.example.controller.commands.*;
import org.example.controller.ExecutableCommand;
import org.example.controller.Serialization;
import org.example.models.RequestDTO;

import java.io.*;
import java.util.HashMap;

/**
 * This is class for taking the order from console.
 */
public class ClientCommandController {
    /**
     * This map stores a list of command classes and string association for each.
     */
    private static final HashMap<String, ExecutableCommand> commands = new HashMap<>();
    private static final HashMap<String, ExecutableCommand> authenticateCom = new HashMap<>();

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
        authenticateCom.put("login", new LoginCommand());
        authenticateCom.put("register", new RegisterCommand());
    }


    /**
     * This method takes a command with arguments from the console and sends it to the right command class.
     * @param command the entered by user command with arguments.
     */
    public static void parseCommand(String[] command, String userName, String password) throws IOException {

        if(commands.containsKey(command[0])){
            ExecutableCommand executableCommand = commands.get(command[0]);
            executableCommand.setCmd(command);
            executableCommand.setUserName(userName);
            executableCommand.setPassword(password);

            if(executableCommand.validate()) {

                byte[] serializedCommand = Serialization.SerializeObject(executableCommand);
                ClientNetController.SendRequest(serializedCommand);

                ClientNetController.GetResponse();
            }
        }else{
            System.out.println("\u001B[31m" + "Команда "+command[0]+" не найдена!" + "\u001B[0m");
        }
    }

    public static boolean parseAuthentication(String[] command, String userName, String password) throws IOException {
        if(authenticateCom.containsKey(command[0])){
            ExecutableCommand executableCommand = authenticateCom.get(command[0]);
            executableCommand.setCmd(command);
            executableCommand.setUserName(userName);
            executableCommand.setPassword(password);

            if(executableCommand.validate()){
                byte[] bytesOfAuth = Serialization.SerializeObject(executableCommand);


                boolean result =  ClientNetController.Authenticate(bytesOfAuth);
                System.out.println("ok");
                if(result)
                    System.out.println("Вы успешно вошли!");
                else if(executableCommand.getType().equals("register"))
                    System.out.println("Пользователь с таким именем уже существует!");
                else
                    System.out.println("Вы неправильно ввели имя пользователя или пароль!");

                return result;
            }
            System.out.println("not ok");
        }
        System.out.println("Такого варианта аутентификации нет!");
        return false;
    }
}
