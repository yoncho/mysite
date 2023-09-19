package com.poscodx.mysite.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	private final String URL = "jdbc:mariadb://192.168.0.181:3307/webdb?charset=utf8";
	private final String ID = "mysite";
	private final String PW = "mysite";
	
	@Autowired
	private SqlSession sqlSession;
	
	public boolean insert(BoardVo vo) {
		System.out.println("vo.gno : " + vo.getGno());
		System.out.println("vo.ono : " + vo.getOno());
		System.out.println("vo.depth : " + vo.getDepth());
		int count = sqlSession.update("board.updateOrder", vo);
		count = sqlSession.insert("board.insert", vo);
		return count == 1;
	}

	public boolean updateStateByNo(int boardNo) {
		int count = sqlSession.update("board.updateStateByNo", boardNo);
		return count == 1;
	}


	public boolean update(BoardVo vo) {
		int count = sqlSession.update("board.update", vo);
		return count == 1;
	}

	public List<BoardVo> findAll() {
		return sqlSession.selectList("board.findAll");
	}
	
	public int findLastNoOfGroup() {
		return sqlSession.selectOne("board.findLastNoOfGroup");
	}

	public List<BoardVo> findByRange(int boardCountPerPage ,int currentPage) {
		Map<String, Integer> map = new HashMap();
		map.put("startNo", (currentPage - 1)*boardCountPerPage);
		map.put("count", boardCountPerPage);
		
		return sqlSession.selectList("board.findByRange", map);
	}
	

	public List<BoardVo> findByRangeAndKeyword(int boardCountPerPage, int currentPage, String keyword) {
		Map<String, Object> map = new HashMap();
		map.put("startNo", (currentPage - 1)*boardCountPerPage);
		map.put("count", boardCountPerPage);
		map.put("keyword", keyword);
		
		return sqlSession.selectList("board.findByRangeAndKeyword", map);
	}
	
	public BoardVo findByNo(int boardNo) {
		BoardVo vo = sqlSession.selectOne("board.findByNo", boardNo);
		return vo;
	}
	
	public int findTotalCount() {
		return sqlSession.selectOne("board.findTotalCount");
	}

	public int findTotalCountByKeyword(String keyword) {
		return sqlSession.selectOne("board.findTotalCountByKeyword", keyword);
	}
	
	public boolean updateHitByNo(int boardNo) {
		int count = sqlSession.update("board.updateHitByNo");
		return count == 1;
	}
}
