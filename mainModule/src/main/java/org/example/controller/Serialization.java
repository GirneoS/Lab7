package org.example.controller;

import java.io.*;

public class Serialization {
    public static byte[] SerializeObject(Object object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeObject(object);
        return baos.toByteArray();
    }

    public static <T> T DeserializeObject(byte[] buff) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(buff);
        ObjectInputStream ois = new ObjectInputStream(bais);

        return (T) ois.readObject();
    }
}
