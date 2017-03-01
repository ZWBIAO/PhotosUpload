package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.domain.PageUtil;
import com.demo.domain.Photos;
import com.demo.repository.PhotosRepository;

@Controller//控制器，可以定义网址
public class PhotosController {
	@Autowired
	private PhotosRepository photosRepository;
	
	@GetMapping("/photos")//同一个网页两种功能
	public String list(Model model,@RequestParam(value="page", required=false, defaultValue="1") int page,
			@RequestParam(value="pagesize", required=false, defaultValue="3") int pagesize){//取出数据库所有照片
		PageUtil pu = photosRepository.finall(page, pagesize);
		model.addAttribute("pu",pu);
		return "photos";
	}
	
}
