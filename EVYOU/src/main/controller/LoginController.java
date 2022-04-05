package main.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import member.LoginDAO;
import member.LoginImpl;
import member.MemberTemp;

public class LoginController implements Initializable
{
	@FXML
	private TextField input_id;
	
	@FXML
	private PasswordField input_pwd;
	
	@FXML
	private Button joinBtn;
	
	@FXML
	private Button login_bt;

	String chger_type;
	ComboBox<String> combobox;
	MemberTemp mtemp = new MemberTemp();
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		LoginDAO log = new LoginImpl();
		
		//비밀번호 입력후 enter키 입력시 로그인버튼이 클릭되게 해주는 부분
		input_pwd.setOnAction(enter -> {login_bt.fire();});
		
		//로그인버튼을 눌렀을 시
		login_bt.setOnAction((evnet)->
	    {
	    	String id = input_id.getText();
	    	String pwd = input_pwd.getText();
		      
		    System.out.println(input_id.getText());
		    System.out.println(input_pwd.getText());
		    
		    //tryLogin가 정상적으로 실행되었을 경우
		    if((log.tryLogin(id, pwd)) == 1)
		    {
		    	//MemberTemp에 로그인상태정보와 id, pwd를 set해두는 부분
		    	id = input_id.getText();
		    	mtemp.setLoginFlag(true);
		    	mtemp.setId(id);
		    	MemberTemp.setPw(pwd);
		    	
		    	//로그인 성공 alert창 출력
				try
				{
					Parent root = FXMLLoader.load(Class.forName("main.controller.LoginController").getResource("/main/fxml/SuccessAlert.fxml"));
					Scene scene = new Scene(root);
				    Stage stage = new Stage();
				    stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
				    stage.setScene(scene);
				    stage.show();
				    stage.setTitle("EVYOU");
				    Platform.runLater(()->{
				    	Stage loginstage = (Stage) login_bt.getScene().getWindow();
				        loginstage.close();	
				    });
				}
				
				catch (IOException e)
				{
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    
		    //로그인 실패했을 경우
		    else
		    {
		    	input_id.setText(null);
		    	input_pwd.setText(null);
		    	
		    	//FaliAlert창 출력
		    	try
		    	{
			    	Parent root = FXMLLoader.load(Class.forName("main.controller.LoginController").getResource("/main/fxml/FailAlert.fxml"));
					Scene scene = new Scene(root);
				    Stage stage = new Stage();
				    stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
				    stage.setScene(scene);
				    stage.show();
				    stage.setTitle("EVYOU");
		    	}
		    	catch (IOException e)
		    	{
				
		    	} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    System.out.println(id);
	    });
		
		//회원가입 버튼을 눌렀을 시 회원가입창 호출
		joinBtn.setOnAction((event) ->
		{
			Parent root;
			try {
				root = FXMLLoader.load(Class.forName("main.controller.LoginController").getResource("/main/fxml/join.fxml"));
				Scene scene = new Scene(root);
			    Stage stage = new Stage();
			    stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
			    stage.setScene(scene);
			    stage.show();
			    stage.setTitle("회원가입");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
	}

}