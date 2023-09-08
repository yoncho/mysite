package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.poscodx.mysite.vo.UserVo;

public class UserDao {
	private final String URL = "jdbc:mariadb://192.168.0.181:3307/webdb?charset=utf8";
	private final String ID = "yoncho";
	private final String PW = "tkaak1212";
	
	public boolean insert(UserVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {
			conn = getConnection();

			String authorSql = "insert into user values (null,?,?,password(?),?,current_date())";
			pstmt = conn.prepareStatement(authorSql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			
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
	
	public UserVo findByEmailAndPassword(String email, String password) {
		UserVo userVo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();

			String authorSql = "select no, name " +
					 			"from user " +
					 			"where email=?" + 
					 			"and password=password(?);";
			pstmt = conn.prepareStatement(authorSql);
			
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			rs =  pstmt.executeQuery();
			if(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				
				userVo = new UserVo();
				userVo.setNo(no);
				userVo.setName(name);
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
		return userVo;
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
