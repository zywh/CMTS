package edu.hu.bigdata;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
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

public class InventorOld extends Configured implements Tool {
	public static class MapClass extends MapReduceBase implements
			Mapper<Text, Text, Text, Text> {
		public void map(Text key, Text value,
				OutputCollector<Text, Text> output, Reporter reporter)
				throws IOException {
			String country = null;
			String[] s = value.toString().split(",");

			if (s[3].matches(".US.")) {
				country = s[3].replace("\"", "") + "_" + s[4].replace("\"", "");
			} else {
				country = s[3].replace("\"", "");
			}

			output.collect(new Text(country), key);
		}
	}

	public static class Reduce extends MapReduceBase implements
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

	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		JobConf job = new JobConf(conf, InventorOld.class);
		Path in = new Path(args[0]);
		Path out = new Path(args[1]);
		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);
		job.setJobName("InventorOld");
		job.setMapperClass(MapClass.class);
		job.setReducerClass(Reduce.class);
		job.setInputFormat(KeyValueTextInputFormat.class);
		job.setOutputFormat(TextOutputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.set("key.value.separator.in.input.line", ",");
		JobClient.runJob(job);
		return 0;
	}

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new InventorOld(), args);
		System.exit(res);
	}
}
