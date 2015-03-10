package edu.hu.bigdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

public class ChainedHistogramOld extends Configured implements Tool {
	public static class MapClass1 extends MapReduceBase implements
			Mapper<Text, Text, Text, Text> {
		public void map(Text key, Text value,
				OutputCollector<Text, Text> output, Reporter reporter)
				throws IOException {
			String country = null;
			String[] s = value.toString().split(",");
			//Prepare Country String
			if (s[3].matches(".US.")) {
				country = s[3].replace("\"", "") + "_" + s[4].replace("\"", "");
			} else {
				country = s[3].replace("\"", "");
			}

			output.collect(new Text(country), key);
		}
	}

	public static class Reduce1 extends MapReduceBase implements
			Reducer<Text, Text, Text, Text> {
		public void reduce(Text key, Iterator<Text> values,
				OutputCollector<Text, Text> output, Reporter reporter)
				throws IOException {
			StringBuilder s = new StringBuilder();
			while (values.hasNext()) {
				if (s.length() > 0)
					s.append(",");
				s.append(values.next().toString());
			}
			output.collect(key, new Text(s.toString()));
			
		}
	}

	public static class Reduce2 extends MapReduceBase implements
			Reducer<Text, Text, Text, IntWritable> {
		public void reduce(Text key, Iterator<Text> values,
				OutputCollector<Text, IntWritable> output, Reporter reporter)
				throws IOException {
			int count = 0;
			while (values.hasNext()) {
				// Split the Values and get length of string array = number of patents
				String[] s = values.next().toString().split(",");
				count += s.length;
			}
			output.collect(key, new IntWritable(count));
		}
	}
	//Copy and Paste from Lecture
	private JobConf createJob1(Configuration conf, Path in, Path out) {
		JobConf job = new JobConf(conf, ChainedHistogramOld.class);
		job.setJobName("Generate Country to Patents List");
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

	private void cleanup(Path temp, Configuration conf) throws IOException {
		FileSystem fs = temp.getFileSystem(conf);
		fs.delete(temp, true);
	}
	
	//Copy and Paste from Lecture
	private JobConf createJob2(Configuration conf, Path in, Path out) {
		JobConf job = new JobConf(conf, ChainedHistogramOld.class);
		job.setJobName("job2");
		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);
		// job.setMapperClass(MapClass2.class);
		job.setReducerClass(Reduce2.class);
		job.setInputFormat(KeyValueTextInputFormat.class);
		job.setOutputFormat(TextOutputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		return job;
	}
	
	//Copy and Paste from Lecture
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		Path in = new Path(args[0]);
		Path out = new Path(args[1]);
		Path temp = new Path("chain-temp");
		JobConf job1 = createJob1(conf, in, temp);
		JobClient.runJob(job1);
		JobConf job2 = createJob2(conf, temp, out);
		JobClient.runJob(job2);
		cleanup(temp, conf);
		return 0;
	}

	//Copy and Paste from Lecture
	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(),
				new ChainedHistogramOld(), args);
		System.exit(res);
	}
}
