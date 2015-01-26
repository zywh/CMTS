package com.cogeco;

public class RegexExamples {

	public RegexExamples() {
		// TODO Auto-generated constructor stub
	}
	public boolean isTrue(String s){return s.matches("This");}
	public boolean isThreeLetters(String s){ return s.matches("[a-zA-Z]{3}"); }
	// [^abc] means not a/b/c
	public boolean isNoNumberAtBeginning(String s){	    return s.matches("^[^\\d].*");	  }
	//match a not followed by b
	
//	(?!) - negative lookahead
//	(?=) - positive lookahead
//	(?<=) - positive lookbehind
//	(?<!) - negative lookbehind
//
//	(?>) - atomic group
	
	//(?=.{6,}) = 6 or more,(?=.[a-z]) = min 1 lowercase, (?=.[A-Z]) = min 1 uppercase
	

	public static boolean passwordcheck (String s){ return s.matches("^(?=.*[0-9])(?=.*[a-z]).*"); }
	
	public boolean positivelookahead (String s){ return s.matches("a(?<=b)");}	
	  // returns true if the string contains a arbitrary number of characters except b
	  public boolean isIntersection(String s){
	    return s.matches("([\\w&&[^b]])*");
	  }
	  // returns true if the string contains a number less then 300
	  public boolean isLessThenThreeHundred(String s){
	    return s.matches("[^0-9]*[12]?[0-9]{1,2}[^0-9]*");
	  }

	
	
	public static void main (String args[]){
	
		String pxf = "1/5              l3 131077     0/0      1024   88855527              0/0";
		String filter = "0/0";
		if ( pxf.matches(".*" + filter + ".*")) {
			System.out.println("Match");
		}
		
		String teststring = "This is test string";
	//Split
	
	String stringa[] = teststring.split("\\s+");
	for ( String fstring: stringa){
		System.out.print(fstring + " Length:");
		System.out.println(fstring.length());
	}
	//
	
	String passwordtest = "PasswordTest1";
	System.out.println(passwordtest);
	// boolean result = passwordcheck(passwordtest);
	if (  passwordcheck(passwordtest) ){ System.out.println(passwordtest + "passes the validation"); } 
	else System.out.println(passwordtest + " doesn't pass the validation");
	
	}
}
