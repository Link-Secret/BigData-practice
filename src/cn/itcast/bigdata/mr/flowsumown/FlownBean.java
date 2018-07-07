package cn.itcast.bigdata.mr.flowsumown;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class FlownBean implements Writable{

	private long upFlown;
	private long dFlown;
	private long sumFlown;
	
//	FlownCount中用到FlownBean反射需要无参构造函数
	public FlownBean() {
	
	}
	
	public FlownBean(long upFlown, long dFlown) {
		this.upFlown = upFlown;
		this.dFlown = dFlown;
		sumFlown = upFlown + dFlown;
	}
	
	public long getUpFlown() {
		return upFlown;
	}
	public void setUpFlown(long upFlown) {
		this.upFlown = upFlown;
	}
	public long getdFlown() {
		return dFlown;
	}
	public void setdFlown(long dFlown) {
		this.dFlown = dFlown;
	}
	
	public long getSumFlown() {
		return sumFlown;
	}

	public void setSumFlown(long sumFlown) {
		this.sumFlown = sumFlown;
	}

	@Override
	public String toString() {
		return upFlown + "\t" + dFlown + "\t" + sumFlown;
	}
	
//	序列化----对象保存到文件中
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeLong(upFlown);
		out.writeLong(dFlown);
		out.writeLong(sumFlown);
	}

//	反序列化
	@Override
	public void readFields(DataInput in) throws IOException {
		upFlown = in.readLong();
		dFlown = in.readLong();
		sumFlown = in.readLong();
	}
	
}
