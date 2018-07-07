package cn.itcast.bigdata.mr.wordcount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,
			Context context) throws IOException, InterruptedException {
		//先将map中传进来的类型转换下
		int count = 0;
		for(IntWritable value : values) {
			count += value.get();
		}
		context.write(key, new IntWritable(count));
	}
}
