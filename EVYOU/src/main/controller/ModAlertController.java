package main.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

//회원정보수정이 제대로 완료되었을 때 출력해주는 alert창 클래스
public class ModAlertController implements Initializable
{
	@FXML
	private Button alertBtn;
	@FXML
	private Label label;
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		label.setText("수정이 완료되었습니다.");
		alertBtn.setOnAction(e -> {
			Stage stage = (Stage) alertBtn.getScene().getWindow();
				stage.close();
		});
	}
}