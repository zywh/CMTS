package edu.hu.bigdata;
import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.KeyValueTextInputFormat;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import edu.hu.bigdata.P5Histogram.MapClass1;
import edu.hu.bigdata.P5Histogram.Reduce1;

public class P5Histogram extends Configured implements Tool {
	public static class MapClass1 extends MapReduceBase implements
			Mapper<Text, Text, Text, Text> {
		public void map(Text key, Text value,
				OutputCollector<Text, Text> output, Reporter reporter)
				throws IOException {
			output.collect(value, key);
		}
	}

	public static class MapClass2 extends MapReduceBase implements
	Mapper<Text, Text, IntWritable, IntWritable> {
private final static IntWritable uno = new IntWritable(1);
private IntWritable citationCount = new IntWritable();

public void map(Text key, Text value,
		OutputCollector<IntWritable, IntWritable> output,
		Reporter reporter) throws IOException {
	citationCount.set(Integer.parseInt(value.toString()));
	output.collect(citationCount, uno);
}
}
	
	public static class Reduce2 extends MapReduceBase implements
	Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {
public void reduce(IntWritable key, Iterator<IntWritable> values,
		OutputCollector<IntWritable, IntWritable> output,
		Reporter reporter) throws IOException {
	int count = 0;
	while (values.hasNext()) {
		count += values.next().get();
	}
	output.collect(key, new IntWritable(count));
}
}
	
	public static class Reduce1 extends MapReduceBase implements
			Reducer<Text, Text, Text, IntWritable> {
		public void reduce(Text key, Iterator<Text> values,
				OutputCollector<Text, IntWritable> output, Reporter reporter)
				throws IOException {
			int counter = 0;
			while (values.hasNext()) {
				counter =+1 ;
				
			}
			output.collect(key, new IntWritable(counter));
		}
	}

	//Copy and Paste from Lecture
		private JobConf createJob1(Configuration conf, Path in, Path out) {
			JobConf job = new JobConf(conf, P5Histogram.class);
			job.setJobName("Job1");
			FileInputFormat.setInputPaths(job, in);
			FileOutputFormat.setOutputPath(job, out);
			job.setMapperClass(MapClass1.class);
			job.setReducerClass(Reduce1.class);
			job.setInputFormat(KeyValueTextInputFormat.class);
			job.setOutputFormat(TextOutputFormat.class);
			job.set("key.value.separator.in.input.line", ",");
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			return job;
		}
		
		private JobConf createJob2(Configuration conf, Path in, Path out) {
			JobConf job = new JobConf(conf, P5Histogram.class);
			job.setJobName("Job2");
			FileInputFormat.setInputPaths(job, in);
			FileOutputFormat.setOutputPath(job, out);
			job.setMapperClass(MapClass2.class);
			job.setReducerClass(Reduce2.class);
			job.setInputFormat(KeyValueTextInputFormat.class);
			job.setOutputFormat(TextOutputFormat.class);
//			job.set("key.value.separator.in.input.line", ",");
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			return job;
		}
		
		private void cleanup(Path temp, Configuration conf) throws IOException {
			FileSystem fs = temp.getFileSystem(conf);
			fs.delete(temp, true);
		}
		
		public int run(String[] args) throws Exception {
			Configuration conf = getConf();
			Path in = new Path(args[0]);
			Path out = new Path(args[1]);
			Path temp = new Path("temp1");
			JobConf job1 = createJob1(conf, in, temp);
			JobClient.runJob(job1);
			JobConf job2 = createJob2(conf, temp, out);
			JobClient.runJob(job2);
			cleanup(temp, conf);
			return 0;
		}

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new P5Histogram(), args);
		System.exit(res);
	}
}
