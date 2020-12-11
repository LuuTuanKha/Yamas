package timKiemMatHang;

import java.awt.Window;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.geom.Area;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class MenuChucNangController implements Initializable {
	@FXML
	Button btnChon;
	@FXML
	AnchorPane pane;
	@Override
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnChon.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				DirectoryChooser chooser = new DirectoryChooser();
				Stage newWindow = new Stage();
                newWindow.setTitle("Second Stage");
                StackPane secondaryLayout = new StackPane();
               
                Scene secondScene = new Scene(secondaryLayout, 230, 100);
                
				newWindow.setScene(secondScene);
 
                // Set position of second window, related to primary window.
                newWindow.setX(300);
                newWindow.setY(300);
				File file = chooser.showDialog(newWindow);
				System.out.println(file.getAbsolutePath());
			}
			 
           
        });
		// TODO Auto-generated method stub
		
	}
	

	}

	