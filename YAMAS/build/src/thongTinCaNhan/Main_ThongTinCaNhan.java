package thongTinCaNhan;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;


public class Main_ThongTinCaNhan extends Application {
	Button button;
	@Override
	public void start(Stage primaryStage) {
		
			try {
				Parent root = FXMLLoader.load(getClass().getResource("ThongTinCaNhan2.fxml"));
	            Scene scene = new Scene(root);
	            //primaryStage.initStyle(StageStyle.UNDECORATED);
	            //primaryStage.initStyle(StageStyle.UNDECORATED);
				scene.getStylesheets().add(getClass().getResource("application2.css").toExternalForm());
				
				primaryStage.setScene(scene);
				primaryStage.getIcons().add(new Image("img/logo.png"));
				primaryStage.setTitle("THÔNG TIN CÁ NHÂN");
				//primaryStage.centerOnScreen();
				primaryStage.setFullScreen(true);
				primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
