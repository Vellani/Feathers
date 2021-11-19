package com.example.feathers.util;

public class ObjectConverter {


    public Byte[] toObjects(byte[] primitive) {
        Byte[] bytes = new Byte[primitive.length];
        int i = 0;
        for (byte b : primitive) bytes[i++] = b;
        return bytes;
    }

    public byte[] toPrimitives(Byte[] oBytes) {
        byte[] bytes = new byte[oBytes.length];
        for(int i = 0; i < oBytes.length; i++){
            bytes[i] = oBytes[i];
        }
        return bytes;
    }


}
