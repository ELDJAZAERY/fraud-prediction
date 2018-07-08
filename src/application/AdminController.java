package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.jfoenix.controls.JFXTextField;

import data.Controler;
import data.Payement;
import data.Profile;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class AdminController {

	 @FXML
	 private FontAwesomeIconView blocked;
	 
	@FXML
	private AnchorPane rootPane;
	  
    @FXML
    private TableView<LineTableProfile> profileTab;

    @FXML
    private Label penaliteVal;
    
    @FXML 
    private Label minimalRevenuVal;

    @FXML 
    private Label maximalRevenuVal;

    @FXML
    private TableColumn<?, ?> id;

    @FXML
    private TableColumn<?, ?> limitBall;

    @FXML
    private TableColumn<?, ?> balance;

    @FXML
    private TableColumn<?, ?> age;

    @FXML
    private TableColumn<?, ?> gegnre;

    @FXML
    private TableColumn<?, ?> situatoin;

    @FXML
    private TableColumn<?, ?> education;
    
    
    @FXML
    private TableView<LineTabPayement> histPay;

    @FXML
    private TableColumn<?, ?> Date;

    @FXML
    private TableColumn<?, ?> Bille;

    @FXML
    private TableColumn<?, ?> montant;

    @FXML
    private TableColumn<?, ?> status;

    @FXML
    private Label userID;

    @FXML
    private TextArea action;

    @FXML
    private Label totalDP;

    @FXML
    private Label ScusDP;

    @FXML
    private Label userClass;

    @FXML
    private Label userScore;
    
    @FXML
    private Label credibleValue;
    
    @FXML
    private ComboBox<String> typeData;

    @FXML
    private JFXTextField RechercheID;
    
    @FXML
    private Pane clientDescripPane;

	ObservableList<String> TypeData = FXCollections.observableArrayList("Credibles","InCredibles","ALL");
    
    private static ArrayList<Profile> actuelProfile = new ArrayList<>();
    private boolean initialized = false;
    
    
    @FXML
    private void test(){
    	System.out.println("mouse moved !!");
    }
    
	@FXML
	private void initialize() {
		if(initialized) return;
    	initialized=true;
    	clientDescripPane.setVisible(false);
// List des Profiles		
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		limitBall.setCellValueFactory(new PropertyValueFactory<>("limitBAL"));
		balance.setCellValueFactory(new PropertyValueFactory<>("Ballance"));
		age.setCellValueFactory(new PropertyValueFactory<>("age"));
		gegnre.setCellValueFactory(new PropertyValueFactory<>("sex"));
		situatoin.setCellValueFactory(new PropertyValueFactory<>("mariage"));
		education.setCellValueFactory(new PropertyValueFactory<>("education"));
// Historique de Payement 		
		Date.setCellValueFactory(new PropertyValueFactory<>("Date"));
		Bille.setCellValueFactory(new PropertyValueFactory<>("Bille"));
		montant.setCellValueFactory(new PropertyValueFactory<>("montant"));
		status.setCellValueFactory(new PropertyValueFactory<>("payStatus"));

		typeData.setItems(TypeData);
    	showData();
    	
    	
    	profileTab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    	    if (newSelection != null) {
    	    	if(!clientDescripPane.isVisible()) clientDescripPane.setVisible(true);
    	        showUserDesc(newSelection.getId());
    	    }
    	});
    	
    	
    	RechercheID.textProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable,
    	            String oldValue, String newValue) {
    	    	MAJ();
    	    }
    	});
    	
		typeData.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue){
				MAJ();
			}
		});
	
	}
    
	private void MAJ(){
    	profileTab.getItems().clear();
    	showData();
    }
	
	
	
    private void showData(){
		ObservableList<LineTableProfile> data = FXCollections.observableArrayList();
		actuelProfile = Controler.getNonCredibles();
		
		if(typeData.getValue()!=null){
			if(typeData.getValue().equals("Credibles")){
				actuelProfile = Controler.getCredibles();
			}else if(typeData.getValue().equals("InCredibles")){
				actuelProfile = Controler.getNonCredibles();
			}else{
				actuelProfile = Controler.profiles;
			}			
		}
		
		if(!RechercheID.getText().isEmpty()){
			try{
				int id = Integer.parseInt(RechercheID.getText());
				for(Profile p:actuelProfile){
					if(p.getId()==id)
					data.add(new LineTableProfile(p));
				}
			}catch(Exception e){}
			
			profileTab.getItems().clear();
			profileTab.getItems().addAll(data);
			return;
		}
		
		for(Profile p:actuelProfile)
			data.add(new LineTableProfile(p));
		
		profileTab.getItems().clear();
		profileTab.getItems().addAll(data);
		
    	penaliteVal.setText(""+Controler.predireInteret());
    	minimalRevenuVal.setText(""+Controler.predireMinRevenu());
    	maximalRevenuVal.setText(""+Controler.predireMaxRevenu());
    }
    
    
    
    private void showUserDesc(String idString){
    	int id = Integer.parseInt(idString);
    	Profile p = null;
    	for(Profile pf:actuelProfile)
    		if(pf.getId()==id) p = pf;
    	
    	if(p==null) return;
    	
    	ObservableList<LineTabPayement> historiesPay = FXCollections.observableArrayList();
    	Collections.sort(p.getHistoriePay(),Collections.reverseOrder());
    	for(Payement pay:p.getHistoriePay())
    		historiesPay.add(new LineTabPayement(pay));
    	
    	histPay.getItems().clear();
    	histPay.getItems().addAll(historiesPay);
    	
    	userID.setText(""+id);
    	userScore.setText(""+p.getScorePoint());
    	userClass.setText(p.profClass().name());
    	totalDP.setText(""+p.DPTotal());
    	ScusDP.setText(""+p.DPSucses());
    	
    	if(!p.getHistoriePay().isEmpty()){
        	if(p.credible()) credibleValue.setText("Credible"); else credibleValue.setText("Non Credible");
    	}else credibleValue.setText("Noveau Utilisateur");
    	action.setText(p.rappelle());
    	
    	if(!p.blocked()) blocked.setVisible(false);
    	else blocked.setVisible(true);
    }
    
    @FXML
    private void Logout(ActionEvent e) throws IOException{
       	AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
    	rootPane.getChildren().setAll(pane);
    	Main.fullScreen(false);
    	rootPane.getScene().getWindow().setWidth(400);
    	rootPane.getScene().getWindow().setHeight(420);
    	rootPane.getScene().getWindow().centerOnScreen();
    }
    
}