package manHinhLamViecChinh;

import com.jfoenix.controls.JFXProgressBar;

import javafx.application.Preloader;
import javafx.application.Preloader.StateChangeNotification.Type;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SplashScreen extends Preloader {
	JFXProgressBar bar;
	Label lblPer100;
	private Stage stage;

	private Scene createPreloaderScene() {
		bar = new JFXProgressBar();
		lblPer100 = new Label("");
		BorderPane pane = new BorderPane();
		HBox p = new HBox();
		p.getChildren().add(bar);
		p.getChildren().add(lblPer100);
		// p.setCenter(bar);
		pane.setBottom(p);
		bar.setProgress(0);
		return new Scene(pane, 700, 400);
	}

	public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;
		try {

			Parent root = FXMLLoader.load(getClass().getResource("Splash.fxml"));
			// Parent root = createPreloaderScene();
			Scene scene = new Scene(root);
			// primaryStage.initStyle(StageStyle.UNDECORATED);

			scene.getStylesheets().add(getClass().getResource("application2.css").toExternalForm());
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			//primaryStage.getIcons().add(new Image("img/logo.png"));
			//primaryStage.setTitle("THÊM HOÁ ĐƠN");
			primaryStage.getIcons().add(new Image("img/logo.png"));
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

//	@Override
//	public void handleProgressNotification(ProgressNotification pn) {
//		bar.setProgress(pn.getProgress());
//
//	}

	@Override
	public void handleStateChangeNotification(StateChangeNotification stateChangeNotification) {
		if (stateChangeNotification.getType() == Type.BEFORE_START) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stage.hide();
		}
	}

}
