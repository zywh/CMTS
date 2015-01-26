package com.cogeco;
import java.util.Scanner;
//import java.io.*;



public class Test1David {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Standard IN and OUT
		int in = input();
		output(in);
		
	}
		
		
	public static int input(){
		
		 Scanner input = new Scanner(System.in);
		 
	    System.out.println("Enter a int:");
	 
	  	int inta = input.nextInt();
	    return inta;
	    	    
	}
	public static void output(int number7){
			
		int number2 = number7 * 7;
		System.out.println("This is multipled by 10: " + number2);
		int d1 = (int) Math.round(89.54);
		
		System.out.println(d1);
		
	}
	

}

	