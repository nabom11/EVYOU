package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.DBConnection;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.controller.JoinController;

public class JoinImpl implements JoinDAO
{
	Connection conn;
	ResultSet rs;
	PreparedStatement pstmt;
	boolean judge = false;
	
	//회원가입에 필요한 정보들을 매개변수로 받아 DB에 INSERT하는 메소드
	public int tryJoin(String id, String pwd, String chger_type, String car_type)
	{
		try
		{
			//비밀번호가 입력 되었을 때
			if(!pwd.isEmpty())
			{
				conn = DBConnection.tryConnection();
				String query = "INSERT INTO MEMBER VALUES(?, ?, ?, ?)";
				pstmt = conn.prepareStatement(query);
				System.out.println(query);
				
				pstmt.setString(1, id);
				pstmt.setString(2, pwd);
				pstmt.setString(3, chger_type);
				pstmt.setString(4, car_type);
				
				int result = pstmt.executeUpdate();
				
				//쿼리문 실행결과가 1일때 회원가입 완료
				if (result == 1)
				{
					try
					{
						Parent root = FXMLLoader.load(Class.forName("member.JoinImpl").getResource("/main/fxml/CompleteAlert.fxml"));
						Scene scene = new Scene(root);
				        Stage stage = new Stage();
				        stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
				        stage.setScene(scene);
				        stage.show();
				        stage.setTitle("EVYOU");
					}
					
					catch (Exception e)
					{
						
					}
					return 0;
				}
			}
			//비밀번호를 입력하지 않았을 때
			else
			{
				Parent root = FXMLLoader.load(Class.forName("member.JoinImpl").getResource("/main/fxml/CheckAlert.fxml"));
				Scene scene = new Scene(root);
		        Stage stage = new Stage();
		        stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
		        stage.setScene(scene);
		        stage.show();
		        stage.setTitle("EVYOU");
			}
			
		}
		
		//필요한 정보들을 입력하지 않았을 때
		catch(Exception e)
		{
			try
			{
				Parent root = FXMLLoader.load(Class.forName("member.JoinImpl").getResource("/main/fxml/CheckAlert.fxml"));
				Scene scene = new Scene(root);
		        Stage stage = new Stage();
		        stage.getIcons().add(new Image("file:src\\main\\images\\Lightning.png" ));
		        stage.setScene(scene);
		        stage.show();
		        stage.setTitle("EVYOU");
			}
			
			catch (Exception e1)
			{
				
			}
		}
		finally
		{
			DBConnection.close(conn, pstmt, rs);
		}
		System.out.println("다시 시도해주세요");
		return -1;
	}
	
	//ID중복체크를 검사하고 boolean자료형으로 return 해주는 메소드
	public boolean check(String id)
	{
		//judge = ID 중복체크 판단 boolean 변수
		judge = false;
		try
		{
			conn = DBConnection.tryConnection();
			
			String query = "SELECT * FROM MEMBER WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				judge = true;
			}
		}
		catch(SQLException e)
		{
			e.getMessage();
			e.printStackTrace();
		}
		finally
		{
			DBConnection.close(conn, pstmt, rs);
		}
		return judge;
	}
}
