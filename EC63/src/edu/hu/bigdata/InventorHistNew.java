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

//import edu.hu.bigdata.InventorNewUsingList.MapClass2;
//import edu.hu.bigdata.InventorNewUsingList.Reduce2;



public class InventorHistNew extends Configured implements Tool {
	
	

	public static class Reduce extends 	Reducer<Text, Text, Text, IntWritable> {
		public void reduce(Text key, Iterable<Text> values,	Context context)
				throws IOException ,InterruptedException{
			int count = 0;
			for (Text val:values){
				//Split the Values and get length of string array, number of patents
				String [] s = val.toString().split(",");
				count += s.length;
			}
			context.write(key, new IntWritable(count));
		}
	}

	

	
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		
		@SuppressWarnings("deprecation")
		Job job = new Job(conf, "InventorHistNew.class");
		job.setJarByClass(InventorHistNew.class);
		Path in = new Path(args[0]);
		Path out = new Path(args[1]);
		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);
		job.setJobName("InventorHistNew");
		job.setJarByClass(InventorHistNew.class); 
		//job.setMapperClass(MapClass.class); No mapper is required. Count is done in reducer
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
		int res = ToolRunner.run(new Configuration(), new InventorHistNew(), args);
		System.exit(res);
	}
}
