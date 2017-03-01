package com.demo.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.demo.domain.PageUtil;
import com.demo.domain.Photos;
import com.demo.domain.Product;

@Repository // 这是数据库程序，别的类可以使用:一个标记为仓库
@Transactional // 这个也是注入实体管理器：一个标记为事物
// 事务是为解决数据安全操作提出的,事务控制实际上就是控制数据的安全访问。
public class PhotosRepository {
	@PersistenceContext // 注入实体管理器，spring有个叫实体管理器，里面已经有session，系统产生，直接调用即可
	private EntityManager entityManager;// 这是由spring jpa提供（实体管理器）

	public Session getSession() {// 数据库的连接

		return entityManager.unwrap(Session.class);// 获取一个session unwrap解包：
													// 打开声明<Session>，告诉我一个内，可以返回一个指定的session
	}// 这个是数据库的连接对象

	public void save(Photos photos) {// 添加照片，自動写入数据库
		getSession().save(photos);

	}

	public PageUtil finall(int page, int pagesize) {// 查询所有，另一种写法，返回值是list集合，里面为用户对象
		
		DetachedCriteria dc = DetachedCriteria.forClass(Photos.class);// 新建查询对象，并且告诉查询是Product这个类
		Criteria criteria = dc.getExecutableCriteria(getSession());// public Session getSession将数据库连接传给查询对象，得到criteria对象		
		Long o = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();// rowCount求总数  总行数  uniqueResult唯一结果 投影														
		// 包装，将list，pagecount两个值传出去
		PageUtil<Photos> pu = new PageUtil<Photos>(o, pagesize, page);
		// 38行，是显示数据库的总数 Hibernate里的setProjection（静态类）投影很多功能

		// //java精确运算类
		// BigDecimal b1 = new BigDecimal(o);//总数
		// BigDecimal b2 = new BigDecimal(pagesize);//页面大小
		// double c =
		// b1.divide(b2,2,BigDecimal.ROUND_HALF_UP).doubleValue();//除法
		// //b1除b2，保留两位小数，得到浮点数的值
		//
		// //向（距离）最近的一边舍入，除非两边（的距离）是相等,如果是这样，向上舍入, 1.55保留一位小数结果为1.6
		// //int toatl = o.intValue();//有问题，如果有小数点怎么办
		// //把浮点数值取整
		// int pagecount =(int)Math.ceil(c);//页 = 总数/行数 Java之小数取整
		// 方法执行的是向上取整计算（取得整数）
		criteria.setProjection(null);// 用完关闭投影
		// *****************************************************以上为总页代码
		// 查询这一页所有数据
		List<Photos> list = criteria.setFirstResult(pu.getStat()).setMaxResults(pagesize).list();// 要检索的第一个结果查询，从stat ,最多几条=(page-1)*pagesize;
																							
		// return criteria.list();//然后调用criteria，list就是获取列表

		pu.setList(list);
		// pu.setPagecount(pagecount);

		return pu;// 返回为包， public改为包名PageUtil
		// 如果return语句不够用，可打包，有多少值都可以装进去
	}
}
