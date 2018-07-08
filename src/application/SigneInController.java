package application;

import java.io.IOException;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import data.Controler;
import data.Profile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;



public class SigneInController {

    @FXML
    private JFXTextField fullname;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXPasswordField password1;

    @FXML
    private JFXTextField account;

    @FXML
    private JFXTextField bin;

    @FXML
    private JFXTextField datePlace;

    @FXML
    private ComboBox<String> famille;

    @FXML
    private ComboBox<String> sex;

    @FXML
    private ComboBox<String> education;
    
    @FXML
    private AnchorPane rootPane;
    
	ObservableList<String> sexList = FXCollections.observableArrayList("Male","Female","Other");
	ObservableList<String> familleList = FXCollections.observableArrayList("married","Single","Other");
	ObservableList<String> educationLists = FXCollections.observableArrayList("graduate school","university","high school","Other");
	
	private boolean initialized = false;
	
    @FXML
    private void initialze() {
    	if(initialized) return;
    	initialized = true;
    	sex.setItems(sexList);
    	famille.setItems(familleList);
    	education.setItems(educationLists);	

    	rootPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                	try {
						signin(new ActionEvent());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
			}
		});
    
    }
    
    
    @FXML
    private void signin(ActionEvent e) throws IOException{
    	if(!champsVide() && password.getText().equals(password1.getText())){
    		int limitBAL = 300; // 300$
    		int userSex = sex.getItems().indexOf(sex.getValue()) + 1 ;
    		int userEducation = education.getItems().indexOf(education.getValue()) + 1 ;
    		int userMariage = famille.getItems().indexOf(famille.getValue()) + 1 ;
    		int age = 2018 - 1990;
    		
    		Profile profile = new Profile(limitBAL, userSex, userEducation, userMariage, age,1);
    		Controler.addProfile(profile);
    	}else {
        	password.setText("");
        	password1.setText("");    		    		
    	}
    	
    	if(champsVide()) return;
       	AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
    	rootPane.getChildren().setAll(pane);
    	rootPane.getScene().getWindow().setWidth(400);
    	rootPane.getScene().getWindow().setHeight(420);
    	rootPane.getScene().getWindow().centerOnScreen();  	
    }
        
    private boolean champsVide(){
    	if(fullname.getText().isEmpty() || datePlace.getText().isEmpty() ||
    			account.getText().isEmpty() || bin.getText().isEmpty() || 
    			password.getText().isEmpty() || password1.getText().isEmpty())
    		return true;
    	return false;
    }
    
    
    @FXML
    private void exite(ActionEvent e) throws IOException{
       	AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
    	rootPane.getChildren().setAll(pane);
    	rootPane.getScene().getWindow().setWidth(400);
    	rootPane.getScene().getWindow().setHeight(420);
    	rootPane.getScene().getWindow().centerOnScreen();
    }

    
}

