package main.controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import charging.ChargeDAO;
import charging.ChargeImpl;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MainController implements Initializable
{
	@FXML
	private ImageView main;
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		//thread를 이용하여 충전소 정보가 로딩 완료되었을 때 LoginSidebar화면 부분을 호출
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				ChargeDAO n = new ChargeImpl();
		    	n.refresh();
		    	Stage stage = (Stage) main.getScene().getWindow();
		    	Platform.runLater(() ->
		    	{
//					stage.close();
					try
					{
						Parent root;
						root = FXMLLoader.load(Class.forName("main.controller.MainController").getResource("/main/fxml/LoginSidebar.fxml"));
						Scene scene = new Scene(root);
						stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
						stage.setScene(scene);
						stage.show();
						stage.setTitle("EVYOU");
					}
					
					catch (IOException e)
					{
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			};
		};
		thread.start();
	}
}