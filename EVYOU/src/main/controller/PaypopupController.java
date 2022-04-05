package main.controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import member.MemberTemp;
import point.Payment;
import point.PointDAO;
import point.PointImpl;

public class PaypopupController implements Initializable
{
	MemberTemp mtemp = new MemberTemp();
	PointDAO pDAO = new PointImpl();
	DecimalFormat formatter = new DecimalFormat("###,###");
	
	@FXML
	private Button payon;
	@FXML
	private TextField paytext;
	
	static String value;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		//팝업창이 열렸을 때 focus가 textfield에 가지 않도록 설정
		paytext.setFocusTraversable(false);
		
		//결제 버튼을 눌렀을 때
		payon.setOnAction((event)->
		{
			try
			{
				Payment payment = new Payment();
				//충전량 입력받기
				int usage = Integer.valueOf(paytext.getText());
				
				//0보다 작은 값을 입력했을때 경고창 출력
				if(usage < 0)
				{
					try
					{
						Parent root = FXMLLoader.load(Class.forName("main.controller.PaypopupController").getResource("PNumAlert.fxml"));
						Scene scene = new Scene(root);
				        Stage stage = new Stage();
				        stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
				        stage.setScene(scene);
				        stage.show();
				        stage.setTitle("EVYOU");
					}
					
					catch(Exception e)
					{
						
					}
				}
				//결제기능 실행부분
				else
				{
					//결제 할 금액을 result에 set
					payment.setResult(payment.pay(usage));
					System.out.println(payment.getResult());
					value = formatter.format(payment.pay(usage));
					
					//DB에 포인트정보를 insert해주는 메소드 실행
					pDAO.pointInsert(mtemp.getId(), mtemp.getStat_name(), (int)(payment.getResult() * 0.005));
					System.out.println(mtemp.getId());
					System.out.println(mtemp.getStat_name());
					System.out.println(payment.getResult());
					
					//기능 실행 후 창이 닫히고 PayAlert창 호출
					Stage paystage = (Stage) payon.getScene().getWindow();
					paystage.close();
				
					try
					{
						Parent root = FXMLLoader.load(Class.forName("main.controller.PaypopupController").getResource("/main/fxml/PayAlert.fxml"));
						Scene scene = new Scene(root);
				        Stage stage = new Stage();
				        stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
				        stage.setScene(scene);
				        stage.show();
				        stage.setTitle("EVYOU");
					} catch (Exception e) {
						
					}
				}
			}
			
			//정수만 입력하라는 경고 Alert창 호출
			catch(NumberFormatException e)
			{
				try {
					Parent root = FXMLLoader.load(Class.forName("main.controller.PaypopupController").getResource("IntegersAlert.fxml"));
					Scene scene = new Scene(root);
			        Stage stage = new Stage();
			        stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
			        stage.setScene(scene);
			        stage.show();
			        stage.setTitle("EVYOU");
				} catch (Exception e1) {
					
				}
			}
		});
	}
}