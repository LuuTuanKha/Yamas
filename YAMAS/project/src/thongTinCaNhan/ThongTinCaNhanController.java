package thongTinCaNhan;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ThongTinCaNhanController implements Initializable {
	private ObservableList<String> list = FXCollections.observableArrayList("    Thông tin cá nhân","    Thêm hoá đơn","    Danh sách hoá đơn","    Thống kê hoá đơn");
	private ObservableList<String> listLoaiXe = FXCollections.observableArrayList("Tất cả", "Xe số", "Xe tay ga",
			"Xe thể thao");
	private ObservableList<String> listPhanKhoi = FXCollections.observableArrayList("Tất cả", "Từ 100cc đến 135cc",
			"Từ 135cc đến 175cc", "Lớn hơn 175");
	private ObservableList<String> listMauSac = FXCollections.observableArrayList("Tất cả", "Đen", "Trắng", "Xám", "Đỏ",
			"Vàng", "Xanh lá cây", "Xanh Dương", "Xanh Aqua");
	private ObservableList<String> listGiaThanh = FXCollections.observableArrayList("Tất cả", "Đen", "Trắng", "Xám",
			"Đỏ", "Vàng", "Xanh lá cây", "Xanh Dương", "Xanh Aqua");
	private ImageView imgLuu = new ImageView(new Image("img/save.png"));
	private ImageView imgXT = new ImageView(new Image("img/deletewhite.png"));
	private ImageView imgHT = new ImageView(new Image("img/return.png"));
	
	private Image imgSP = new Image("img/ex01.png");
	//private Image imgXT = new Image("img/ex01.png");
	
	

	@FXML
	JFXListView<String> jlistView;
	@FXML
	JFXButton btnLuu;
	@FXML
	JFXButton btnHoanTac;
	@FXML
	JFXButton btnXoaTrang;
	
	
	@FXML
	JFXButton btnDangXuat;
	
	FlowPane paneSanPham= new FlowPane();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// THÔNG TIN SẢN PHẨM TRẢ VỀ
		// -KHỞI TẠO
		VBox vboxSP = new VBox();

		Label lblSanPham = new Label("Tên sản phẩm ở đây");
		Label SanPham = new Label();
		JFXButton btnThemSP = new JFXButton("Thêm");
		JFXButton btnXemThongTin = new JFXButton("Thông tin chi tiết");
		HBox hboxButton = new HBox();
		// -- THÊM VÀO ROOT
		hboxButton.getChildren().add(btnThemSP);
		hboxButton.getChildren().add(btnXemThongTin);

		vboxSP.getChildren().add(lblSanPham);
		vboxSP.getChildren().add(SanPham);
		vboxSP.getChildren().add(hboxButton);
		// --- XỬ LÝ GIAO DIỆN
		vboxSP.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		lblSanPham.getStyleClass().add("labelSP");
		lblSanPham.setAlignment(Pos.CENTER);

		lblSanPham.setPrefWidth(20);
		btnThemSP.getStyleClass().add("btnSanPham");
		btnXemThongTin.getStyleClass().add("btnSanPham");
		// btnThemSP.setStyle("-fx-text-fill: white");

		// btnXemThongTin.setStyle("fx-background-color: #f6931e; -fx-text-fill: white;
		// -fx-font-weight: BOLD");
		// btnXemThongTin.setStyle("-fx-text-fill: white");

		SanPham.setGraphic(new ImageView(imgSP));
		vboxSP.setStyle("-fx-background-color: WHITE");
		hboxButton.setPadding(new Insets(3, 3, 3, 10));
		hboxButton.setSpacing(10);
		
		btnLuu.setGraphic(imgLuu);
		btnXoaTrang.setGraphic(imgXT);
		btnHoanTac.setGraphic(imgHT);
		//paneSanPham.setPadding(new Insets(10, 10, 10, 10));
//		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
//		sp.setContent(paneSanPham);
//		sp.setFitToWidth(true);
//		sp.setFitToHeight(true);
//		sp.setPannable(true);
		//paneSanPham.getChildren().add(scroll);
		paneSanPham.setHgap(10);
		paneSanPham.setVgap(10);
		// SanPham.getStyleClass().add("buttonMenu");
		//paneSanPham.getChildren().add(vboxSP);
		// --------------------------
		
		
		// txtSearch.setPrefHeight(100);
		
		// TODO Auto-generated method stub
		jlistView.getSelectionModel().select("    Tìm kiếm sản phẩm");
		//btnGioHang.setGraphic(new ImageView(image));
		jlistView.setItems(list);

	}

	

}
