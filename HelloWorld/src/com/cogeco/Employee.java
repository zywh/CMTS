package com.cogeco;

import java.util.Arrays;

//import java.io.*;
public class Employee{
  
	String name;
	int age;
	String designation;
	double salary;
	String notes;
   
  
   // This is the constructor of the class Employee
	public Employee(String name) {
		this.name = name;
		// TESTS = "THis is test string2";
	}
   //If there are two argument
   public Employee(String name, int age){
	      this(name);
	      this.age = age;
	   }
   
   public Employee(String name, int age, double salary){
	      this(name,age);
	      this.salary = salary;
	      
	   }
   
   // Assign the age of the Employee  to the variable age.
   public void empAge(int empAge){
      age =  empAge;
   
   }
   /* Assign the designation to the variable designation.*/
   public void empDesignation(String empDesig){
      designation = empDesig;
   }
   /* Assign the salary to the variable	salary.*/
   public void empSalary(double empSalary){
      salary = empSalary;
   }
   /* Print the Employee details */
   public void printEmployee(String... noteargs){
    
     
	 // notes = Arrays.toString(noteargs);  
	   notes = Arrays.toString(noteargs);
     // msg += ((notes.length ==0)?defaultName: notes );
	  System.out.println("Name:"+ name );
      System.out.println("Age:" + age );
      System.out.println("Designation:" + designation );
      System.out.println("Salary:" + salary);
      System.out.println(notes);
     
   }
   
}