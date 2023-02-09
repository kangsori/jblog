package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.repository.UserRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.UserVo;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BlogRepository blogRepository;
	
	public boolean checkId(String id) {
		return userRepository.selectExistsID(id);
	}
	
	@Transactional
	public void join(UserVo vo) {
		// 유저 등록 
		userRepository.insertUser(vo);
		
		// 기본 블로그 등록
		BlogVo blogVo = new BlogVo();
		blogVo.setId(vo.getId());
		blogRepository.insertBlog(blogVo);
		
		// 기본 카테고리 등록 
		
	}

	public UserVo getUser(UserVo vo) {
		return userRepository.findByEmailAndPassword(vo);
		
	}

}
