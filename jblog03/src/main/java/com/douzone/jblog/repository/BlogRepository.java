package com.douzone.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Repository
public class BlogRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<BlogVo> selectBlogList() {
		return sqlSession.selectList("selectBlogList");
	}

	public void insertBlog(BlogVo vo) {
	System.out.println(vo);
		sqlSession.insert("blog.insertBlog",vo);
	}

	public BlogVo getBlog(String id) {
		return sqlSession.selectOne("blog.selectBlog",id);
	}

	public void updateBlog(BlogVo vo) {
		sqlSession.update("blog.updateBlog",vo);
	}
	
	public List<CategoryVo> getCategory(String id) {
		return sqlSession.selectList("blog.selectCategory",id);
	}

	public void insertCategory(CategoryVo vo) {
		sqlSession.insert("blog.insertCategory",vo);
	}

	public void deleteCategory(int no) {
		sqlSession.delete("blog.deleteCategory",no);
	}
	
	public boolean selectExistsCategory(String id) {
		return sqlSession.selectOne("blog.selectExistsCategory",id);
	}

	public void insertPost(PostVo vo) {
		sqlSession.insert("blog.insertPost",vo);
	}

	public List<PostVo> getPost(Integer no) {
		return sqlSession.selectList("blog.selectPost",no);
	}

	public PostVo getPostView(Integer no) {
		return sqlSession.selectOne("blog.selectPostView",no);
	}

}
