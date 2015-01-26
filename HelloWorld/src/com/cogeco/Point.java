package com.cogeco;

public class Point {

	  int x;
	  int y;
	  public Point(int x, int y)
	  { this.x = x; this.y = y; }
	  public String toString()
	  { return "(" + x + "," + y + ")" ; } // sample output: "(2,3)"
	  
}

abstract class Shape
{ 
   private Point o;
   public Point getOrigin() { return o; }
     
   public Shape() { o = new Point(0,0); }
   public Shape(int x, int y) { o = new Point(x,y); }
   public Shape(Point o) { this.o = o; }
   abstract public void draw(); // deliberately unimplemented
}

class Circle extends Shape
{
  private double radius;
  public Circle(double rad) { super(); radius = rad; }
  public Circle(int x, int y, double rad) 
  { 
    super(x,y); 
    radius = rad; 
  }
  public Circle(Point o, double rad) { super(o); radius = rad; }
  public void draw() 
  {
    System.out.println("Circle@" + getOrigin() + ", rad = " + radius);
  }

  static public void main(String argv[])
  {
    Circle circle = new Circle(1.0);
    circle.draw();
    
    
  }
}
// Output: Circle@(0,0), rad = 1.0