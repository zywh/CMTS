package com.cogeco;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;



public class MapExample {

	public MapExample() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
		
		HashMap<String,Object> testhm = new HashMap<String,Object>();
		testhm.put("a", 22222);
		testhm.put("b", "test");
		
		
		HashMap<String, Integer[]> newcounter = new HashMap<String,Integer[]>();
		//HashMap<K,V> hm = new HashMap();
		
		Integer a[] = {2000,3000};
		Integer b[] = {3000,4000};
		newcounter.put("busy1ubr",a);
		newcounter.put("busy2ubr", b);	
		
		String searchkey = "busy1ubr";
		System.out.println(newcounter.get(searchkey)[0]);
		System.out.println(newcounter.get(searchkey)[1]);
		 // create map to store
        Map<String, List<String>> map = new HashMap<String, List<String>>();
 
        // create list one and store values
        List<String> valSetOne = new ArrayList<String>();
        valSetOne.add("Apple");
        valSetOne.add("Aeroplane");
 
        // create list two and store values
        List<String> valSetTwo = new ArrayList<String>();
        valSetTwo.add("Bat");
        valSetTwo.add("Banana");
 
        // create list three and store values
        List<String> valSetThree = new ArrayList<String>();
        valSetThree.add("Cat");
        valSetThree.add("Car");
 
        // put values into map
        map.put("A", valSetOne);
        map.put("B", valSetTwo);
        map.put("C", valSetThree);
 
        // iterate and display values
        System.out.println("Fetching Keys and corresponding [Multiple] Values n");
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();
            System.out.println("Key = " + key);
            System.out.println("Values = " + values + "n");
        }
    }
	
	

}
