package com.poscodx.mysite.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	private final String URL = "jdbc:mariadb://192.168.0.181:3307/webdb?charset=utf8";
	private final String ID = "mysite";
	private final String PW = "mysite";
	
	public boolean insert(GuestbookVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {
			conn = getConnection();

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
			conn = getConnection();

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
		List<GuestbookVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = "select no, name, contents, reg_date from guestbook order by reg_date desc";
			pstmt = conn.prepareStatement(sql);
			rs =  pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt(1);
				String name = rs.getString(2);
				String contents = rs.getString(3);
				String regDate = rs.getString(4);
				
				GuestbookVo vo = new GuestbookVo();			
				vo.setNo(no);
				vo.setName(name);
				vo.setContents(contents);
				vo.setRegDate(regDate);

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
