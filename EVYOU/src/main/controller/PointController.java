package main.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.Date;
import java.util.ResourceBundle;

import common.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import member.MemberTemp;
import point.PointDAO;
import point.PointImpl;
import point.PointVO;

public class PointController implements Initializable
{
	@FXML
	private TableView<PointVO> tableView;
	@FXML
	private TableColumn<PointVO, String> tc1;
	@FXML
	private TableColumn<PointVO, Date> tc2;
	@FXML
	private TableColumn<PointVO, Integer> tc3;
	@FXML
	private Label totalPoint;
	
	MemberTemp mtemp = new MemberTemp();
	PointDAO pDAO = new PointImpl();
	ObservableList<PointVO> ov;
	DBConnection dbc = new DBConnection();
	Connection conn;
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		//로그인을 했을 경우
		if(mtemp.getId() != null)
		{
			conn = DBConnection.tryConnection();
			ov = FXCollections.observableArrayList();
			MemberTemp mtemp = new MemberTemp();
			
			tc1.setCellValueFactory(new PropertyValueFactory<PointVO, String>("stat_name"));
			tc2.setCellValueFactory(new PropertyValueFactory<PointVO, Date>("accum_date"));
			tc3.setCellValueFactory(new PropertyValueFactory<PointVO, Integer>("save_point"));
			
			tc1.setStyle("-fx-alignment:CENTER");
			tc2.setStyle("-fx-alignment:CENTER");
			tc3.setStyle("-fx-alignment:CENTER");
			
			ov.clear();
			String id = mtemp.getId();
			
			//id를 get해오고 pointlist메소드에 매개변수로 넘겨준 후 포인트 목록을 가져오는 부분
			ov.addAll(pDAO.pointList(id));
			tableView.setItems(ov);
			
			totalPoint.setText("총 포인트 : "+String.valueOf(pDAO.total_point(id)));
		}
		else
		{
			totalPoint.setText("포인트가 없습니다.");
		}
	}
}