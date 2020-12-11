package manHinhLamViecChinh;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Yamas extends Application {
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
		    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		    if(screenSize.width < 1600) primaryStage.setFullScreen(true);
		    else {
		    	//primaryStage.initStyle(StageStyle.UNDECORATED);
		    	primaryStage.setResizable(false);
		    	primaryStage.setFullScreen(false);
		    }
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
