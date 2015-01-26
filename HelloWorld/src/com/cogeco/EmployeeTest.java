package com.cogeco;

public class EmployeeTest {
	

	public static void main(String args[]){
	      /* Create two objects using constructor */
	      Employee empOne = new Employee("James Smith");
	      Employee empTwo = new Employee("Marry",14);
	      //System.out.println(Employee.TESTS);
	      
	    

	      // Invoking methods for each object created
	      empOne.empAge(26);
	      empOne.empDesignation("Senior Software Engineer");
	      empOne.empSalary(1000);
	      empOne.printEmployee();

	      empTwo.empDesignation("Software Engineer");
	      empTwo.empSalary(500);
	      empTwo.printEmployee("Special Notes","Second Arg");
	      System.out.println("HashCode:" + empOne.hashCode());
	     
	      String teststring = "12345678";
	      String s1 = "Alvin  Alexander Talkeetna Alaska"
	      		+ "\nTest Test2"
	      		+ "\nTest4 Test6";
	    
	      
	      String s1a[] = s1.split("\\s+");
	      for (String string : s1a) {
			System.out.println(string);
		}
	      System.out.println(teststring.substring(0,1));
	      System.out.println(teststring.replaceAll("2&3", "222"));
	      //Two dimentional array
	     
	   }
}
