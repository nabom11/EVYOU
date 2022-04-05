package main.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

//회원가입이 완료되었을 때 출력해주는 alert창 클래스
public class CompleteAlertController implements Initializable
{
	@FXML
	private Button alertBtn;
	@FXML
	private Label label;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		label.setText("회원가입이 완료되었습니다.");
		alertBtn.setOnAction(e ->
		{
			Stage stage = (Stage) alertBtn.getScene().getWindow();
			stage.close();
		});
	}
}