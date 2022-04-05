package main.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

//ID 중복체크를 눌렀을 때 사용하지 못할 경우 출력하는 alert창 클래스
public class NotuseAlertController implements Initializable
{
	@FXML
	private Button alertBtn;
	@FXML
	private Label label;
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		label.setText("사용할 수 없는 아이디입니다.");
		alertBtn.setOnAction(e -> {
			Stage stage = (Stage) alertBtn.getScene().getWindow();
				stage.close();
		});
	}
}