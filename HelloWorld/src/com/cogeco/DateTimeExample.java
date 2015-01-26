package com.cogeco;
import java.util.Date; 
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTimeExample {

	public DateTimeExample() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date date = new Date();
		System.out.println(date.toString());
		//print using printf
		System.out.printf("Current Date/Time : %tc",date);
		//Date oldtime;
		
		
		
		//Due date: February 09, 2004
		System.out.printf("%1$s %2$tB %2$td, %2$tY\n", "Due date:", date);
		System.out.printf("%s %tB %<te,%<tY\n","Due Date:",date);
		//print using SimpleDataFormat
		
		SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
		//Current Date: Sun (E day in week) 2004.07.18 at('text') 04:14:09 PM( a AM/PM) PDT
		System.out.println("Current Date: " + ft.format(date));
		
		SimpleDateFormat ft1 = new SimpleDateFormat("yyyy-mm-dd");
		
		//Parse Date String
		String input = args.length == 0? "1818-11-11" : args[0];
		
		try {
		date= ft1.parse(input);
		System.out.println(date);
		} catch (ParseException e){
			System.out.println("Unparsable using" + ft1);
		}
		
		/*
		 * PRINTF Usage
		 * % [flags] [width] [.precision] conversion-character ( square brackets denote optional parameters 
				 * Flags:
		- : left-justify ( default is to right-justify )
		+ : output a plus ( + ) or minus ( - ) sign for a numerical value
		0 : forces numerical values to be zero-padded ( default is blank padding )
		, : comma grouping separator (for numbers > 1000)
		 : space will display a minus sign if the number is negative or a space if it is positive
		Width:
		Specifies the field width for outputting the argument and represents the minimum number of characters to
		be written to the output. Include space for expected commas and a decimal point in the determination of
		the width for numerical values.
		Precision:
		Used to restrict the output depending on the conversion. It specifies the number of digits of precision when
		outputting floating-point values or the length of a substring to extract from a String. Numbers are rounded
		to the specified precision.
		Conversion-Characters:
		d : decimal integer [byte, short, int, long]
		f : floating-point number [float, double]
		c : character Capital C will uppercase the letter
		s : String Capital S will uppercase all the letters in the string
		h : hashcode A hashcode is like an address. This is useful for printing a reference
		n : newline Platform specific newline character- use %n instead of \n for greater compatibility
		 */
		
		float dblTotal = 1000; int intValue=2000; String stringVal="TestString";
		System.out.printf("Total is: $%,.2f%n\n", dblTotal);
		System.out.printf("Total: %-10.2f: \n", dblTotal);
		System.out.printf("% 4d\n", intValue);
		System.out.printf("%20.10s\n", stringVal);
		String s = "Hello World";
		System.out.printf("The String object %s is at hash code %h%n", s, s);
	}

}
