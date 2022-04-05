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

//중복체크 버튼을 눌렀을 시 사용가능할 경우 출력해주는 Alert창 클래스
public class UsableAlertController implements Initializable
{
	@FXML
	private Button alertBtn;
	@FXML
	private Label label;
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		label.setText("사용가능한 아이디입니다.");
		alertBtn.setOnAction(e ->
		{
			Stage stage = (Stage) alertBtn.getScene().getWindow();
			stage.close();
		});	
	}
}