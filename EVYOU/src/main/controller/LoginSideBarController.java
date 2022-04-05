package main.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import charging.ChargeVO;
import common.DBConnection;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import member.LoginDAO;
import member.LoginImpl;
import member.MemberTemp;

//로그인을 하지않았을 때 보여지는 창 LoginController.java와 동일
public class LoginSideBarController implements Initializable 
{
    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;
    @FXML
    private Button loginBtn;
    @FXML
    private Button searchBtn;
    @FXML
    private Button sfavorbutton;
    @FXML
    private Button pointBtn;
    @FXML
	private TextField input_id;
	
	@FXML
	private PasswordField input_pwd;
	
	@FXML
	private Button joinBtn;
	
	@FXML
	private Button login_bt;
	
	MemberTemp mtemp = new MemberTemp();
	BorderPane root;
	
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
    	LoginDAO log = new LoginImpl();
		input_pwd.setOnAction(enter -> {login_bt.fire();});
		login_bt.setOnAction((evnet)->
	    {
	    	String id = input_id.getText();
	    	String pwd = input_pwd.getText();
		      
		    System.out.println(input_id.getText());
		    System.out.println(input_pwd.getText());
		    
		    if((log.tryLogin(id, pwd)) == 1)
		    {
		    	id = input_id.getText();
		    	mtemp.setLoginFlag(true);
		    	mtemp.setId(id);
		    	MemberTemp.setPw(pwd);
		    	
				try {
					Parent root = FXMLLoader.load(Class.forName("main.controller.LoginSideBarController").getResource("/main/fxml/SuccessAlert.fxml"));
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
				    
				    
				} catch (Exception e) {
					
				}
				
		    } else {
		    	id = null;
		    	try {
		    	Parent root = FXMLLoader.load(Class.forName("main.controller.LoginSideBarController").getResource("/main/fxml/FailAlert.fxml"));
				Scene scene = new Scene(root);
			    Stage stage = new Stage();
			    stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
			    stage.setScene(scene);
			    stage.show();
			    stage.setTitle("EVYOU");
			    
			} catch (IOException e) {
				
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    }
		    System.out.println(id);
	    });
		
		joinBtn.setOnAction((event) ->
		{
			Parent root;
			try {
				root = FXMLLoader.load(Class.forName("main.controller.LoginSideBarController").getResource("/main/fxml/join.fxml"));
				Scene scene = new Scene(root);
			    Stage stage = new Stage();
			    stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
			    stage.setScene(scene);
			    stage.show();
			    stage.setTitle("회원가입");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
    	
		//사이드바 버튼들을 눌렀을 때 각 페이지들 호출하는 부분
    	loginBtn.setOnAction((event) -> {
    		loadPage("Login");
		});
    	
    	searchBtn.setOnAction((event) -> {
    		loadPage("Search");
    		
    	});
    	
    	sfavorbutton.setOnAction(e -> {
    		loadPage("Favorites");
    	});
	
    	pointBtn.setOnAction(e -> {
    		loadPage("Point");
    	});
}
    //페이지 호출해오는 loadPage 메소드
    private void loadPage(String page) {
        try {
            Parent root = FXMLLoader.load(Class.forName("main.controller.LoginSideBarController").getResource("/main/fxml/" +page + ".fxml"));
            bp.setCenter(root);
        } catch (IOException ex) {
            Logger.getLogger(LoginSideBarController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    
    
}
