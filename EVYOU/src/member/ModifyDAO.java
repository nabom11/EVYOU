package member;

import java.util.List;

public interface ModifyDAO
{
	public List<MemberVO> selectquery();
	public void updatequery(String id, String pwd, String chgertype, String cartype);
}