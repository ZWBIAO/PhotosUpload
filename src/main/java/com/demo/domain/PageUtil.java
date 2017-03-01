package com.demo.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PageUtil<T> {
	
	private int pagecount = 0;//默认为0 页码总数
	private List<T> list = new ArrayList<T>();//ArrayList构造一个空列表的初始容量10。
	//照片列表
	
	//保存传进来的两个值
	private Long o;
	private int pagesize;
	private int page;
	private int stat;

	public PageUtil(Long o,int pagesize,int page){//构造器，类实例化，自动运行
		           	//允许外面程序传两个值，传进来将值保存到14 15行
		this.o=o;
		this.pagesize=pagesize;
		this.page=page;
	}
	
	public int getPagecount() {//网页photos.html对应
		//java精确运算类
				BigDecimal b1 = new BigDecimal(o);//总数
				BigDecimal b2 = new BigDecimal(pagesize);//页面大小
				//如何传递其他参数，使用构造器
				
				double c = b1.divide(b2,2,BigDecimal.ROUND_HALF_UP).doubleValue();//除法 
				//b1除b2，保留两位小数，得到浮点数的值		
				
				//向（距离）最近的一边舍入，除非两边（的距离）是相等,如果是这样，向上舍入, 1.55保留一位小数结果为1.6
				//int toatl = o.intValue();//有问题，如果有小数点怎么办
				//把浮点数值取整 
				pagecount =(int)Math.ceil(c);//页 = 总数/行数   Java之小数取整 方法执行的是向上取整计算（取得整数）
				//int pagecount重新创建变量，去掉int，直接调用int pagecount = 0
		return pagecount;
	}
	public void setPagecount(int pagecount) {
		this.pagecount = pagecount;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	
	public int getPage() {
		return page;
	}
	//公式
	public int getStat() {//取出去
		stat = (page - 1) * pagesize;
		return stat;
	}



}
