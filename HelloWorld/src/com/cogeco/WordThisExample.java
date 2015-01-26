package com.cogeco;

class WordThisExample{  
    int id;  
    String name;  
    String city;  
      
    WordThisExample(int id,String name){  
    this.id = id;  
    this.name = name;  
    }  
    WordThisExample(int id,String name,String city){  
    this(id,name);//now no need to initialize id and name  
    this.city=city;  
    }  
    void display(){System.out.println(id+" "+name+" "+city);}  
      
    public static void main(String args[]){  
    WordThisExample e1 = new WordThisExample(111,"karan");  
    WordThisExample e2 = new WordThisExample(222,"Aryan","delhi");  
    e1.display();  
    e2.display();  
   
    
   }  
}  
