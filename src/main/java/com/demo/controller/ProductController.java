package com.demo.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.domain.PageUtil;
import com.demo.domain.Product;
import com.demo.repository.ProductRepository;



@Controller//控制器，可以定义网址
public class ProductController {
	@Autowired
	private ProductRepository productRepository;
	
	@RequestMapping(value="/products")//产品展示
	public String list(Model model,@RequestParam(value="page", required=false, defaultValue="1") int page,
			@RequestParam(value="pagesize", required=false, defaultValue="3") int pagesize){//取出数据库所有照片)
		
		PageUtil pu = productRepository.finall(page, pagesize);	//如何将list值放在网页上，需要model存放值
		model.addAttribute("pu"/*这个随意写，不能重复*/,pu/* 将结果传到变量list，将这个变量放到网页上*/);//快递员，将属性存放再网页上
		return "products";	
	}	
	
	@RequestMapping(value="/product/input")//产品输入网址
	public String input(){
			
		return "product_input";	
	}	
	@RequestMapping(value="/product_save")
	public String save(@Valid Product product, BindingResult bindingResult){//返回一个对象，不用String
		// Valid（校验）:检查用户表单输入值对不对，BindingResult检查完绑定结果，检测完，正确后传入绑定的结果，保存用户输入值（校验对象。后面校验结果）（只要表明校验，后面需要跟上校验结果 BindingResult bindingResult）
		// 用户提交按钮，检查输入是否正确，正常就把值传入绑定结果
		//需要添加新校验，需要重新添加		
		System.out.println("aaa");
		product.setId(UUID.randomUUID().toString());
		
		productRepository.save(product);
		return "index";
	}
	
}
