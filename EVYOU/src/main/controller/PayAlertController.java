package main.controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import member.MemberTemp;
import point.Payment;
import point.PointImpl;

//결제가 완료 되었을 때 표시해주는 alert창 클래스
public class PayAlertController implements Initializable {
	@FXML
	private Button alertBtn;
	@FXML
	private Label result;
	@FXML
	private Label resultLabel;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		PaypopupController p = new PaypopupController();
		result.setText("결제가 완료되었습니다.");
		resultLabel.setText("결제 된 금액 : \\" + p.value);
		alertBtn.setOnAction(e -> {
			Stage stage = (Stage) alertBtn.getScene().getWindow();
			stage.close();
	});
	}
}