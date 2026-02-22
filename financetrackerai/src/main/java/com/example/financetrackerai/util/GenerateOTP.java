package com.example.financetrackerai.util;
import java.util.*;
public class GenerateOTP {
     public static int generateOTP(){
        Random rnd = new Random();
        int number =  100000 + rnd.nextInt(900000);
        return number;
    }
}
