package com.example.feathers.util;

public class ObjectConverter {

    public Byte[] toObjects(byte[] primitive) {
        Byte[] bytes = new Byte[primitive.length];
        int i = 0;
        for (byte b : primitive) bytes[i++] = b;
        return bytes;
    }

    public byte[] toPrimitives(Byte[] objects) {
        byte[] bytes = new byte[objects.length];
        for(int i = 0; i < objects.length; i++){
            bytes[i] = objects[i];
        }
        return bytes;
    }

}
