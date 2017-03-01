package com.demo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;
import com.demo.domain.User;

@Repository//这是数据库程序，别的类可以使用:一个标记为仓库
@Transactional//这个也是注入实体管理器：一个标记为事物
//事务是为解决数据安全操作提出的,事务控制实际上就是控制数据的安全访问。
public class UserRepository {
	@PersistenceContext//注入实体管理器，spring有个叫实体管理器，里面已经有session，系统产生，直接调用即可
	private EntityManager entityManager;//这是由spring jpa提供（实体管理器）
	
	public Session getSession(){//数据库的连接
		
		return entityManager.unwrap(Session.class);//获取一个session unwrap解包： 打开声明<Session>，告诉我一个内，可以返回一个指定的session
	}
	
	public void save(User user){//保存数据库
//		String id = UUID.randomUUID().toString();//加上toString
//		User user = new User(id,"zwb","11111","吱吱吱",23);
		getSession().save(user);//save(user)保存到数据库     控制台显示：  Hibernate: insert into user (account, age, name, passwd, id) values (?, ?, ?, ?, ?)
		//object不是所有都可以用，必须是标记了实体才可以用@Entity
	}
	//授权，返回字符串
	public String auth(String account,String passwd){
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);// 新建查询对象，并且告诉查询是User这个类
		dc.add(Property.forName("account").eq(account));//按字段来比较，与数据库是否相同
		Criteria criteria = dc.getExecutableCriteria(getSession());// public Session getSession将数据库连接传给查询对象，得到criteria对象		
		//在线获取seesion
		List list = criteria.list();
		if(null != list &&list.size()>0){//判断是否有值，没就是账号错误
			User user = (User)list.get(0);//取出第一个用户
			if(null != user && StringUtils.equals(user.getPasswd(), passwd)){//检查密码是否相同
				return "success";
			}else{
				return "passwd_is_error";
			}
			
		}else{
			return "account_is_error";		
		}
	}
}
