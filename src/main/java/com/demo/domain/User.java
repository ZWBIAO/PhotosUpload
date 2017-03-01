package com.demo.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity//Unknown entity,打上实体标签 这个是与数据库有关联，不标记系统不会显示
public class User {
	@Id//表里有主键，所以不能少
	private String id;
	@NotNull//来自java校验框架
	@Size(min=2,max=30)
	private String account;//表单账号不能为空，不然报错，输入字符最小2，最大30
	@NotNull//来自java校验框架
	@Size(min=2,max=30)
	private String passwd;
	private String name;
	@OneToMany//一对多，一个用户发很多个产品。想取得用户所有产品，直接调用用户
	private List<Product> products = new ArrayList<Product>();
	
	
	public List<Product> getProduct() {
		return products;
	}
	public void setProduct(List<Product> product) {
		this.products = product;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
