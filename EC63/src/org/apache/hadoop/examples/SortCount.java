package org.apache.hadoop.examples;

import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class SortCount {

	public static class TokenizerMapper extends
			Mapper<Object, Text, IntWritable, Text> {

		// private final static IntWritable oprivate
		Text word = new Text();
		IntWritable count = new IntWritable();

		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {

			String[] words = value.toString().split("\\s+");
			word = new Text(words[0]);
			count = new IntWritable(Integer.parseInt(words[1]));

			context.write(count, word);

		}
	}

		public static class SortReducer extends
				Reducer<IntWritable, Text, IntWritable, Text> {
			private IntWritable result = new IntWritable();

			public void reduce(IntWritable count, Text text, Context context)
					throws IOException, InterruptedException {

				if (count.get() < 1000)
					context.write(count, text);
			}
		}

		public static void main(String[] args) throws Exception {
			Configuration conf = new Configuration();
			String[] otherArgs = new GenericOptionsParser(conf, args)
					.getRemainingArgs();
			System.out.println("Argument Length:" + otherArgs.length);
			if (otherArgs.length != 2) {
				System.err.println("Usage: sortcount <in> <out>");
				System.exit(2);
			}
			Job job = new Job(conf, "sort count");
			job.setJarByClass(SortCount.class);
			job.setMapperClass(TokenizerMapper.class);
			job.setCombinerClass(SortReducer.class);
			job.setReducerClass(SortReducer.class);
			job.setOutputKeyClass(IntWritable.class);
			job.setOutputValueClass(Text.class);
			FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
			FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
			System.exit(job.waitForCompletion(true) ? 0 : 1);
		}
	}

