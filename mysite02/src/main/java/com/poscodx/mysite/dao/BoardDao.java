package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poscodx.mysite.vo.BoardVo;

public class BoardDao {
	private final String URL = "jdbc:mariadb://192.168.0.181:3307/webdb?charset=utf8";
	private final String ID = "mysite";
	private final String PW = "mysite";
	
	public boolean insert(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {
			conn = getConnection();
			
			//1. Update
			String updateQuery 
				= "update board set o_no=o_no+1 where g_no=? and o_no >= ?";
			pstmt = conn.prepareStatement(updateQuery);
			
			pstmt.setInt(1, vo.getGno());
			pstmt.setInt(2, vo.getOno());
			
			pstmt.executeUpdate();
			
			//2. Insert
			String authorSql 
				= "insert into board values (null,?,?,?,current_date(),?,?,?,?,?)";
			pstmt = conn.prepareStatement(authorSql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, vo.getHit());
			pstmt.setInt(4, vo.getGno());
			pstmt.setInt(5, vo.getOno());
			pstmt.setInt(6, vo.getDepth());			
			pstmt.setInt(7, vo.getUserNo());
			pstmt.setString(8, "active");
			
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

	public boolean deleteByNo(int boardNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {
			conn = getConnection();

//			String deleteSql = "delete from board where no=?";
//			pstmt = conn.prepareStatement(deleteSql);
//			
			String updateStateSql = "update board set state='deactive' where no=?";
			pstmt = conn.prepareStatement(updateStateSql);
			
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


	public boolean update(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {
			conn = getConnection();

			String deleteSql = "update board"
								+ " set title=?, contents=?"
								+ " where no=?";
			pstmt = conn.prepareStatement(deleteSql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, vo.getNo());
			
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

	public List<BoardVo> findAll() {
		List<BoardVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String selectSql
				= "select b.no, b.title, b.contents, b.hit, b.reg_date, b.g_no, b.o_no, b.depth, b.user_no, b.state,u.name"
						+ " from board b, user u"
						+ " where b.user_no=u.no"
						+ " order by g_no desc, o_no asc";
			pstmt = conn.prepareStatement(selectSql);
			rs =  pstmt.executeQuery();
			
			while (rs.next()) {
				int no = rs.getInt(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				Date regDate = rs.getDate(5);
				int gNo = rs.getInt(6);
				int oNo = rs.getInt(7);
				int depth = rs.getInt(8);
				int userNo = rs.getInt(9);
				String state = rs.getString(10);
				String userName = rs.getString(11);
				
				BoardVo vo = new BoardVo();			
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setGno(gNo);
				vo.setOno(oNo);
				vo.setDepth(depth);
				vo.setUserNo(userNo);
				vo.setState(state);
				vo.setUserName(userName);
				result.add(vo);
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
	
	public int findLastNoOfGroup() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;
		try {
			conn = getConnection();

			String selectQuery = "select COALESCE(max(g_no), 0) from board";
			pstmt = conn.prepareStatement(selectQuery);
			rs =  pstmt.executeQuery();
			rs.next();
			result = rs.getInt(1);
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

	public List<BoardVo> findByRange(int boardCountPerPage ,int currentPage) {
		List<BoardVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String selectSql
				= "select b.no, b.title, b.contents, b.hit, b.reg_date, b.g_no, b.o_no, b.depth, b.user_no, b.state, u.name"
						+ " from board b, user u"
						+ " where b.user_no=u.no"
						+ " order by g_no desc, o_no asc"
						+ " limit ?, ?";
			pstmt = conn.prepareStatement(selectSql);
			pstmt.setInt(1, (currentPage - 1)*boardCountPerPage);
			pstmt.setInt(2, boardCountPerPage);
			rs =  pstmt.executeQuery();
			
			while (rs.next()) {
				int no = rs.getInt(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				Date regDate = rs.getDate(5);
				int gNo = rs.getInt(6);
				int oNo = rs.getInt(7);
				int depth = rs.getInt(8);
				int userNo = rs.getInt(9);
				String state = rs.getString(10);
				String userName = rs.getString(11);
				
				BoardVo vo = new BoardVo();			
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setGno(gNo);
				vo.setOno(oNo);
				vo.setDepth(depth);
				vo.setUserNo(userNo);
				vo.setState(state);
				vo.setUserName(userName);
				result.add(vo);
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
	

	public List<BoardVo> findByRangeAndKeyword(int boardCountPerPage, int currentPage, String keyword) {
		List<BoardVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String selectSql
				= "select b.no, b.title, b.contents, b.hit, b.reg_date, b.g_no, b.o_no, b.depth, b.user_no, b.state, u.name"
						+ " from board b, user u, (select g_no as m_g_no from board where title like ? and o_no=1) as m"
						+ " where b.user_no=u.no"
						+ " and b.state='active'"
						+ " and b.g_no=m.m_g_no"
						+ " order by g_no desc, o_no asc";
			pstmt = conn.prepareStatement(selectSql);
			pstmt.setString(1, "%"+keyword+"%");
			pstmt.setInt(2, (currentPage - 1)*boardCountPerPage);
			pstmt.setInt(3, boardCountPerPage);
			rs =  pstmt.executeQuery();
			
			while (rs.next()) {
				int no = rs.getInt(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				Date regDate = rs.getDate(5);
				int gNo = rs.getInt(6);
				int oNo = rs.getInt(7);
				int depth = rs.getInt(8);
				int userNo = rs.getInt(9);
				String state = rs.getString(10);
				String userName = rs.getString(11);
				
				BoardVo vo = new BoardVo();			
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setGno(gNo);
				vo.setOno(oNo);
				vo.setDepth(depth);
				vo.setUserNo(userNo);
				vo.setState(state);
				vo.setUserName(userName);
				result.add(vo);
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
	
	public BoardVo findByNo(int boardNo) {
		BoardVo result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String selectSql
				= "select b.no, b.title, b.contents, b.hit, b.reg_date, b.g_no, b.o_no, b.depth, b.user_no, b.state ,u.name from board b, user u where b.no=?";
			pstmt = conn.prepareStatement(selectSql);
			pstmt.setInt(1, boardNo);
			rs =  pstmt.executeQuery();
			
			if (rs.next()) {
				int no = rs.getInt(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				Date regDate = rs.getDate(5);
				int gNo = rs.getInt(6);
				int oNo = rs.getInt(7);
				int depth = rs.getInt(8);
				int userNo = rs.getInt(9);
				String state = rs.getString(10);
				String userName = rs.getString(11);
				
				result = new BoardVo();			
				result.setNo(no);
				result.setTitle(title);
				result.setContents(contents);
				result.setHit(hit);
				result.setRegDate(regDate);
				result.setGno(gNo);
				result.setOno(oNo);
				result.setDepth(depth);
				result.setUserNo(userNo);
				result.setState(state);
				result.setUserName(userName);
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
				= "select count(*)"
				+ " from board"
				+ " where title like ?"
				+ " and state='active'";
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
