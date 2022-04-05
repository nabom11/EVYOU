package main.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import charging.ChargeVO;
import common.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.map.MyBrowser;
import member.JoinDAO;
import member.JoinImpl;
import member.MemberTemp;
import member.ModifyDAO;
import member.ModifyImpl;

//FavorController.java와 거의 비슷함
public class SearchController implements Initializable
{
	@FXML
	private Button bt1;
	@FXML
	private TextField tf;
	@FXML
	private Button favBtn;
	@FXML
	private Button payBtn;
	@FXML
	private TableView<ChargeVO> tableView;
	@FXML
	private TableColumn<ChargeVO, String> tc1;
	@FXML
	private TableColumn<ChargeVO, String> tc2;
	@FXML
	private TableColumn<ChargeVO, Integer> tc3;
	@FXML
	private TableColumn<ChargeVO, String> tc4;
	@FXML
	private TableColumn<ChargeVO, String> tc5;
	@FXML
	private TableColumn<ChargeVO, String> tc6;
	@FXML
	private ComboBox<String> chgerTypeBox;

	DBConnection dbc = new DBConnection();
	MemberTemp mtemp = new MemberTemp();
	ModifyDAO mod = new ModifyImpl();
	
	Connection conn;
	ResultSet rs;
	PreparedStatement pstmt;
	BorderPane root;
	ObservableList ov;
	Scene scene;
	VBox vb;
	JoinDAO join = new JoinImpl();
	String chgertype = null;
	Stage stage;
	Stage pop;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		mtemp.setStat_name(null);
		conn = dbc.tryConnection();
		ov = FXCollections.observableArrayList();

		tc1.setCellValueFactory(new PropertyValueFactory<ChargeVO, String>("statNm"));
		tc2.setCellValueFactory(new PropertyValueFactory<ChargeVO, String>("statid"));
		tc3.setCellValueFactory(new PropertyValueFactory<ChargeVO, Integer>("chgerid"));
		tc4.setCellValueFactory(new PropertyValueFactory<ChargeVO, String>("chagerType"));
		tc5.setCellValueFactory(new PropertyValueFactory<ChargeVO, String>("stat"));
		tc6.setCellValueFactory(new PropertyValueFactory<ChargeVO, String>("addr"));

		tc1.setStyle("-fx-alignment:CENTER");
		tc2.setStyle("-fx-alignment:CENTER");
		tc3.setStyle("-fx-alignment:CENTER");
		tc4.setStyle("-fx-alignment:CENTER");
		tc5.setStyle("-fx-alignment:CENTER");
		tc6.setStyle("-fx-alignment:CENTER");

		String[] chgerType = {"전체 타입","DC차데모", "AC완속", "DC차데모+AC3상", "DC콤보", "DC차데모+DC콤보", "DC차데모+AC3상+DC콤보", "AC3상"};
		ObservableList<String> chgerTypeList = FXCollections.observableArrayList(chgerType);
		
		if(mtemp.getId() != null)
        {
            chgertype = mod.selectquery().get(0).getChgertype();
            chgerTypeBox.setValue(chgertype);
        }
		
		chgerTypeBox.getItems().addAll(chgerTypeList);
	    
