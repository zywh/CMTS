package com.cogeco;

public class TestArgs {
	
	 public static void main (String args[] ) {
//		 public static void main (String [] args) {
	// public static void main (String...args) {
	 
		
	
	    
	    PuppyExample myPuppy = new PuppyExample( "tommy" );
	    PuppyExample myPuppy1 = new PuppyExample("Justin");
	    
	    Employee EmpOne = new Employee("David");
	    Employee EmpTwo = new Employee("Victor");
	    
	    EmpTwo.empSalary(2000);
	    EmpTwo.empAge(26);
	    EmpTwo.printEmployee();
	    System.out.println("EmpTwo Age is:" + EmpTwo.age);
	    
	   
	     
		 for (String s: args) {
	            System.out.println(s);
	            int number1 = 0;
	            System.out.printf("%s" + "%d",s,number1);
	            
	           
	           String [] sarray =  s.split("/");
	           for (String m: sarray){
	        	   System.out.println(m);
	           }
	            
	        }
	        
		// usage: ParseCmdLine [-verbose] [-xn] [-output afile] filename
		//
		int i = 0, j;
		String arg;
		char flag;
		boolean vflag = false;
		String outputfile = "";
		// string.length string.startWith
		while (i < args.length && args[i].startsWith("-")) {
			arg = args[i++];

			// use this type of check for "wordy" arguments
			// string.equals
	            
	            if (arg.equals("-verbose")) {
	                System.out.println("verbose mode on");
	                vflag = true;
	            }

	    // use this type of check for arguments that require arguments
	            else if (arg.equals("-output")) {
	                if (i < args.length)
	                    outputfile = args[i++];
	                else
	                    System.err.println("-output requires a filename");
	                if (vflag)
	                    System.out.println("output file = " + outputfile);
	            }

	    // use this type of check for a series of flag arguments
	            else {
	                for (j = 1; j < arg.length(); j++) {
	                    flag = arg.charAt(j);
	                    switch (flag) {
	                    case 'x':
	                        if (vflag) System.out.println("Option x");
	                        break;
	                    case 'n':
	                        if (vflag) System.out.println("Option n");
	                        break;
	                    default:
	                        System.err.println("ParseCmdLine: illegal option " + flag);
	                        break;
	                    }
	                }
	            }
	        }
	        if (i == args.length)
	            System.err.println("Usage: ParseCmdLine [-verbose] [-xn] [-output afile] filename");
	        else
	            System.out.println("Success!");
	    }

	
}

