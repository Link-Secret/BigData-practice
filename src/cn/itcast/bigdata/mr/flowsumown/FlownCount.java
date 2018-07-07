package cn.itcast.bigdata.mr.flowsumown;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MRBench.Reduce;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FlownCount {

	
//	mapper
	static class FlownCountMapper extends Mapper<LongWritable, Text, Text, FlownBean> {
		
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
//			类型转换
			String line = value.toString();
			String[] fields = line.split("\t");
			
			String phoneNum = fields[1];
			long upFlown = Long.parseLong(fields[fields.length-3]);
			long dFlown = Long.parseLong(fields[fields.length-2]);
			
			context.write(new Text(phoneNum), new FlownBean(upFlown,dFlown));
		}
		
	}
	
	
	static class FlownCountReducer extends Reducer<Text, FlownBean, Text, FlownBean> {
		
		@Override
		protected void reduce(Text key, Iterable<FlownBean> value,
				Context context) throws IOException, InterruptedException {
			long sum_upFlown = 0;
			long sum_dFlown = 0;
			
			for(FlownBean flownBean : value) {
				sum_upFlown += flownBean.getUpFlown();
				sum_dFlown += flownBean.getdFlown();
			}
			context.write(key, new FlownBean(sum_upFlown, sum_dFlown));
		}
		
	}
	
	
//	main方法
	public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration();
		Job job = Job.getInstance();
		
//		jar包地址
		job.setJar("D:/wc/FlownSum/FlownCount1.jar");
		
//		mapper和Reduce类
		job.setMapperClass(FlownCountMapper.class);
		job.setReducerClass(FlownCountReducer.class);
		
//		mapper输出
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(FlownBean.class);
		
//		reducer输出
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FlownBean.class);
		
//		输入输出路径
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		
//		分区reduce,以及指定reduce数量
		job.setPartitionerClass(ProvicePartitioner.class);
		job.setNumReduceTasks(5);
		
//		执行
		boolean res = job.waitForCompletion(true);
		System.exit(res ? 0 : 1);
		
		
		
		
	}
}
