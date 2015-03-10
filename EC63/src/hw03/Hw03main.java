package hw03;

import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Hw03main {

	private static Map<Integer, String> linemap = new HashMap<Integer, String>();
	private static Map<String, Integer> outputmap = new HashMap<String, Integer>();

	public static void main(String[] args) {

		if (args.length < 1) {
			System.out.println("Proper Usage is: java filename");
			System.exit(0);
		}

		String inputfile = args[0];
		System.out.println("Input Filename: " + inputfile);

		try {
			map(inputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		reduce();

		// Print Final Output
		for (Map.Entry<String, Integer> entry : outputmap.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			System.out.println("Key:" + key + ",Count:" + value);

		}
	}

	private static void reduce() {

		// Loop through linemap
		for (Map.Entry<Integer, String> entry : linemap.entrySet()) {
			// Integer key = entry.getKey();

			String values = entry.getValue();
			combiner(values);

		}

	}

	private static void combiner(String words) {
		String[] tokens = words.split("\\s+");

		for (String s : tokens) {

			if (outputmap.containsKey(s)) {
				int a = outputmap.get(s);
				outputmap.put(s, a + 1);
			} else {
				outputmap.put(s, 1);

			}
		}
	}

	private static void map(String filename) throws IOException {

		BufferedReader inputStream = null;

		try {
			inputStream = new BufferedReader(new FileReader(filename));

			String line;
			int linenumber = 0;
			System.out
					.println("Print Map Output: Key=line number, value=line string");
			while ((line = inputStream.readLine()) != null) {

				linenumber += 1;
				System.out.println("Key:" + linenumber + ",Value:" + line);
				linemap.put(linenumber, line);

			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}

		}

	}

}
