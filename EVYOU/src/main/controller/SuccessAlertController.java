package main.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

//로그인에 성공했을 경우 회원전용 화면부분인 LogoutSideBar.fxml을 호출한다.
public class SuccessAlertController implements Initializable
{
	@FXML
	private Button alertBtn;
	@FXML
	private Label label;
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		try
		{
			Parent root = FXMLLoader.load(Class.forName("main.controller.SuccessAlertController").getResource("/main/fxml/LogoutSideBar.fxml"));
			Scene scene = new Scene(root);
	        Stage stage1 = new Stage();
	        stage1.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
	        stage1.setScene(scene);
	        stage1.show();
	        stage1.setTitle("EVYOU");
		}
		
		catch (Exception e1)
		{
			// TODO: handle exception
		}
		label.setText("로그인되었습니다.");
		
		//Alert창 닫기
		alertBtn.setOnAction(e ->
		{
			Stage stage = (Stage) alertBtn.getScene().getWindow();
			stage.close();
		});
				
	}
}