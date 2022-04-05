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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.map.MyBrowser;
import member.MemberTemp;

public class FavorController implements Initializable
{
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

	DBConnection dbc = new DBConnection();
	MemberTemp mtemp = new MemberTemp();
	Connection conn;
	ResultSet rs;
	PreparedStatement pstmt;
	BorderPane root;
	ObservableList ov;
	Scene scene;
	VBox vb;
	ComboBox<String> combobox;
	String chger_type;
	
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

		// 즐겨찾기 조회부분
		try
		{
			ov.clear();
			pstmt = conn.prepareStatement(
					"SELECT stat_name, stat_id, chger_id, chger_type, stat, addr FROM favor WHERE id = ?");
			pstmt.setString(1, mtemp.getId());
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
		catch (SQLException e)
		{
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		// 마우스 클릭시 지도출력하는 부분
		tableView.setOnMouseClicked(click ->
		{	
			try
			{
				//클릭했을 때 충전소명을 setter에 저장
				mtemp.setStat_name(tableView.getSelectionModel().getSelectedItem().getStatNm());
				
				//마우스 2번이상 클릭시
				if (click.getClickCount() > 1)
				{
					//로그인 상태일시
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
					//로그인 상태가 아닐시 로그인하라는 alert창 출력
					else
					{
						try
						{
							Parent root = FXMLLoader.load(Class.forName("main.controller.FavorController").getResource("/main/fxml/LoginAlert.fxml"));
							Scene scene = new Scene(root);
					        Stage stage = new Stage();
					        stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
					        stage.setScene(scene);
					        stage.show();
					        stage.setTitle("EVYOU");
						}
						
						catch (Exception e)
						{
							
						}
					}
				}
			}
			catch(NullPointerException e)
			{
				
			}
			
		});
		
		//즐겨찾기해제 버튼을 눌렀을 시
		favBtn.setOnAction((favBtnClick) ->
		{
			try {
				/** 즐겨찾기 로직처리 */
				if(mtemp.getId() != null)
				{
					ChargeVO vo = new ChargeVO();
					ChargeVO vtd = tableView.getSelectionModel().getSelectedItem();
					int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
					
					//즐겨찾기 메뉴에서 선택한 항목이 있을 때
					if(selectedIndex>=0)
					{
						pstmt = conn.prepareStatement("DELETE FROM FAVOR WHERE id=? and stat_name=? and stat_id=? and chger_id=?");
						pstmt.setString(1, mtemp.getId());
						pstmt.setString(2, vtd.getStatNm());
						pstmt.setString(3, vtd.getStatid());
						pstmt.setInt(4, vtd.getChgerid());
						pstmt.executeQuery();
						
						//선택한 후 즐겨찾기가 삭제되었기 때문에 다시 충전소명을 null로 set해주는 부분
						mtemp.setStat_name(null);
						
						Parent root = FXMLLoader.load(Class.forName("main.controller.FavorController").getResource("/main/fxml/FavorDelAlert.fxml"));
						Scene scene = new Scene(root);
				        Stage stage = new Stage();
				        stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
				        stage.setScene(scene);
				        stage.show();
				        stage.setTitle("EVYOU");
				        
				        //즐겨찾기해제한 후 새로고침 된 항목을 보여주는 부분
							try
							{
								ov.clear();
								pstmt = conn.prepareStatement(
										"SELECT stat_name, stat_id, chger_id, chger_type, stat, addr FROM favor WHERE id = ?");
								pstmt.setString(1, mtemp.getId());
								rs = pstmt.executeQuery();
								
								while (rs.next())
								{
									String statNm = rs.getString("stat_name");
									String statid = rs.getString("stat_id");
									int chgerid = rs.getInt("chger_id");
									String chagerType = rs.getString("chger_type");
									String stat = rs.getString("stat");
									String addr = rs.getString("addr");
									
									ChargeVO vo1 = new ChargeVO();
									
									vo1.setStatNm(statNm);
									vo1.setStatid(statid);
									vo1.setChgerid(chgerid);
									vo1.setChagerType(chagerType);
									vo1.setStat(stat);
									vo1.setAddr(addr);

									ov.add(vo1);
									tableView.setItems(ov);
								} // while end
							}
							
							catch (SQLException e)
							{
								e.printStackTrace();
							}
						
					}
					
					//항목을 선택하지 않고 버튼을 실행했을 경우 경고창 출력해주는 부분
					else
					{
						try
						{
							Parent root = FXMLLoader.load(Class.forName("main.controller.FavorController").getResource("/main/fxml/FavorDataAlert.fxml"));
							Scene scene = new Scene(root);
					        Stage stage = new Stage();
					        stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
					        stage.setScene(scene);
					        stage.show();
					        stage.setTitle("EVYOU");
						}
						
						catch (Exception e)
						{
							
						}
					}
				}
				
				//로그인을 하지않고 즐겨찾기 버튼을 눌렀을 경우 로그인 alert창 출력
				else
				{
					try
					{
						Parent root = FXMLLoader.load(Class.forName("main.controller.FavorController").getResource("/main/fxml/LoginAlert.fxml"));
						Scene scene = new Scene(root);
				        Stage stage = new Stage();
				        stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
				        stage.setScene(scene);
				        stage.show();
				        stage.setTitle("EVYOU");
					}
					
					catch (Exception e)
					{
						
					}
				}
				
			}
			
			catch (Exception e)
			{
				e.printStackTrace();
			}
		});
		
		//결제버튼을 눌렀을 경우
		payBtn.setOnAction((event)->
		{
			try
			{
				//로그인했을 경우
				if(mtemp.getId() != null)
				{
					//충전소를 선택했을 경우 팝업창을 띄워주는 부분
					if(mtemp.getStat_name() != null)
					{
						pop = new Stage(StageStyle.DECORATED);
						stage = (Stage)payBtn.getScene().getWindow();
						
						pop.initModality(Modality.WINDOW_MODAL);
						pop.initOwner(stage);
						pop.setTitle("결제");
						
						try
						{
							Parent parent = FXMLLoader.load(Class.forName("main.controller.FavorController").getResource("/main/fxml/paypopup.fxml"));
							
							Scene scene = new Scene(parent);
							
							pop.setScene(scene);
							pop.show();
						}
						
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}
					//충전소를 선택하지 않았을 경우 alert창 출력
					else
					{
						try
						{
							Parent root = FXMLLoader.load(Class.forName("main.controller.FavorController").getResource("/main/fxml/EVAlert.fxml"));
							Scene scene = new Scene(root);
					        Stage stage = new Stage();
					        stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
					        stage.setScene(scene);
					        stage.show();
					        stage.setTitle("EVYOU");
						}
						
						catch (Exception e)
						{
							
						}
					}
				}
				//로그인을 하지 않았을 경우 alert창 출력
				else
				{
					try
					{
						Parent root = FXMLLoader.load(Class.forName("main.controller.FavorController").getResource("/main/fxml/LoginAlert.fxml"));
						Scene scene = new Scene(root);
				        Stage stage = new Stage();
				        stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
				        stage.setScene(scene);
				        stage.show();
				        stage.setTitle("EVYOU");
					}
					
					catch (Exception e)
					{
						
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