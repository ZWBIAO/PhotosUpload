package com.demo.repository;

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
import com.demo.domain.User;


@Repository//这是数据库程序，别的类可以使用:一个标记为仓库
@Transactional//这个也是注入实体管理器：一个标记为事物，事务是为解决数据安全操作提出的,事务控制实际上就是控制数据的安全访问。
public class ProductRepository {
	@PersistenceContext//注入实体管理器，spring有个叫实体管理器，里面已经有session，系统产生，直接调用即可
	private EntityManager entityManager;//这是由spring jpa提供（实体管理器）
	
	public Session getSession(){//数据库的连接
		
		return entityManager.unwrap(Session.class);//获取一个session unwrap解包： 打开声明<Session>，告诉我一个内，可以返回一个指定的session
	}//这个是数据库的连接对象
	public void save(Product product){//添加
		getSession().save(product);
		}
	public void update(Product product){//更新
		getSession().merge(product);//merge

	}
	public void delete(Product product){
		getSession().delete(product);//基于对象关系映射原理
	}
	public PageUtil<Product> finall(int page, int pagesize){//查询所有，另一种写法，返回值是list集合，里面为用户对象 	
		//DetachedCriteria dc = DetachedCriteria.forClass(Product.class);//新建查询对象，并且告诉查询是Product这个类
		//Criteria criteria = dc.getExecutableCriteria(getSession());//public Session getSession将数据库连接传给查询对象，得到criteria对象
		
		DetachedCriteria dc = DetachedCriteria.forClass(Product.class);// 新建查询对象，并且告诉查询是Product这个类
		Criteria criteria = dc.getExecutableCriteria(getSession());// public Session getSession将数据库连接传给查询对象，得到criteria对象		
		Long o = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();// rowCount求总数  总行数  uniqueResult唯一结果 投影														
		// 包装，将list，pagecount两个值传出去
		PageUtil<Product> pu = new PageUtil<Product>(o, pagesize, page);//泛型类型的引用PageUtil < T >应该是参数化的
		// 38行，是显示数据库的总数 Hibernate里的setProjection（静态类）投影很多功能
		criteria.setProjection(null);// 用完关闭投影
		
		List<Product> list = criteria.setFirstResult(pu.getStat()).setMaxResults(pagesize).list();// 要检索的第一个结果查询，从stat ,最多几条=(page-1)*pagesize;
		pu.setList(list);
		//return criteria.list();//然后调用criteria，list就是获取列表
		return pu;
	}
	
}
