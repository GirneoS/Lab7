package org.example.server;

import org.example.controller.commands.SaveCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class ServerInput extends Thread{
    private static HashSet<String> serverCommand = new HashSet<>();
    private static ServerInput consoleInputServer;

    private ServerInput() {
    }

    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                if(serverCommand.contains("save")){
                    SaveCommand saveCommand = new SaveCommand();
                    System.out.println(saveCommand.execute("",""));

                    serverCommand.remove("save");

                }else if(serverCommand.contains("")){
                    serverCommand.remove("");
                    continue;

                }else if(!serverCommand.isEmpty()){
                    System.out.println("\u001B[31m" + "Введена неверная серверная команда!" + "\u001B[0m");
                    serverCommand.clear();
                }else{
                    String input = reader.readLine();
                    serverCommand.add(input);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ServerInput makeConsoleInput(){
        if (consoleInputServer==null){
            consoleInputServer = new ServerInput();
            return consoleInputServer;
        }else{
            return consoleInputServer;
        }
    }
}
