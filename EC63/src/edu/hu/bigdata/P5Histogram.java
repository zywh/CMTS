package edu.hu.bigdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.tools.GetConf;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
//import org.apache.hadoop.mapred.lib.TokenCountMapper;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.map.InverseMapper;
import org.apache.hadoop.mapreduce.lib.map.TokenCounterMapper;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.reduce.IntSumReducer;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.util.Tool;

public class P5Histogram extends Configured implements Tool {

	public static class MapClass extends
			Mapper<Text, Text, IntWritable, IntWritable> {
		private IntWritable ibin = new IntWritable();
		private IntWritable out = new IntWritable();

		public void map(Text key, Text value, Context context)
				throws IOException, InterruptedException {
			int a = Integer.parseInt(key.toString());
			int bin = (a - 1) / 20;
			ibin.set(bin);
			out.set(Integer.parseInt(value.toString()));
			context.write(ibin, out);

		}
	}

	public static class ReduceClass extends
			Reducer<IntWritable, IntWritable, Text, Text> {
		private Text word = new Text();

		public void reduce(IntWritable key, Iterable<IntWritable> values,
				Context context) throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {

				sum += val.get();

			}
			// Generate Key String bin box
			int a = key.get();
			int start = a * 20 + 1;
			int stop = a * 20 + 20;
			String outkey = Integer.toString(start) + "-"
					+ Integer.toString(stop);
			word.set(outkey);

			// Translate it into Asterisk
			Double count = 4 * Math.log10(sum + 1);
			// Get Integer of log value for looping to generate *
			int d = count.intValue();

			StringBuilder s = new StringBuilder();
			for (int i = 0; i < d; i++) {
				s.append("*");
			}
			context.write(word, new Text(s.toString()));
		}
	}

	private void cleanup(Path temp, Configuration conf) throws IOException {
		FileSystem fs = temp.getFileSystem(conf);
		fs.delete(temp, true);
	}

	private Job createJob1(Configuration conf, Path in, Path out)
			throws IOException {

		conf.set(
				"mapreduce.input.keyvaluelinerecordreader.key.value.separator",
				",");
		Job job = Job.getInstance(conf, "P5 Histogram");
		job.setJarByClass(P5Histogram.class);
		job.setMapperClass(TokenCounterMapper.class);
		job.setReducerClass(IntSumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);

		return job;

	}

	private Job createJob2(Configuration conf, Path in, Path out)
			throws IOException {

		conf.unset("mapreduce.input.keyvaluelinerecordreader.key.value.separator");
		Job job = Job.getInstance(conf, "P5 Histogram");
		job.setJarByClass(P5Histogram.class);
		job.setMapperClass(TokenCounterMapper.class);
		job.setReducerClass(IntSumReducer.class);
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);

		return job;

	}

	private Job createJob3(Configuration conf, Path in, Path out)
			throws IOException {

		Job job = Job.getInstance(conf, "P5 Histogram");
		job.setJarByClass(P5Histogram.class);
		job.setMapperClass(MapClass.class);
		job.setReducerClass(ReduceClass.class);
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(IntWritable.class);
//		job.setOutputKeyClass(Text.class);
//		job.setOutputValueClass(Text.class);
		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);

		return job;

	}

	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		Path in = new Path(args[0]);
		Path out = new Path(args[1]);
		Path temp = new Path("job1-out");
		Path temp2 = new Path("job2-out");
		// Start First Job
		Job job1 = createJob1(conf, in, temp);
		job1.waitForCompletion(true);
		// Start Second Job
		Job job2 = createJob2(conf, temp, temp2);
		job2.waitForCompletion(true);
		// Start Third Job
		Job job3 = createJob3(conf, temp2, out);
		job3.waitForCompletion(true);
		// Clean temp DIR
		cleanup(temp, conf);
		cleanup(temp2, conf);

		return 0;
	}

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new P5Histogram(), args);
		System.exit(res);
	}
}