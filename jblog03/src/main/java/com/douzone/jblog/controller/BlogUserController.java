package com.douzone.jblog.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.jblog.security.Auth;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Auth
@Controller
@RequestMapping("/jblog/{id}")
public class BlogUserController {
	
	@Autowired
	public BlogService blogService;

	@RequestMapping({"","/{categoryNo}","/{categoryNo}/{postNo}"})
	public String main(@PathVariable("id") String id,
					   @PathVariable("categoryNo") Optional<Integer> categoryNo,
					   @PathVariable("postNo") Optional<Integer> postNo,
					   Model model) {
		List<PostVo> postList = null;
		PostVo postVo = null;
		
		//	카테고리 가져오기 
		List<CategoryVo> categoryList =blogService.getCategory(id);
		model.addAttribute("categoryList",categoryList);
		
		// 선택한 카테고리의 글목록 가져오기 
		if(categoryNo.isPresent()) {
			postList =blogService.getPost(categoryNo.get());
		}else {
			// 첫번째 카테고리의 글목록 가져오기 
			postList = blogService.getPost(categoryList.get(0).getNo());
		}
		
		// 최근글 가져오기 
		if(postList.size() > 0) {
			postVo = blogService.getPostView(postList.get(0).getNo());
		}
	
		// 선택한 글 가져오기  
		if(postNo.isPresent()) {
			postVo = blogService.getPostView(postNo.get());
		}
		
		model.addAttribute("postList",postList);
		model.addAttribute("postVo",postVo);

		return "/blog/main";
	}

}
