package hw04;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer.Context;

import java.io.BufferedReader;
import java.io.FileReader;

public class StandaloneMap {
	private Text word = new Text();
	private final static IntWritable one = new IntWritable(1);
	
	private static Map<String, List<IntWritable>> combinemap = new HashMap<String, List<IntWritable>>();

	public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {

		StringTokenizer itr = new StringTokenizer(value.toString(), " \";:."); // Make
																				// punctuations
																				// and
																				// space
																				// as
																				// delimiter
		while (itr.hasMoreTokens()) {
			word.set(itr.nextToken());
			if (word.toString().matches("\\w+")) { // Output only if it's word
				System.out.println("Word:" + word + " Value:" + one);
				combine(word.toString(), one);
			}

		}

	}

	public Map<String, List<IntWritable>> combine(String text, IntWritable value) {

		// Check if combinemap key exist then merger
		if (combinemap.containsKey(text)) {
			List<IntWritable> combinelist = combinemap.get(text);
			combinelist.add(value);
			// System.out.println("Combine with exist key:" + text + ",Value:" +
			// combinelist);
			combinemap.put(text, combinelist);
		} else {
			List<IntWritable> combinelist = new ArrayList<IntWritable>();
			combinelist.add(value);
			// System.out.println("Create new combinekey:" + text + ",Value:" +
			// combinelist);
			combinemap.put(text, combinelist);
		}
		
		return combinemap;
	}

	private IntWritable result = new IntWritable();

	public void reduce(Text key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {
		int sum = 0;
		for (IntWritable val : values) {
			sum += val.get();

		}
		result.set(sum);
		if (sum < 1000 && key.toString().matches("[a-zA-Z]+") )
			System.out.println("OutputKey:" + key + " Count=" + sum);
		// context.write(key, result);
	}

	public static void main(String[] args) throws IOException, Exception {

		if (args.length < 1) {
			System.out.println("Proper Usage is: java filename");
			System.exit(0);
		}

		FileReader input = new FileReader(args[0]);
		BufferedReader bufRead = new BufferedReader(input);

		String line;
		int linenumber = 0;
		StandaloneMap testmap = new StandaloneMap();
		while ((line = bufRead.readLine()) != null) {
			linenumber += 1; // Increase line number as KEY
			Text value = new Text(line);
			Context context = null;
			Object x = (int) linenumber;
			testmap.map(x, value, context);

		}

		bufRead.close();

		for (Map.Entry<String, List<IntWritable>> entry : combinemap.entrySet()) {
			String key = entry.getKey();

			Text textkey = new Text(key);
			List<IntWritable> value = entry.getValue();
			Context context = null;

			// System.out.println("Combine:Output Key:" + key.toString() +
			// ",Count:" + value);
			testmap.reduce(textkey, value, context);

		}

	}

}
