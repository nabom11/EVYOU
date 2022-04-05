package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//DB에 접속하는 Connection 메소드와 close 메소드로 구성된 클래스
public class DBConnection
{
	public static Connection tryConnection()
	{
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "electro";
		String password = "1234";
		
		Connection conn = null;
		
		try
		{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
	{
		try
		{
			if(rs != null)
			{
				rs.close();
			}
			if(pstmt != null)
			{
				pstmt.close();
			}
			if(conn != null)
			{
				conn.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}
