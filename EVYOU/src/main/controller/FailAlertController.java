package main.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

//로그인에 실패했을때 나타내는 alert창 클래스
public class FailAlertController implements Initializable
{
	@FXML
	private Button alertBtn;
	@FXML
	private Label label;
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		label.setText("아이디와 비밀번호를 다시 입력해주세요.");
		alertBtn.setOnAction(e -> {
			Stage stage = (Stage) alertBtn.getScene().getWindow();
				stage.close();
		});
	}
}