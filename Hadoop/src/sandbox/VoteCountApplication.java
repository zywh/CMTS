package sandbox;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
//import org.apache.hadoop.util.Tool;
//import org.apache.hadoop.util.ToolRunner;

//import sandbox.WordCount.IntSumReducer;
//import sandbox.WordCount.TokenizerMapper;


//  Remove Implement: public class VoteCountApplication extends Configured implements Tool {
	public class VoteCountApplication extends Configured {

	// CountMapper
	public  static class VoteCountMapper extends Mapper<Object, Text, Text, IntWritable> {
	
	   static final IntWritable one = new IntWritable(1);
	
	 
	    public void map(Object key, Text value, Context output) throws IOException,
	            InterruptedException {
	
	        //If more than one word is present, split using white space.
	        String[] words = value.toString().split(" ");
	        //Only the first word is the candidate name
	        output.write(new Text(words[0]), one);
	    	
	    }
	}	
		
   // CountReducer
	public static class VoteCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	
	  
	    public void reduce(Text key, Iterable<IntWritable> values, Context output)
	            throws IOException, InterruptedException {
	        int voteCount = 0;
	        for(IntWritable value: values){
	            voteCount+= value.get();
	        }
	        output.write(key, new IntWritable(voteCount));
	    }
	}

    public static void main(String[] args) throws Exception {
//        int res = ToolRunner.run(new Configuration(), new VoteCountApplication(), args);
//        System.exit(res);       
//    }
 
//    @Override
//    public int run(String[] args) throws Exception {
//        if (args.length != 2) {
//            System.out.println("usage: [input] [output]");
//            System.exit(-1);
//        }

     // Configuration conf = new Configuration();
//      Job job = Job.getInstance(conf, "word count");
        Job job = Job.getInstance(new Configuration(),"Vote Count");
           
//      job.setOutputKeyClass(Text.class);
//      job.setOutputValueClass(IntWritable.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

//      job.setMapperClass(TokenizerMapper.class);
//      job.setCombinerClass(IntSumReducer.class);
//      job.setReducerClass(IntSumReducer.class);
        
        job.setMapperClass(VoteCountMapper.class);
        job.setReducerClass(VoteCountReducer.class);

       // job.setInputFormatClass(TextInputFormat.class);
        //job.setOutputFormatClass(TextOutputFormat.class);

//      FileInputFormat.addInputPath(job, new Path(args[0]));
//      FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
//      job.setJarByClass(WordCount.class);
        job.setJarByClass(VoteCountApplication.class);

        //job.submit();
        System.exit(job.waitForCompletion(true) ? 0 : 1);
        //return 0;
        
        
        
    }
}