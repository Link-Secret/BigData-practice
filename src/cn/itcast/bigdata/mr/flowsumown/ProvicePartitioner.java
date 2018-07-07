package cn.itcast.bigdata.mr.flowsumown;

import java.util.HashMap;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class ProvicePartitioner extends Partitioner<Text, FlownBean>{

	public static HashMap<String, Integer> proviceDict = new HashMap<String,Integer>();
	static {
		proviceDict.put("136", 0);
		proviceDict.put("137", 1);
		proviceDict.put("138", 2);
		proviceDict.put("139", 3);

	}
	
	@Override
	public int getPartition(Text key, FlownBean value, int numPartitions) {
		String prefix = key.toString().substring(0, 3);
		Integer proviceId = proviceDict.get(prefix);
		
//		如果hashmap中返回null，则不是这四个省份id中
		return proviceId == null ? 4 : proviceId;
	}

}











