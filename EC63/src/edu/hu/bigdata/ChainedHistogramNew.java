package edu.hu.bigdata; 
import java.io.IOException; //import java.util.Iterator;
import java.lang.InterruptedException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer.Context;
//import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
//import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
//import org.apache.hadoop.mapred.JobClient; //import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
//import org.apache.hadoop.mapred.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.Mapper; //import org.apache.hadoop.mapred.Mapper;
//import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapreduce.Reducer; // import org.apache.hadoop.mapred.Reducer;
//import org.apache.hadoop.mapred.Reporter; //import org.apache.hadoop.mapred.MapReduceBase
//import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

//import edu.hu.bigdata.ChainedHistogramNew.Reduce2;





public class ChainedHistogramNew extends Configured implements Tool {
	public static class MapClass1 extends Mapper<Text, Text, Text, Text> {
		public void map(Text key, Text value, Context context)
				throws IOException, InterruptedException {
			String country = null;
		   String[]  s = value.toString().split(",");
			
			//Generate Country String. If it's US, join US and State
			if (s[3].matches(".US.")) {
				country = s[3].replace("\"", "") + "_"
						+ s[4].replace("\"", "");
			} else {
				country = s[3].replace("\"", "");
			}
			
			
			context.write(new Text(country), key);
			
		}
	}

	
	public static class Reduce1 extends 	Reducer<Text, Text, Text, Text> {
		public void reduce(Text key, Iterable<Text> values,	Context context)
				throws IOException ,InterruptedException{
			//Use List to Capture each Iterable Text (patent)
			List<String> l = new ArrayList<String>();
			for (Text val:values){
				l.add(val.toString());
			
			}
			//Prepare CSV format of value. Replace [] from arraylist.tostring
			String s = l.toString().replaceAll("[\\[\\]\\s+]","");
			context.write(key, new Text(s));
		}
	}
	
	public static class Reduce2 extends Reducer<Text, Text, Text, IntWritable> {
		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			int count = 0;
			for (Text val : values) {
				// Split the Values and get length of string array, number of
				// patents
				String[] s = val.toString().split(",");
				count += s.length;
			}
			context.write(key, new IntWritable(count));
		}
	}
	private void cleanup(Path temp, Configuration conf) throws IOException {
		FileSystem fs = temp.getFileSystem(conf);
		fs.delete(temp, true);
	}
	//First Job to generate "Country patent1,patent2,...."
	private Job createJob1(Configuration conf, Path in, Path out)
			throws IOException {

		conf.set(
				"mapreduce.input.keyvaluelinerecordreader.key.value.separator",
				",");
		//Old API
		// JobConf job = new JobConf(conf, InventorHistNew.class);
		@SuppressWarnings("deprecation")
		Job job = new Job(conf, "Job1");
		job.setJarByClass(ChainedHistogramNew.class);
		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);
		job.setMapperClass(MapClass1.class);
		job.setReducerClass(Reduce1.class);
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		return job;

	}
	
	//Second Job to generate "Country count_of_patents"
	private Job createJob2(Configuration conf, Path in, Path out)
			throws IOException {
		
		//Restore Delimiter for Job1
		conf.unset("mapreduce.input.keyvaluelinerecordreader.key.value.separator");
		@SuppressWarnings("deprecation")
		Job job = new Job(conf, "Job2");
		
		job.setJarByClass(ChainedHistogramNew.class);
		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);
		//Default mapper is used
		//job.setMapperClass(MapClass2.class);
		job.setReducerClass(Reduce2.class);
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setOutputKeyClass(Text.class);
		//job.setOutputValueClass(IntWritable.class);
		job.setOutputValueClass(Text.class);
		return job;

	}
	
	

	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		Path in = new Path(args[0]);
		Path out = new Path(args[1]);
		Path temp = new Path("chain-temp");
		Job job1 = createJob1(conf, in, temp);
		//
		//System.exit(job1.waitForCompletion(true) ? 0 : 1);
		//Exit without continuing on second Job. 
		job1.waitForCompletion(true);
		//Start Second Job
		Job job2 = createJob2(conf, temp, out);
		job2.waitForCompletion(true);
		//Clean temp DIR
		cleanup(temp, conf);
		return 0;
	}
	
	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new ChainedHistogramNew(), args);
		System.exit(res);
	}
}
