package org.example.models.basics;

import org.example.models.exceptions.OutOfWorldException;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class represents the x-axis and y-axis coordinates
 */
public class Coordinates implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;
    private Integer x; //Значение поля должно быть больше -970, Поле не может быть null
    private float y; //Значение поля должно быть больше -896

    public Coordinates(int x, float y) {
        if(x>-970) {
            this.x = x;
        } else{throw new OutOfWorldException("Координата \"x\" должна быть больше -970, исправьте значение в файле!");}

        if(y>-896) {
            this.y = y;
        }else{throw new OutOfWorldException("Координата \"y\" должна быть больше -896, исправьте значение в файле!");}
    }

    public Integer getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
