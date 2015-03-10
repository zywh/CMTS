package hw03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Hw03p3 {

	private static Map<String, Integer> outputmap = new HashMap<String, Integer>();
	private static Map<String, List<Integer>> combinemap = new HashMap<String, List<Integer>>();

	public static void main(String[] args) throws IOException {

		if (args.length < 1) {
			System.out.println("Proper Usage is: java filename");
			System.exit(0);
		}

		String inputfile = args[0];
		System.out.println("Input Filename: " + inputfile);
		BufferedReader inputStream = null;

		try {
			inputStream = new BufferedReader(new FileReader(inputfile));

			String line;
			int linenumber = 0;
			System.out
					.println("Print Map Output: Key=line number, value=line string");
			// Read File line by line
			while ((line = inputStream.readLine()) != null) {
				linenumber += 1; // Increase line number as KEY
				System.out.println("Key:" + linenumber + ",Value:" + line);
				// map line and string
				Map<String, Integer> intermap = map(linenumber, line);
				combine(intermap);

			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}

		}
		
		reduce(combinemap);
		
		// // Print Final Output
		// for (Map.Entry<String, Integer> entry : outputmap.entrySet()) {
		// String key = entry.getKey();
		// Integer value = entry.getValue();
		// System.out.println("Output Key:" + key + ",Count:" + value);
		//
		// }
	}

	public static Map<String, Integer> reduce(Map<String, List<Integer>> in) {

		for (Map.Entry<String, List<Integer>> entry : in.entrySet()) {
			String key = entry.getKey();
			List<Integer> outlist = entry.getValue();
			int count = 0;
			// loop through list and sum
			for (Integer i : outlist) {
				count = count + i;
			}
			//System.out.println("Word:" + key + ",Counter:" + count);
			outputmap.put(key, count);
		}
		return outputmap;
	}

	public static Map<String, List<Integer>> combine(Map<String, Integer> in) {

		for (Map.Entry<String, Integer> entry : in.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();

			// Check if combinemap key exist then merger
			if (combinemap.containsKey(key)) {
				List<Integer> combinelist = combinemap.get(key);
				combinelist.add(value);
				System.out.println("Combine with exist key:" + key + ",Value:"
						+ value);
				combinemap.put(key, combinelist);
			} else {
				List<Integer> combinelist = new ArrayList<Integer>();
				combinelist.add(value);
				//System.out.println("Create new combinekey:" + key + ",Value:"	+ value);
				combinemap.put(key, combinelist);
			}

		}
		return combinemap;
	}

	public static Map<String, Integer> map(Integer key, String value) {

		// System.out.println("Key:" + key + ",Value:" + value);
		Map<String, Integer> intermap = new HashMap<String, Integer>();

		String[] tokens = value.split("\\s+");

		for (String s : tokens) {

			if (intermap.containsKey(s)) {
				int a = intermap.get(s);
				int b = a + 1;
				intermap.put(s, b);
				//System.out.println("Intermediate Map Key:" + s + ",Value:" + b);
			} else {
				intermap.put(s, 1);
				//System.out.println("Intermediate Map Key:" + s + ",Value:1");

			}
		}

		return intermap;
	}

}
