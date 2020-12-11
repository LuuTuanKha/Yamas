package manHinhLamViecChinh;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import connectDATA.Connect_Data;
import dao.HoaDonDAO;
import dao.KhachHangDAO;
import dao.MatHangDAO;
import dao.NhanVienDAO;
import entity.CTHD;
import entity.HoaDon;
import entity.KhachHang;
import entity.MatHang;
import entity.NhanVien;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class MHLVChinhController implements Initializable {
	int start = 0;
	int end = 15;
	int pageCount = 1;
	int soLuongTrang = 1;

	@FXML
	Label lblTenNVLogin;
	DecimalFormat formatter = new DecimalFormat("###,###,### VNĐ");
	private NhanVien nv;
	@FXML
	AnchorPane yamas;
	@FXML
	AnchorPane LoginPane;
	@FXML
	BorderPane MenuPane;

	Stage thisStage;
	@FXML
	Button btnDangNhap;
	@FXML
	Button btnThoat;
	@FXML
	JFXTextField txtMaDangNhap;
	@FXML
	JFXPasswordField txtMatKhau;
	@FXML
	Label lblLGStatus;
	// ====dialog=====
	@FXML
	AnchorPane dialog;
	@FXML
	Label lblDialogTitle;
	@FXML
	Label lblDialogContent;
	@FXML
	JFXButton btnYes;
	@FXML
	JFXButton btnNo;

	// ==========
	public void setStage(Stage stage) {
		thisStage = stage;
	}

	public void showStage() {
		thisStage.setTitle("YAMAS");
		thisStage.show();
	}

	private int count = 1; // biến đếm tầng giao diện
	private ObservableList<String> list = FXCollections.observableArrayList("    Tạo giỏ hàng mới",
			"    Tạo hoá đơn mới", "    Quản lý khách hàng", "    Quản lý sản phẩm", "    Quản lý hoá đơn");
//	private ObservableList<String> listLoaiXe = FXCollections.observableArrayList("Tất cả","Xe số","Xe tay ga","Xe thể thao");
//	private ObservableList<String> listPhanKhoi = FXCollections.observableArrayList("Tất cả","Từ 100cc đến 135cc","Từ 135cc đến 175cc","Lớn hơn 175");
//	private ObservableList<String> listMauSac = FXCollections.observableArrayList("Tất cả","Đen","Trắng","Xám","Đỏ","Vàng","Xanh lá cây","Xanh Dương","Xanh Aqua");
//	private ObservableList<String> listGiaThanh = FXCollections.observableArrayList("Tất cả","Đen","Trắng","Xám","Đỏ","Vàng","Xanh lá cây","Xanh Dương","Xanh Aqua");
	private Image image = new Image("img/myprofile.png");
	private Image image2 = new Image("img/analysis.png");
	private Image image3 = new Image("img/customer.png");
	private Image image4 = new Image("img/bill.png");
	// btnMenu: Size : 250x250, image: 128x128, text: white

	@FXML
	JFXListView<String> jlistView;

//	@FXML
//	JFXTextField txtSearch;
	@FXML
	JFXButton btnQuayLai;

	@FXML
	JFXButton btnChucNang1;
	@FXML
	JFXButton btnChucNang2;
	@FXML
	JFXButton btnChucNang3;
	@FXML
	JFXButton btnChucNang4;
	@FXML
	JFXButton btnDangXuat;

	@FXML
	VBox vboxListView;
	Label lblMenu = new Label("SSSSSSSSSSSSSSSs");
	Label lblHeader;
	// ===========ABOUT============================
	@FXML
	JFXButton btnNoAbout;
	@FXML
	AnchorPane About;
	// =========thống kê====
	@FXML
	AnchorPane ThongKePane;

	// ==================MENU TÌM KIẾM SẢN PHẨM=====================
	private ObservableList<String> listLoaiXe = FXCollections.observableArrayList("Tất cả", "Xe số", "Xe tay ga",
			"Xe thể thao");
	private ObservableList<String> listPhanKhoi = FXCollections.observableArrayList("Tất cả", "Bé hơn 100cc",
			"Từ 100cc đến 135cc", "Từ 135cc đến 175cc", "Lớn hơn 175");
	private ObservableList<String> listMauSac = FXCollections.observableArrayList("Tất cả", "Đen", "Trắng", "Xám", "Đỏ",
			"Vàng", "Xanh lá", "Xanh Dương", "Xanh Aqua");
	private ObservableList<String> listGiaThanh = FXCollections.observableArrayList("Tất cả", "Dưới 30 triệu VNĐ",
			"Từ 30 - 50 Triệu VNĐ", "Từ 50 - 80 triệu VNĐ ", "Từ 80-100 Triệu VNĐ", "Trên 100 Triệu VNĐ");
	private Image imageshopping = new Image("img/shopping.png");
	private Image imgSP = new Image("img/ex01.png");

	@FXML
	JFXTextField txtSearch;
	@FXML
	JFXButton btnTimKiem;
	@FXML
	JFXButton btnGioHang;
	@FXML
	JFXButton btnTaoHoaDon;
	@FXML
	JFXButton btnXoaSPGioHang;
	@FXML
	JFXButton btnXoaGioHang;
	@FXML
	Label lblTrangThaiThemSP;
	@FXML
	JFXComboBox<String> jcbLoaiXe;
	@FXML
	JFXComboBox<String> jcbPhanKhoi;
	@FXML
	JFXComboBox<String> jcbMauSac;
	@FXML
	JFXComboBox<String> jcbGiaThanh;
	@FXML
	FlowPane paneSanPham = new FlowPane();
	@FXML
	AnchorPane chiTietSP;
	@FXML
	AnchorPane gioHang;
	@FXML
	Label lblTenSP;
	@FXML
	Label lblTongTienGH;
	@FXML
	Label lblPageQLSP;
	@FXML
	TableView<CTHD> tableGioHang = new TableView<>();
	@FXML
	TableColumn<CTHD, String> maGioHang = new TableColumn<CTHD, String>();
	@FXML
	TableColumn<CTHD, Double> donGiaGioHang = new TableColumn<CTHD, Double>();
	@FXML
	TableColumn<CTHD, Integer> soLuongGioHang = new TableColumn<CTHD, Integer>();
	@FXML
	TableColumn<CTHD, Double> thanhTienGioHang = new TableColumn<CTHD, Double>();

	ObservableList<CTHD> dsGioHang = FXCollections.observableArrayList();
	Double thanhtienGioHang = 0.0;
//	@FXML
//	TableColumn<MatHang, Integer> soLuongSPCol1 = new TableColumn<MatHang, Integer>();
	// ==================HẾT MENU TÌM KIẾM SP===============

	// =======var======THÔNG TIN CÁ NHÂN=========================================
	private ImageView imgLuu = new ImageView(new Image("img/save.png"));
	private ImageView imgXT = new ImageView(new Image("img/deletewhite.png"));
	@FXML
	private ImageView imgHT = new ImageView(new Image("img/return.png"));
	@FXML
	private Label lblmaSp;
	@FXML
	private Label lbltenSp;
	@FXML
	private Label lblLoaiSp;
	@FXML
	private Label lblSoLuongSp;
	@FXML
	private Label lblDungTichBXSp;
	@FXML
	private Label lblDungTichDMSp;
	@FXML
	private Label lblHeThongLyHopSp;
	@FXML
	private Label lblMoTaSp;
	@FXML
	private Label lblMauSp;
	@FXML
	private Label lblDongCoSp;
	@FXML
	private Label lblKichThuocSp;
	@FXML
	private Label lblKhungSp;
	@FXML
	private Label lblGiaSp;
	@FXML
	private ImageView imgViewBIG;
	// private Image imgSP = new Image("img/ex01.png");
	// private Image imgXT = new Image("img/ex01.png");

	@FXML
	JFXButton btnLuu;
	@FXML
	JFXButton btnHoanTac;
	@FXML
	JFXButton btnXoaTrang;
	@FXML
	AnchorPane ThongTinCaNhan;
	@FXML
	JFXTextField txtMaNVTT;
	@FXML
	JFXTextField txtHoNVTT;
	@FXML
	JFXTextField txtTenNVTT;
	@FXML
	JFXTextField txtChucVuNVTT;
	@FXML
	JFXTextField txtLuongNVTT;
	@FXML
	JFXTextField txtCaLamNVTT;
	@FXML
	JFXTextField txtNgayVaoLamNVTT;
	@FXML
	JFXTextField txtDiaChiNVTT;
	@FXML
	JFXTextField txtGioiTinhNVTT;
	@FXML
	JFXTextField txtMatKhauNVTT;

	// =================HẾT TT CÁ NHÂN============================================

	// ================VAR LẬP HOÁ ĐƠN=============================================
	@FXML
	AnchorPane themHoaDon;
	@FXML
	JFXButton btnTTKH;
	@FXML
	JFXButton btnGioHangHD;
	@FXML
	JFXButton btnTaoHDMoi;

	@FXML
	TableView<CTHD> tableLapHoaDon = new TableView<>();
	@FXML
	TableColumn<CTHD, String> maGioHang1 = new TableColumn<CTHD, String>();
	@FXML
	TableColumn<CTHD, Double> donGiaGioHang1 = new TableColumn<CTHD, Double>();
	@FXML
	TableColumn<CTHD, Integer> soLuongGioHang1 = new TableColumn<CTHD, Integer>();
	@FXML
	TableColumn<CTHD, Double> thanhTienGioHang1 = new TableColumn<CTHD, Double>();
	@FXML
	TableColumn<CTHD, String> tenSP1 = new TableColumn<CTHD, String>();

	@FXML
	JFXTextField txtMaLHD;
	@FXML
	JFXTextField txtHoNVLHD;
	@FXML
	JFXTextField txtTenNVLHD;
	@FXML
	JFXTextField txtMaNVLHD;
	@FXML
	JFXTextField txtHoKHLHD;
	@FXML
	JFXTextField txtTenKHLHD;
	@FXML
	JFXTextField txtCMNDKHLHD;
	@FXML
	JFXTextField txtNgayLHD;

	// ObservableList<CTHD> dsGioHang = FXCollections.observableArrayList();

	// ==================HẾT LẬP HĐ================================================

	// ===================VAR QUẢN LÝ SẢN PHẨM===================================
	@FXML
	TableView<MatHang> tableQLSP = new TableView<>();
	@FXML
	TableColumn<MatHang, String> maSPCol = new TableColumn<MatHang, String>();
	@FXML
	TableColumn CountSPCol = new TableColumn("#");
	@FXML
	TableColumn<MatHang, String> tenSPCol = new TableColumn<MatHang, String>();
	@FXML
	TableColumn<MatHang, String> loaiSPCol = new TableColumn<MatHang, String>();
	@FXML
	TableColumn<MatHang, Integer> soLuongSPCol = new TableColumn<MatHang, Integer>();
	@FXML
	TableColumn<MatHang, Double> donGiaSPCol = new TableColumn<MatHang, Double>();
	@FXML
	TableColumn<MatHang, Double> binhXangSPCol = new TableColumn<MatHang, Double>();
	@FXML
	TableColumn<MatHang, Double> dauMaySPCol = new TableColumn<MatHang, Double>();
	@FXML
	TableColumn<MatHang, String> lyDongSPCol = new TableColumn<MatHang, String>();
	@FXML
	TableColumn<MatHang, String> moTaCol = new TableColumn<MatHang, String>();
	@FXML
	TableColumn<MatHang, String> mauXeSPCol = new TableColumn<MatHang, String>();
	@FXML
	TableColumn<MatHang, Double> dongCoSPCol = new TableColumn<MatHang, Double>();
	@FXML
	TableColumn<MatHang, String> kichThuocSPCol = new TableColumn<MatHang, String>();
	@FXML
	TableColumn<MatHang, String> khungXeSPCol = new TableColumn<MatHang, String>();
	@FXML
	TableColumn<MatHang, String> hinhAnhSPCol = new TableColumn<MatHang, String>();
	ObservableList<MatHang> dsQLSP = FXCollections.observableArrayList();
	@FXML
	JFXButton btnLeft;
	@FXML
	JFXButton btnRight;
	@FXML
	JFXButton btnFirst;
	@FXML
	JFXButton btnLast;

	@FXML
	Label trangThaiQLMatHang;
	@FXML
	JFXComboBox<String> jcbLoaiXe1;
	@FXML
	JFXComboBox<String> jcbPhanKhoi1;
	@FXML
	JFXComboBox<String> jcbMauSac1;
	@FXML
	JFXComboBox<String> jcbGiaThanh1;
	@FXML
	JFXButton btnSearchQLSP;
	@FXML
	JFXTextField txtSearchQLSP;
	@FXML
	JFXCheckBox cbDonGiaQLSP;
	@FXML
	JFXTextField lblMaMatHangQLSP;
	@FXML
	JFXTextField lblTenMatHangQLSP;
	@FXML
	JFXTextField lblLoaiMatHangQLSP;
	@FXML
	JFXTextField lblSoLuongMatHangQLSP;
	@FXML
	JFXTextField lblDonGiaMatHangQLSP;
	@FXML
	JFXTextField lblBinhXangMatHangQLSP;
	@FXML
	JFXTextField lblDauMayMatHangQLSP;
	@FXML
	JFXTextField lblKichThuocMatHangQLSP;
	@FXML
	JFXTextField lblLyDongMatHangQLSP;
	@FXML
	JFXTextField lblMauXeQLSP;
	@FXML
	JFXTextField lblDongCoMatHangQLSP;
	@FXML
	JFXTextField lblKhungXeMatHangQLSP;
	@FXML
	JFXTextField lblMoTaMatHangQLSP;
	@FXML
	JFXButton btnThemHinhAnhQLSP;
	@FXML
	ImageView imgViewQLSP;
	@FXML
	JFXButton btnLuuQLSP;
	@FXML
	JFXButton btnXoaTrangQLSP;
	@FXML
	JFXButton btnXoaQLSP;
	@FXML
	JFXButton btnThemQLSP;
	@FXML
	JFXButton btnHoanTacQLSP;

	// =====================VAR QUẢN LÝ KHÁCH
	// HÀNG===================================
	@FXML
	AnchorPane QLKhachHangPane;
	@FXML
	JFXButton btnThemKHVaoHD;
	@FXML
	TableView<KhachHang> tableQLKhachHang = new TableView<>();
	@FXML
	TableColumn<KhachHang, String> soCMNDCol = new TableColumn<KhachHang, String>();
	@FXML
	TableColumn<KhachHang, String> hoKHCol = new TableColumn<KhachHang, String>();
	@FXML
	TableColumn<KhachHang, String> tenKHCol = new TableColumn<KhachHang, String>();
	@FXML
	TableColumn<KhachHang, String> gioiTinhKHCol = new TableColumn<KhachHang, String>();
	@FXML
	TableColumn<KhachHang, String> diaChiKHCol = new TableColumn<KhachHang, String>();
	@FXML
	TableColumn<KhachHang, String> sDTKHCol = new TableColumn<KhachHang, String>();

	ObservableList<KhachHang> dsQLKhachHang = FXCollections.observableArrayList();
	@FXML
	JFXTextField txtsoCMNDQLKH;
	@FXML
	JFXTextField txttenQLKH;
	@FXML
	JFXTextField txthoQLKH;
	@FXML
	JFXTextField txtgioiTinhQLKH;
	@FXML
	JFXTextField txtDiaChiQLKH;
	@FXML
	JFXTextField txtSoDTQLKH;

	// =============================================================================

	// ====================BẮT SỰ KIỆN==============================
	@FXML
	AnchorPane timKiem_them_matHang;
	@FXML
	AnchorPane manHinhLamViecChinh;
	@FXML
	AnchorPane QLSanPham;

	@FXML
	private void ChuyenChucNang(MouseEvent e) {
		if (jlistView.getSelectionModel().getSelectedIndex() == 0) { // tạo giỏ hàng mới

			count = 0;
			timKiem_them_matHang.toFront();
			chiTietSP.setVisible(false);
			// lblTenSP.setText("Đã chuyển tên SP");
			// System.out.println(count);
		} else if (jlistView.getSelectionModel().getSelectedIndex() == 1) { // tạo hoá đơn mới
			XuLyLapHoaDon();
			count = 0;
			themHoaDon.toFront();
			// lblTenSP.setText("Đã chuyển tên SP");
			// System.out.println(count);
		} else if (jlistView.getSelectionModel().getSelectedIndex() == 3) { // quản lý sản phẩm / mặt hàng
			XuLyBangQuanLySanPham();
			count  = 0;
			// XuLyBangSQLSP();

		} else if (jlistView.getSelectionModel().getSelectedIndex() == 2) {
			count = 0;
			QLKhachHangPane.toFront();

		}

	}
	// ======================================================================

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {
			Connect_Data.getInstance().connect();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		About.setVisible(false);
		About.toBack();
		lblLGStatus.setVisible(false);
		// lblLGStatus.setVisible(false);
		// yamas.setStyle("-fx-background-color: transparent");
		LoginPane.setCenterShape(true);
		MenuPane.setVisible(false);
		dialog.setVisible(false);
		MatHangDAO matHangDAO = new MatHangDAO();

		// txtSearch.setPrefWidth(200);
		// jlistView.getSelectionModel().select(" Tìm kiếm sản phẩm");

		btnChucNang1.setGraphic(new ImageView(image));
		btnChucNang2.setGraphic(new ImageView(image2));
		btnChucNang3.setGraphic(new ImageView(image3));
		btnChucNang4.setGraphic(new ImageView(image4));
		btnChucNang1.setText("Text");
		btnQuayLai.setGraphic(new ImageView(new Image("img/back.png")));
		// vboxListView.getChildren().add(lblMenu);
		jlistView.setItems(list);
		// vboxListView.getChildren().add(jlistView);
//		btnQuayLai.setOnAction(event->{
//			
//		});

		// ==================THÔNG TIN CÁ NHÂN=======================
		btnLuu.setGraphic(imgLuu);
		btnXoaTrang.setGraphic(imgXT);
		btnHoanTac.setGraphic(imgHT);

		// ================hết==THÔNG TIN CÁ NHÂN=======================
		// ===================THÊM HOÁ ĐƠN=========================================
		btnTTKH.setGraphic(new ImageView(new Image("img/whiteUSER.png")));

		// =================THÊM SẢN PHẨM===========================
		btnGioHang.setGraphic(new ImageView(imageshopping));
		btnGioHangHD.setGraphic(new ImageView(imageshopping));

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
		// paneSanPham.setPadding(new Insets(10, 10, 10, 10));
//				sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
//				sp.setContent(paneSanPham);
//				sp.setFitToWidth(true);
//				sp.setFitToHeight(true);
//				sp.setPannable(true);
		// paneSanPham.getChildren().add(scroll);
		paneSanPham.setHgap(10);
		paneSanPham.setVgap(10);
		// SanPham.getStyleClass().add("buttonMenu");
		// paneSanPham.getChildren().add(vboxSP);
		// --------------------------

//				for (int i = 0; i < 20; i++) {
//					this.AddSP("tên SP" + i, i+"");
//
//				}

		txtSearch.setPrefWidth(200);

		// txtSearch.setPrefHeight(100);
		jcbLoaiXe.setItems(listLoaiXe);
		jcbLoaiXe.setValue("Tất cả");
		jcbPhanKhoi.setItems(listPhanKhoi);
		jcbPhanKhoi.setValue("Tất cả");
		jcbMauSac.setItems(listMauSac);
		jcbMauSac.setValue("Tất cả");
		jcbGiaThanh.setItems(listGiaThanh);
		jcbGiaThanh.setValue("Tất cả");
		// ====== XỬ LÝ COMBOBOX QL MẶT HÀNG=========
		jcbLoaiXe1.setItems(listLoaiXe);
		jcbLoaiXe1.setValue("Tất cả");
		jcbPhanKhoi1.setItems(listPhanKhoi);
		jcbPhanKhoi1.setValue("Tất cả");
		jcbMauSac1.setItems(listMauSac);
		jcbMauSac1.setValue("Tất cả");
		jcbGiaThanh1.setItems(listGiaThanh);
		jcbGiaThanh1.setValue("Tất cả");

		jlistView.getSelectionModel().select("    Tìm kiếm sản phẩm");
		btnGioHang.setGraphic(new ImageView(imageshopping));
		jlistView.setItems(list);
		// ============xử lý bảng GIỎ HÀNG=============
		maGioHang.setCellValueFactory(new PropertyValueFactory<>("maMatHang"));
		soLuongGioHang.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
		// soLuongGioHang.setCellValueFactory(new PropertyValueFactory<>("maMatHang"));

//		maSPCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MatHang, String>>() {
//
//			@Override
//			public void handle(CellEditEvent<MatHang, String> e) {
//				// TODO Auto-generated method stub
//				TablePosition<MatHang, String> pos = e.getTablePosition();
//
//				String newValue = e.getNewValue();
//
//				int row = pos.getRow();
//				MatHang mh = e.getTableView().getItems().get(row);
//
//				mh.setMaMatHang(newValue);
//			}
//		});

		soLuongGioHang.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		soLuongGioHang.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CTHD, Integer>>() {

			@Override
			public void handle(CellEditEvent<CTHD, Integer> e) {
				// TODO Auto-generated method stub
				TablePosition<CTHD, Integer> pos = e.getTablePosition();

				Integer newValue = e.getNewValue();

				int row = pos.getRow();
				CTHD mh = e.getTableView().getItems().get(row);
				MatHang chekmh = matHangDAO.getMatHangg(mh.getMaMatHang());
				if (chekmh.getSoLuong() >= newValue) {
					mh.setSoLuong(newValue);
					mh.setThanhTien();
					tableGioHang.refresh();
					thanhtienGioHang = 0.0;
					for (CTHD cthd : dsGioHang) {
						thanhtienGioHang += cthd.getThanhTien();

					}

					lblTongTienGH.setText(formatter.format(thanhtienGioHang));

				} else {

					dialog.toFront();
					dialog.setVisible(true);
					lblDialogTitle.setText("SỐ LƯỢNG VƯỢT QUÁ QUY ĐỊNH! ");
					lblDialogContent.setText(
							"Số lượng không vượt quá: " + chekmh.getSoLuong() + " \n Bạn có muốn nhập lại không?");
					btnNo.setDisable(true);
					btnYes.setOnAction(e2 -> {

						dialog.setVisible(false);
						tableGioHang.refresh();
						btnNo.setDisable(false);
						thanhtienGioHang = 0.0;
						for (CTHD cthd : dsGioHang) {
							thanhtienGioHang += cthd.getThanhTien();

						}

						lblTongTienGH.setText(formatter.format(thanhtienGioHang));

					});
				}
			}
		});
		// soLuongGioHang.setCellFactory(TextFieldTableCell.<CTHD>forTableColumn());
		donGiaGioHang.setCellValueFactory(new PropertyValueFactory<>("donGia"));
		thanhTienGioHang.setCellValueFactory(new PropertyValueFactory<>("thanhTien"));

		donGiaGioHang.setCellFactory(c -> {
			return new TableCell<CTHD, Double>() {
				@Override
				protected void updateItem(Double price, boolean empty) {
					super.updateItem(price, empty);
					if (empty) {
						setText(null);
					} else {
						setText(formatter.format(price));
					}
				}
			};
		});
		// thanhTienGioHang1.setCellValueFactory(new
		// PropertyValueFactory<>("thanhTien"));
		thanhTienGioHang.setCellFactory(c -> {
			return new TableCell<CTHD, Double>() {
				@Override
				protected void updateItem(Double price, boolean empty) {
					super.updateItem(price, empty);
					if (empty) {
						setText(null);
					} else {
						setText(formatter.format(price));
					}
				}
			};
		});
		tableGioHang.setEditable(true);
		tableGioHang.setItems(dsGioHang);
		tableGioHang.setPlaceholder(new Label("Giỏ hàng trống!"));
		tableGioHang.setOnMouseClicked(e -> {

		});

		// =================XỬ LÝ BẢNG LẬP HOÁ ĐƠN=============================
		maGioHang1.setCellValueFactory(new PropertyValueFactory<>("maMatHang"));
		soLuongGioHang1.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
		donGiaGioHang1.setCellValueFactory(new PropertyValueFactory<>("donGia"));

		donGiaGioHang1.setCellFactory(c -> {
			return new TableCell<CTHD, Double>() {
				@Override
				protected void updateItem(Double price, boolean empty) {
					super.updateItem(price, empty);
					if (empty) {
						setText(null);
					} else {
						setText(formatter.format(price));
					}
				}
			};
		});
		thanhTienGioHang1.setCellValueFactory(new PropertyValueFactory<>("thanhTien"));
		thanhTienGioHang1.setCellFactory(c -> {
			return new TableCell<CTHD, Double>() {
				@Override
				protected void updateItem(Double price, boolean empty) {
					super.updateItem(price, empty);
					if (empty) {
						setText(null);
					} else {
						setText(formatter.format(price));
					}
				}
			};
		});
		tenSP1.setCellValueFactory(new PropertyValueFactory<>("maHoaDon"));
		tableLapHoaDon.setItems(dsGioHang);
		tableLapHoaDon.setPlaceholder(new Label("Giỏ hàng trống!"));
		// ============xử lý bảng QL KHÁCH HÀNG======-===========

		soCMNDCol.setCellValueFactory(new PropertyValueFactory<>("soCMND"));
		tenKHCol.setCellValueFactory(new PropertyValueFactory<>("tenKH"));
		hoKHCol.setCellValueFactory(new PropertyValueFactory<>("hoKH"));
		gioiTinhKHCol.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
		diaChiKHCol.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
		sDTKHCol.setCellValueFactory(new PropertyValueFactory<>("soDT"));
		KhachHangDAO qlkhdao = new KhachHangDAO();

		dsQLKhachHang = qlkhdao.getDSKhachHang();
		tableQLKhachHang.setItems(dsQLKhachHang);
		tableQLKhachHang.setOnMouseClicked(e -> {
			KhachHang selectKH = tableQLKhachHang.getSelectionModel().getSelectedItem();
			txtsoCMNDQLKH.setText(selectKH.getSoCMND());
			txtDiaChiQLKH.setText(selectKH.getDiaChi());
			txtgioiTinhQLKH.setText(selectKH.getGioiTinh());
			txthoQLKH.setText(selectKH.getHoKH());
			txttenQLKH.setText(selectKH.getTenKH());
			txtSoDTQLKH.setText(selectKH.getSoDT());

			// ObservableList selectedCells = selectionModel.getSelectedCells();
		});
		// ===========XỬ LÝ BẢNG QLSP===========================
//			String maMatHang;
//		 String tenMatHang;
//		 String maLoai;
//		 int soLuong;
//		 double binhXang;
//		 double dauMay;
//		 String lyDong;
//		 String moTa;
//		 String mauXe;
//		 double dongCo;
//		 String kichThuoc;
//		 String khungxe;
//		 double dongia;
//		 String image;
		btnLeft.setGraphic(new ImageView(new Image("img/left.png")));
		btnRight.setGraphic(new ImageView(new Image("img/right.png")));
		btnFirst.setGraphic(new ImageView(new Image("img/lastLeft.png")));
		btnLast.setGraphic(new ImageView(new Image("img/lastRight.png")));
		int i = (matHangDAO.getDSMatHang_Loc(jcbLoaiXe1, jcbPhanKhoi1, jcbMauSac1, jcbGiaThanh1, txtSearchQLSP, dsQLSP,
				tableQLSP, 0, 15));
		if (i % 15 == 0)
			soLuongTrang = i / 15;
		else
			soLuongTrang = (i / 15) + 1;

		tableQLSP.setEditable(true);
		XuLyBangSQLSP();

		// tableQLSP.setItems(dsQLSP);
		// ==========BẮT SỰ KIỆN BUTTON ===================================

		// ====XỬ LÝ ĐĂNG NHẬP - THOÁT - ĐĂNG XUẤT====
		txtMaDangNhap.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					nv = DangNhap();
				}

			}

		});
		txtMatKhau.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					nv = DangNhap();
				}

			}

		});

		btnDangNhap.setOnAction(Event -> {
			nv = DangNhap();

		});
		btnThoat.setOnAction(Event -> {
			dialogThoat();
		});
		btnQuayLai.setOnAction(Event -> {

			if (count == 0) {
				// System.out.println(count);
				manHinhLamViecChinh.toFront();
				count = 1;
			} else if (count == 1) {

				// System.exit(0);
				dialogThoat();

			}
		});
		btnDangXuat.setOnAction(Event -> {
			dialogDangXuat();
		});

		// ==== MENU CHÍNH ====
		btnChucNang1.setOnAction(Event -> {
			ThongTinCaNhan.toFront();
			count = 0;
		});
		btnChucNang3.setOnAction(e -> {
			About.setVisible(true);
			About.toFront();
		});
		btnNoAbout.setOnAction(e -> {
			About.setVisible(false);
			About.toBack();

		});
		btnChucNang2.setOnAction(e -> {
			ThongKePane.toFront();
			count = 0;
		});
		// btn giỏ hàng - thêm sp
		btnTimKiem.setOnAction(Event -> {
			lblTrangThaiThemSP.setText("");
			paneSanPham.getChildren().clear();

			Map<String, String[]> map = new HashMap<>();
			map = matHangDAO.getImageData(jcbLoaiXe, jcbPhanKhoi, jcbMauSac, jcbGiaThanh, txtSearch);
			for (String key : map.keySet()) {
//				System.out.println("key : " + key);
//				System.out.println("value : " + map.get(key)[0]);
				AddSP(key, map.get(key)[0], map.get(key)[1]);
			}
			if (map.size() == 0)
				lblTrangThaiThemSP.setText("*Không tìm thấy sản phẩm nào!");

		});
		btnTaoHoaDon.setOnAction(Event -> {
			XuLyLapHoaDon();
			themHoaDon.toFront();
			count = 0;
			// vboxListView.getS
			jlistView.getSelectionModel().select(1);
		});
		btnGioHang.setOnAction(E -> {
			gioHang.setVisible(true);
			gioHang.toFront();
			chiTietSP.setVisible(false);
		});
		btnXoaGioHang.setOnAction(e -> {
			dsGioHang.clear();
			tableGioHang.refresh();
		});
		btnXoaSPGioHang.setOnAction(e -> {
			int index = tableGioHang.getSelectionModel().getSelectedIndex();
			dsGioHang.remove(index);
			tableGioHang.refresh();

		});

		// ==============TẠO HOÁ ĐƠN===========================
		btnGioHangHD.setOnAction(e -> {

			timKiem_them_matHang.toFront();
			count = 0;
			jlistView.getSelectionModel().select(0);
		});

		btnTTKH.setOnAction(e -> {
			QLKhachHangPane.toFront();
			count = 0;
			jlistView.getSelectionModel().select(2);
		});
		btnTaoHDMoi.setOnAction(e -> {
			Boolean check = dialogLapHDMoi();
		});
		// ===========BẮT SỰ KIỆN BUTON===QUẢN LÝ KHÁCH
		// HÀNG============================================
		btnThemKHVaoHD.setOnAction(e -> {
			KhachHang selectKH = tableQLKhachHang.getSelectionModel().getSelectedItem();
//			if (selectKH == null) {
//				// thông báo chưa chọn
//			} else {
			// XuLyLapHoaDon();
			// themHoaDon.toFront();
			XuLyLapHoaDon();
			themHoaDon.toFront();
			count = 0;
			// vboxListView.getS
			jlistView.getSelectionModel().select(1);
			txtCMNDKHLHD.setText(selectKH.getSoCMND());
			txtHoKHLHD.setText(selectKH.getHoKH());
			txtTenKHLHD.setText(selectKH.getTenKH());
//			}

		});
		// ==============bắt sự kiện jcb========================================
		// ==============QUẢN LÝ MẶT HÀNG==========
		// jcbGiaThanh1.setOnMouseEntered(e ->{});
		btnXoaQLSP.setOnAction(e -> {
			dialog.setVisible(true);
			dialog.toFront();
			lblDialogTitle.setText("Xác nhận xoá sản phẩm!");
			lblDialogContent.setText("Lưu ý: Sản phẩm sẽ được xoá hoàn toàn và có thể ảnh hưởng đến một số hoá đơn!");
			btnYes.setText("Xoá");
			btnYes.setOnAction(e1 -> {
				MatHang selectedItem = tableQLSP.getSelectionModel().getSelectedItem();
				if (matHangDAO.XoaMatHang_TheoMa(selectedItem.getMaMatHang()) == true) {
					dialog.setVisible(true);
					tableQLSP.getItems().remove(selectedItem);
					dialog.setVisible(false);
					tableQLSP.refresh();
					gioHang.toFront();
					// dialog.setVisible(false);
					// xác nhận xoá

					btnYes.setText("");
					lblDialogTitle.setText("XOÁ THÀNH CÔNG!");
					lblDialogContent.setText("Sản phẩm đã được xoá khỏi hệ thống!");
					dialog.setVisible(true);
					btnYes.setDisable(true);

				}
				;

			});
			btnNo.setOnAction(e2 -> {
				btnNo.setText("Thoát");
				dialog.setVisible(false);
				dialog.toBack();
				btnYes.setText("Có");
			});

		});
		btnLuuQLSP.setOnAction(e -> {
			matHangDAO.CapNhat_QLSP(dsQLSP, tableQLSP, start, end);
		});
		txtSearchQLSP.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					XuLybtnTimKiemQLSP();
				}

			}

		});
		btnThemHinhAnhQLSP.setOnAction(e -> {

			FileChooser chooser = new FileChooser();
			FileChooser.ExtensionFilter imageFilte = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
			chooser.getExtensionFilters().add(imageFilte);

			File file = chooser.showOpenDialog(manHinhLamViecChinh.getScene().getWindow());

			// newWindow.show();

			if (file == null) {

			} else {
				Image img = new Image(file.toURI().toString(), 200, 150, false, false);
				imgViewQLSP.setImage(img);

			}
		});
	}

	public void AddSP(String maSP, String tenSP, String path) {

		// Image imgTest = dao.getImageData();
		Image imgSanPham = new Image(new File(path).toURI().toString(), 200, 160, false, false);

		VBox vboxSP = new VBox();
		vboxSP.setPrefHeight(200);
		;
		Label lblSanPham = new Label(tenSP);
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

		lblSanPham.setPrefWidth(200);
		btnThemSP.getStyleClass().add("btnSanPham");
		btnXemThongTin.getStyleClass().add("btnSanPham");
		// btnThemSP.setStyle("-fx-text-fill: white");

		// btnXemThongTin.setStyle("fx-background-color: #f6931e; -fx-text-fill: white;
		// -fx-font-weight: BOLD");
		// btnXemThongTin.setStyle("-fx-text-fill: white");
		ImageView imgV = new ImageView(imgSanPham);
		SanPham.setGraphic(imgV);
		vboxSP.setStyle("-fx-background-color: WHITE");
		hboxButton.setPadding(new Insets(3, 3, 3, 10));
		hboxButton.setSpacing(10);
		paneSanPham.setPadding(new Insets(10, 10, 10, 10));
		// SanPham.getStyleClass().add("buttonMenu");
		paneSanPham.getChildren().add(vboxSP);
		btnXemThongTin.setOnAction(Event -> {
			MatHangDAO mhdao = new MatHangDAO();
			MatHang mh = mhdao.getMatHangg(maSP);
			chiTietSP.setVisible(true);
			chiTietSP.toFront();
			gioHang.setVisible(false);

			lblmaSp.setText("Mã sản phẩm: " + mh.getMaMatHang());
			lbltenSp.setText(tenSP);
			lblLoaiSp.setText("Loại xe: " + mh.getMaLoai());
			lblSoLuongSp.setText("Số lượng: " + mh.getSoLuong());
			lblDungTichBXSp.setText("Dung tích bình xăng: " + mh.getBinhXang());
			lblDungTichDMSp.setText("Dung tích dầu máy: " + mh.getDauMay());
			lblHeThongLyHopSp.setText("Hệ thống ly hợp: " + mh.getLyDong());
			lblMoTaSp.setText("Mô tả: " + mh.getMoTa());
			lblMauSp.setText("Màu xe: " + mh.getMauXe());
			lblDongCoSp.setText("Động cơ: " + mh.getDongCo());
			lblKichThuocSp.setText("Kích thước: " + mh.getKichThuoc());
			lblKhungSp.setText("Khung xe: " + mh.getKhungxe());
			lblGiaSp.setText("Đơn giá (VNĐ): " + formatter.format(mh.getDongia()));
			Image imgBIG = new Image(new File(path).toURI().toString());
			imgViewBIG.setImage(imgBIG);
			// Dialog("", btnThemSP);

		});
		btnThemSP.setOnAction(Event -> {
			MatHangDAO mhdao = new MatHangDAO();
			MatHang mh = mhdao.getMatHangg(maSP);
			int check = 0;

			for (CTHD cthd : dsGioHang) {

				if (cthd.getMaMatHang().equals(maSP) == true)
					check = 1;
			}
			if (check == 1)
				dialogThemVaoGioHang_Trung();
			else {
				DialogthemSP(mh, maSP);
				// btnThemSP.setDisable(true);
				lblTrangThaiThemSP.setText("*Đã thêm sản phẩm " + mh.getMaMatHang() + " vào giỏ hàng.");

			}

		});

	}

	private void XuLyBangSQLSP() {

		// TODO Auto-generated method stub
		tenSPCol.setCellValueFactory(new PropertyValueFactory<>("tenMatHang"));
		soLuongSPCol.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
		soLuongSPCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		donGiaSPCol.setCellValueFactory(new PropertyValueFactory<>("dongia"));
		soLuongSPCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MatHang, Integer>>() {

			@Override
			public void handle(CellEditEvent<MatHang, Integer> e) {
				// TODO Auto-generated method stub

				TablePosition<MatHang, Integer> pos = e.getTablePosition();

				int newValue = e.getNewValue();

				int row = pos.getRow();
				MatHang mh = e.getTableView().getItems().get(row);

				mh.setSoLuong(newValue);

			}
		});
		// binhXangSPCol.setCellFactory(TextFieldTableCell.<MatHang>forTableColumn());
		// Đang
		// CountSPCol.setCellValueFactory();
		//CountSPCol.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
		CountSPCol.setCellValueFactory(new Callback<CellDataFeatures<MatHang, MatHang>, ObservableValue<MatHang>>() {
			  @Override public ObservableValue<MatHang> call(CellDataFeatures<MatHang, MatHang> p) {
			    return new ReadOnlyObjectWrapper(p.getValue());
			  }
			});

		CountSPCol.setCellFactory(new Callback<TableColumn<MatHang, MatHang>, TableCell<MatHang, MatHang>>() {
			  @Override public TableCell<MatHang, MatHang> call(TableColumn<MatHang, MatHang> param) {
			    return new TableCell<MatHang, MatHang>() {
			      @Override protected void updateItem(MatHang item, boolean empty) {
			        super.updateItem(item, empty);

			        if (this.getTableRow() != null && item != null) {
			        	int i = this.getTableRow().getIndex();
			        	//MatHang mh = dsQLSP.get(i+start);
			        	//rowcount = dsQLSP.get
			          setText(this.getTableRow().getIndex()+start+1+"");
			        } else {
			          setText("");
			        }
			      }
			    };
			  }
			});
			CountSPCol.setSortable(false);
		
		donGiaSPCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

		donGiaSPCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MatHang, Double>>() {

			@Override
			public void handle(CellEditEvent<MatHang, Double> e) {
				// TODO Auto-generated method stub

				TablePosition<MatHang, Double> pos = e.getTablePosition();

				Double newValue = e.getNewValue();

				int row = pos.getRow();
				MatHang mh = e.getTableView().getItems().get(row);

				mh.setDongia(newValue);

			}
		});

		cbDonGiaQLSP.setOnAction(e -> {
			if (cbDonGiaQLSP.isSelected() == true) {
				donGiaSPCol.setCellFactory(c -> {
					return new TableCell<MatHang, Double>() {

						@Override
						protected void updateItem(Double price, boolean empty) {

							super.updateItem(price, empty);
							if (empty) {
								setText(null);
							} else {
								setText(formatter.format(price));
							}
						}
					};

				});

			} else {
				donGiaSPCol.setCellValueFactory(new PropertyValueFactory<>("dongia"));

				// binhXangSPCol.setCellFactory(TextFieldTableCell.<MatHang>forTableColumn());

				donGiaSPCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

				donGiaSPCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MatHang, Double>>() {

					@Override
					public void handle(CellEditEvent<MatHang, Double> e) {
						// TODO Auto-generated method stub

						TablePosition<MatHang, Double> pos = e.getTablePosition();

						Double newValue = e.getNewValue();

						int row = pos.getRow();
						MatHang mh = e.getTableView().getItems().get(row);

						mh.setDongia(newValue);

					}
				});
			}
			tableQLSP.refresh();
		});
		loaiSPCol.setCellValueFactory(new PropertyValueFactory<>("maLoai"));
		loaiSPCol.setCellFactory(TextFieldTableCell.<MatHang>forTableColumn());
		loaiSPCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MatHang, String>>() {

			@Override
			public void handle(CellEditEvent<MatHang, String> e) {
				// TODO Auto-generated method stub
				TablePosition<MatHang, String> pos = e.getTablePosition();

				String newValue = e.getNewValue();

				int row = pos.getRow();
				MatHang mh = e.getTableView().getItems().get(row);

				mh.setMaLoai(newValue);
			}
		});
		binhXangSPCol.setCellValueFactory(new PropertyValueFactory<>("binhXang"));
		binhXangSPCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		// binhXangSPCol.setCellFactory(TextFieldTableCell.<MatHang>forTableColumn());
		binhXangSPCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MatHang, Double>>() {

			@Override
			public void handle(CellEditEvent<MatHang, Double> e) {
				// TODO Auto-generated method stub
				TablePosition<MatHang, Double> pos = e.getTablePosition();

				Double newValue = e.getNewValue();

				int row = pos.getRow();
				MatHang mh = e.getTableView().getItems().get(row);

				mh.setBinhXang(newValue);
			}
		});
		dauMaySPCol.setCellValueFactory(new PropertyValueFactory<>("dauMay"));
		dauMaySPCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		// binhXangSPCol.setCellFactory(TextFieldTableCell.<MatHang>forTableColumn());
		dauMaySPCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MatHang, Double>>() {

			@Override
			public void handle(CellEditEvent<MatHang, Double> e) {
				// TODO Auto-generated method stub
				TablePosition<MatHang, Double> pos = e.getTablePosition();

				Double newValue = e.getNewValue();

				int row = pos.getRow();
				MatHang mh = e.getTableView().getItems().get(row);

				mh.setDauMay(newValue);
			}
		});
		lyDongSPCol.setCellValueFactory(new PropertyValueFactory<>("lyDong"));
		lyDongSPCol.setCellFactory(TextFieldTableCell.<MatHang>forTableColumn());
		lyDongSPCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MatHang, String>>() {

			@Override
			public void handle(CellEditEvent<MatHang, String> e) {
				// TODO Auto-generated method stub
				TablePosition<MatHang, String> pos = e.getTablePosition();

				String newValue = e.getNewValue();

				int row = pos.getRow();
				MatHang mh = e.getTableView().getItems().get(row);

				mh.setLyDong(newValue);
			}
		});
		moTaCol.setCellValueFactory(new PropertyValueFactory<>("moTa"));
		moTaCol.setCellFactory(TextFieldTableCell.<MatHang>forTableColumn());
		moTaCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MatHang, String>>() {

			@Override
			public void handle(CellEditEvent<MatHang, String> e) {
				// TODO Auto-generated method stub
				TablePosition<MatHang, String> pos = e.getTablePosition();

				String newValue = e.getNewValue();

				int row = pos.getRow();
				MatHang mh = e.getTableView().getItems().get(row);

				mh.setMoTa(newValue);
			}
		});
		kichThuocSPCol.setCellValueFactory(new PropertyValueFactory<>("kichThuoc"));
		kichThuocSPCol.setCellFactory(TextFieldTableCell.<MatHang>forTableColumn());
		kichThuocSPCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MatHang, String>>() {

			@Override
			public void handle(CellEditEvent<MatHang, String> e) {
				// TODO Auto-generated method stub
				TablePosition<MatHang, String> pos = e.getTablePosition();

				String newValue = e.getNewValue();

				int row = pos.getRow();
				MatHang mh = e.getTableView().getItems().get(row);

				mh.setKichThuoc(newValue);
			}
		});
		hinhAnhSPCol.setCellValueFactory(new PropertyValueFactory<>("image"));
		hinhAnhSPCol.setCellFactory(TextFieldTableCell.<MatHang>forTableColumn());

		hinhAnhSPCol.setCellValueFactory(new PropertyValueFactory<>("image"));

		Callback<TableColumn<MatHang, String>, TableCell<MatHang, String>> cellFactory = //
				new Callback<TableColumn<MatHang, String>, TableCell<MatHang, String>>() {
					@Override
					public TableCell call(final TableColumn<MatHang, String> param) {
						final TableCell<MatHang, String> cell = new TableCell<MatHang, String>() {

							final Button btn = new Button("Thêm ảnh");

							@Override
							public void updateItem(String item, boolean empty) {

								super.updateItem(item, empty);
								if (empty) {
									setGraphic(null);
									setText(null);
								} else {

									btn.setOnAction(event -> {
										MatHang mh = getTableView().getItems().get(getIndex());
//	                                System.out.println(person.getFirstName()
//	                                        + "   " + person.getLastName());
										FileChooser chooser = new FileChooser();
										FileChooser.ExtensionFilter imageFilte = new FileChooser.ExtensionFilter(
												"Image Files", "*.jpg", "*.png");
										chooser.getExtensionFilters().add(imageFilte);

										File file = chooser.showOpenDialog(manHinhLamViecChinh.getScene().getWindow());

										if (file == null) {

										} else {

											final String itemx = file.getPath().toString();
											setText(itemx);
											mh.setImage(itemx);
											// ĐANG
										}
									});
									setText(item);
									setGraphic(btn);

								}
							}
						};
						return cell;
					}
				};
		hinhAnhSPCol.setCellFactory(cellFactory);
