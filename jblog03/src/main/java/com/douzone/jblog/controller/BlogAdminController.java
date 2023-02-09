package com.douzone.jblog.controller;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.security.Auth;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.FileuploadService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Auth(admin=true)
@Controller
@RequestMapping("/jblog/admin/{id}")
public class BlogAdminController {
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private FileuploadService fileuploadService;

	
	@RequestMapping("/blog")
	public String main(@PathVariable("id") String id) {
		BlogVo blogVo = blogService.getBlog(id);
		servletContext.setAttribute("blogVo",blogVo);
		
		return "/blog/admin-basic";
	}
	
	@RequestMapping(value = "/blog/update", method = RequestMethod.POST)
	public String blog(BlogVo vo, MultipartFile file) {
		String profile = fileuploadService.restore(file);
		if(profile != null) {
			vo.setProfile(profile);
		}
		
		blogService.updateBlog(vo);
		
		//객체를 servletContext에 저장
		servletContext.setAttribute("blogVo",vo);

		return "redirect:/jblog/admin/"+vo.getId()+"/blog";
	}
	
	@RequestMapping("/category")
	public String category(@PathVariable("id") String id,Model model) {
		List<CategoryVo> categorylist =blogService.getCategory(id);
		model.addAttribute("categorylist",categorylist);
		
		return "/blog/admin-category";
	}
	
	@RequestMapping(value = "/category/add", method = RequestMethod.POST)
	public String categoryAdd(@PathVariable("id") String id,CategoryVo vo) {
		blogService.addCategory(vo);
		
		return "redirect:/jblog/admin/"+id+"/category";
	}
	
	@RequestMapping("/category/delete/{no}")
	public String categoryDelete(@PathVariable("id") String id, @PathVariable("no") int no,Model model) {
		
		//아이디 체크(중복일경우 로그인페이지 재로드 + 메세지 출력)
		if(!blogService.checkCategory(id)) {
			List<CategoryVo> list =blogService.getCategory(id);
			model.addAttribute("list",list);
			model.addAttribute("categorymessage","카테고리는 최소 한개 이상 등록해야 합니다.");
			return "/blog/admin-category";
		}
		
		blogService.deleteCategory(no);
		
		return "redirect:/jblog/admin/"+id+"/category";
	}
	
	@RequestMapping("/write")
	public String write(@PathVariable("id") String id, Model model) {
		List<CategoryVo> categorylist =blogService.getCategory(id);
		model.addAttribute("categorylist",categorylist);
		
		return "/blog/admin-write";
	}
	
	@RequestMapping(value = "/write/add", method = RequestMethod.POST)
	public String writeAdd(@PathVariable("id") String id,PostVo vo) {
		blogService.addPost(vo);
		
		return "redirect:/jblog/admin/"+id+"/write";
	}
}
