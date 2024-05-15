package org.example.models;

import org.example.models.basics.Coordinates;
import org.example.models.basics.Dragon;
import org.example.models.basics.DragonHead;
import org.example.models.basics.DragonType;
import org.example.models.exceptions.LogicException;
import org.example.models.exceptions.NullFieldException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.PriorityQueue;

public class MainCollection {
    private static PriorityQueue<Dragon> appQueue = new PriorityQueue<>();
    private static Date initDate;
    private static ArrayList<Integer> dragonIDlist = new ArrayList();

//    static{
//        File file = new File(System.getenv("INIT_DRAGON"));
//        if (!Files.isReadable(file.toPath())) {
//            System.out.println("\u001B[31m" + "У вас нет доступа к файлу инициализации коллекции!" + "\u001B[0m");
//            System.exit(1);
//        } else {
//            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//                reader.readLine();
//                String line = reader.readLine();
//                while (line != null) {
//                    String[] fields = line.split(",");
//
//
//                    String nameField = fields[0];
//                    if (nameField.equals("")){
//                        throw new LogicException("Введите конкретное имя дракона");
//                    }
//
//                    String Xline = fields[1];
//                    Integer coordinateXField;
//                    if(Xline.equals(""))
//                        throw new NullFieldException("Введите конкретное число в поле x координаты");
//                    else
//                        coordinateXField = Integer.parseInt(Xline);
//
//                    float coordinateYField = Float.parseFloat(fields[2]);
//                    long ageField = Long.parseLong(fields[3]);
//                    if(ageField<=0){
//                        throw new LogicException("Возраст дракона, указанный в файле, должен быть больше 0");
//                    }
//                    String wingLine = fields[4];
//                    Float wingspanField;
//                    if(wingLine.equals("")) {
//                        wingspanField = null;
//                    }else{
//                        if(Float.parseFloat(wingLine)>0) {
//                            wingspanField = Float.parseFloat(wingLine);
//                        }else{
//                            throw new LogicException("Размах крыльев дракона, указанный в файле, должен быть больше 0");
//                        }
//
//                    }
//                    boolean speakingField = Boolean.parseBoolean(fields[5]);
//                    String typeLine = fields[6];
//                    DragonType typeField;
//                    if(typeLine.equals("")){
//                        typeField = null;
//                    }else {
//                        typeField =DragonType.valueOf(typeLine);
//                    }
//                    long headSize = Long.parseLong(fields[7]);
//                    String eyesLine = fields[8];
//                    Float headEyesCount;
//                    if(eyesLine.equals("")){
//                        headEyesCount = null;
//                    }else {
//                        headEyesCount = Float.parseFloat(fields[8]);
//                    }
//                    float headToothCount = Float.parseFloat(fields[9]);
//
//                    appQueue.add(new Dragon(nameField, new Coordinates(coordinateXField, coordinateYField), ageField, wingspanField, speakingField, typeField, new DragonHead(headSize, headEyesCount, headToothCount)));
//
//                    line = reader.readLine();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//        initDate = new Date();
//    }

    public static PriorityQueue<Dragon> getQueue(){
        return appQueue;
    }
    public static Date getInitDate() {
        return initDate;
    }
    public static ArrayList<Integer> getDragonIDlist(){
        return dragonIDlist;
    }
}
