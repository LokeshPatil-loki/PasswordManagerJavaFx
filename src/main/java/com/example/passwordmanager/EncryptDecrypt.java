package com.example.passwordmanager;
public class EncryptDecrypt {
    public static String encrypt(String message){
        String result = "";
        byte[] byteArray = message.getBytes();//
        for(int i=0;i<message.length();i++){
            result += (char)(byteArray[i] + (i % 2 == 0 ? 1 : 2));
        }
        return result;
    }
    public static String decrypt(String message){
        String result = "";
        byte[] byteArray = message.getBytes();
        for(int i=0;i<message.length();i++){
            result += (char)(byteArray[i] - (i % 2 == 0 ? 1 : 2));
        }
        return result;
    }
}