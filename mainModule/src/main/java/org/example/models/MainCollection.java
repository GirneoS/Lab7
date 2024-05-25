package org.example.models;

import org.example.models.basics.Coordinates;
import org.example.models.basics.Dragon;
import org.example.models.basics.DragonHead;
import org.example.models.basics.DragonType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MainCollection {
    private static PriorityQueue<Dragon> appQueue = new PriorityQueue<>();
    private static Date initDate;
    private static ArrayList<Integer> dragonIDlist = new ArrayList();
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    public static void initCollection(){
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
    public static ReadWriteLock getLock(){
        return lock;
    }
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