//		hinhAnhSPCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MatHang, String>>() {
//
//			@Override
//			public void handle(CellEditEvent<MatHang, String> e) {
//				// TODO Auto-generated method stub
//				TablePosition<MatHang, String> pos = e.getTablePosition();
//				JFXButton btn = new JFXButton("Check");
//				String newValue = e.getNewValue();
//
//				int row = pos.getRow();
//				MatHang mh = e.getTableView().getItems().get(row);
//
//				mh.setImage(newValue);
//			}
//		});
		khungXeSPCol.setCellValueFactory(new PropertyValueFactory<>("khungxe"));
		khungXeSPCol.setCellFactory(TextFieldTableCell.<MatHang>forTableColumn());
		khungXeSPCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MatHang, String>>() {

			@Override
			public void handle(CellEditEvent<MatHang, String> e) {
				// TODO Auto-generated method stub
				TablePosition<MatHang, String> pos = e.getTablePosition();

				String newValue = e.getNewValue();

				int row = pos.getRow();
				MatHang mh = e.getTableView().getItems().get(row);

				mh.setKhungxe(newValue);
			}
		});
		dongCoSPCol.setCellValueFactory(new PropertyValueFactory<>("dongCo"));
		dongCoSPCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		// binhXangSPCol.setCellFactory(TextFieldTableCell.<MatHang>forTableColumn());
		dongCoSPCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MatHang, Double>>() {

			@Override
			public void handle(CellEditEvent<MatHang, Double> e) {
				// TODO Auto-generated method stub
				TablePosition<MatHang, Double> pos = e.getTablePosition();

				Double newValue = e.getNewValue();

				int row = pos.getRow();
				MatHang mh = e.getTableView().getItems().get(row);

				mh.setDongCo(newValue);
			}
		});
		mauXeSPCol.setCellValueFactory(new PropertyValueFactory<>("mauXe"));
		mauXeSPCol.setCellFactory(TextFieldTableCell.<MatHang>forTableColumn());
		mauXeSPCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MatHang, String>>() {

			@Override
			public void handle(CellEditEvent<MatHang, String> e) {
				// TODO Auto-generated method stub
				TablePosition<MatHang, String> pos = e.getTablePosition();

				String newValue = e.getNewValue();

				int row = pos.getRow();
				MatHang mh = e.getTableView().getItems().get(row);

				mh.setMauXe(newValue);
			}
		});
		tenSPCol.setCellFactory(TextFieldTableCell.<MatHang>forTableColumn());
		tenSPCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MatHang, String>>() {

			@Override
			public void handle(CellEditEvent<MatHang, String> e) {
				// TODO Auto-generated method stub
				TablePosition<MatHang, String> pos = e.getTablePosition();

				String newValue = e.getNewValue();

				int row = pos.getRow();
				MatHang mh = e.getTableView().getItems().get(row);

				mh.setTenMatHang(newValue);
			}
		});
		maSPCol.setCellValueFactory(new PropertyValueFactory<>("maMatHang"));
