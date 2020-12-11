package themHoaDon;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;


public class Main_ThemHoaDon extends Application {
	Button button;
	@Override
	public void start(Stage primaryStage) {
		
			try {
				Parent root = FXMLLoader.load(getClass().getResource("ThemHoaDon.fxml"));
	            Scene scene = new Scene(root);
	            //primaryStage.initStyle(StageStyle.UNDECORATED);

				scene.getStylesheets().add(getClass().getResource("application2.css").toExternalForm());
				
				primaryStage.setScene(scene);
				primaryStage.getIcons().add(new Image("img/logo.png"));
				primaryStage.setTitle("THÊM HOÁ ĐƠN");
				primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
