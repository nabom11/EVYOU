package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import common.DBConnection;

public class ModifyImpl implements ModifyDAO
{
	Connection conn;
	ResultSet rs;
	PreparedStatement pstmt;
	MemberTemp mtemp = new MemberTemp();
	
	//회원정보수정 메뉴를 처음 들어갔을 때 나오는 정보들을 가져오는 메소드
	public List<MemberVO> selectquery()
	{
		List<MemberVO> statlist = new ArrayList<>();
		try
		{
			String query = "SELECT * FROM member WHERE ID = ?";
			conn = DBConnection.tryConnection();
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, mtemp.getId());
			
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				String userid = rs.getString(1);
				String userpw = rs.getString(2);
				String chgertype = rs.getString(3);
				String cartype = rs.getString(4);
				
				MemberVO mvo = new MemberVO();
				
				mvo.setId(userid);
				mvo.setPw(userpw);
				mvo.setChgertype(chgertype);
				mvo.setCartype(cartype);
				
				statlist.add(mvo);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return statlist;
	}
	
	//수정한 회원정보들을 DB에 UPDATE하는 메소드
	public void updatequery(String id, String pwd, String chgertype, String cartype)
	{
		try
		{
			String query = "UPDATE MEMBER SET CHGER_TYPE = ?, CAR_TYPE = ?, PWD = ? WHERE ID = ?";
			conn = DBConnection.tryConnection();
			pstmt = conn.prepareStatement(query);
			System.out.println(query);
			
			pstmt.setString(1, chgertype);
			pstmt.setString(2, cartype);
			pstmt.setString(3, pwd);
			pstmt.setString(4, id);
			
			int result = pstmt.executeUpdate();
			System.out.println("query 완료 : " + result);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}