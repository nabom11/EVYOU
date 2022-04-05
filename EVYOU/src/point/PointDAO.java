package point;

import java.util.List;

public interface PointDAO
{
	public List<PointVO> pointList(String user_id);
	public int total_point(String input_id);
	public void pointInsert(String input_id, String input_stat_name, int usage);
}