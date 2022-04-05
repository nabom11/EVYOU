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

//결제창에서 정수를 입력하지 않았을 경우의 alert창 클래스
public class IntegersAlertController implements Initializable
{
	@FXML
	private Button alertBtn;
	@FXML
	private Label label;
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		label.setText("정수만 입력해주세요.");
		alertBtn.setOnAction(e -> {
			Stage stage = (Stage) alertBtn.getScene().getWindow();
			stage.close();
	});
				
	}
}