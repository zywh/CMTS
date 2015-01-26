package com.cogeco;
import java.io.*;
import java.util.Scanner;

public class CopyBytes {
    public static void main(String[] args) throws IOException {

    	
    	//Copy Bytes use FileInputStream and FileOutputSteam
   
        FileInputStream in = null;
        FileOutputStream out = null;
        //Enter Source
        System.out.println("Enter Source File: ");
        Scanner input = new Scanner(System.in);
        String sourcefilename = input.nextLine();
        //Enter Destination
        System.out.println("Enter Destination File: ");
        String destfilename = input.nextLine();
        
        
        try {
            in = new FileInputStream(sourcefilename);
            out = new FileOutputStream(destfilename);
            int c;

            while ((c = in.read()) != -1) {
                out.write(c);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

}