package org.example.models.basics;

import org.example.models.exceptions.LogicException;
import org.example.models.exceptions.NullFieldException;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;

/**
 * The main object in the app that contained in collection
 */
public class Dragon implements Comparable<Dragon>, Serializable {
    @Serial
    private static final long serialVersionUID = 1;
    private int id = 0; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final Coordinates coordinates; //Поле не может быть null
    private final Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private final long age; //Значение поля должно быть больше 0
    private final Float wingspan; //Значение поля должно быть больше 0, Поле может быть null
    private final boolean speaking;
    private final DragonType type; //Поле может быть null
    private final DragonHead head;






    public Dragon(String name, Coordinates coordinates, long age, Float wingspan, boolean speaking, DragonType type, DragonHead head) {

        this.creationDate = new Date(0L);

        if(name.equals("")) {
            throw new LogicException("Вы должны задать имя дракона в файле!");
        }else {
            this.name = name;
        }
        if(coordinates==null){
            throw new NullFieldException("Вы должны задать координату дракона в файле!");
        }else {
            this.coordinates = coordinates;
        }
        if(age<0){
            throw new LogicException("Возраст должен быть задан в файле положительным числом!");
        }else {
            this.age = age;
        }
        if(wingspan==null)
            this.wingspan=null;
        else{
            if (wingspan < 0) {
                throw new LogicException("Размах крыльев должен быть задан в файле положительным числом!");
            } else {
                this.wingspan = wingspan;
            }
        }
        this.speaking = speaking;
        this.type = type;
        this.head = head;
    }

    public void setId(int id){
        this.id = id;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public boolean getSpeaking() {
        return speaking;
    }

    public DragonHead getHead() {
        return head;
    }

    public int getId() {
        return id;
    }

    public Float getWingspan() {
        return wingspan;
    }

    public String getName() {
        return name;
    }

    public long getAge() {
        return age;
    }

    public DragonType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Дракон "+name+"{age="+age+"; "+"id="+id+"; "+"wingspan="+wingspan+"}";
    }

    @Override
    public int compareTo(Dragon d) {
        int thisX = this.coordinates.getX();
        float thisY = this.coordinates.getY();
        int dX = d.coordinates.getX();
        float dY = d.coordinates.getY();
        if(thisX*thisX+thisY*thisY > dX*dX+dY*dY){
            return 1;
        }else if(thisX*thisX+thisY*thisY == dX*dX+dY*dY){
            return 0;
        }
        return -1;
    }

}
