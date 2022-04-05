package point;

public class Payment
{
	public int electro;
	public int total;
	public int result;
	
	//사용량을 입력받아서 결제할 금액을 return해주는 메소드
	public int pay(int usage)
	{
		if((usage > 0) && (usage <= 50))
		{
			electro = 292;
			total = (electro * usage);
		}
		else
		{
			electro = 309;
			total = (electro * usage);
		}
		return total;
	}
	
	//총 포인트량을 저장해둘 getter, setter 메소드
	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
	
	
}