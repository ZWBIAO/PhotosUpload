package com.demo.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Photos {
	@Id
	private String id;
	private String path;
	
	@ManyToOne//外键 关联(不要依赖数据库的配置)：寻找另外一张表
    @JoinColumn(name = "userid")//使用哪个字段找，uerid数据库里的，存放user 的Id
	private User user;//这个产品是哪一个用户
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	

}
