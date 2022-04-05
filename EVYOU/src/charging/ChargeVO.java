package charging;

public class ChargeVO {
	
	private String statNm;		
	private String statid;		
	private int chgerid;		
	private String chagerType;		
	private String addr;		
	private String useTime;		
	private String busiCall;	
	private String stat;			
	private Long lastTedt;		
	private String id;
	private String ct;
	private String st;
	
	public ChargeVO() {
	}
	


	public ChargeVO(String statNm, String statid, int chgerid, String chagerType, String addr, String stat) {
		super();
		this.statNm = statNm;
		this.statid = statid;
		this.chgerid = chgerid;
		this.chagerType = chagerType;
		this.addr = addr;
		this.stat = stat;
	}



	public ChargeVO(String statNm, String statid, int chgerid, String chagerType, String addr, String useTime,
			String busiCall, String stat, Long lastTedt, String id) {
		super();
		this.statNm = statNm;
		this.statid = statid;
		this.chgerid = chgerid;
		this.chagerType = chagerType;
		this.addr = addr;
		this.useTime = useTime;
		this.busiCall = busiCall;
		this.stat = stat;
		this.lastTedt = lastTedt;
		this.id = id;
	}

	public String getStatNm() {
		return statNm;
	}

	public void setStatNm(String statNm) {
		this.statNm = statNm;
	}

	public String getStatid() {
		return statid;
	}

	public void setStatid(String statid) {
		this.statid = statid;
	}

	public int getChgerid() {
		return chgerid;
	}

	public void setChgerid(int chgerid) {
		this.chgerid = chgerid;
	}

	public String getChagerType() {
		return chagerType;
	}

	public void setChagerType(String chagerType) {
		this.chagerType = chagerType;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getUseTime() {
		return useTime;
	}

	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}

	public String getBusiCall() {
		return busiCall;
	}

	public void setBusiCall(String busiCall) {
		this.busiCall = busiCall;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public Long getLastTedt() {
		return lastTedt;
	}

	public void setLastTedt(Long lastTedt) {
		this.lastTedt = lastTedt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public String getCt() {
		return ct;
	}



	public void setCt(String ct) {
		this.ct = ct;
	}



	public String getSt() {
		return st;
	}



	public void setSt(String st) {
		this.st = st;
	}
	
	

}
