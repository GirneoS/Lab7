package org.example.controller.commands;

import org.example.controller.ExecutableCommand;
import org.example.models.DataBaseHandler;
import org.example.models.MainCollection;
import org.example.models.basics.Coordinates;
import org.example.models.basics.Dragon;
import org.example.models.basics.DragonHead;
import org.example.models.basics.DragonType;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Scanner;

public class AddCommand implements ExecutableCommand, Serializable {
    private static final long serialVersionUID = 1L;
    private Dragon dragon;

    private String[] cmd;
    /**
     * This method contains logic for "add" command. Here a new instance of object added to the PriorityQueue.
     *
     */
    @Override
    public String execute(String userName, String password) {
        DataBaseHandler handler = new DataBaseHandler();
        int userID = handler.getUserIdByName(userName);

        if(handler.insertDragon(dragon,userID)) {
            MainCollection.getQueue().add(dragon);
            HistoryCommand.UpdateHistory("add");

            return "\033[0;34m" + "Новый дракон: " + dragon + "\u001B[0m";
        }
        return "\u001B[31m" + "Нового дракона не получилось добавить в базу данных!"+"\u001B[0m";
    }

    /**
     * This method validates an arguments for "add" command
     * @return returns true if arguments was entered correctly and false if it was entered incorrectly
     */
    @Override
    public boolean validate() {
        if(cmd.length==2){
            if(cmd[1].equals("Dragon")) {
                makeDragon();

                return true;
            }else {
                System.out.println("\u001B[31m" + "В коллекцию могут быть добавлены только объекты типа \"Dragon\"!" + "\u001B[0m");
                return false;
            }
        }else{
            System.out.println("\u001B[31m" + "У команды должен быть 1 аргумент!" + "\u001B[0m");
            return false;
        }
    }

    private Dragon makeDragon(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите имя нового дракона: ");
        String nameField = scanner.nextLine();
        while (true) {
            if (nameField.equals("") || nameField.equals(" ")) {
                System.out.println("\u001B[31m" + "Введенное имя не должно быть пустым!" + "\u001B[0m");
                System.out.print("Введите имя нового дракона: ");
                nameField = scanner.nextLine();
            } else {
                break;
            }
        }

        int x;
        while (true) {
            try {
                System.out.print("Введите координату x, на которой находится новый дракон: ");
                x = Integer.parseInt(scanner.nextLine());

                if (x <= -970) {
                    System.out.println("\u001B[31m" + "Введенная координата x должна быть больше -970!" + "\u001B[0m");
                } else {
                    break;
                }

            } catch(NumberFormatException e){
                System.out.println("\u001B[31m" + "Координата должна быть введена числом!" + "\u001B[0m");
            }

        }


        int y;
        while (true) {
            try {
                System.out.print("Введите координату y, на которой находится новый дракон: ");
                y = Integer.parseInt(scanner.nextLine());
                if (y <= -896) {
                    System.out.println("\u001B[31m" + "Введенная координата y должна быть больше -896!" + "\u001B[0m");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31m" + "Координата должна быть введена числом!" + "\u001B[0m");
            }
        }


        long ageField;
        while (true) {
            try {
                System.out.print("Введите возраст нового дракон: ");
                ageField = Long.parseLong(scanner.nextLine());
                if (ageField <= 0) {
                    System.out.println("\u001B[31m" + "Возраст дракона должен быть больше 0!" + "\u001B[0m");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31m" + "Возраст дракона должен быть введен числом!" + "\u001B[0m");
            }
        }


        Float wingspanField;
        while (true) {
            System.out.print("Введите размах крыльев нового дракона: ");
            var val = scanner.nextLine();
            if(!val.equals("")) {
                try {
                    wingspanField = Float.parseFloat(val);
                    if (wingspanField <= 0) {
                        System.out.println("\u001B[31m" + "Размах крыльев дракона должен быть больше 0!" + "\u001B[0m");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\u001B[31m" + "Размах крыльев дракона должен быть введен числом!" + "\u001B[0m");
                }
            }else{
                wingspanField=null;
                break;
            }
        }

        System.out.print("Укажите, может ли новый дракон говорить(true, false): ");
        String validationBool = scanner.nextLine();
        boolean speakingField;
        while (true) {
            if (validationBool.equals("false") || validationBool.equals("true")) {
                speakingField = Boolean.parseBoolean(validationBool);
                break;
            } else {
                System.out.println("\u001B[31m" + "Вы можете указать только \"true\" или \"false\"!" + "\u001B[0m");
                System.out.print("Укажите, может ли новый дракон говорить(true, false): ");
                validationBool = scanner.nextLine();
            }

        }


        DragonType typeField;
        while (true) {
            System.out.print("Введите тип нового дракона" + Arrays.toString(DragonType.values()) + ": ");
            var typeVal = scanner.nextLine();
            if (typeVal.equals("")){
                typeField=null;
                break;
            }else {
                try {
                    typeField = DragonType.valueOf(typeVal);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("\u001B[31m" + "Указанный вами тип дракона не может быть добавлен в коллекцию!" + "\u001B[0m");
                }
            }
        }

        long headSize;
        while(true) {
            try {
                System.out.print("Введите размер головы нового дракона: ");
                headSize = Long.parseLong(scanner.nextLine());
                break;
            }catch(NumberFormatException e){
                System.out.println("\u001B[31m" + "Размер головы должен быть введен числом!" + "\u001B[0m");
            }
        }
        Float headEyesCount;
        while(true){
            try{
                System.out.print("Введите количество глаз у нового дракона: ");
                String eyesCount = scanner.nextLine();
                if(eyesCount.equals("")){
                    headEyesCount = null;
                }else {
                    headEyesCount = Float.parseFloat(eyesCount);
                }
                break;
            }catch(NumberFormatException e){
                System.out.println("\u001B[31m" + "Неправильный формат ввода количества глаз!" + "\u001B[0m");
            }
        }

        float headToothCount;
        while(true) {
            try {
                System.out.print("Введите количество зубов у нового дракона: ");
                headToothCount = Float.parseFloat(scanner.nextLine());
                break;
            }catch(NumberFormatException e){
                System.out.println("\u001B[31m" + "Неправильный формат ввода количества зубов!" + "\u001B[0m");
            }
        }
        dragon = new Dragon(nameField, new Coordinates(x, y), ageField, wingspanField, speakingField, typeField, new DragonHead(headSize, headEyesCount, headToothCount));
        return null;
    }

    public void setCmd(String[] cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "AddCommand{" +
                "cmd=" + Arrays.toString(cmd) +
                '}';
    }
}
