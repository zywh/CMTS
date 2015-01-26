package com.cogeco;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class SimpleWordCounter {
    
    public static void main(String[] args) {
//        try {
//            File f = new File("ciaFactBook2008.txt");
//            Scanner sc;
//            sc = new Scanner(f);
//            // sc.useDelimiter("[^a-zA-Z']+");
//            Map<String, Integer> wordCount = new TreeMap<String, Integer>();
//            while(sc.hasNext()) {
//                String word = sc.next();
//                if(!wordCount.containsKey(word))
//                    wordCount.put(word, 1);
//                else
//                    wordCount.put(word, wordCount.get(word) + 1);
//            }
//            
//            // show results
//            for(String word : wordCount.keySet())
//                System.out.println(word + " " + wordCount.get(word));
//            System.out.println(wordCount.size());
//        }
//        catch(IOException e) {
//            System.out.println("Unable to read from file.");
//        }
        
        Map<String, Integer > tm = new TreeMap<String,Integer>();
        
        //Add Key/Value pairs
      
        tm.put("Ed", 47);
        tm.put("Alan", 34);
        tm.put("Sheila", 65);
        tm.put("Becca", 44);

        //Iterate over HashMap
        for(String key:tm.keySet()){
            System.out.println(key  +" :: "+ tm.get(key));
        }
    
    
    
}
}