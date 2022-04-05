package main.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import charging.ChargeVO;
import common.DBConnection;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import member.JoinImpl;
import member.MemberTemp;

//로그인 했을 때 보여지는 화면 클래스
public class LogoutSideBarController implements Initializable  {

    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;
    @FXML
    private Button logoutBtn;
    @FXML
    private Button searchBtn;
    @FXML
    private Button sfavorbutton;
    @FXML
    private Button pointBtn;
    @FXML
    private Button modifyBtn;
    @FXML
    private Text id;
    
	MemberTemp mtemp = new MemberTemp();
	BorderPane root;
	
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
    	id.setText(mtemp.getId()+"님 만나서 반갑습니다!");
    	
    	//로그아웃 버튼을 클릭했을 때
    	logoutBtn.setOnAction((event) ->
    	{
    		Parent root;
			try {
				
				Stage logoutstage = (Stage) logoutBtn.getScene().getWindow();
				logoutstage.close();
				
				//set해준 id정보를 null로 바꿔준다
				mtemp.setId(null);
				
				//다시 LoginSidebar 화면을 호출
				root = FXMLLoader.load(Class.forName("main.controller.LogoutSideBarController").getResource("/main/fxml/LoginSideBar.fxml"));
				Scene scene = new Scene(root);
		        Stage stage = new Stage();
		        stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
		        stage.setScene(scene);
		        stage.show();
		        stage.setTitle("EVYOU");
			}
			
			catch (IOException e1)
			{
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
    	
    	//로그인 화면부분에서 로그인을 제외하고 조회, 즐겨찾기, 포인트, 회원정보 수정 페이지들을 호출
    	searchBtn.setOnAction((event) -> {
    		loadPage("Search");
    		
    	});
    	
    	sfavorbutton.setOnAction(e -> {
    		loadPage("Favorites");
    	});
	
    	pointBtn.setOnAction(e -> {
    		loadPage("Point");
    	});
    	modifyBtn.setOnAction(e -> {
    		loadPage("Modify");
    	});
}
    
    //페이지를 호출 해오는 loadPage 메소드
    private void loadPage(String page) {
        try {
            Parent root = FXMLLoader.load(Class.forName("main.controller.LogoutSideBarController").getResource("/main/fxml/" + page + ".fxml"));
            bp.setCenter(root);
        } catch (IOException ex) {
            Logger.getLogger(LogoutSideBarController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

}