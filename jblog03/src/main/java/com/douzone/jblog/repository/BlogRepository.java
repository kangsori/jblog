package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;

@Repository
public class BlogRepository {
	
	@Autowired
	private SqlSession sqlSession;

	public void insertBlog(BlogVo vo) {
	System.out.println(vo);
		sqlSession.insert("blog.insertBlog",vo);
	}

	public BlogVo getBlog(String id) {
		return sqlSession.selectOne("blog.getBlog",id);
	}

	public void updateBlog(BlogVo vo) {
		sqlSession.update("blog.updateBlog",vo);
		
	}

}
