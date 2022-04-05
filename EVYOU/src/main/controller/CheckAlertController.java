package main.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

//회원가입시 필수항목들을 모두 입력하지 않았을 때 출력하는 checkalert창 클래스
public class CheckAlertController implements Initializable
{
	@FXML
	private Button alertBtn;
	@FXML
	private Label label;
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		label.setText("다시 시도해 주세요.");
		alertBtn.setOnAction(e -> {
			Stage stage = (Stage) alertBtn.getScene().getWindow();
				stage.close();
		});
	}
}