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
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.PriorityQueue;

public class MainCollection {
    private static PriorityQueue<Dragon> appQueue = new PriorityQueue<>();
    private static Date initDate;

    static{

        DataBaseHandler dbHandler = new DataBaseHandler();
        dbHandler.connectToDataBase();

        ResultSet dragons = dbHandler.getDragons();

        int id;
        String name;
        Date creationDate;
        long age;
        Float wingspan;
        boolean speaking;
        DragonType type;

        try{
            while(dragons.next()){
                id = dragons.getInt("id");
                name = dragons.getString("name");
                age = dragons.getLong("age");
                wingspan = dragons.getFloat("wingspan");
                speaking = dragons.getBoolean("speaking");
                type = DragonType.valueOf(dragons.getString("type"));


                ResultSet head_data = dbHandler.getDragonHeadById(dragons.getInt("head_id"));
                head_data.next();
                DragonHead dragon_head = new DragonHead(head_data.getInt("size"),head_data.getFloat("eyes_count"),head_data.getFloat("tooth_count"));


                ResultSet coordinates_data = dbHandler.getCoordinatesRowById(dragons.getInt("coordinates_id"));
                coordinates_data.next();
                Coordinates coordinates = new Coordinates(coordinates_data.getInt("x"),coordinates_data.getFloat("y"));

                Dragon dragon = new Dragon(name, coordinates, age, wingspan,speaking,type,dragon_head);
                dragon.setId(id);

                appQueue.add(dragon);

            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static PriorityQueue<Dragon> getQueue(){
        return appQueue;

    }
    public static Date getInitDate() {
        return initDate;
    }
}
