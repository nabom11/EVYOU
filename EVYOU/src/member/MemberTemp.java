package member;

//프로그램이 실행되는 동안 회원들의 정보들을 기억해두는 클래스
public class MemberTemp
{
//	id정보
//	로그인 여부
//	충전소명
//	비밀번호
	static private String id;
	static private Boolean loginFlag;
	static private String stat_name;
	static private String pw;
	
	public static String getPw() {
		return pw;
	}
	public static void setPw(String pw) {
		MemberTemp.pw = pw;
	}
	public static String getStat_name() {
		return stat_name;
	}
	public static void setStat_name(String stat_name) {
		MemberTemp.stat_name = stat_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		MemberTemp.id = id;
	}
	public Boolean getLoginFlag() {
		return loginFlag;
	}
	public void setLoginFlag(Boolean loginFlag) {
		MemberTemp.loginFlag = loginFlag;
	}
}