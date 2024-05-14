package org.example.models.basics;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class represents a head of Dragon
 */
public class DragonHead implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;
    private long size;
    private Float eyesCount; //Поле может быть null
    private float toothCount;

    public DragonHead(long size, Float eyesCount, float toothCount) {
        this.size = size;
        this.eyesCount = eyesCount;
        this.toothCount = toothCount;
    }

    public long getSize() {
        return size;
    }

    public Float getEyesCount() {
        return eyesCount;
    }

    public float getToothCount() {
        return toothCount;
    }
}
