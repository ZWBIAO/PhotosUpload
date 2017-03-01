package com.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.domain.User;
import com.demo.repository.UserRepository;

@Controller//控制器，可以定义网址
public class IndexController {
	@Autowired
	private UserRepository userRepository;
	
	
	@RequestMapping(value="/")
	public String view(){
//		User user = new User();//新建用户，new一个对象
//		user.setId(UUID.randomUUID().toString());//id随机
//		user.setAccount("66666");
//		user.setPasswd("123456");
//		user.setName("zhangsan");
//		userRepository.save(user);//保存用户的数据
		
		
		return "index";
		
	}
}
