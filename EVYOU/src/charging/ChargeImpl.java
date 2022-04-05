package charging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.DBConnection;

//api servicekey를 이용해서 공공데이터서버에서 충전소 정보들을 parsing해와서 DB에 등록하는 클래스
public class ChargeImpl implements ChargeDAO
{
	Connection conn;
	DBConnection connDB = new DBConnection();
	PreparedStatement pstmt;
	ResultSet rs;
	String serviceKey ="g6Dxv5J0aA8LFTtolR9QJpeCCmv8d%2BhQl5%2FowyUkgBv1L5mkj%2BFUHegSsJmd8c9qPf%2BfLyT0b9lkf37noyzPhw%3D%3D";
	public void refresh()
	{
		conn = connDB.tryConnection();
		try
		{
			PreparedStatement pstmt = conn.prepareStatement("TRUNCATE TABLE search");
			rs = pstmt.executeQuery();
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		try
		{
			PreparedStatement pstmt=conn.prepareStatement("Insert into search values(?,?,?,?,?,?,?,?)");
			String url = "http://apis.data.go.kr/B552584/EvCharger/getChargerInfo?serviceKey="+serviceKey+"&pageNo="+1+"&numOfRows=4000&zcode=30";
			
			DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
			Document doc = dBuilder.parse(url);
			
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("item");
			
			for(int temp = 0; temp < nList.getLength(); temp++)
			{
				Node nNode = nList.item(temp);
				if(nNode.getNodeType() == Node.ELEMENT_NODE)
				{
					Element eElement = (Element) nNode;
					
					String statNm = this.getTagValue("statNm", eElement);		//충전소명
					String statid = this.getTagValue("statId", eElement);		//충전소ID
					int chgerid = Integer.parseInt(this.getTagValue("chgerId", eElement));		//충전기ID
					String chagerType = this.getTagValue("chgerType", eElement);		//충전기타입
					
					//충전기타입을 숫자로 제공해주기 때문에 Stringtype으로 변환 후 DB에 등록
					switch (chagerType)
					{
	                    case "01" : chagerType = "DC차데모";
	                    break;
	                    case "02" : chagerType = "AC완속";
	                    break;
	                    case "03" : chagerType = "DC차데모+AC3상";
	                    break;
	                    case "04" : chagerType = "DC콤보";
	                    break;
	                    case "05" : chagerType = "DC차데모+DC콤보";
	                    break;
	                    case "06" : chagerType = "DC차데모+AC3상+DC콤보";
	                    break;
	                    case "07" : chagerType = "AC3상";
	                    break;
					}
					
					String addr = this.getTagValue("addr", eElement);		//주소
					String location  = this.getTagValue("location", eElement);	//상세위치
					String stat = this.getTagValue("stat", eElement);			//충전기 상태(1: 통신이상, 2: 충전대기, 3: 충전중, 4: 운영중지, 5: 점검중, 9: 상태미확인)
					
					//충전기 상태정보도 Stringtype으로 DB에 등록
					switch (stat)
					{
	                    case "1" : stat = "통신이상";
	                    break;
	                    case "2" : stat = "충전대기";
	                    break;
	                    case "3" : stat = "충전중";
	                    break;
	                    case "4" : stat = "운영중지";
	                    break;
	                    case "5" : stat = "점검중";
	                    break;
	                    case "9" : stat = "상태미확인";
	                    break;
					}
					Long statUpdDt = Long.parseLong(this.getTagValue("statUpdDt", eElement));		//충전기 상태 갱신 일시
					Long lastTsdt = Long.parseLong(this.getTagValue("lastTsdt", eElement));		//마지막 충전 시작일시
					Long lastTedt = Long.parseLong(this.getTagValue("lastTedt", eElement));		//마지막 충전종료일시
					Long nowTsdt = Long.parseLong(this.getTagValue("nowTsdt", eElement));		//충전중 시작일시
					double lat = Double.parseDouble(this.getTagValue("lat", eElement));
					double lng = Double.parseDouble(this.getTagValue("lng", eElement));
					
					pstmt.setString(1, statNm);
					pstmt.setString(2, statid);
					pstmt.setInt(3, chgerid);
					pstmt.setString(4, chagerType);
					pstmt.setString(5, stat);
					pstmt.setString(6, addr);
					pstmt.setDouble(7, lat);
					pstmt.setDouble(8, lng);
					
					pstmt.addBatch();
					pstmt.clearParameters();
				}	// if end
				pstmt.executeBatch();
				pstmt.clearBatch();
			}	// for end
			System.out.println("추가 완료");
		}
		
		catch (Exception e)
		{	
			e.printStackTrace();
		}	// try~catch end
		finally
		{
			connDB.close(conn, pstmt, rs);
		}
	}	// Paser end
	
		// tag값의 정보를 가져오는 메소드
		public String getTagValue(String tag, Element eElement)
		{
		    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		    Node nValue = (Node) nlList.item(0);
		    if(nValue == null) 
		        return "0";
		    return nValue.getNodeValue();	
		}

}
