package com.demo.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.util.StringUtils;

import com.demo.domain.User;
import com.demo.repository.UserRepository;

@Controller
public class AuthController {
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/login")
	public String login(User user) {
		return "login";
	}

	@PostMapping("/auth") // 授权
	// SpringMVC存取Session的两种方法 HttpSession httpSession
	// 提供了一种方法来识别用户在多个页面请求或访问一个网站,来存储有关用户的信息。
	public String auth(HttpSession httpSession, @Valid User user, BindingResult bindingResult) {// 返回一个对象，不用String
		// Valid（校验）:检查用户表单输入值对不对，BindingResult检查完绑定结果，检测完，正确后传入绑定的结果，保存用户输入值（校验对象。后面校验结果）（只要表明校验，后面需要跟上校验结果
		// BindingResult bindingResult）
		// 用户提交按钮，检查输入是否正确，正常就把值传入绑定结果
		// 需要添加新校验，需要重新添加)
		if (bindingResult.hasErrors()) {// bindingResult绑定结果为错误
			return "login";// 则返回元页面，重新填写
		}
		String result = userRepository.auth(user.getAccount(), user.getPasswd());// 调用数据库程序，将账号密码，传给auth，做数据库查询
		if (StringUtils.equals(result, "success")) {
			httpSession.setAttribute("user", user.getAccount());// 如果没问题，将user写入session中
		} else {
			return "redirect:/error ";
		}
		return "redirect:/ "; // 重定向
		
		

	}

}
