package main.map;

import java.net.URL;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;

//지도 위치 정보를 받아와 Webview로 표시해주는 클래스
public class MyBrowser extends Pane
{
    WebView webView = new WebView();
    final WebEngine webEngine = webView.getEngine();
    

    public MyBrowser(double lat, double lng)
    {
    	webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>()
    	{
			@Override
			public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue)
			{
				try {
					webEngine.executeScript("" +
		                     "window.lat = " + lat + ";" +
		                     "window.lon = " + lng + ";" +
		                     "document.goToLocation(window.lat, window.lon);"
		                     );
				}
				
				catch (Exception e)
				{
					System.out.println("잠시만 기다려주세요!");
				}
			}
		});
    	try {
    		 final URL urlGoogleMaps = getClass().getResource("Webview.html");
    	        webEngine.load(urlGoogleMaps.toExternalForm());
    	        webEngine.setOnAlert(new EventHandler<WebEvent<String>>()
    	        {
    	            @Override
    	            public void handle(WebEvent<String> e)
    	            {
    	                System.out.println(e.toString());
    	            }
    	        });

    	        getChildren().add(webView);

    	        HBox toolbar  = new HBox();
    	        toolbar.getChildren().addAll();

    	        getChildren().addAll(toolbar);

    	    
		} catch (Exception e) {
			// TODO: handle exception
		}
       
}
}
