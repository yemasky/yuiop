package com.web.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class indexController  {
	

	@RequestMapping("*")
	public String index(HttpServletRequest request) {
		;
		System.out.println(request.getParameterNames().toString());
		// 视图渲染，/WEB-INF/jsps/home.jsp
		return "index";
	}
}
