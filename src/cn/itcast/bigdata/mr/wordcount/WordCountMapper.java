package cn.itcast.bigdata.mr.wordcount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/*	Java			hadoop
 * Long				LongWritable
 * String			Text
 * Integer			IntWritable
 * 
 */



public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		
		//对maptask 传入map中的文本进行处理
		String line = value.toString();
		String[] words = line.split(" ");
		
		//对每个单词进行map存储 <单词，1>
		for(String word : words) {
			context.write(new Text(word), new IntWritable(1));
		}
	}
}
