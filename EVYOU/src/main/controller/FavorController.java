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

		// ???????????? ????????????
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
		
		// ????????? ????????? ?????????????????? ??????
		tableView.setOnMouseClicked(click ->
		{	
			try
			{
				//???????????? ??? ??????????????? setter??? ??????
				mtemp.setStat_name(tableView.getSelectionModel().getSelectedItem().getStatNm());
				
				//????????? 2????????? ?????????
				if (click.getClickCount() > 1)
				{
					//????????? ????????????
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
					//????????? ????????? ????????? ?????????????????? alert??? ??????
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
		
		//?????????????????? ????????? ????????? ???
		favBtn.setOnAction((favBtnClick) ->
		{
			try {
				/** ???????????? ???????????? */
				if(mtemp.getId() != null)
				{
					ChargeVO vo = new ChargeVO();
					ChargeVO vtd = tableView.getSelectionModel().getSelectedItem();
					int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
					
					//???????????? ???????????? ????????? ????????? ?????? ???
					if(selectedIndex>=0)
					{
						pstmt = conn.prepareStatement("DELETE FROM FAVOR WHERE id=? and stat_name=? and stat_id=? and chger_id=?");
						pstmt.setString(1, mtemp.getId());
						pstmt.setString(2, vtd.getStatNm());
						pstmt.setString(3, vtd.getStatid());
						pstmt.setInt(4, vtd.getChgerid());
						pstmt.executeQuery();
						
						//????????? ??? ??????????????? ??????????????? ????????? ?????? ??????????????? null??? set????????? ??????
						mtemp.setStat_name(null);
						
						Parent root = FXMLLoader.load(Class.forName("main.controller.FavorController").getResource("/main/fxml/FavorDelAlert.fxml"));
						Scene scene = new Scene(root);
				        Stage stage = new Stage();
				        stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
				        stage.setScene(scene);
				        stage.show();
				        stage.setTitle("EVYOU");
				        
				        //????????????????????? ??? ???????????? ??? ????????? ???????????? ??????
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
					
					//????????? ???????????? ?????? ????????? ???????????? ?????? ????????? ??????????????? ??????
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
				
				//???????????? ???????????? ???????????? ????????? ????????? ?????? ????????? alert??? ??????
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
		
		//??????????????? ????????? ??????
		payBtn.setOnAction((event)->
		{
			try
			{
				//??????????????? ??????
				if(mtemp.getId() != null)
				{
					//???????????? ???????????? ?????? ???????????? ???????????? ??????
					if(mtemp.getStat_name() != null)
					{
						pop = new Stage(StageStyle.DECORATED);
						stage = (Stage)payBtn.getScene().getWindow();
						
						pop.initModality(Modality.WINDOW_MODAL);
						pop.initOwner(stage);
						pop.setTitle("??????");
						
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
					//???????????? ???????????? ????????? ?????? alert??? ??????
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
				//???????????? ?????? ????????? ?????? alert??? ??????
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