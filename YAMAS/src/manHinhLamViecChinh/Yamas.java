package manHinhLamViecChinh;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;

import com.sun.javafx.application.LauncherImpl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Yamas extends Application {
	@Override
	public void init() throws Exception {
		// Do some heavy lifting
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
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
			Parent root = FXMLLoader.load(getClass().getResource("Yamas2.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			if (screenSize.width < 1600 && screenSize.width>1500)
				primaryStage.setFullScreen(true);
			else {
				// primaryStage.initStyle(StageStyle.UNDECORATED);
				//primaryStage.setResizable(false);
				primaryStage.setFullScreen(false);
			}
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("img/logo.png"));
			scene.getStylesheets().add(getClass().getResource("application2.css").toExternalForm());
			
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public static void main(String[] args) throws Exception {
		// System.out.println(args);
		LauncherImpl.launchApplication(Yamas.class, SplashScreen.class, args);
		// launch(args);
	}

}
