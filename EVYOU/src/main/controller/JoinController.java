package main.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import member.JoinDAO;
import member.JoinImpl;

public class JoinController implements Initializable
{

	@FXML
	private TextField joinId;
	@FXML
	private PasswordField joinPwd;
	@FXML
	private ComboBox<String> chgerTypeBox;
	@FXML
	private TextField carType;
	@FXML
	public Button joinSubmit;
	@FXML
	private Button checkId;

	String chgerType1;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		JoinDAO join = new JoinImpl();
	
		//충전기 타입 String배열
		String[] chgerType = {"DC차데모", "AC완속", "DC차데모+AC3상", "DC콤보", "DC차데모+DC콤보", "DC차데모+AC3상+DC콤보", "AC3상"};
		ObservableList<String> chgerTypeList = FXCollections.observableArrayList(chgerType);
		
		//콤보박스에 충전기 타입 String배열 항목들을 모두 넣어준다.
		chgerTypeBox.getItems().addAll(chgerTypeList);
	    
		//콤보박스에서 항목을 선택했을 시 하나의 value만 가져온다
		chgerTypeBox.setOnAction((e)->
		{
			chgerType1 = (String) chgerTypeBox.getValue();
			System.out.println("선택한 충전기 타입 : " + chgerTypeBox.getValue());
		});
		
		//회원가입 버튼을 눌렀을 시
	    joinSubmit.setOnAction((e)->
	    {
	    	String id = joinId.getText();
	    	String pwd = joinPwd.getText();
	    	String car_type = carType.getText();
		      
		    System.out.println(joinId.getText());
		    System.out.println(joinPwd.getText());
		    System.out.println(carType.getText());
		    
		    join.tryJoin(id, pwd, chgerType1, car_type);
		    Stage joinstage = (Stage) joinSubmit.getScene().getWindow();
			joinstage.close();
	    });
	    
	    //중복체크를 눌렀을 시
	    checkId.setOnAction((e)->
	    {
	    	String id = joinId.getText();
	    	
	    	//DB에 이미 같은 ID가 있을 경우 NotuseAlert창 출력
	    	if((join.check(id)) == true)
	    	{
	    		try
	    		{
			    	Parent root = FXMLLoader.load(Class.forName("main.controller.JoinController").getResource("/main/fxml/NotuseAlert.fxml"));
					Scene scene = new Scene(root);
				    Stage stage = new Stage();
				    stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
				    stage.setScene(scene);
				    stage.show();
				    stage.setTitle("EVYOU");
				}
	    		
	    		catch (IOException e1)
	    		{
					
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
	    	}
	    	//ID를 입력하지 않고 중복체크를 눌렀을 경우 NotuseAlert창 출력
	    	else if(joinId.getText().isEmpty())
	    	{
	    		try
	    		{
			    	Parent root = FXMLLoader.load(Class.forName("main.controller.JoinController").getResource("/main/fxml/NotuseAlert.fxml"));
					Scene scene = new Scene(root);
				    Stage stage = new Stage();
				    stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
				    stage.setScene(scene);
				    stage.show();
				    stage.setTitle("EVYOU");
				}
	    		
	    		catch (IOException e1)
	    		{
					
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    	}
	    	
	    	//사용가능한 경우 UsableAlert창 출력
	    	else
	    	{
	    		try
	    		{
			    	Parent root = FXMLLoader.load(Class.forName("main.controller.JoinController").getResource("/main/fxml/UsableAlert.fxml"));
					Scene scene = new Scene(root);
				    Stage stage = new Stage();
				    stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
				    stage.setScene(scene);
				    stage.show();
				    stage.setTitle("EVYOU");
				}
	    		
	    		catch (IOException e1)
	    		{
					
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    	}
	    });
	}
}