package com.douzone.jblog.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.jblog.security.Auth;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.BlogVo;

@Auth()
@Controller
@RequestMapping("/jblog")
public class BlogUserController {
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	public BlogService blogService;

	@RequestMapping("/{id}")
	public String main(@PathVariable("id") String id) {
		return "/blog/main";
	}
	
	
}
