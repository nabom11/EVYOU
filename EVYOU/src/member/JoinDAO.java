package member;

public interface JoinDAO
{
	public int tryJoin(String id, String pwd, String chger_type, String car_type);
	public boolean check(String id);
}
