package application;
	
import data.Controler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	public static Stage stage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage=primaryStage;
		Parent root = FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("-- DiNaRaKoM --");
		primaryStage.show();
		Controler.vide();
	}
	
	
	public static void fullScreen(boolean value){
		stage.setFullScreen(value);
	}
	
	public static void main(String[] args) {launch(args);}
}
