package main.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

//로그인을 하지않고 다른 기능들을 선택했을 때 보여지는 경고창 alert창 클래스
public class LoginAlertController implements Initializable
{
	@FXML
	private Button alertBtn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		alertBtn.setOnAction(e ->
		{
			Stage stage = (Stage) alertBtn.getScene().getWindow();
				stage.close();
		});
	}
}