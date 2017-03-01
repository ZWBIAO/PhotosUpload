package com.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.demo.domain.Photos;
import com.demo.domain.User;
import com.demo.repository.PhotosRepository;

@Controller
public class FileUploadController {
	@Autowired//自动将仓库对象注入进来 
	private PhotosRepository photosRepository;
	
	@GetMapping("/upload")//同一个网页两种功能
	//判断账号密码是否登陆
	public String input(HttpSession Session){
		String sess_account = (String)Session.getAttribute("user");//将结果放入sess_account
		if(!StringUtils.isEmptyOrWhitespace(sess_account)){//判断用户是否为空
			return "upload";//登陆可上传
		}else{
			return "redirect:/login";//没登陆返回
		}
		
	}
	
	@PostMapping("/upload")//提交方式：-表单
	public String handleFileUpload(@RequestParam("files") List<MultipartFile> files){//上传
		//网页上upload.html <input type="file" name="file" id="fileField"> file要对上	  //接收多个文件列表
		//MultipartFile类型，如何保存在磁盘
		System.out.println("111");
		Path path  = Paths.get("e:/upload/");//文件保存路径
		for(MultipartFile file:files){//将接收多个文件列表做循环，并取得原来文件名和地址
		Path Filename = path.resolve(file.getOriginalFilename());//获取原始文件名：拷贝数据从临时路径到指定路径
		try {
			Files.copy(file.getInputStream(),Filename );//保存---目标 //文件拷贝（静态方法），临时拷贝到指定路径
			               //获取输入流
			Photos photos = new Photos();//创建对象，并调用存储方法
			User user = new User();//正真的是从sesstion去得用户的id
			user.setId("0809d4e5-537d-47e6-8fb3-98e37d40a54c");
			photos.setUser(user);//再将这个用户对象设置给照片对象：图片是谁上传的
			photos.setId(UUID.randomUUID().toString());//下面43、44行只是固定写法，查看谁上传用Session
			photos.setPath("/upload/"+file.getOriginalFilename());//可通过浏览器访问
			photosRepository.save(photos);//财务，需要打包 //对象关系映射   四行：存储到数据库的
		} catch (IOException e) {//io异常 
			// TODO Auto-generated catch block
			e.printStackTrace();//可删除错误
			}
		}
		
		//导入import java.nio.file.Path;这个
		return "ok";
	}
	

}

