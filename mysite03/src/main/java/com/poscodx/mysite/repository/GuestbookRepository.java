package com.poscodx.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	

	public boolean insert(GuestbookVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {
			conn = dataSource.getConnection();

			String authorSql = "insert into guestbook values (null,?,?,?,now())";
			pstmt = conn.prepareStatement(authorSql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContents());
			
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
	
	public boolean delete(Long no, String password) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {
			conn = dataSource.getConnection();

			String deleteSql = "delete from guestbook where no=? and password=?";
			pstmt = conn.prepareStatement(deleteSql);
			
			pstmt.setLong(1, no);
			pstmt.setString(2, password);
			
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
	
	public List<GuestbookVo> findAll() {
		return sqlSession.selectList("guestbook.findAll");
	}
}
