package com.cogeco;

//import com.cogeco.ClassStructor2.ClassStructor2Inner;

//Class with nested class
class ClassStructor2 {
	// private String s;
	String s = "Default String";

	public ClassStructor2(String s) {
		this.s = s;
	}

	// Inner Class ClassStructor2.ClassStructor2Inner
	public static class ClassStructor2Inner {

		void printsomething() {
			System.out.println("This is method defined in inner class");
			System.out.println(this);
		}

		public String toString() {
			return String.format("Custom toString:%s", this.getClass());
		}
	}

	public static String getString() {
		ClassStructor2 cs2 = new ClassStructor2("This is passed string");
		return cs2.s;
	}

	// Static Method doesn't need new object created. it's class level method
	public static void PrintSomething() {

		System.out.println(ClassStructor2.getString());
		// System.out.println(this); this can't be used since there is no OBJECT
	}

	// Need new object, object level method
	public void PrintSomething22(String s1) {
		System.out.println(s1);
		System.out.println(this);
	}
}

// Extended Class

class ClassConstructor2Ext extends ClassStructor2 {
	public ClassConstructor2Ext(String s) {
		super(s);

	}

	public void printsomething2() {
		// super();
		System.out.println("This is extend class from ClassStructor2");
	}
}

public class ClassStructor {
	private int i = 0;
	public static int b = 100;

	ClassStructor() {
		// TODO Auto-generated constructor stub
	}

	ClassStructor(int i) {
		// TODO Auto-generated constructor stub
		this.i = i;
		// this.b = i;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClassStructor2 cs2 = new ClassStructor2("Passed String ");
		cs2.PrintSomething22("Hello");
		ClassStructor2.PrintSomething();
		String string2 = ClassStructor2.getString();

		ClassStructor2.ClassStructor2Inner cs2inner = new ClassStructor2.ClassStructor2Inner();
		cs2inner.printsomething();

		ClassStructor cs1 = new ClassStructor(2);

		System.out.println("instance variable: " + cs1.i
				+ ", Static variable b:" + ClassStructor2.getString());

	}

}
