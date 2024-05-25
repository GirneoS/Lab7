package org.example.client;


import java.io.IOException;
import java.util.*;

public class InputClient {
    private static boolean RunningStatus = true;
    private static String userName = null;
    private static String password = null;
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        String[] authentication;
        while(true){
            System.out.print("Вы хотите войти(login) или зарегестрироваться(register): ");
            authentication = scanner.nextLine().split(" ");
            if(authentication[0].equals("login") || authentication[0].equals("register")) {
                System.out.print("Введите никнейм: ");
                String enteredName = scanner.nextLine();
                System.out.print("Введите пароль: ");
                String enteredPass = scanner.nextLine();

                userName = enteredName;
                password =  enteredPass;
                if (ClientCommandController.parseAuthentication(authentication, userName, password)) {
                    break;
                }
            }
        }

        String[] command;
        while(RunningStatus){



            System.out.print(">");
            if(scanner.hasNextLine()) {
                command = scanner.nextLine().split(" ");
                ClientCommandController.parseCommand(command,userName,password);
            }else{
                command = new String[]{"exit"};
                ClientCommandController.parseCommand(command,userName,password);
                break;
            }
        }

    }
}
