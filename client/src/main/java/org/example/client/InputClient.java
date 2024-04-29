package org.example.client;

import java.io.IOException;
import java.util.*;

public class InputClient {
    private static boolean RunningStatus = true;
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        String[] command;
        while(RunningStatus){
            System.out.print(">");
            if(scanner.hasNextLine()) {
                command = scanner.nextLine().split(" ");
                ClientCommandController.parseCommand(command);
            }else{
                command = new String[]{"exit"};
                ClientCommandController.parseCommand(command);
                break;
            }
        }

    }

    public static void setRunningStatus(boolean status){
        RunningStatus = status;
    }
}
