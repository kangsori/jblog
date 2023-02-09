package com.douzone.jblog.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.security.Auth;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.FileuploadService;
import com.douzone.jblog.vo.BlogVo;

@Auth(admin=true)
@Controller
@RequestMapping("/jblog/admin")
public class BlogAdminController {
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	public BlogService blogService;
	
	@Autowired
	private FileuploadService fileuploadService;

	
	@RequestMapping(value = "/{id}/blog/update", method = RequestMethod.GET)
	public String blog(@PathVariable("id") String id) {
		BlogVo blogVo = blogService.getBlog(id);
		servletContext.setAttribute("blogVo",blogVo);
		
		return "/blog/admin-basic";
	}
	
	@RequestMapping(value = "/{id}/blog/update", method = RequestMethod.POST)
	public String blog(BlogVo vo, MultipartFile file) {
		String profile = fileuploadService.restore(file);
		if(profile != null) {
			vo.setProfile(profile);
		}
		
		blogService.updateBlog(vo);
		
		//객체를 servletContext에 저장
		servletContext.setAttribute("blogVo",vo);

		return "redirect:/jblog/admin/"+vo.getId()+"/blog/update";
	}
}
