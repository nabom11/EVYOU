package point;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.DBConnection;

public class PointImpl implements PointDAO
{
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	
	//사용자별로 pointlist를 검색해오는 메소드
	public List<PointVO> pointList(String user_id)
	{
		List<PointVO> point_list = new ArrayList<>();
		
		try
		{
			String query = "select * from (select * from (select * from point where id = ?) order by accum_date desc)";
			conn = DBConnection.tryConnection();
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, user_id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				String stat_name = rs.getString(2);
				Date accum_date = rs.getDate(3);
				int save_point = rs.getInt(4);
				
				PointVO pvo = new PointVO();
				
				pvo.setStat_name(stat_name);
				pvo.setAccum_date(accum_date);
				pvo.setSave_point(save_point);
				
				point_list.add(pvo);
			}
		}
		catch(SQLException e)
		{
			
		}
		catch(Exception e)
		{
			
		}
		return point_list;
	}
	
	//사용자의 ID를 매개변수로 받아 총 포인트를 return해주는 메소드
	public int total_point(String input_id)
	{
		int total = 0;
		try
		{
			String query = "select id, save_point, sum(save_point) over(PARTITION BY id) as total from point where id in ?";
			conn = DBConnection.tryConnection();
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, input_id);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			total = rs.getInt(3);
		}
		catch(SQLException e)
		{
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return total;
	}
	
	//포인트 정보를 DB에 등록하는 메소드
	public void pointInsert(String input_id, String input_stat_name, int usage)
	{
		try
		{
			String query = "insert into point values(?, ?, sysdate, ?)";
			conn = DBConnection.tryConnection();
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, input_id);
			pstmt.setString(2, input_stat_name);
			pstmt.setDouble(3, usage);
			
			pstmt.executeQuery();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}