		//sidebar에 있는 조회 버튼을 눌렀을 시 바로 쿼리문 실행
		try {
			ov.clear();
			pstmt = conn.prepareStatement("SELECT stat_name, stat_id, chger_id, chger_type, stat, addr FROM SEARCH");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String statNm = rs.getString("stat_name");
				String statid = rs.getString("stat_id");
				int chgerid = rs.getInt("chger_id");
				String chagerType = rs.getString("chger_type");
				String stat = rs.getString("stat");
				String addr = rs.getString("addr");

				ChargeVO vo = new ChargeVO();
				vo.setStatNm(statNm);
				vo.setStatid(statid);
				vo.setChgerid(chgerid);
				vo.setChagerType(chagerType);
				vo.setStat(stat);
				vo.setAddr(addr);

				ov.add(vo);
				tableView.setItems(ov);
			} // while end
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		//콤보박스의 항목을 선택했을 때 충전기타입별로 검색하는 부분
		chgerTypeBox.setOnAction((e)->
		{
			mtemp.setStat_name(null);
			try
			{
				chgertype = (String) chgerTypeBox.getValue();
				System.out.println("선택한 충전기 타입 : " + chgerTypeBox.getValue());
				for(String type:chgerType)
				{
					//검색한 주소와 모든충전기 타입을 검색
					if(chgertype.equals("전체 타입"))
					{
						ov.clear();
						pstmt = conn.prepareStatement("SELECT stat_name, stat_id, chger_id, chger_type, stat, addr FROM search WHERE addr LIKE ?");
						pstmt.setString(1, "%" + tf.getText() + "%");
						rs = pstmt.executeQuery();
						while (rs.next())
						{
							String statNm = rs.getString("stat_name");
							String statid = rs.getString("stat_id");
							int chgerid = rs.getInt("chger_id");
							String chagerType = rs.getString("chger_type");
							String stat = rs.getString("stat");
							String addr = rs.getString("addr");
		
							ChargeVO vo = new ChargeVO();
							vo.setStatNm(statNm);
							vo.setStatid(statid);
							vo.setChgerid(chgerid);
							vo.setChagerType(chagerType);
							vo.setStat(stat);
							vo.setAddr(addr);
		
							ov.add(vo);
							tableView.setItems(ov);
						} // while end
					}
					//선택한 충전기 타입과 검색조건에 맞는 항목들을 검색
					else if(chgertype.equals(type))
					{
						ov.clear();
						pstmt = conn.prepareStatement("SELECT stat_name, stat_id, chger_id, chger_type, stat, addr FROM search WHERE addr LIKE ? and chger_Type = '"+type+"'");
						pstmt.setString(1, "%" + tf.getText() + "%");
						rs = pstmt.executeQuery();
						while (rs.next())
						{
							String statNm = rs.getString("stat_name");
							String statid = rs.getString("stat_id");
							int chgerid = rs.getInt("chger_id");
							String chagerType = rs.getString("chger_type");
							String stat = rs.getString("stat");
							String addr = rs.getString("addr");
	
							ChargeVO vo = new ChargeVO();
							vo.setStatNm(statNm);
							vo.setStatid(statid);
							vo.setChgerid(chgerid);
							vo.setChagerType(chagerType);
							vo.setStat(stat);
							vo.setAddr(addr);
	
							ov.add(vo);
							tableView.setItems(ov);
						} // while end
					}
				}
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		});
		
		// 검색버튼을 눌렀을 시
		tf.setOnAction(enter ->
		{
			bt1.fire();
			tf.setText("");
		});
		bt1.setOnAction(bt1Click ->
		{
			try
			{
				chgertype = (String) chgerTypeBox.getValue();
				System.out.println("선택한 충전기 타입 : " + chgerTypeBox.getValue());
				for(String type:chgerType)
				{
					//전체타입을 선태갛고 충전기타입을 선택했을 경우
					if(chgertype == null)
					{
						ov.clear();
						pstmt = conn.prepareStatement(
								"SELECT stat_name, stat_id, chger_id, chger_type, stat, addr FROM search WHERE addr LIKE ?");
						pstmt.setString(1, "%" + tf.getText() + "%");
						rs = pstmt.executeQuery();
						while (rs.next())
						{
							String statNm = rs.getString("stat_name");
							String statid = rs.getString("stat_id");
							int chgerid = rs.getInt("chger_id");
							String chagerType = rs.getString("chger_type");
							String stat = rs.getString("stat");
							String addr = rs.getString("addr");
		
							ChargeVO vo = new ChargeVO();
							vo.setStatNm(statNm);
							vo.setStatid(statid);
							vo.setChgerid(chgerid);
							vo.setChagerType(chagerType);
							vo.setStat(stat);
							vo.setAddr(addr);
		
							ov.add(vo);
							tableView.setItems(ov);
						} // while end
					}
				//선택한 충전기 타입과 검색조건에 맞는 항목들을 검색
					else if(chgertype.equals(type))
					{
						ov.clear();
						pstmt = conn.prepareStatement(
								"SELECT stat_name, stat_id, chger_id, chger_type, stat, addr FROM search WHERE addr LIKE ? and chger_Type = '"+type+"'");
						pstmt.setString(1, "%" + tf.getText() + "%");
						rs = pstmt.executeQuery();
						while (rs.next())
						{
							String statNm = rs.getString("stat_name");
							String statid = rs.getString("stat_id");
							int chgerid = rs.getInt("chger_id");
							String chagerType = rs.getString("chger_type");
							String stat = rs.getString("stat");
							String addr = rs.getString("addr");

							ChargeVO vo = new ChargeVO();
							vo.setStatNm(statNm);
							vo.setStatid(statid);
							vo.setChgerid(chgerid);
							vo.setChagerType(chagerType);
							vo.setStat(stat);
							vo.setAddr(addr);

							ov.add(vo);
							tableView.setItems(ov);
						} // while end
					}else if(chgertype.equals("전체 타입")){
						ov.clear();
						pstmt = conn.prepareStatement(
								"SELECT stat_name, stat_id, chger_id, chger_type, stat, addr FROM search WHERE addr LIKE ?");
						pstmt.setString(1, "%" + tf.getText() + "%");
						rs = pstmt.executeQuery();
						while (rs.next())
						{
							String statNm = rs.getString("stat_name");
							String statid = rs.getString("stat_id");
							int chgerid = rs.getInt("chger_id");
							String chagerType = rs.getString("chger_type");
							String stat = rs.getString("stat");
							String addr = rs.getString("addr");
		
							ChargeVO vo = new ChargeVO();
							vo.setStatNm(statNm);
							vo.setStatid(statid);
							vo.setChgerid(chgerid);
							vo.setChagerType(chagerType);
							vo.setStat(stat);
							vo.setAddr(addr);
		
							ov.add(vo);
							tableView.setItems(ov);
						} // while end
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});

		// 마우스 클릭시 지도출력
		// FavorController.java의 지도부분과 같음
		tableView.setOnMouseClicked(click ->
		{	
			try
			{
				mtemp.setStat_name(tableView.getSelectionModel().getSelectedItem().getStatNm());
				
				if (click.getClickCount() > 1)
				{
				if(mtemp.getId() != null)
				{
					tableView.getSelectionModel().getSelectedItem().getAddr();
					try
					{
						pstmt = conn.prepareStatement("SELECT DISTINCT LAT,LNG FROM SEARCH WHERE addr = ?");
						pstmt.setString(1, tableView.getSelectionModel().getSelectedItem().getAddr());
						rs = pstmt.executeQuery();
						rs.next();
						double lat = Double.parseDouble(rs.getString("lat"));
						double lng = Double.parseDouble(rs.getString("lng"));
						
						MyBrowser mybrowser = new MyBrowser(lat, lng);
						Scene scene = new Scene(mybrowser);

						Stage window = new Stage();
						window.getIcons().add(new Image("file:src\\main\\images\\Lightning.png"));
						window.setScene(scene);
						window.show();
						window.setTitle("EVYOU");
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
					else
					{
						try {
							Parent root = FXMLLoader.load(Class.forName("main.controller.SearchController").getResource("/main/fxml/LoginAlert.fxml"));
							Scene scene = new Scene(root);
					        Stage stage = new Stage();
					        stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
					        stage.setScene(scene);
					        stage.show();
					        stage.setTitle("EVYOU");
						} catch (Exception e) {
							
						}
					}
				}
			}
			catch(NullPointerException e)
			{
				
			}
			
		});
		
		//즐겨찾기 버튼을 눌렀을시
		favBtn.setOnAction((favBtnClick) ->
		{
			try
			{
				/** 즐겨찾기 로직처리 */
				//로그인 했을 때
				if(mtemp.getId() != null)
				{
					int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
					//선택한 항목이 있을 때
					if(selectedIndex>=0)
					{
						boolean searchResult = false;
						
						ChargeVO vo = new ChargeVO();
						ChargeVO vtd = tableView.getSelectionModel().getSelectedItem();
						
						pstmt = conn.prepareStatement("SELECT DECODE(COUNT(*),1,'true','false') as result from favor where id=? and stat_name=? and stat_id=? and chger_id=?");
						pstmt.setString(1, mtemp.getId());
						pstmt.setString(2, vtd.getStatNm());
						pstmt.setString(3, vtd.getStatid());
						pstmt.setInt(4, vtd.getChgerid());
						
						ResultSet rs = pstmt.executeQuery();
						rs.next();
						searchResult = Boolean.parseBoolean(rs.getString("result"));
						System.out.println(searchResult);
						
						//즐겨찾기 테이블에 없는 항목일 경우 insert쿼리 실행
						if(searchResult == false)
						{
							ChargeVO vo1 = new ChargeVO();
							ChargeVO vtd1 = tableView.getSelectionModel().getSelectedItem();
							
							pstmt = conn.prepareStatement("INSERT INTO FAVOR VALUES(?,?,?,?,?,?,?)");
							pstmt.setString(1, mtemp.getId());
							pstmt.setString(2, vtd1.getStatNm());
							pstmt.setString(3, vtd1.getStatid());
							pstmt.setInt(4, vtd1.getChgerid());
							pstmt.setString(5, vtd1.getChagerType());
							pstmt.setString(6, vtd1.getStat());
							pstmt.setString(7, vtd1.getAddr());
							pstmt.executeUpdate();
							
							//즐겨찾기 항목을 DB에 추가한 후 Alert창 출력
							try
							{
								Parent root = FXMLLoader.load(Class.forName("main.controller.SearchController").getResource("/main/fxml/FavorAddAlert.fxml"));
								Scene scene = new Scene(root);
						        Stage stage = new Stage();
						        stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
						        stage.setScene(scene);
						        stage.show();
						        stage.setTitle("EVYOU");
							} catch (Exception e) {
								
							}
						}
						
						//한번 더 눌렀을 시 DELETE쿼리 실행
						else
						{
							pstmt = conn.prepareStatement("DELETE FROM FAVOR WHERE id=? and stat_name=? and stat_id=? and chger_id=?");
							pstmt.setString(1, mtemp.getId());
							pstmt.setString(2, vtd.getStatNm());
							pstmt.setString(3, vtd.getStatid());
							pstmt.setInt(4, vtd.getChgerid());
							pstmt.executeUpdate();
							
							//즐겨찾기 제거 Alert창 출력
							try
							{
								Parent root = FXMLLoader.load(Class.forName("main.controller.SearchController").getResource("/main/fxml/FavorDelAlert.fxml"));
								Scene scene = new Scene(root);
						        Stage stage = new Stage();
						        stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
						        stage.setScene(scene);
						        stage.show();
						        stage.setTitle("EVYOU");
							} catch (Exception e) {
								
							}
						}

					}
					//항목을 선택하지 않았을 시 Alert창 출력
					else
					{
						/** 선택한 자료가 없을 경우 안내처리 */
						try {
							Parent root = FXMLLoader.load(Class.forName("main.controller.SearchController").getResource("/main/fxml/FavorDataAlert.fxml"));
							Scene scene = new Scene(root);
					        Stage stage = new Stage();
					        stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
					        stage.setScene(scene);
					        stage.show();
					        stage.setTitle("EVYOU");
						} catch (Exception e) {
							
						}
					}
				}
				//로그인을 하지 않았을 때 로그인요청 Alert창 출력
				else 
				{
					try 
					{
						Parent root = FXMLLoader.load(Class.forName("main.controller.SearchController").getResource("/main/fxml/LoginAlert.fxml"));
						Scene scene = new Scene(root);
				        Stage stage = new Stage();
				        stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
				        stage.setScene(scene);
				        stage.show();
				        stage.setTitle("EVYOU");
					} catch (Exception e) {
						
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		});
		
		//결제버튼을 눌렀을 때
		//FavorController.java클래스와 같음
		payBtn.setOnAction((event)->
		{
			try
			{
				if(mtemp.getId() != null)
				{
					if(mtemp.getStat_name() != null)
					{
						pop = new Stage(StageStyle.DECORATED);
						stage = (Stage)payBtn.getScene().getWindow();
						
						pop.initModality(Modality.WINDOW_MODAL);
						pop.initOwner(stage);
						pop.setTitle("결제");
						pop.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
						try
						{
							Parent parent = FXMLLoader.load(Class.forName("main.controller.SearchController").getResource("/main/fxml/paypopup.fxml"));
							Scene scene = new Scene(parent);
							
							pop.setScene(scene);
							pop.show();
						}
						
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}
					else
					{
						try
						{
							Parent root = FXMLLoader.load(Class.forName("main.controller.SearchController").getResource("/main/fxml/EVAlert.fxml"));
							Scene scene = new Scene(root);
					        Stage stage = new Stage();
					        stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
					        stage.setScene(scene);
					        stage.show();
					        stage.setTitle("EVYOU");
						} catch (Exception e) {
							
						}
					}
				}
				else
				{
					try {
						Parent root = FXMLLoader.load(Class.forName("main.controller.SearchController").getResource("/main/fxml/LoginAlert.fxml"));
						Scene scene = new Scene(root);
				        Stage stage = new Stage();
				        stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
				        stage.setScene(scene);
				        stage.show();
				        stage.setTitle("EVYOU");
					} catch (Exception e) {
						
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		});
	}
}