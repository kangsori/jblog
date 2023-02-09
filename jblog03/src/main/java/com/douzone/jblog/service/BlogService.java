package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Service
public class BlogService {
	
	@Autowired
	public BlogRepository blogRepository;

	public BlogVo getBlog(String id) {
		return blogRepository.getBlog(id);
	}

	public void updateBlog(BlogVo vo) {
		blogRepository.updateBlog(vo);
		
	}
	
	public List<CategoryVo> getCategory(String id) {
		return blogRepository.getCategory(id);
	}

	public void addCategory(CategoryVo vo) {
		blogRepository.insertCategory(vo);
		
	}

	public void deleteCategory(int no) {
		blogRepository.deleteCategory(no);
		
	}
	
	public boolean checkCategory(String id) {
		return blogRepository.selectExistsCategory(id);
	}

	public void addPost(PostVo vo) {
		blogRepository.insertPost(vo);
	}

	public List<PostVo> getPost(Integer no) {
		return blogRepository.getPost(no);
	}

	public PostVo getPostView(Integer no) {
		return blogRepository.getPostView(no);
	}

	
}
