package edu.hu.bigdata; 
import java.io.IOException; //import java.util.Iterator;
import java.lang.InterruptedException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
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



public class InventorNew extends Configured implements Tool {
	public static class MapClass extends Mapper<Text, Text, Text, Text> {
		public void map(Text key, Text value, Context context)
				throws IOException, InterruptedException {
			String country = null;
		   String[]  s = value.toString().split(",");
			
			
			if (s[3].matches(".US.")) {
				country = s[3].replace("\"", "") + "_"
						+ s[4].replace("\"", "");
			} else {
				country = s[3].replace("\"", "");
			}
			
			
			context.write(new Text(country), key);
			
		}
	}

	public static class Reduce extends 	Reducer<Text, Text, Text, Text> {
		public void reduce(Text key, Iterable<Text> values,	Context context)
				throws IOException ,InterruptedException{
			String csv = "";
			for (Text val:values){
				if (csv.length() > 0)
					csv += ",";
				csv += val.toString();
				
				
			}
			context.write(key, new Text(csv));
		}
	}

	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", ",");
		//JobConf job = new JobConf(conf, InventorNew.class);
		@SuppressWarnings("deprecation")
		Job job = new Job(conf, "InventorNew.class");
		job.setJarByClass(InventorNew.class);
		Path in = new Path(args[0]);
		Path out = new Path(args[1]);
		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);
		job.setJobName("InventorNew");
		job.setJarByClass(InventorNew.class); 
		job.setMapperClass(MapClass.class);
		job.setReducerClass(Reduce.class);
		
		//job.setInputFormat(KeyValueTextInputFormat.class);
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		//job.setOutputFormat(TextOutputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		

		//JobClient.runJob(job);
		System.exit(job.waitForCompletion(true)?0:1);
		return 0;
	}

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new InventorNew(), args);
		System.exit(res);
	}
}
