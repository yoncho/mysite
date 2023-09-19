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
//		return sqlSession.selectOne("board.findByNo", boardNo);
//		
//		return count == 1;
		
		BoardVo vo = sqlSession.selectOne("board.findByNo", boardNo);
		return vo;
//		
//		BoardVo result = null;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			conn = getConnection();
//			
//			String selectSql
//				= "select b.no, b.title, b.contents, b.hit, b.reg_date, b.g_no, b.o_no, b.depth, b.user_no, b.state ,u.name from board b, user u where b.no=?";
//			pstmt = conn.prepareStatement(selectSql);
//			pstmt.setInt(1, boardNo);
//			rs =  pstmt.executeQuery();
//			
//			if (rs.next()) {
//				int no = rs.getInt(1);
//				String title = rs.getString(2);
//				String contents = rs.getString(3);
//				int hit = rs.getInt(4);
//				String regDate = rs.getString(5);
//				int gNo = rs.getInt(6);
//				int oNo = rs.getInt(7);
//				int depth = rs.getInt(8);
//				Long userNo = rs.getLong(9);
//				String state = rs.getString(10);
//				String userName = rs.getString(11);
//				
//				result = new BoardVo();			
//				result.setNo(no);
//				result.setTitle(title);
//				result.setContents(contents);
//				result.setHit(hit);
//				result.setRegDate(regDate);
//				result.setGno(gNo);
//				result.setOno(oNo);
//				result.setDepth(depth);
//				result.setUserNo(userNo);
//				result.setState(state);
//				result.setUserName(userName);
//			}
//		} catch(SQLException e) {
//			System.out.println("error:" + e);
//		} finally{
//			try {
//				if(rs != null) {
//					rs.close();
//				}
//				if(pstmt != null) {
//					pstmt.close();
//				}
//				if (conn != null && !conn.isClosed())
//				{
//					conn.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return result;
	}
	
	public int findTotalCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;
		try {
			conn = getConnection();
			
			String selectSql
				= "select count(*) from board";
			pstmt = conn.prepareStatement(selectSql);
			rs =  pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch(SQLException e) {
			System.out.println("error:" + e);
		} finally{
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if (conn != null && !conn.isClosed())
				{
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}


	public int findTotalCountByKeyword(String kwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;
		try {
			conn = getConnection();
			
			String selectSql
				= "select count(*) "
				+ "from board b, (select g_no as s_g_no from board where title like ? and o_no=1) as subq "
				+ "where g_no=subq.s_g_no";
			pstmt = conn.prepareStatement(selectSql);
			pstmt.setString(1, "%"+kwd+"%");
			rs =  pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch(SQLException e) {
			System.out.println("error:" + e);
		} finally{
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if (conn != null && !conn.isClosed())
				{
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public boolean updateHitByNo(int boardNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {
			conn = getConnection();

			String updateQuery = "update board"
								+ " set hit=hit+1"
								+ " where no=?";
			pstmt = conn.prepareStatement(updateQuery);
			
			pstmt.setInt(1, boardNo);
			
			result =  pstmt.executeUpdate() == 1;
		} catch(SQLException e) {
			System.out.println("error:" + e);
		} finally{
			try {
				//5. 자원 정리
				if(pstmt != null) {
					pstmt.close();
				}
				if (conn != null && !conn.isClosed())
				{
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(URL, ID, PW);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return conn;
	}
}
