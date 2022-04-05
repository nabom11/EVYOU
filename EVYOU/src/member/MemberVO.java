package member;

public class MemberVO
{
	private String id;
	private String pw;
	private String chgertype;
	private String cartype;

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getChgertype() {
		return chgertype;
	}

	public void setChgertype(String chgertype) {
		this.chgertype = chgertype;
	}

	public String getCartype() {
		return cartype;
	}

	public void setCartype(String cartype) {
		this.cartype = cartype;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}