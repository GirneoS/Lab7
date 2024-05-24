package org.example.client;

import org.example.controller.AuthenticationForm;
import org.example.controller.Serialization;

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

                if (ClientCommandController.parseAuthentication(authentication)) {
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
//    public static void setRunningStatus(boolean status){
//        RunningStatus = status;
//    }
//
//    public static void getDataAboutUser(String form) throws IOException {
//        AuthenticationForm authenticationForm = new AuthenticationForm("login");
//        if (form.equals("2")) {
//            authenticationForm.setForm("registration");
//        }
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.print("Введите никнейм: ");
//        String enteredName = scanner.nextLine();
//        System.out.print("Введите пароль: ");
//        String enteredPass = scanner.nextLine();
//
//        authenticationForm.setUserName(enteredName);
//        authenticationForm.setPassword(enteredPass);
//        byte[] bytesOfAuthentication = Serialization.SerializeObject(authenticationForm);
//
//        boolean result = ClientNetController.Authenticate(bytesOfAuthentication);
//        if(result) {
//            userName = enteredName;
//            password = enteredPass;
//            System.out.println("\033[0;34m" + "Вы вошли!" + "\u001B[0m");
//        }else if(form.equals("2"))
//            System.out.println("\u001B[31m" + "Такой пользователь уже существует" + "\u001B[0m");
//        else
//            System.out.println("\u001B[31m" + "Неверное имя пользователя или пароль" + "\u001B[0m");
//    }
}
