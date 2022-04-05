package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import common.DBConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LoginImpl implements LoginDAO
{
	Connection conn;
	ResultSet rs;
	PreparedStatement pstmt;
	
	//ID와 PWD를 매개변수로 받아 로그인을 시도하는 메소드
	public int tryLogin(String id, String pwd)
	{
		try
		{
			conn = DBConnection.tryConnection();
			
			String query = "SELECT pwd FROM MEMBER WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				//SELECT한 결과의 ID와 PWD가 일치할 경우 1을 return
				if(rs.getString(1).contentEquals(pwd))
				{
					return 1;
				}
				else
				{
					return 0;
				}
			}
			System.out.println("아이디가 존재하지 않습니다");
			return -1;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DBConnection.close(conn, pstmt, rs);
		}
		System.out.println("DB연결에 실패했습니다");
		return -2;
	}
}
