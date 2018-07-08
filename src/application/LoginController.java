package application;


import java.io.IOException;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;


public class LoginController {

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;
    
    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private Label signeinlabel;
      
    
    @FXML
    private void initialize(){
    	rootPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                	try{login(new ActionEvent());}catch(Exception e){}
                }
			}
		});
	}    
    
    @FXML
    private void login(ActionEvent e) throws IOException{
    	String userName = username.getText(),
    		   passWord = password.getText();

    	if(userName.equals(passWord) && userName.equals("admin")){
        	AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/Admin.fxml"));
        	rootPane.getChildren().setAll(pane);
        	Main.fullScreen(true);
    	}else{
    		username.setText(""); password.setText("");
    	}
    	
    	System.out.println(userName+"-->"+passWord);
    }

    
    @FXML
    private void signeIn(ActionEvent e) throws IOException{
    	AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/SignIn.fxml"));
    	rootPane.getChildren().setAll(pane);
    	rootPane.getScene().getWindow().setWidth(522);
    	rootPane.getScene().getWindow().setHeight(650);
    	rootPane.getScene().getWindow().centerOnScreen();
    }
    
    @FXML
    private void exite(ActionEvent e){
    	System.exit(0);
    }
    
    
    
    
}
