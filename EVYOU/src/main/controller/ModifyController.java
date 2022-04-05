package main.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import member.MemberTemp;
import member.ModifyDAO;
import member.ModifyImpl;

public class ModifyController implements Initializable
{
	@FXML
	private TextField userId;
	@FXML
	private TextField userPw;
	@FXML
	private TextField userPw2;
	@FXML
	private ComboBox<String> chgerType;
	@FXML
	private TextField carType;
	@FXML
	private Button commit;
	@FXML
	private Label checkLabel1;
	@FXML
	private Label checkLabel2;
	
	
	MemberTemp mtemp = new MemberTemp();
	ModifyDAO mod = new ModifyImpl();
	
	String chgertype = null;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		String id = mod.selectquery().get(0).getId();
		chgertype = mod.selectquery().get(0).getChgertype();
		String cartype = mod.selectquery().get(0).getCartype();
		
		//ID부분은 수정할 수 없도록 설정
		userId.setEditable(false);
		userId.setText(id);
		
		//초기에 설정한 충전기타입 정보를 콤보박스에 바로 보여준다
		//콤보박스 하위메뉴들 setting
		chgerType.setPromptText(chgertype);
		String[] chgertype_list = {"DC차데모", "AC완속", "DC차데모+AC3상", "DC콤보", "DC차데모+DC콤보", "DC차데모+AC3상+DC콤보", "AC3상"};
		ObservableList<String> chgerTypeList = FXCollections.observableArrayList(chgertype_list);
		chgerType.getItems().addAll(chgerTypeList);
		
		carType.setText(cartype);
		
		//콤보박스의 항목을 선택할 때 값을 가져오는 부분
		chgerType.setOnAction(e ->
		{
			chgertype = (String) chgerType.getValue();
			System.out.println(chgertype);
		});
		
		//수정완료 버튼을 눌렀을 시
		commit.setOnAction(e ->
		{
			//비밀번호를 변경하지 않을 경우
			if((userPw.getText().isEmpty()) && (userPw2.getText().isEmpty()))
			{				
				mod.updatequery(id, MemberTemp.getPw(), chgertype, cartype);
				checkLabel1.setText("");
				checkLabel2.setText("");
				
				//수정완료 alert창 출력
				try
				{
					Parent root = FXMLLoader.load(Class.forName("main.controller.ModifyController").getResource("/main/fxml/ModAlert.fxml"));
					Scene scene = new Scene(root);
			        Stage stage = new Stage();
			        stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
			        stage.setScene(scene);
			        stage.show();
			        stage.setTitle("EVYOU");
				}
				
				catch (Exception e1)
				{
					// TODO: handle exception
				}
			}
			//입력했던 비밀번호가 현재비밀번와 같을 경우
			else if(userPw.getText().equals(MemberTemp.getPw()))
			{
				checkLabel1.setText("기존 비밀번호와 같습니다.");
				checkLabel2.setText("");
			}
			//변경한 비밀번호와 확인비밀번호가 다를 경우
			else if(!userPw.getText().equals(userPw2.getText()))
			{
				checkLabel1.setText("");
				checkLabel2.setText("비밀번호가 일치하지 않습니다.");
			}
			//변경한 비밀번호와 확인비밀번호가 같을 경우
			else if(userPw.getText().equals(userPw2.getText()))
			{
				mod.updatequery(id, userPw.getText(), chgertype, cartype);
				checkLabel1.setText("");
				checkLabel2.setText("");
				//연속해서 변경할 경우를 대비해 temp에 변경했던 pw정보 set
				MemberTemp.setPw(userPw.getText());
				
				//수정완료 alert창 출력
				try
				{
					Parent root = FXMLLoader.load(Class.forName("main.controller.ModifyController").getResource("/main/fxml/ModAlert.fxml"));
					Scene scene = new Scene(root);
			        Stage stage = new Stage();
			        stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
			        stage.setScene(scene);
			        stage.show();
			        stage.setTitle("EVYOU");
				} catch (Exception e1) {
					// TODO: handle exception
				}
			}
		});
	}
}