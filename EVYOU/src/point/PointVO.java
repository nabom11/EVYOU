package point;

import java.util.Date;

public class PointVO
{
	private String id;
	private String stat_name;
	private Date accum_date;
	private int save_point;
	
	public PointVO()
	{
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStat_name() {
		return stat_name;
	}

	public void setStat_name(String stat_name) {
		this.stat_name = stat_name;
	}

	public Date getAccum_date() {
		return accum_date;
	}

	public void setAccum_date(Date accum_date) {
		this.accum_date = accum_date;
	}

	public int getSave_point() {
		return save_point;
	}

	public void setSave_point(int save_point) {
		this.save_point = save_point;
	}
	
	
}