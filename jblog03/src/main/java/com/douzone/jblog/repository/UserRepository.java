package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.UserVo;

@Repository
public class UserRepository {
	
	@Autowired
	private SqlSession sqlSession;

	public boolean selectExistsID(String id) {
		return sqlSession.selectOne("user.selectExistsID",id);
	}

	public void insertUser(UserVo vo) {
		sqlSession.insert("user.insertUser",vo);
		
	}

	public UserVo findByEmailAndPassword(UserVo vo) {
		return sqlSession.selectOne("user.findByEmailAndPassword",vo);
	}

}
