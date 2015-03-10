package com.cogeco;

import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.ws.util.StringUtils;


public class ListTest {

	public ListTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		List<String> csv = new ArrayList<String>();
		
		csv.add("1");
		
		csv.add("2");
		csv.add("3");
		String s = csv.toString().replaceAll("[\\[\\]\\s+]", "");
		
		System.out.println(s);
		

	}

}
