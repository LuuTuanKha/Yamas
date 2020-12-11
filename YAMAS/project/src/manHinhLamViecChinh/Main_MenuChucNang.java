package manHinhLamViecChinh;
	
import java.net.URL;
import java.util.List;



import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;


public class Main_MenuChucNang extends Application {
	
	public Main_MenuChucNang() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void start(Stage primaryStage) {
		try {
//			Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
//            Scene scene = new Scene(root);
//            //primaryStage.initStyle(StageStyle.UNDECORATED);
//
//			scene.getStylesheets().add(getClass().getResource("application2.css").toExternalForm());
//			//primaryStage.setFullScreen(true);
//
//			primaryStage.setScene(scene);
			
//			primaryStage.setTitle("-YAMAS-");
//			primaryStage.show();
			
			URL location = getClass().getResource("Yamas2.fxml");
		    FXMLLoader fxmlLoader = new FXMLLoader();
		    fxmlLoader.setLocation(location);
		    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
		    Parent root = (Parent) fxmlLoader.load(location.openStream());
		    Scene scene = new Scene(root);
		    primaryStage.setScene(scene);
		    primaryStage.setFullScreen(true);
		    primaryStage.getIcons().add(new Image("img/logo.png"));
		    scene.getStylesheets().add(getClass().getResource("application2.css").toExternalForm());
		    MHLVChinhController mainController = fxmlLoader.getController();
		    mainController.setStage(primaryStage);
		    mainController.showStage();
		} catch(Exception e) {
			e.printStackTrace();
			
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println(args);
		launch(args);
	}
	
}