//		maSPCol.setCellFactory(TextFieldTableCell.<MatHang>forTableColumn());
//		maSPCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MatHang, String>>() {
//
//			@Override
//			public void handle(CellEditEvent<MatHang, String> e) {
//				// TODO Auto-generated method stub
//				TablePosition<MatHang, String> pos = e.getTablePosition();
//
//				String newValue = e.getNewValue();
//
//				int row = pos.getRow();
//				MatHang mh = e.getTableView().getItems().get(row);
//
//				mh.setMaMatHang(newValue);
//			}
//		});

	}

	private void dialogThemVaoGioHang_Trung() {
		dialog.setVisible(true);
		dialog.toFront();
		lblDialogTitle.setText("Sản phẩm đã có trong giỏ hàng!");
		lblDialogContent.setText("Lưu ý: Có thể chỉnh sửa số lượng ngay trên bảng giỏ hàng?");
		btnYes.setText("Đồng ý");
		btnYes.setOnAction(e -> {
			dialog.setVisible(false);
			btnYes.setText("Có");
			gioHang.toFront();
			chiTietSP.setVisible(false);
		});
		btnNo.setOnAction(e -> {
			dialog.setVisible(false);
			dialog.toBack();
			btnYes.setText("Có");
		});

		// TODO Auto-generated method stub

	}

	public void Dialog(String thongbao, JFXButton btn) {
		JFXDialogLayout content = new JFXDialogLayout();
		content.setHeading(new Text("Error, No selection"));
		content.setBody(new Text("No student selected"));
		StackPane stackpane = new StackPane();
		JFXDialog themSPDialog = new JFXDialog(stackpane, content, JFXDialog.DialogTransition.CENTER);
		content.setActions(btn);
		themSPDialog.show();
	}

	public void DialogthemSP(MatHang mh, String maSP) {
		TextInputDialog inputdialog = new TextInputDialog();
		CTHD hd = new CTHD(mh.getTenMatHang(), maSP, 0, mh.getDongia());
		// CTHD hd = new CTHD(maHoaDon, maMatHang, soLuong, donGia, giamGia)

		inputdialog.setTitle("THÊM SẢN PHẨM: " + hd.getMaMatHang());
		inputdialog.setHeaderText("Lưu ý: Số lượng không vượt quá:  " + mh.getSoLuong());
		inputdialog.setContentText("Nhập số lượng mua:");
		((Button) inputdialog.getDialogPane().lookupButton(ButtonType.OK)).setText("Thêm");
		((Button) inputdialog.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Thoát");

		Optional<String> result = inputdialog.showAndWait();
		boolean check = regexDialog();

		result.ifPresent(sl -> {

			if (Integer.parseInt(sl.toString()) <= mh.getSoLuong()) {
				hd.setSoLuong(Integer.parseInt(sl.toString()));
				hd.setThanhTien();

				// System.out.println(mh.getSoLuong());

				dsGioHang.add(hd);
				thanhtienGioHang += hd.getThanhTien();
				lblTongTienGH.setText(formatter.format(thanhtienGioHang));
				tableGioHang.refresh();
				tableLapHoaDon.refresh();
			} else {

				dialog.setVisible(true);
				dialog.toFront();
				lblDialogTitle.setText("SỐ LƯỢNG MUA VƯỢT QUÁ QUY ĐỊNH!");
				lblDialogContent.setText("Bạn có muốn nhập lại không?");
				btnYes.setOnAction(e2 -> {
					dialog.setVisible(false);
					DialogthemSP(mh, maSP);

				});
				btnNo.setOnAction(e3 -> {
					dialog.setVisible(false);
					dialog.toBack();
				});

			}

		});

	}

	/**
	 * 
	 * @return tính hợp lệ của số lượng sản phẩm!
	 */
	private boolean regexDialog() {
		return true;

	}

	private boolean dialogLapHDMoi() {
		dialog.setVisible(true);
		dialog.toFront();
		lblDialogTitle.setText("Xác nhận lập hoá đơn");
		lblDialogContent.setText("Lưu ý: Hoá đơn đã tạo sẽ không thể xoá hoặc sửa?");
		btnYes.setOnAction(e -> {
			lblDialogTitle.setText("Đang lưu hoá đơn...");
			lblDialogContent.setText("Lưu ý: Vui lòng chờ!");
			long millis = System.currentTimeMillis();
			java.sql.Date date = new java.sql.Date(millis);
			// System.out.println(date);
			txtNgayLHD.setText(date.toString());
			HoaDonDAO hddao = new HoaDonDAO();
			String maHD = java.time.LocalDateTime.now().toString();
			maHD = maHD.replace(".", "");
			maHD = maHD.replace("-", "");
			maHD = maHD.replace(":", "");
			// System.out.println(maHD);
			Double thanhTien = 0.0;
			for (CTHD cthd : dsGioHang) {
				cthd.setMaHoaDon(maHD);
				thanhTien += cthd.getThanhTien();

			}
			HoaDon hd = new HoaDon(maHD, txtCMNDKHLHD.getText().trim(), txtMaNVLHD.getText(), date, thanhTien);
			hddao.themHoaDon(hd, dsGioHang);
			// dsGioHang.get(1);
			dialog.setVisible(false);
			dialogThemHDThanhCong();

		});
		btnNo.setOnAction(e -> {
			dialog.setVisible(false);
			dialog.toBack();
		});

		return true;

	}

	private void dialogThemHDThanhCong() {
		// TODO Auto-generated method stub
		dialog.setVisible(true);
		dialog.toFront();
		// lblDialogTitle.setText("Xác nhận lập hoá đơn");
		lblDialogTitle.setText("THÊM HOÁ ĐƠN THÀNH CÔNG");
		lblDialogContent.setText("Bạn có muốn lập hoá đơn mới không?");
		btnYes.setOnAction(e -> {
			dsGioHang.clear();
			tableGioHang.refresh();
			tableLapHoaDon.refresh();
			txtCMNDKHLHD.setText("");
			lblTrangThaiThemSP.setText("");
			timKiem_them_matHang.toFront();
			count = 0;
			jlistView.getSelectionModel().select(0);
			dialog.setVisible(false);
			dialog.toBack();

		});
		btnNo.setOnAction(e -> {
			manHinhLamViecChinh.toFront();
			count = 1;
			dsGioHang.clear();
			tableGioHang.refresh();
			tableLapHoaDon.refresh();
			txtCMNDKHLHD.setText("");
			lblTrangThaiThemSP.setText("");

		});

	}

	private void dialogDangXuat() {
		dialog.setVisible(true);
		dialog.toFront();
		lblDialogTitle.setText("Xác nhận đăng xuất");
		lblDialogContent.setText("Bạn có chắc chắn muốn đăng xuất khỏi tải khoản này không?");
		btnYes.setOnAction(e -> {
			dialog.setVisible(false);
			MenuPane.setVisible(false);
			LoginPane.setVisible(true);
		});
		btnNo.setOnAction(e -> {
			dialog.setVisible(false);
		});
	}

	private void dialogThoat() {
		dialog.setVisible(true);
		dialog.toFront();
		lblDialogTitle.setText("Xác nhận thoát chương trình");
		lblDialogContent.setText("Bạn có chắc chắn muốn thoát phần mềm không?");
		btnYes.setOnAction(e -> {
//			dialog.setVisible(false);
//			MenuPane.setVisible(false);
//			LoginPane.setVisible(false);
			System.exit(0);

		});
		btnNo.setOnAction(e -> {
			dialog.setVisible(false);
		});
	}

	/**
	 * Xử lý đăng nhập
	 * 
	 */
	private NhanVien DangNhap() {

		NhanVienDAO nvDao = new NhanVienDAO();
		String tk = txtMaDangNhap.getText().trim();
		String mk = txtMatKhau.getText().trim();
		// System.out.println(tk + "..." + mk);
		manHinhLamViecChinh.toFront();

		if ((tk.equals("") || mk.equals("")) == true) {
			lblLGStatus.setVisible(true);
			lblLGStatus.setText("*Vui lòng không để trống mã đăng nhập và mật khẩu!");
			return null;
		} else {
			// xử lý không nhập TK/MK

			NhanVien nv = nvDao.getNhanVientheoMaNV_Login(tk, mk);
			if (nv == null) {
				// Xử lý đăng nhập KHÔNG THÀNH CÔNG
				lblLGStatus.setVisible(true);
				lblLGStatus.setText("*Tên đăng nhập không tồn tại!");
				return null;
			} else {
				if (mk.equals(nv.getMatKhau()) == true) {

					lblTenNVLogin.setText(nv.getHoNV() + " " + nv.getTenNV());
					LoginPane.setVisible(false);
					MenuPane.setVisible(true);

					lblLGStatus.setText("");
					lblLGStatus.setText("*Đăng nhập thành công!");
					ThongTinCaNhan(nv);

					return nv;
				} else {
					lblLGStatus.setVisible(true);
					lblLGStatus.setText("*Mật khẩu không chính xác!");
					return null;
				}

				// return null;

			}

		}
	}

	public void XuLyLapHoaDon() {
		txtMaNVLHD.setText(nv.getMaNV());
		txtTenNVLHD.setText(nv.getTenNV());
		txtHoNVLHD.setText(nv.getHoNV());
		// txtHoKHLHD.setText("");
		txtCMNDKHLHD.setText(txtsoCMNDQLKH.getText().trim());
		if (txtCMNDKHLHD.getText().trim().equals("") == true) {
			dialog.toFront();
			dialog.setVisible(true);
			lblDialogTitle.setText("KHÁCH HÀNG TRỐNG! ");
			lblDialogContent.setText("Bạn có muốn chuyển sang chức năng chọn khách hàng không? ");
			btnNo.setDisable(true);
			btnYes.setOnAction(e -> {

				dialog.setVisible(false);
				QLKhachHangPane.toFront();
				count = 0;
				jlistView.getSelectionModel().select(2);
				btnNo.setDisable(false);
			});
			btnNo.setOnAction(e -> {
				dialog.setVisible(false);
			});

		}
		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
		// System.out.println(date);
		txtNgayLHD.setText(date.toString());

	}

	private void XuLyBangQuanLySanPham() {

		QLSanPham.toFront();
		pageCount = 1;
		if (pageCount == soLuongTrang || soLuongTrang == 0) {
			btnRight.setDisable(true);
			btnLast.setDisable(true);
		}

		else {
			btnRight.setDisable(false);
			btnLast.setDisable(false);
		}
		if (pageCount == 1) {
			btnLeft.setDisable(true);
			btnFirst.setDisable(true);

		}

		else {
			btnLeft.setDisable(false);
			btnFirst.setDisable(false);

		}
		// lblTenSP.setText("Đã chuyển tên SP");
		// System.out.println(count);
		lblPageQLSP.setText(pageCount + "/" + soLuongTrang);
		btnSearchQLSP.setOnAction(e2 -> {
			XuLybtnTimKiemQLSP();

		});
		btnRight.setOnAction(e1 -> {

			pageCount = pageCount + 1;
			if (pageCount == soLuongTrang || soLuongTrang == 0) {
				btnRight.setDisable(true);
				btnLast.setDisable(true);
			}

			else {
				btnRight.setDisable(false);
				btnLast.setDisable(false);
			}
			if (pageCount == 1) {
				btnLeft.setDisable(true);
				btnFirst.setDisable(true);

			}

			else {
				btnLeft.setDisable(false);
				btnFirst.setDisable(false);

			}
			start += 15;
			end += 15;
			// System.out.println(start + " " + end);
			int count = dsQLSP.size();
			// if(count<)
			if (count > start) {
				if (count >= end)
					tableQLSP.setItems(FXCollections.observableArrayList(dsQLSP.subList(start, end)));
				else
					tableQLSP.setItems(FXCollections.observableArrayList(dsQLSP.subList(start, count)));
				tableQLSP.refresh();
				lblPageQLSP.setText(pageCount + "/" + soLuongTrang);
			}
		});
		btnLeft.setOnAction(e3 -> {

			pageCount = pageCount - 1;
			if (pageCount == soLuongTrang || soLuongTrang == 0) {
				btnRight.setDisable(true);
				btnLast.setDisable(true);
			}

			else {
				btnRight.setDisable(false);
				btnLast.setDisable(false);
			}
			if (pageCount == 1) {
				btnLeft.setDisable(true);
				btnFirst.setDisable(true);

			}

			else {
				btnLeft.setDisable(false);
				btnFirst.setDisable(false);

			}
			start -= 15;
			end -= 15;
			int count = dsQLSP.size();
			if (count > start) {
				if (count >= end)
					tableQLSP.setItems(FXCollections.observableArrayList(dsQLSP.subList(start, end)));
				else
					tableQLSP.setItems(FXCollections.observableArrayList(dsQLSP.subList(start, count)));
				tableQLSP.refresh();
				lblPageQLSP.setText(pageCount + "/" + soLuongTrang);
			}
		});

		btnFirst.setOnAction(e -> {

			pageCount = 1;
			if (pageCount == soLuongTrang || soLuongTrang == 0) {
				btnRight.setDisable(true);
				btnLast.setDisable(true);
			}

			else {
				btnRight.setDisable(false);
				btnLast.setDisable(false);
			}
			if (pageCount == 1) {
				btnLeft.setDisable(true);
				btnFirst.setDisable(true);

			}

			else {
				btnLeft.setDisable(false);
				btnFirst.setDisable(false);

			}
			start = 0;
			end = 15;
			int count = dsQLSP.size();
			if (count > start) {
				if (count >= end)
					tableQLSP.setItems(FXCollections.observableArrayList(dsQLSP.subList(start, end)));
				else
					tableQLSP.setItems(FXCollections.observableArrayList(dsQLSP.subList(start, count)));
				tableQLSP.refresh();
				lblPageQLSP.setText(pageCount + "/" + soLuongTrang);
			}

		});
		btnLast.setOnAction(e -> {

			pageCount = soLuongTrang;
			if (pageCount == soLuongTrang || soLuongTrang == 0) {
				btnRight.setDisable(true);
				btnLast.setDisable(true);
			}

			else {
				btnRight.setDisable(false);
				btnLast.setDisable(false);
			}
			if (pageCount == 1) {
				btnLeft.setDisable(true);
				btnFirst.setDisable(true);

			}

			else {
				btnLeft.setDisable(false);
				btnFirst.setDisable(false);

			}
			start = (soLuongTrang - 1) * 15;
			end = (soLuongTrang - 1) * 15 + 15;
			// System.out.println(start + " " + end);
			if (count > start) {
				if (count >= end)
					tableQLSP.setItems(FXCollections.observableArrayList(dsQLSP.subList(start, end)));
				else
					tableQLSP.setItems(FXCollections.observableArrayList(dsQLSP.subList(start, count)));
				tableQLSP.refresh();
				lblPageQLSP.setText(pageCount + "/" + soLuongTrang);
			}
		});

//	tableQLSP.refresh();

	}

	private void XuLybtnTimKiemQLSP() {
		// TODO Auto-generated method stub
		if (pageCount == soLuongTrang || soLuongTrang == 0) {
			btnRight.setDisable(true);
			btnLast.setDisable(true);
		}

		else {
			btnRight.setDisable(false);
			btnLast.setDisable(false);
		}
		if (pageCount == 1) {
			btnLeft.setDisable(true);
			btnFirst.setDisable(true);

		}

		else {
			btnLeft.setDisable(false);
			btnFirst.setDisable(false);

		}

		count = 0;
		QLSanPham.toFront();
		start = 0;
		end = 15;
		pageCount = 1;
		MatHangDAO mhDao = new MatHangDAO();
		dsQLSP.removeAll(dsQLSP);
		int count = mhDao.getDSMatHang_Loc(jcbLoaiXe1, jcbPhanKhoi1, jcbMauSac1, jcbGiaThanh1, txtSearchQLSP, dsQLSP,
				tableQLSP, start, end);
		if (count % 15 == 0)
			soLuongTrang = (count / 15);
		else
			soLuongTrang = (count / 15) + 1;
		if (soLuongTrang == 1)
			btnRight.setDisable(true);
		if (count == 0)
			tableQLSP.setPlaceholder(new Label("Không có sản phẩm để hiển thị!")); // Khi bảng trống

		// System.out.println(start + " " + end);
		// System.out.println(i + " " + soLuongTrang);
		// XuLyBangQuanLySanPham();
		if (pageCount == soLuongTrang || soLuongTrang == 0) {
			btnRight.setDisable(true);
			btnLast.setDisable(true);
		}

		else {
			btnRight.setDisable(false);
			btnLast.setDisable(false);
		}
		if (pageCount == 1) {
			btnLeft.setDisable(true);
			btnFirst.setDisable(true);

		}

		else {
			btnLeft.setDisable(false);
			btnFirst.setDisable(false);

		}
		lblPageQLSP.setText(pageCount + "/" + soLuongTrang);

	}

	public void ThongTinCaNhan(NhanVien nv) {
		txtMaNVTT.setText(nv.getMaNV().toString());

		txtHoNVTT.setText(nv.getHoNV());

		txtTenNVTT.setText(nv.getTenNV());

		txtChucVuNVTT.setText(nv.getChucVu());

		txtLuongNVTT.setText(formatter.format(nv.getLuong()));

		txtCaLamNVTT.setText(nv.getCaLam());

		txtNgayVaoLamNVTT.setText(nv.getNgayLam().toString());

		txtDiaChiNVTT.setText(nv.getDiaChi());

		txtGioiTinhNVTT.setText(nv.getGioiTinh());

		txtMatKhauNVTT.setText(nv.getMatKhau());
	}

	private int setDogCount(TableView<MatHang> table, int index, int count) {
		if (index == 0) {
			if (table.getItems().get(index) instanceof MatHang) {
				return count + 1;
			} else {
				return count;
			}
		}
		if (table.getItems().get(index) instanceof MatHang) {
			return setDogCount(table, --index, ++count);
		} else {
			return setDogCount(table, --index, count);
		}
	}

}
