package main.launch;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

//Main 클래스
public class Main extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
    	System.setProperty("prism.lcdtext", "false");
    	Font.loadFont(getClass().getResourceAsStream("/main/font/GmarketSansTTFMedium.ttf"), 10);
    	Font.loadFont(getClass().getResourceAsStream("/main/font/GmarketSansTTFBold.ttf"), 10);
    	Font.loadFont(getClass().getResourceAsStream("/main/font/NanumBarunGothic.ttf"), 10);

        Parent root = FXMLLoader.load(Class.forName("main.launch.Main").getResource("/main/fxml/Main.fxml"));
        
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
        stage.setScene(scene);
        stage.show();
        stage.setTitle("EVYOU");
    }

    public static void main(String[] args)
    {
        launch(args);
    }
    
}
