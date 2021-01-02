package manHinhLamViecChinh;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeView;

import connectDATA.Connect_Data;
import dao.CTHDDAO;
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
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class MHLVChinhController implements Initializable {
	static String DataPath = "T:\\ptud\\";
	static String LocalPath = "D:\\BTL\\";

	public String getLocalPath() {
		return LocalPath;
	}

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
	AnchorPane dialogThongBao;
	@FXML
	AnchorPane dialogDoiMatKhau;
	@FXML
	Label lblDialogTitle;
	@FXML
	Label lblDialogContent;
	@FXML
	JFXButton btnYes;
	@FXML
	JFXButton btnNo;
	@FXML
	AnchorPane dialogValue;
	@FXML
	Label lblDialogVLContent;
	@FXML
	Label lblDialogTitleVL;
	@FXML
	Label lblDialogWarningVL;
	@FXML
	JFXButton btnYesVL;
	@FXML
	JFXButton btnNoVL;
	@FXML
	JFXTextField txtValueDialog;

	// Dialog Đổi mật khẩu
	@FXML
	JFXPasswordField txtMatKhauCu;
	@FXML
	JFXPasswordField txtMatKhauMoi;
	@FXML
	JFXPasswordField txtMatKhauXacNhan;
	@FXML
	JFXTextField txtMatKhauCutxt;
	@FXML
	JFXTextField txtMatKhauMoitxt;
	@FXML
	JFXTextField txtMatKhauXacNhantxt;
	@FXML
	JFXButton btnMaskMatKhauCu;

	@FXML
	JFXButton btnMaskMatKhauMoi;

	@FXML
	JFXButton btnMaskMatKhauXacNhan;
	@FXML
	JFXButton btnThoatDoiMK;
	@FXML
	JFXButton btnThayDoiMK;
	@FXML
	Label txtTrangThaiDoiMK;

	// ==========
	// =====Biến local=====
	//private String password = "";
	private String passwordC = "";
	private String passwordM = "";
	private String passwordXN = "";

	public void setStage(Stage stage) {
		thisStage = stage;
	}

	public void showStage() {
		thisStage.setTitle("YAMAS");
		thisStage.show();
	}

	private int count = 1; // biến đếm tầng giao diện
//	private ObservableList<String> list = FXCollections.observableArrayList("    Tạo giỏ hàng mới",
//			"    Tạo hoá đơn mới", "    Quản lý khách hàng", "    Quản lý sản phẩm", "    Quản lý hoá đơn");
//	private ObservableList<String> listLoaiXe = FXCollections.observableArrayList("Tất cả","Xe số","Xe tay ga","Xe thể thao");
//	private ObservableList<String> listPhanKhoi = FXCollections.observableArrayList("Tất cả","Từ 100cc đến 135cc","Từ 135cc đến 175cc","Lớn hơn 175");
//	private ObservableList<String> listMauSac = FXCollections.observableArrayList("Tất cả","Đen","Trắng","Xám","Đỏ","Vàng","Xanh lá cây","Xanh Dương","Xanh Aqua");
//	private ObservableList<String> listGiaThanh = FXCollections.observableArrayList("Tất cả","Đen","Trắng","Xám","Đỏ","Vàng","Xanh lá cây","Xanh Dương","Xanh Aqua");
	private Image image = new Image("img/myprofile.png");
	private Image image2 = new Image("img/analysis.png");
	private Image image3 = new Image("img/customer.png");
	private Image image4 = new Image("img/bill.png");
	private Image imageUnMask = new Image("img/unmask.png");
	private Image imageMask = new Image("img/mask.png");
	private ImageView imgVUnMask = new ImageView(imageUnMask);
	private ImageView imgVMask = new ImageView(imageMask);
	// btnMenu: Size : 250x250, image: 128x128, text: white
	// TREEVIEW
	@FXML
	JFXTreeView<String> tree;
	TreeItem<String> root = new TreeItem<>("ROOT");
	TreeItem<String> tree_QLHD = new TreeItem<>("1. Hoá đơn");
	TreeItem<String> HD_taoHD = new TreeItem<>("Lập hoá đơn");
	TreeItem<String> HD_TaoGioHang = new TreeItem<>("Tạo giỏ hàng");
	TreeItem<String> HD_QLHD = new TreeItem<>("Xem hoá đơn cũ");
	TreeItem<String> tree_QLKH = new TreeItem<>("2. Khách hàng");
	TreeItem<String> tree_QLSP = new TreeItem<>("3. Sản phẩm");
	TreeItem<String> tree_TK = new TreeItem<>("4. Thống kê");
	TreeItem<String> TK_DoanhThu = new TreeItem<>("TK doanh thu");
	TreeItem<String> TK_SanPham = new TreeItem<>("TK sản phẩm");
	TreeItem<String> TK_NhanVien = new TreeItem<>("TK nhân viên");
	TreeItem<String> TK_KhachHang = new TreeItem<>("TK khách hàng");

	// @FXML
	// JFXListView<String> jlistView;

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
	// ============Menu HELP========================
	@FXML
	AnchorPane menuHelp;
	@FXML
	AnchorPane menuHelpMini;
	@FXML
	AnchorPane menuCaiDat;
	@FXML
	JFXButton btnThemFolderImg;
	@FXML
	JFXButton btnThemDatabase;
	@FXML
	JFXButton btnThemFilePath;
	@FXML
	TextField txtThemFolderImg;
	@FXML
	TextField txtThemDatabase;
	@FXML
	TextField txtThemFilePath;
	@FXML
	Button btnLogOut;
	@FXML
	Button btnCaiDat;
	@FXML
	Button btnHuongDanSD;
	@FXML
	Button btnAbout;
	// ===========ABOUT============================
	@FXML
	JFXButton btnNoAbout;
	@FXML
	AnchorPane About;
	// =========thống kê====
	@FXML
	AnchorPane ThongKePane;
	@FXML
	AnchorPane ThongKeDoanhThuPane;
	@FXML
	LineChart<String, Double> lineChartDoanhThu;
	@FXML
	JFXButton btnTaoThongKeDoanhThu;
	@FXML
	JFXDatePicker dpkFromTKDT;
	@FXML
	JFXDatePicker dpToTKDT;
	@FXML
	JFXComboBox<String> cbbTKDoanhThu;
	private ObservableList<String> listThongKeDoanhThu = FXCollections.observableArrayList("Ngày", "Tháng", "Quý",
			"Năm");
	@FXML
	TableView<HoaDon> tableTKDT;
	@FXML
	TableColumn<HoaDon, String> ThoiGianTKDTCol = new TableColumn<HoaDon, String>();
	@FXML
	TableColumn<HoaDon, Double> TongDoanhThuTKDTCol = new TableColumn<HoaDon, Double>();
	@FXML
	TableColumn<HoaDon, Double> TongLaiTKDTCol = new TableColumn<HoaDon, Double>();
	// ===================QUẢN LÝ HOÁ ĐƠN CŨ====================
	@FXML
	AnchorPane paneQLHD;

	@FXML
	TableView<HoaDon> tableQLHD_HD;
	@FXML
	TableColumn<HoaDon, String> maHD_QLHD_HDCol = new TableColumn<HoaDon, String>();
	@FXML
	TableColumn<HoaDon, String> tenKH_QLHD_HDCol = new TableColumn<HoaDon, String>();
	@FXML
	TableColumn<HoaDon, String> hoKH_QLHD_HDCol = new TableColumn<HoaDon, String>();
	@FXML
	TableColumn<HoaDon, String> soCMNDKH_QLHD_HDCol = new TableColumn<HoaDon, String>();
	@FXML
	TableColumn<HoaDon, String> soDTKH_QLHD_HDCol = new TableColumn<HoaDon, String>();
	@FXML
	TableColumn<HoaDon, String> maNV_QLHD_HDCol = new TableColumn<HoaDon, String>();
	@FXML
	TableColumn<HoaDon, String> hoNV_QLHD_HDCol = new TableColumn<HoaDon, String>();
	@FXML
	TableColumn<HoaDon, String> tenNV_QLHD_HDCol = new TableColumn<HoaDon, String>();
	@FXML
	TableColumn<HoaDon, Date> ngayLapHD_QLHD_HDCol = new TableColumn<HoaDon, Date>();
	@FXML
	TableColumn<HoaDon, Double> TongTienHD_QLHD_HDCol = new TableColumn<HoaDon, Double>();

	@FXML
	TableView<CTHD> tableQLHD_CTHD;
	@FXML
	TableColumn<CTHD, String> maMH_QLHD_CTHDCol = new TableColumn<CTHD, String>();
	@FXML
	TableColumn<CTHD, String> tenMH_QLHD_CTHDCol = new TableColumn<CTHD, String>();
	@FXML
	TableColumn<CTHD, Integer> soLuongMH_QLHD_CTHDCol = new TableColumn<CTHD, Integer>();
	@FXML
	TableColumn<CTHD, Double> donGiaMH_QLHD_CTHDCol = new TableColumn<CTHD, Double>();
	@FXML
	TableColumn<CTHD, Double> giamGiaMH_QLHD_CTHDCol = new TableColumn<CTHD, Double>();
	@FXML
	TableColumn<CTHD, Double> thanhTienMH_QLHD_CTHDCol = new TableColumn<CTHD, Double>();
	@FXML
	TableColumn<CTHD, Double> thueMH_QLHD_CTHDCol = new TableColumn<CTHD, Double>();
	@FXML
	JFXTextField txtQLSPCMNDKH;
	@FXML
	JFXTextField txtQLSPHoKH;
	@FXML
	JFXTextField txtQLSPTenKH;
	@FXML
	JFXTextField txtQLSPSDTKH;
	@FXML
	JFXTextField txtQLSPMaNV;
	@FXML
	JFXTextField txtQLSPTimKiem;

	@FXML
	JFXButton btnQLHDThongTinKH;
	@FXML
	JFXButton btnQLHDThongTinNV;
	@FXML
	JFXButton btnTimKiemQLHD;
	@FXML
	JFXButton btnXoaTrangQLHD;
	@FXML
	JFXTextField txtSoCMNDKHQLHD;
	@FXML
	JFXTextField txtHoKHQLHD;
	@FXML
	JFXTextField txtTenKHQLHD;
	@FXML
	JFXTextField txtSoDTKHQLHD;
	@FXML
	JFXTextField txtMANVQLHD;
	@FXML
	JFXTextField txtMAHDQLHD;
	@FXML
	JFXDatePicker dpkFromQLHD;
	@FXML
	JFXDatePicker dpktoQLHD;
	ObservableList<HoaDon> dsHoaDonQLHD = FXCollections.observableArrayList();
	ObservableList<CTHD> dsCTHD_QLHD = FXCollections.observableArrayList();

	// ==================MENU TÌM KIẾM SẢN PHẨM=====================
	private ObservableList<String> listLoaiXe = FXCollections.observableArrayList("Tất cả", "Xe số", "Xe tay ga",
			"Xe thể thao");
	private ObservableList<String> listPhanKhoi = FXCollections.observableArrayList("Tất cả", "Bé hơn 100cc",
			"Từ 100cc đến 135cc", "Từ 135cc đến 175cc", "Lớn hơn 175");
	private ObservableList<String> listMauSac = FXCollections.observableArrayList("Tất cả", "Đen", "Trắng", "Xám", "Đỏ",
			"Vàng", "Xanh lá", "Xanh Dương", "Xanh Aqua");
	private ObservableList<String> listGiaThanh = FXCollections.observableArrayList("Tất cả", "Dưới 30 triệu VNĐ",
			"Từ 30 - 50 Triệu VNĐ", "Từ 50 - 80 triệu VNĐ ", "Từ 80-100 Triệu VNĐ", "Trên 100 Triệu VNĐ");
	private ObservableList<String> listDongXe = FXCollections.observableArrayList("Tất cả", "Dưới 30 triệu VNĐ",
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
	JFXComboBox<String> jcbDongXe;
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
	TableColumn<CTHD, Double> giamGiaGioHang = new TableColumn<CTHD, Double>();
	@FXML
	TableColumn<CTHD, Double> thanhTienGioHang = new TableColumn<CTHD, Double>();

	ObservableList<CTHD> dsGioHang = FXCollections.observableArrayList();
	Double thanhtienGioHang = 0.0;
	Double price = 0.0;
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
	JFXButton btnDoiMatKhauQLNV;
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
	JFXComboBox<String> cbbGioiTinhNVTT;

	@FXML
	JFXPasswordField txthideMatKhauNVTT;
	@FXML
	JFXTextField txtsoCMNDNVTT;
//	@FXML
//	JFXButton btnUnMaskTTCN;
	ObservableList<String> dsGioiTinhNVTT = FXCollections.observableArrayList();
	// =================HẾT TT CÁ NHÂN============================================

	// ================VAR Tạo HOÁ ĐƠN=============================================
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
	TableColumn<CTHD, Double> giamGiaThemHD = new TableColumn<CTHD, Double>();
	@FXML
	TableColumn<CTHD, Double> ThueThemHD = new TableColumn<CTHD, Double>();
//	@FXML
//	TableColumn ThueThemHD = new TableColumn("#");
	@FXML
	TableColumn STTThemHD = new TableColumn("#");

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
	Label txtTongTienThemHD;
	@FXML
	JFXTextField txtTienKhachTraThemHD;
	@FXML
	Label txtTienThoiThemHD;
	@FXML
	JFXTextField txtNgayLHD;
	@FXML
	TextField txtTienThuaLHD;
	@FXML
	JFXButton btnXoaThemHD;

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
	TableColumn<MatHang, Double> ThueSPCol = new TableColumn<MatHang, Double>();
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
	@FXML
	TableColumn<MatHang, String> dongSPCol = new TableColumn<MatHang, String>();
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
	JFXComboBox<String> jcbDongXe1;
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
	JFXTextField lblDongMatHangQLSP;
	@FXML
	JFXTextField lblThueMatHangQLSP;
	@FXML
	JFXTextField lblMauXeQLSP;
	@FXML
	JFXTextField lblDongCoMatHangQLSP;
	@FXML
	JFXTextField lblPathHinhAnhQLSP;
	@FXML
	JFXTextField lblKhungXeMatHangQLSP;
	@FXML
	JFXTextField lblMoTaMatHangQLSP;
	@FXML
	JFXButton btnThemHinhAnhQLSP;
	@FXML
	JFXComboBox<String> cbbLoaiXeThemQLSP;
	@FXML
	JFXComboBox<String> cbbDongXeThemQLSP;
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
	@FXML
	JFXButton btnLeftQLKH;
	@FXML
	JFXButton btnRightQLKH;
	@FXML
	JFXButton btnFirstQLKH;
	@FXML
	JFXButton btnLastQLKH;
	@FXML
	Label lblPageQLKH;
	@FXML
	Button btnLuuQLKH;
	@FXML
	Button btnHoanTacQLKH;
	@FXML
	Button btnThemQLKH;
	@FXML
	Button btnXoaTrangQLKH;
	// =============================================================================

	// ====================BẮT SỰ KIỆN==============================
	@FXML
	AnchorPane timKiem_them_matHang;
	@FXML
	AnchorPane manHinhLamViecChinh;
	@FXML
	AnchorPane QLSanPham;

//	private void ChuyenChucNang(MouseEvent e) {
//		if (jlistView.getSelectionModel().getSelectedIndex() == 0 ) { // tạo giỏ hàng mới
//			System.out.println( tree.getSelectionModel().getSelectedIndex());
//			count = 0;
//			timKiem_them_matHang.toFront();
//			chiTietSP.setVisible(false);
//			// lblTenSP.setText("Đã chuyển tên SP");
//			// System.out.println(count);
//		} else if (jlistView.getSelectionModel().getSelectedIndex() == 1) { // tạo hoá đơn mới
//			XuLyLapHoaDon();
//			count = 0;
//			themHoaDon.toFront();
//			// lblTenSP.setText("Đã chuyển tên SP");
//			// System.out.println(count);
//		} else if (jlistView.getSelectionModel().getSelectedIndex() == 3 || tree.getSelectionModel().getSelectedIndex() == 2) { // quản lý sản phẩm / mặt hàng
//			XuLyBangQuanLySanPham();
//			count  = 0;
//			// XuLyBangSQLSP();
//
//		} else if (jlistView.getSelectionModel().getSelectedIndex() == 2) {
//			count = 0;
//			QLKhachHangPane.toFront();
//
//		}
//
//	}
//	// ======================================================================
	@FXML
	public void ChuyenChucNang2() {

		// TreeItem<String> selectedItem = tree.getSelectionModel().getSelectedItem();

		int selectedTree = 0;
		selectedTree = tree.getSelectionModel().getSelectedIndex();
		boolean check = tree_QLHD.isExpanded();
		System.out.println(selectedTree);
		// System.out.println(selectedTree);
		// System.out.println(selectedItem.toString());
//		if (selectedTree == 0) {
//			if (selectedItem.toString().equals("TreeItem [ value: Lập hoá đơn ]")) {
//				// int index = selectedItem.getParent().getChildren().indexOf(selectedItem);
//				int x = tree.getRow(selectedItem);
//				// System.out.println(index);
//				XuLyLapHoaDon();
//				count = 0;
//				themHoaDon.toFront();
//
//				// System.out.println(x);
//
//			} else if (selectedItem.toString().equals("TreeItem [ value: Tạo giỏ hàng ]")) {
//
//				count = 0;
//				timKiem_them_matHang.toFront();
//				chiTietSP.setVisible(false);
//
//			}
//
//		}
		if (check == true) {
			if (selectedTree == 1) {
				// int index = selectedItem.getParent().getChildren().indexOf(selectedItem);
				// int x = tree.getRow(selectedItem);
				// System.out.println(index);
				XuLyLapHoaDon();
				count = 0;
				themHoaDon.toFront();
				// System.out.println(x);

			} else if (selectedTree == 2) {

				count = 0;
				timKiem_them_matHang.toFront();
				chiTietSP.setVisible(false);

			} else if (selectedTree == 3) { // quản lý Hoá đơn
				count = 0;
				paneQLHD.toFront();
				xuLyChucNangQuanLyHoaDon();
			}

			else if (selectedTree == 4) { // quản lý khách hàng
				count = 0;
				QLKhachHangPane.toFront();
			} else if (selectedTree == 5) { // quản lý sản phẩm / mặt hàng
				XuLyBangQuanLySanPham();
				count = 0;
				XuLyBangSQLSP();
			} else if (tree_TK.isExpanded() == true) {
				if (selectedTree == 7) { // thống kê doanh thu
					count = 0;
					XuLyThongKeDoanhThu_Backgr();
					ThongKePane.toFront();
					ThongKeDoanhThuPane.toFront();

				} else if (selectedTree == 5) { // quản lý sản phẩm / mặt hàng
					XuLyBangQuanLySanPham();
					count = 0;
					XuLyBangSQLSP();
				}

			}

		} else {
//			if (selectedTree == 0) {
//
//				ChuyenChucNang2();
//
//			}

			if (selectedTree == 1) { // quản lý khách hàng
				count = 0;
				QLKhachHangPane.toFront();
			} else if (selectedTree == 2) { // quản lý sản phẩm / mặt hàng
				XuLyBangQuanLySanPham();
				count = 0;
				// XuLyBangSQLSP();
			} else if (tree_TK.isExpanded() == true) {
				if (selectedTree == 4) { // thống kê doanh thu
					count = 0;
					XuLyThongKeDoanhThu_Backgr();
					ThongKePane.toFront();
					ThongKeDoanhThuPane.toFront();

				} else if (selectedTree == 5) { // quản lý sản phẩm / mặt hàng
					XuLyBangQuanLySanPham();
					count = 0;
					// XuLyBangSQLSP();
				}

			}

		}

	}

	private void XuLyThongKeDoanhThu_Backgr() {
		LocalDate dateNow = LocalDate.now();

		dpToTKDT.setValue(dateNow);
		dpkFromTKDT.setValue(dateNow);

		dpToTKDT.setOnAction(e -> {
			if (dpkFromTKDT.getValue().isAfter(dpToTKDT.getValue()) == true && dpkFromTKDT.getValue() != null)
				dpToTKDT.setValue(dpkFromTKDT.getValue());
			XuLyThongKeDoanhThu();

		});
		dpkFromTKDT.setOnAction(e -> {
			if (dpkFromTKDT.getValue().isAfter(dpToTKDT.getValue()) == true && dpToTKDT.getValue() != null)
				dpkFromTKDT.setValue(dpToTKDT.getValue());
			XuLyThongKeDoanhThu();

		});
		cbbTKDoanhThu.setItems(listThongKeDoanhThu);
		cbbTKDoanhThu.setValue("Ngày");
	}

	@FXML
	private void XuLyThongKeDoanhThu() {
		lineChartDoanhThu.getData().clear();
		HoaDonDAO hdDao = new HoaDonDAO();
		// lineChartDoanhThu.getData().clear();
		XYChart.Series<String, Double> dsThongKeDoanhThu = new XYChart.Series<>();
		XYChart.Series<String, Double> dsLoiNhuanDoanhThu = new XYChart.Series<>();

		// XYChart<String, Double>

		hdDao.getDSHoaDon_ThongKeDoanhThu_Ngay(dsThongKeDoanhThu, dsLoiNhuanDoanhThu, dpkFromTKDT, dpToTKDT);
		lineChartDoanhThu.getData().add(dsThongKeDoanhThu);
		lineChartDoanhThu.getData().add(dsLoiNhuanDoanhThu);
		dsLoiNhuanDoanhThu.setName("Lợi nhuận");
		dsThongKeDoanhThu.setName("Tổng tiền hoá đơn");
		lineChartDoanhThu.setAnimated(false);

		for (Data<String, Double> entry : dsThongKeDoanhThu.getData()) {

			Tooltip t = new Tooltip(formatter.format(entry.getYValue()));
			System.out.println(entry.getYValue().toString());
			Node node = entry.getNode();
			Tooltip.install(node, t);
		}
		for (Data<String, Double> entry : dsLoiNhuanDoanhThu.getData()) {

			Tooltip t = new Tooltip(formatter.format(entry.getYValue()));
			System.out.println(entry.getYValue().toString());
			Node node = entry.getNode();
			Tooltip.install(node, t);
		}

	}

	private void xuLyChucNangQuanLyHoaDon() {
		txtSoCMNDKHQLHD.setText("");

		txtHoKHQLHD.setText("");

		txtTenKHQLHD.setText("");

		txtSoDTKHQLHD.setText("");

		txtMANVQLHD.setText("");

		txtMAHDQLHD.setText("");

		LocalDate dateNow = LocalDate.now();

		dpktoQLHD.setValue(dateNow);
		dpkFromQLHD.setValue(dateNow);

		XuLySuKienQuanLyHoaDon();
		HoaDonDAO hdDao = new HoaDonDAO();
		tableQLHD_HD.setItems(dsHoaDonQLHD);
		hdDao.getDSHoaDon(txtSoCMNDKHQLHD, txtHoKHQLHD, txtTenKHQLHD, txtSoDTKHQLHD, txtMANVQLHD, txtMAHDQLHD,
				dpkFromQLHD, dpktoQLHD, tableQLHD_HD, dsHoaDonQLHD);
		tableQLHD_HD.refresh();

		tableQLHD_CTHD.setItems(dsCTHD_QLHD);
		// Đầu tiên, xử lý bảng QLHD
		XuLyBangQLHD_HD();
		XuLyBangQLHD_CTHD();

	}

	private void XuLySuKienQuanLyHoaDon() {

		dpktoQLHD.setOnAction(e -> {
			if (dpkFromQLHD.getValue().isAfter(dpktoQLHD.getValue()) == true && dpkFromQLHD.getValidators() != null)
				dpktoQLHD.setValue(dpkFromQLHD.getValue());
			XuLyActionTimKiemHoaDon();
		});
		dpkFromQLHD.setOnAction(e -> {
			if (dpkFromQLHD.getValue().isAfter(dpktoQLHD.getValue()) == true && dpktoQLHD.getValidators() != null)
				dpkFromQLHD.setValue(dpktoQLHD.getValue());
			XuLyActionTimKiemHoaDon();
		});

		txtSoCMNDKHQLHD.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					XuLyActionTimKiemHoaDon();
				}

			}

		});
		txtSoCMNDKHQLHD.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					XuLyActionTimKiemHoaDon();
				}
			}
		});

		txtHoKHQLHD.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					XuLyActionTimKiemHoaDon();
				}
			}
		});

		txtTenKHQLHD.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					XuLyActionTimKiemHoaDon();
				}
			}
		});

		txtSoDTKHQLHD.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					XuLyActionTimKiemHoaDon();
				}
			}
		});

		txtMANVQLHD.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					XuLyActionTimKiemHoaDon();
				}
			}
		});

		txtMAHDQLHD.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					XuLyActionTimKiemHoaDon();
				}
			}
		});
		btnXoaTrangQLHD.setOnAction(e -> {
			txtSoCMNDKHQLHD.setText("");
			txtHoKHQLHD.setText("");
			txtTenKHQLHD.setText("");
			txtSoDTKHQLHD.setText("");
			txtMANVQLHD.setText("");
			txtMAHDQLHD.setText("");
			LocalDate dateNow = LocalDate.now();
			dpktoQLHD.setValue(dateNow);
			dpkFromQLHD.setValue(dateNow);

		});

	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			Connect_Data.getInstance().connect();
		} catch (Exception e) {
			lblLGStatus.setText("");
			e.printStackTrace();
		}
//		FileInputStream fis = null;
//		BufferedReader reader = null;

		InputStream inputStream = this.getClass().getResourceAsStream("./Path.txt");
		try {
			InputStream inputStream2 = new FileInputStream(new File("./Path.txt"));
			String path = "./Path.txt";
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream2));
			String line;
			try {
				line = reader.readLine();
				while (line != null) {
					System.out.println(line);
					txtThemFolderImg.setText(line);
					LocalPath = line;
					line = reader.readLine();

				}
			} catch (IOException e1) {

				e1.printStackTrace();
			}
		} catch (FileNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		// URL filePath = ClassLoader.getSystemResource("/Path.txt");

		About.setVisible(false);
		About.toBack();
		lblLGStatus.setVisible(false);

		LoginPane.setCenterShape(true);
		MenuPane.setVisible(false);
		dialog.setVisible(false);
		dialogValue.setVisible(false);
		dialogThongBao.setVisible(false);
		MatHangDAO matHangDAO = new MatHangDAO();

		btnChucNang1.setGraphic(new ImageView(image));
		btnChucNang2.setGraphic(new ImageView(image2));
		btnChucNang3.setGraphic(new ImageView(image3));
		btnChucNang4.setGraphic(new ImageView(image4));
		btnChucNang1.setText("Text");
		btnQuayLai.setGraphic(new ImageView(new Image("img/back.png")));

		// ==================THÔNG TIN CÁ NHÂN=======================
		btnLuu.setGraphic(new ImageView(new Image("img/save.png")));
		btnXoaTrang.setGraphic(imgXT);
		btnHoanTac.setGraphic(imgHT);
		btnLuu.setOnAction(e -> {

		});
		btnHoanTac.setOnAction(e -> {
			txtMaNVTT.setText(nv.getMaNV().toString());

			txtHoNVTT.setText(nv.getHoNV());

			txtTenNVTT.setText(nv.getTenNV());

			txtChucVuNVTT.setText(nv.getChucVu());

			txtLuongNVTT.setText(formatter.format(nv.getLuong()));

			txtCaLamNVTT.setText(nv.getCaLam());

			txtNgayVaoLamNVTT.setText(nv.getNgayLam().toString());

			txtDiaChiNVTT.setText(nv.getDiaChi());
			NhanVienDAO nhanVienDao = new NhanVienDAO();
			dsGioiTinhNVTT = nhanVienDao.getDSGioiTinh();
			cbbGioiTinhNVTT.setItems(dsGioiTinhNVTT);
			cbbGioiTinhNVTT.requestFocus();
			cbbGioiTinhNVTT.setValue(nv.getGioiTinh());
			txthideMatKhauNVTT.setText(nv.getMatKhau());

			// txtMatKhauNVTT.setText(nv.getMatKhau());
			txtsoCMNDNVTT.setText(nv.getSoCMND());

		});

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
		matHangDAO.getDSMauSac_DongXe(listMauSac, listDongXe);
		// txtSearch.setPrefHeight(100);
		jcbLoaiXe.setItems(listLoaiXe);
		jcbLoaiXe.setValue("Tất cả");
		jcbPhanKhoi.setItems(listPhanKhoi);
		jcbPhanKhoi.setValue("Tất cả");
		jcbMauSac.setItems(listMauSac);
		jcbMauSac.setValue("Tất cả");
		jcbGiaThanh.setItems(listGiaThanh);
		jcbGiaThanh.setValue("Tất cả");
		jcbDongXe.setItems(listDongXe);
		jcbDongXe.setValue("Tất cả");

		// ====== XỬ LÝ COMBOBOX QL MẶT HÀNG=========
		jcbLoaiXe1.setItems(listLoaiXe);
		jcbLoaiXe1.setValue("Tất cả");
		jcbPhanKhoi1.setItems(listPhanKhoi);
		jcbPhanKhoi1.setValue("Tất cả");
		jcbMauSac1.setItems(listMauSac);
		jcbMauSac1.setValue("Tất cả");
		jcbGiaThanh1.setItems(listGiaThanh);
		jcbGiaThanh1.setValue("Tất cả");
		jcbDongXe1.setItems(listDongXe);
		jcbDongXe1.setValue("Tất cả");
		// jlistView.getSelectionModel().select(" Tìm kiếm sản phẩm");
		btnGioHang.setGraphic(new ImageView(imageshopping));
		// jlistView.setItems(list);

		// ============xử lý bảng GIỎ HÀNG=============
		maGioHang.setCellValueFactory(new PropertyValueFactory<>("maMatHang"));
		soLuongGioHang.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
		// soLuongGioHang.setCellValueFactory(new PropertyValueFactory<>("maMatHang"));

//		maSPCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MatHang, String>>() {
//
//			@Override
//			public void handle(CellEditEvent<MatHang, String> e) {
//				
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

				TablePosition<CTHD, Integer> pos = e.getTablePosition();

				Integer newValue = e.getNewValue();

				int row = pos.getRow();
				CTHD mh = e.getTableView().getItems().get(row);
				MatHang chekmh = matHangDAO.getMatHangg(mh.getMaMatHang());
				if (chekmh.getSoLuong() >= newValue) {
					mh.setSoLuong(newValue);
					mh.getThanhTien();

					price = 0.0;
					thanhtienGioHang = 0.0;
					for (CTHD cthd : dsGioHang) {
						thanhtienGioHang += cthd.getThanhTien();
						MatHang mh2 = matHangDAO.getMatHangg(cthd.getMaMatHang());
						Double tamTinh = mh2.getThueBan() / 100 * cthd.getDonGia();
						price += cthd.getThanhTien() + tamTinh * cthd.getSoLuong();

					}
					// CTHD hd = c.getTableView().getItems().get(this.getIndex());

					txtTongTienThemHD.setText(formatter.format(price));
					//
					lblTongTienGH.setText(formatter.format(thanhtienGioHang));
					tableGioHang.refresh();
					tableLapHoaDon.refresh();

				} else {

					dialog.toFront();
					dialogThongBao.setVisible(true);
					dialogThongBao.toFront();
					dialog.setVisible(true);
					lblDialogTitle.setText("SỐ LƯỢNG VƯỢT QUÁ QUY ĐỊNH! ");
					lblDialogContent.setText(
							"Số lượng không vượt quá: " + chekmh.getSoLuong() + " \n Bạn có muốn nhập lại không?");
					btnNo.setDisable(true);
					btnYes.setOnAction(e2 -> {

						dialog.setVisible(false);
						dialogThongBao.setVisible(false);
						tableGioHang.refresh();
						btnNo.setDisable(false);
						thanhtienGioHang = 0.0;

						lblTongTienGH.setText(formatter.format(thanhtienGioHang));

						price = 0.0;
						thanhtienGioHang = 0.0;
						for (CTHD cthd : dsGioHang) {
							thanhtienGioHang += cthd.getThanhTien();
							MatHang mh2 = matHangDAO.getMatHangg(cthd.getMaMatHang());
							Double tamTinh = mh2.getThueBan() / 100 * cthd.getDonGia();
							price += cthd.getThanhTien() + tamTinh * cthd.getSoLuong();

						}
						// CTHD hd = c.getTableView().getItems().get(this.getIndex());

						txtTongTienThemHD.setText(formatter.format(price));
						//
						lblTongTienGH.setText(formatter.format(thanhtienGioHang));
						tableGioHang.refresh();
						tableLapHoaDon.refresh();
						//

					});
				}
			}
		});
		// soLuongGioHang.setCellFactory(TextFieldTableCell.<CTHD>forTableColumn());
		donGiaGioHang.setCellValueFactory(new PropertyValueFactory<>("donGia"));
		thanhTienGioHang.setCellValueFactory(new PropertyValueFactory<>("thanhTien"));
		giamGiaGioHang.setCellValueFactory(new PropertyValueFactory<>("giamGia"));
		giamGiaGioHang.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		giamGiaGioHang.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CTHD, Double>>() {

			@Override
			public void handle(CellEditEvent<CTHD, Double> e) {

				TablePosition<CTHD, Double> pos = e.getTablePosition();

				Double newValue = e.getNewValue();

				int row = pos.getRow();
				CTHD mh = e.getTableView().getItems().get(row);
				// MatHang chekmh = matHangDAO.getMatHangg(mh.getMaMatHang());
				if (newValue >= 0)
					mh.setGiamGia(newValue);
				mh.getThanhTien();
				thanhtienGioHang = 0.0;

				// lblTongTienGH.setText(formatter.format(thanhtienGioHang));

				price = 0.0;
				thanhtienGioHang = 0.0;
				for (CTHD cthd : dsGioHang) {
					thanhtienGioHang += cthd.getThanhTien();
					MatHang mh2 = matHangDAO.getMatHangg(cthd.getMaMatHang());
					Double tamTinh = mh2.getThueBan() / 100 * cthd.getDonGia();
					price += cthd.getThanhTien() + tamTinh * cthd.getSoLuong();

				}
				System.out.println(price);
				txtTongTienThemHD.setText(formatter.format(price));

				lblTongTienGH.setText(formatter.format(thanhtienGioHang));
				tableGioHang.refresh();
				tableLapHoaDon.refresh();

			}
		});

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
		ThueThemHD.setCellValueFactory(new PropertyValueFactory<>("donGia"));
		ThueThemHD.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		ThueThemHD.setCellFactory(c -> {
			return new TableCell<CTHD, Double>() {
				@Override
				protected void updateItem(Double price, boolean empty) {
					super.updateItem(price, empty);
					if (empty) {
						setText(null);
					} else {
						// matHangDAO.getMatHangg()

						CTHD hd = c.getTableView().getItems().get(this.getIndex());
						MatHang mh = matHangDAO.getMatHangg(hd.getMaMatHang());
						Double tamTinh = mh.getThueBan() / 100 * hd.getDonGia();
						setText(mh.getThueBan() + " % = " + formatter.format(tamTinh));
					}
				}
			};
		});

		STTThemHD.setCellValueFactory(new Callback<CellDataFeatures<MatHang, MatHang>, ObservableValue<MatHang>>() {
			@Override
			public ObservableValue<MatHang> call(CellDataFeatures<MatHang, MatHang> p) {
				return new ReadOnlyObjectWrapper(p.getValue());
			}
		});

		STTThemHD.setCellFactory(new Callback<TableColumn<CTHD, CTHD>, TableCell<CTHD, CTHD>>() {
			@Override
			public TableCell<CTHD, CTHD> call(TableColumn<CTHD, CTHD> param) {
				return new TableCell<CTHD, CTHD>() {
					@Override
					protected void updateItem(CTHD item, boolean empty) {
						super.updateItem(item, empty);

						if (this.getTableRow() != null && item != null) {
							int i = this.getTableRow().getIndex();
							// CTHD mh = dsQLSP.get(i+start);
							// rowcount = dsQLSP.get
							setText(this.getTableRow().getIndex() + start + 1 + "");
						} else {
							setText("");
						}
					}
				};
			}
		});

		STTThemHD.setSortable(false);
		soLuongGioHang1.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
		giamGiaThemHD.setCellValueFactory(new PropertyValueFactory<>("giamGia"));
		giamGiaThemHD.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		giamGiaThemHD.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CTHD, Double>>() {

			@Override
			public void handle(CellEditEvent<CTHD, Double> e) {

				TablePosition<CTHD, Double> pos = e.getTablePosition();

				Double newValue = e.getNewValue();

				int row = pos.getRow();
				CTHD mh = e.getTableView().getItems().get(row);
				// MatHang chekmh = matHangDAO.getMatHangg(mh.getMaMatHang());
				if (newValue >= 0)
					mh.setGiamGia(newValue);

				Double tamTinh = 0.0;
				Double price = 0.0;
				thanhtienGioHang = 0.0;
				for (CTHD cthd : dsGioHang) {
					thanhtienGioHang += cthd.getThanhTien();
					MatHang mh2 = matHangDAO.getMatHangg(cthd.getMaMatHang());
					tamTinh = mh2.getThueBan() / 100 * cthd.getDonGia();
					price += cthd.getThanhTien() + tamTinh * cthd.getSoLuong();

				}
				// CTHD hd = c.getTableView().getItems().get(this.getIndex());

				txtTongTienThemHD.setText(formatter.format(price));
				//
				lblTongTienGH.setText(formatter.format(thanhtienGioHang));
				tableGioHang.refresh();
				tableLapHoaDon.refresh();
				// MatHang mh = e.getTableView().getItems().get(row);

				// mh.setSoLuong(newValue);
			}
		});
		donGiaGioHang1.setCellValueFactory(new PropertyValueFactory<>("donGia"));

		donGiaGioHang1.setCellFactory(c -> {
			return new TableCell<CTHD, Double>() {
				@Override
				protected void updateItem(Double price, boolean empty) {
					super.updateItem(price, empty);
					if (empty) {
						setText(null);
					} else {
						// matHangDAO.getMatHangg()

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
						CTHD hd = c.getTableView().getItems().get(this.getIndex());
						MatHang mh = matHangDAO.getMatHangg(hd.getMaMatHang());
						Double x = mh.getDongia() - (hd.getThanhTien() / hd.getSoLuong());
						Double tamTinh = mh.getThueBan() / 100 * hd.getDonGia();
						price = hd.getThanhTien() + tamTinh * hd.getSoLuong();

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
//		ImageView imgVUnMask = new ImageView(imageUnMask);
//		ImageView imgVMask = new ImageView(imageMask);
		//btnUnMaskTTCN.setGraphic(imgVUnMask);
		btnDoiMatKhauQLNV.setGraphic(new ImageView(new Image("img/lock.png")));
		btnDangXuat.setGraphic(new ImageView(new Image("img/warning.png")));
		btnLeft.setGraphic(new ImageView(new Image("img/left.png")));
		btnRight.setGraphic(new ImageView(new Image("img/right.png")));
		btnFirst.setGraphic(new ImageView(new Image("img/lastLeft.png")));
		btnLast.setGraphic(new ImageView(new Image("img/lastRight.png")));
		btnLeftQLKH.setGraphic(new ImageView(new Image("img/left.png")));
		btnRightQLKH.setGraphic(new ImageView(new Image("img/right.png")));
		btnFirstQLKH.setGraphic(new ImageView(new Image("img/lastLeft.png")));
		btnLastQLKH.setGraphic(new ImageView(new Image("img/lastRight.png")));
		// Chưa
		int i = (matHangDAO.getDSMatHang_Loc(jcbLoaiXe1, jcbPhanKhoi1, jcbMauSac1, jcbGiaThanh1, jcbDongXe1,
				txtSearchQLSP, dsQLSP, tableQLSP, 0, 15));
		if (i % 15 == 0)
			soLuongTrang = i / 15;
		else
			soLuongTrang = (i / 15) + 1;

		tableQLSP.setEditable(true);
		XuLyBangSQLSP();

		// tableQLSP.setItems(dsQLSP);
		// ==========BẮT SỰ KIỆN BUTTON && BÀN PHÍM===================================
		XuLyPhimTatChung();
		// ====XỬ LÝ ĐĂNG NHẬP - THOÁT - ĐĂNG XUẤT====
		menuHelp.setVisible(false);

		menuHelp.toBack();
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

//		btnDangXuat.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent e) {
//				menuHelp.setVisible(true);
//				menuHelp.toFront();
//				btnLogOut.setOnAction(e1 -> {
//					dialogDangXuat();
//					menuHelp.setVisible(false);
//					menuHelp.toBack();
//					
//				});
//				btnAbout.setOnAction(e2 -> { // Hiện about
//					About.setVisible(true);
//					About.toFront();
//				});
//				btnNoAbout.setOnAction(e3 -> {
//					About.setVisible(false);
//					About.toBack();
//
//				});
//				yamas.setOnMouseClicked(e2->{
//					menuHelp.setVisible(false);
//					menuHelp.toBack();
//					
//				});
//				
//				
//			}
//		});
//		btnDangXuat.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent e) {
//				menuHelp.setVisible(false);
//				menuHelp.toBack();
//
//			}
//		});
//		

		btnDangXuat.setOnAction(Event -> {
			XuLyMenuHelp_DangXuat();

		});

		// ==== MENU CHÍNH ====
		btnChucNang1.setOnAction(Event -> {
			ThongTinCaNhan.toFront();
			count = 0;
			XuLyQuanLyNhanVien(nv);
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
			map = matHangDAO.getImageData(jcbLoaiXe, jcbPhanKhoi, jcbMauSac, jcbGiaThanh, jcbDongXe, txtSearch);
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
			// jlistView.getSelectionModel().select(1);
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
			// jlistView.getSelectionModel().select(0);
		});

		btnTTKH.setOnAction(e -> {
			QLKhachHangPane.toFront();
			count = 0;
			// jlistView.getSelectionModel().select(2);
		});
		btnTaoHDMoi.setOnAction(e -> {
			dialogLapHDMoi();
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
			// jlistView.getSelectionModel().select(1);
			txtCMNDKHLHD.setText(selectKH.getSoCMND());
			txtHoKHLHD.setText(selectKH.getHoKH());
			txtTenKHLHD.setText(selectKH.getTenKH());
//			}

		});
		// ==============bắt sự kiện jcb========================================
		// ==============QUẢN LÝ MẶT HÀNG==========
		QLSanPham.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.DELETE) && tableQLSP.isFocused() == true) {

					XuLyXoaSQLSP();
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
		// jcbGiaThanh1.setOnMouseEntered(e ->{});
		btnXoaQLSP.setOnAction(e -> {
			XuLyXoaSQLSP();

		});
		btnLuuQLSP.setOnAction(e -> {
			matHangDAO.CapNhat_QLSP(dsQLSP, tableQLSP, start, end);
		});
		btnThemQLSP.setOnAction(e -> {
			XuLyThemQLSP();
		});
		btnHoanTacQLSP.setOnAction(e -> {
			XuLybtnTimKiemQLSP();
		});
		btnXoaTrangQLSP.setOnAction(e -> {
			lblBinhXangMatHangQLSP.setText("");
			lblDauMayMatHangQLSP.setText("");
			lblMoTaMatHangQLSP.setText("");
			lblDongCoMatHangQLSP.setText("");
			lblDonGiaMatHangQLSP.setText("");
			lblKhungXeMatHangQLSP.setText("");
			lblKichThuocMatHangQLSP.setText("");
			lblLyDongMatHangQLSP.setText("");
			lblMaMatHangQLSP.setText("");
			lblMauXeQLSP.setText("");
			lblSoLuongMatHangQLSP.setText("");
			lblTenMatHangQLSP.setText("");
			lblThueMatHangQLSP.setText("");
			cbbDongXeThemQLSP.getSelectionModel().selectFirst();
			cbbLoaiXeThemQLSP.getSelectionModel().selectFirst();
			lblMaMatHangQLSP.requestFocus();
			jcbLoaiXe1.getSelectionModel().selectFirst();
			jcbGiaThanh1.getSelectionModel().selectFirst();
			jcbLoaiXe1.getSelectionModel().selectFirst();
			jcbMauSac1.getSelectionModel().selectFirst();
			jcbPhanKhoi1.getSelectionModel().selectFirst();
			XuLybtnTimKiemQLSP();
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
				lblPathHinhAnhQLSP.setText(file.toURI().toString());

			}
		});
	}

	private void XuLyQuanLyNhanVien(NhanVien nv) {
//		ImageView imgVUnMask = new ImageView(imageUnMask);
//		ImageView imgVMask = new ImageView(imageMask);
		btnDoiMatKhauQLNV.setOnAction(e -> {
			System.out.println("Chọn btn đổi mk");
			XuLydialogDoiMatKhau(nv);

		});
		btnLuu.setOnAction(e -> {

		});

	}

	private void XuLydialogDoiMatKhau(NhanVien nv) {
		ImageView imgVUnMaskC = new ImageView(imageUnMask);
		ImageView imgVMaskC = new ImageView(imageMask);
		ImageView imgVUnMaskM = new ImageView(imageUnMask);
		ImageView imgVMaskM = new ImageView(imageMask);
		ImageView imgVUnMaskXN = new ImageView(imageUnMask);
		ImageView imgVMaskXN = new ImageView(imageMask);
		btnMaskMatKhauCu.setGraphic(imgVUnMaskC);
		btnMaskMatKhauMoi.setGraphic(imgVUnMaskM);
		btnMaskMatKhauXacNhan.setGraphic(imgVUnMaskXN);
		dialog.setVisible(true);
		dialog.toFront();
		dialogDoiMatKhau.setVisible(true);
		dialogDoiMatKhau.toFront();
		passwordC = "đang ẩn";
		passwordM = "đang ẩn";
		passwordXN = "đang ẩn";

		txtMatKhauCu.setText("");
		txtMatKhauMoi.setText("");
		txtMatKhauXacNhan.setText("");

		txtMatKhauCu.toFront();
		txtMatKhauMoi.toFront();
		txtMatKhauXacNhan.toFront();
		txtMatKhauCu.setVisible(true);
		txtMatKhauMoi.setVisible(true);
		txtMatKhauXacNhan.setVisible(true);

		txtMatKhauCutxt.setVisible(false);
		
		txtMatKhauMoitxt.setVisible(false);
		txtMatKhauXacNhantxt.setVisible(false);
		

		btnMaskMatKhauCu.addEventFilter(MouseEvent.MOUSE_CLICKED, ex2 -> {
			if (passwordC.equals("") == false) {
				
				txtMatKhauCutxt.setText(txtMatKhauCu.getText());
				txtMatKhauCu.clear();
				txtMatKhauCutxt.toFront();
				txtMatKhauCutxt.setVisible(true);
				txtMatKhauCu.setVisible(false);
				btnMaskMatKhauCu.setGraphic(imgVMaskC);
				passwordC = "";
				// Đang

			} else {
				txtMatKhauCu.setText(txtMatKhauCutxt.getText());
				txtMatKhauCutxt.clear();
				txtMatKhauCu.toFront();
				txtMatKhauCutxt.setVisible(false);
				txtMatKhauCu.setVisible(true);
				passwordC = "đang ẩn";
				btnMaskMatKhauCu.setGraphic(imgVUnMaskC);
			}

		});
		btnMaskMatKhauMoi.addEventFilter(MouseEvent.MOUSE_CLICKED, ex2 -> {
			if (passwordM.equals("") == false) {
				txtMatKhauMoitxt.setText(txtMatKhauMoi.getText());
				txtMatKhauMoi.clear();
				txtMatKhauMoitxt.toFront();
				txtMatKhauMoitxt.setVisible(true);
				txtMatKhauMoi.setVisible(false);
				btnMaskMatKhauMoi.setGraphic(imgVMaskM);
				passwordM = "";
				// Đang

			} else {
				txtMatKhauMoi.setText(txtMatKhauMoitxt.getText());
				txtMatKhauMoitxt.clear();
				txtMatKhauMoi.toFront();
				txtMatKhauMoitxt.setVisible(false);
				txtMatKhauMoi.setVisible(true);
				passwordM = "đang ẩn";
				btnMaskMatKhauMoi.setGraphic(imgVUnMaskM);
			}

		});
		btnMaskMatKhauXacNhan.addEventFilter(MouseEvent.MOUSE_CLICKED, ex2 -> {
			if (passwordXN.equals("") == false) {
				txtMatKhauXacNhantxt.setText(txtMatKhauXacNhan.getText());
				txtMatKhauXacNhan.clear();
				txtMatKhauXacNhantxt.toFront();
				txtMatKhauXacNhantxt.setVisible(true);
				txtMatKhauXacNhan.setVisible(false);
				btnMaskMatKhauXacNhan.setGraphic(imgVMaskXN);
				passwordXN = "";
				// Đang

			} else {
				txtMatKhauXacNhan.setText(txtMatKhauXacNhantxt.getText());
				txtMatKhauXacNhantxt.clear();
				txtMatKhauXacNhan.toFront();
				txtMatKhauXacNhantxt.setVisible(false);
				txtMatKhauXacNhan.setVisible(true);

				passwordXN = "đang ẩn";
				btnMaskMatKhauXacNhan.setGraphic(imgVUnMaskXN);
			}

		});

		btnThayDoiMK.setOnAction(eDoi -> {
			XuLyBtnDoiMatKhau(nv);

		});
		btnThoatDoiMK.setOnAction(e_thoat -> {
			dialog.setVisible(false);
			dialog.toBack();
			dialogDoiMatKhau.setVisible(false);

		});

		// ======= SỰ KIỆN KEY========

	}

	private boolean XuLyBtnDoiMatKhau(NhanVien nv2) {
		String matKhauCu = "";
		String matKhauMoi = "";
		String matKhauXN = "";
		if (passwordC.equals("") == true)
			matKhauCu = txtMatKhauCutxt.getText();
		else
			matKhauCu = txtMatKhauCu.getText();
		if (passwordM.equals("") == true)
			matKhauMoi = txtMatKhauMoitxt.getText();
		else
			matKhauMoi = txtMatKhauMoi.getText();
		if (passwordXN.equals("") == true)
			matKhauXN = txtMatKhauXacNhantxt.getText();
		else
			matKhauXN = txtMatKhauXacNhan.getText();

//		System.out.println(passwordC + "-" + passwordM + "-" + passwordXN);
//		System.out.println(matKhauCu + "-" + matKhauMoi + "-" + matKhauXN);
		if (regexMatKhau(matKhauCu, matKhauMoi, matKhauXN) == true) {
			if (matKhauCu.equals(nv2.getMatKhau()) == true) {
				if (matKhauMoi.equals(matKhauXN) == true) { // đổi mật khẩu thành công
					nv.setMatKhau(matKhauMoi);
					nv2.setMatKhau(matKhauMoi);
					NhanVienDAO nvDao = new NhanVienDAO();
					nvDao.CapNhat_MatKhau(nv2);
					txtTrangThaiDoiMK.setText("ĐỔI MẬT KHẨU THÀNH CÔNG!");
					txthideMatKhauNVTT.setText(matKhauMoi);
					
					return true;
				} else { // mật khẩu mới k trùng
					txtTrangThaiDoiMK.setText("Mật khẩu mới và xác nhận mật khẩu không trùng khớp!");
					if (passwordM.equals("") == false)
						txtMatKhauMoitxt.requestFocus();
					else
						txtMatKhauMoi.requestFocus();

				}
			} else {   // mật khẩu cũ k đúng
				txtTrangThaiDoiMK.setText("Mật khẩu hiện tại không đúng. Vui lòng nhập lại.");
				if (passwordC.equals("") == false)
					txtMatKhauCutxt.requestFocus();
				else
					txtMatKhauCu.requestFocus();
				return false;
			}
			return true;
		} else
			return false;

	}

	private boolean regexMatKhau(String x, String y, String z) {
		if (!(x.length() > 0 && x.matches("[A-Za-z0-9]{6}[A-Za-z0-9]{0,10}"))) {
			if (x.length() == 0) {
				txtTrangThaiDoiMK.setText("Mật khẩu cũ không được để trống!");

			} else
				txtTrangThaiDoiMK
						.setText("Mật khẩu phải có độ dài 6-16 ký tự, có ít nhất 1 chữ cái (a-z hoặc A-Z) và 1 chữ số");
			if (passwordC.equals("") == true)
				txtMatKhauCutxt.requestFocus();
			else
				txtMatKhauCu.requestFocus();
			return false;
		}
		if (!(y.length() > 0 && y.matches("[A-Za-z0-9]{6}[A-Za-z0-9]{0,10}"))) {
			if (y.length() == 0) {
				txtTrangThaiDoiMK.setText("Mật khẩu mới không được để trống!");

			} else
				txtTrangThaiDoiMK
						.setText("Mật khẩu phải có độ dài 6-16 ký tự, có ít nhất 1 chữ cái (a-z hoặc A-Z) và 1 chữ số");
			if (passwordM.equals("") == true)
				txtMatKhauMoitxt.requestFocus();
			else
				txtMatKhauMoi.requestFocus();
			return false;
		}

		if (!(z.length() > 0 && z.matches("[A-Za-z0-9]{6}[A-Za-z0-9]{0,10}"))) {
			if (z.length() == 0) {
				txtTrangThaiDoiMK.setText("Mật khẩu xác nhận không được để trống!");

			} else
				txtTrangThaiDoiMK
						.setText("Mật khẩu phải có độ dài 6-16 ký tự, có ít nhất 1 chữ cái (a-z hoặc A-Z) và 1 chữ số");
			if (passwordXN.equals("") == true)
				txtMatKhauXacNhantxt.requestFocus();
			else
				txtMatKhauXacNhan.requestFocus();
			return false;
		}
		return true;
	}

	private void XuLyThemQLSP() {
		// Đang26

//		
		if (checkRegex_ThemSp() == true) {

			MatHang mh = new MatHang(lblMaMatHangQLSP.getText().trim(), lblTenMatHangQLSP.getText().toString(),
					cbbLoaiXeThemQLSP.getSelectionModel().getSelectedItem().toString().trim(),
					cbbDongXeThemQLSP.getSelectionModel().getSelectedItem().toString().trim(),
					Integer.parseInt(lblSoLuongMatHangQLSP.getText().trim()),
					Double.parseDouble(lblBinhXangMatHangQLSP.getText().trim()),
					Double.parseDouble(lblDauMayMatHangQLSP.getText().trim()), lblLyDongMatHangQLSP.getText().trim(),
					lblMoTaMatHangQLSP.getText().trim(), lblMauXeQLSP.getText().trim(),
					Double.parseDouble(lblDongCoMatHangQLSP.getText().trim()), lblKichThuocMatHangQLSP.getText().trim(),
					lblKhungXeMatHangQLSP.getText().trim(), Double.parseDouble(lblDonGiaMatHangQLSP.getText().trim()),
					lblPathHinhAnhQLSP.getText().trim(), Double.parseDouble(lblThueMatHangQLSP.getText().trim()));
			System.out.println(mh.toString());

			try {
				String s = XuLyPath_DiChuyenAnhThemSP(lblPathHinhAnhQLSP.getText().trim()).trim();
				mh.setImage(s);
				MatHangDAO mhDao = new MatHangDAO();
				try {
					mhDao.themNhanVien(mh);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					dialog.setVisible(true);
					dialog.toFront();
					dialog.toString();
					dialogThongBao.setVisible(true);
					lblDialogTitle.setText("Trùng mã mặt hàng!");
					lblDialogContent.setText("Vui lòng đổi mã mặt hàng khác.");
					lblMaMatHangQLSP.requestFocus();
					// e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				dialog.setVisible(true);
				dialog.toFront();
				dialog.toString();
				dialogThongBao.setVisible(true);
				lblDialogTitle.setText("Trùng mã mặt hàng!");
				lblDialogContent.setText("Vui lòng đổi mã mặt hàng khác.");
				lblMaMatHangQLSP.requestFocus();
				e.printStackTrace();
			}

			tableQLSP.getItems().add(mh);
			tableQLSP.refresh();
			btnYes.setVisible(true);

		}

	}

	private boolean checkRegex_ThemSp() {

		if (!(lblMaMatHangQLSP.getText().trim().length() > 0
				&& lblMaMatHangQLSP.getText().trim().matches("[A-Z]{3}\\d{3}[A-Z]{1}"))) {

			dialog.setVisible(true);
			dialog.toFront();
			dialogThongBao.setVisible(true);
			lblDialogTitle.setText("Mã mặt hàng không đúng!");
			lblDialogContent.setText("Không đúng cứ pháp XXX---X. Với X là chữ, - là số. VD: EXT123T ");

			XuLyBtnNoRegex(lblMaMatHangQLSP);

			return false;
		}
		if (!(lblTenMatHangQLSP.getText().trim().length() > 0 && lblTenMatHangQLSP.getText().trim().matches(
				"[AÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴĐaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵđA-Za-z\\d -]*"))) {
			dialog.setVisible(true);
			dialog.toFront();
			dialogThongBao.setVisible(true);
			lblDialogTitle.setText("Tên mặt hàng không đúng!");
			lblDialogContent.setText("Tên mặt hàng không được để trống và Chỉ chứa chữ!");

			XuLyBtnNoRegex(lblTenMatHangQLSP);
			return false;
		}
//	 else if (!(lblLoaiMatHangQLSP.getText().trim().length() > 0 && lblLoaiMatHangQLSP.getText().trim().matches(
//			"[AÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴĐaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵđA-Za-z\\d -]*"))) {
//		dialog.setVisible(true);
//		dialog.toFront();
//		dialogThongBao.setVisible(true);
//		lblDialogTitle.setText("Loại xe không đúng!");
//		lblDialogContent.setText("Tên mặt hàng không được để trống và Chỉ chứa chữ!");
//		
//		XuLyBtnNoRegex(lblTenMatHangQLSP);
//		return false;
//	}    LOẠI XE THAY BẰNG COMBOBOX
		if (lblSoLuongMatHangQLSP.getText().trim().length() > 0) {
			try {
				int x = Integer.parseInt(lblSoLuongMatHangQLSP.getText().trim());
				if (x <= 0) {
					dialog.setVisible(true);
					dialog.toFront();
					dialogThongBao.setVisible(true);
					lblDialogTitle.setText("Số lượng không đúng!");
					lblDialogContent.setText("Số lượng không được để trống và phải lớn hơn 0!");

					XuLyBtnNoRegex(lblSoLuongMatHangQLSP);

					return false;
				}
			} catch (Exception e) {
				dialog.setVisible(true);
				dialog.toFront();
				dialogThongBao.setVisible(true);
				lblDialogTitle.setText("Số lượng nhập vào không đúng!");
				lblDialogContent.setText("Số lượng phải là số nguyên dương.");

				XuLyBtnNoRegex(lblSoLuongMatHangQLSP);

				return false;
			}

		} else if (lblSoLuongMatHangQLSP.getText().trim().length() <= 0) {
			dialog.setVisible(true);
			dialog.toFront();
			dialogThongBao.setVisible(true);
			lblDialogTitle.setText("Số lượng nhập vào không được để trống!");
			lblDialogContent.setText("Số lượng phải là số nguyên dương.");

			XuLyBtnNoRegex(lblSoLuongMatHangQLSP);

			return false;
		}

		if (lblDonGiaMatHangQLSP.getText().trim().length() > 0) {
			try {
				Double x = Double.parseDouble(lblDonGiaMatHangQLSP.getText().trim());
				if (x <= 0) {
					dialog.setVisible(true);
					dialog.toFront();
					dialogThongBao.setVisible(true);
					lblDialogTitle.setText("Đơn giá không đúng!");
					lblDialogContent.setText("Đơn giá phải là số thực và phải lớn hơn 0!");

					XuLyBtnNoRegex(lblDonGiaMatHangQLSP);

					return false;
				}
			} catch (Exception e) {
				dialog.setVisible(true);
				dialog.toFront();
				dialogThongBao.setVisible(true);
				lblDialogTitle.setText("Đon giá nhập vào không đúng!");
				lblDialogContent.setText("Đơn giá phải là số thực và phải lớn hơn 0.");

				XuLyBtnNoRegex(lblDonGiaMatHangQLSP);

				return false;
			}

		} else if (lblDonGiaMatHangQLSP.getText().trim().length() <= 0) {
			dialog.setVisible(true);
			dialog.toFront();
			dialogThongBao.setVisible(true);
			lblDialogTitle.setText("Đơn giá nhập vào không được để trống!");
			lblDialogContent.setText("Đơn giá phải là số thực và phải lớn hơn 0.");

			XuLyBtnNoRegex(lblDonGiaMatHangQLSP);

			return false;
		}
		if (lblThueMatHangQLSP.getText().trim().length() > 0) {
			try {
				Double x = Double.parseDouble(lblThueMatHangQLSP.getText().trim());
				if (x <= 0) {
					dialog.setVisible(true);
					dialog.toFront();
					dialogThongBao.setVisible(true);
					lblDialogTitle.setText("Thuế không đúng!");
					lblDialogContent.setText("Thuế phải là số thực và phải >= 0!");

					XuLyBtnNoRegex(lblThueMatHangQLSP);

					return false;
				}
			} catch (Exception e) {
				dialog.setVisible(true);
				dialog.toFront();
				dialogThongBao.setVisible(true);
				lblDialogTitle.setText("Đon giá nhập vào không đúng!");
				lblDialogContent.setText("Thuế phải là số thực và phải >= 0.");

				XuLyBtnNoRegex(lblThueMatHangQLSP);

				return false;
			}

		} else if (lblThueMatHangQLSP.getText().trim().length() <= 0) {
			dialog.setVisible(true);
			dialog.toFront();
			dialogThongBao.setVisible(true);
			lblDialogTitle.setText("Thuế nhập vào không được để trống!");
			lblDialogContent.setText("Thuế phải là số thực và phải >= 0.");

			XuLyBtnNoRegex(lblThueMatHangQLSP);

			return false;
		}

		if (lblBinhXangMatHangQLSP.getText().trim().length() > 0) {
			try {
				Double x = Double.parseDouble(lblBinhXangMatHangQLSP.getText().trim());
				if (x <= 0) {
					dialog.setVisible(true);
					dialog.toFront();
					dialogThongBao.setVisible(true);
					lblDialogTitle.setText("Dung tích bình xăng không đúng!");
					lblDialogContent.setText("Dung tích bình xăng phải là số thực và phải lớn hơn 0!");

					XuLyBtnNoRegex(lblBinhXangMatHangQLSP);

					return false;
				}
			} catch (Exception e) {
				dialog.setVisible(true);
				dialog.toFront();
				dialogThongBao.setVisible(true);
				lblDialogTitle.setText("Dung tích bình xăng không đúng!");
				lblDialogContent.setText("Dung tích bình xăng phải là số thực và phải lớn hơn 0.");

				XuLyBtnNoRegex(lblBinhXangMatHangQLSP);

				return false;
			}

		} else if (lblBinhXangMatHangQLSP.getText().trim().length() <= 0) {
			dialog.setVisible(true);
			dialog.toFront();
			dialogThongBao.setVisible(true);
			lblDialogTitle.setText("Dung tích bình xăng không được để trống!");
			lblDialogContent.setText("Dung tích bình xăng phải là số thực và phải lớn hơn 0.");

			XuLyBtnNoRegex(lblBinhXangMatHangQLSP);

			return false;
		}
		if (lblDauMayMatHangQLSP.getText().trim().length() > 0) {
			try {
				Double x = Double.parseDouble(lblDauMayMatHangQLSP.getText().trim());
				if (x <= 0) {
					dialog.setVisible(true);
					dialog.toFront();
					dialogThongBao.setVisible(true);
					lblDialogTitle.setText("Dung tích dầu máy không đúng!");
					lblDialogContent.setText("Dung tích dầu máy phải là số thực và phải lớn hơn 0!");

					XuLyBtnNoRegex(lblDauMayMatHangQLSP);

					return false;
				}
			} catch (Exception e) {
				dialog.setVisible(true);
				dialog.toFront();
				dialogThongBao.setVisible(true);
				lblDialogTitle.setText("Dung tích dầu máy không đúng!");
				lblDialogContent.setText("Dung tích dầu máy phải là số thực và phải lớn hơn 0.");

				XuLyBtnNoRegex(lblDauMayMatHangQLSP);

				return false;
			}

		} else if (lblDauMayMatHangQLSP.getText().trim().length() <= 0) {
			dialog.setVisible(true);
			dialog.toFront();
			dialogThongBao.setVisible(true);
			lblDialogTitle.setText("Dung tích dầu máy không được để trống!");
			lblDialogContent.setText("Dung tích dầu máy phải là số thực và phải lớn hơn 0.");

			XuLyBtnNoRegex(lblDauMayMatHangQLSP);

			return false;
		}
		if (!(lblKichThuocMatHangQLSP.getText().trim().length() > 0
				&& lblKichThuocMatHangQLSP.getText().trim().matches("(\\d)+( mm x \\d+){2}( mm)"))) {
			dialog.setVisible(true);
			dialog.toFront();
			dialogThongBao.setVisible(true);
			lblDialogTitle.setText("Kích thước không đúng!");
			lblDialogContent.setText("Kích thước phải theo dạng: amm x bmm x cmm ");

			XuLyBtnNoRegex(lblKichThuocMatHangQLSP);
			return false;
		}
		if (!(lblLyDongMatHangQLSP.getText().trim().length() > 0 && lblLyDongMatHangQLSP.getText().trim().matches(
				"[AÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴĐaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵđA-Za-z\\d -]*"))) {
			dialog.setVisible(true);
			dialog.toFront();
			dialogThongBao.setVisible(true);
			lblDialogTitle.setText("Ly động không đúng!");
			lblDialogContent.setText("Ly động không được để trống và Chỉ chứa chữ!");

			XuLyBtnNoRegex(lblLyDongMatHangQLSP);
			return false;
		}
//		if (!(lblDongMatHangQLSP.getText().trim().length() > 0 && lblDongMatHangQLSP.getText().trim().matches(
//				"[AÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴĐaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵđA-Za-z\\d -]*"))) {
//			dialog.setVisible(true);
//			dialog.toFront();
//			dialogThongBao.setVisible(true);
//			lblDialogTitle.setText("Dòng xe không đúng!");
//			lblDialogContent.setText("Dòng xe không được để trống và Chỉ chứa chữ!");
//
//			XuLyBtnNoRegex(lblDongMatHangQLSP);
//			return false;
//		}
		if (!(lblMauXeQLSP.getText().trim().length() > 0 && lblMauXeQLSP.getText().trim().matches(
				"[AÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴĐaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵđA-Za-z\\d -]*"))) {
			dialog.setVisible(true);
			dialog.toFront();
			dialogThongBao.setVisible(true);
			lblDialogTitle.setText("Màu xe không đúng!");
			lblDialogContent.setText("Màu xe không được để trống và Chỉ chứa chữ!");

			XuLyBtnNoRegex(lblMauXeQLSP);
			return false;
		}
		if (!(lblKhungXeMatHangQLSP.getText().trim().length() > 0 && lblKhungXeMatHangQLSP.getText().trim().matches(
				"[AÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴĐaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵđA-Za-z\\d -]*"))) {
			dialog.setVisible(true);
			dialog.toFront();
			dialogThongBao.setVisible(true);
			lblDialogTitle.setText("Khung xe không đúng!");
			lblDialogContent.setText("Khung xe không được để trống và Chỉ chứa chữ!");

			XuLyBtnNoRegex(lblKhungXeMatHangQLSP);
			return false;
		}
		if (!(lblMoTaMatHangQLSP.getText().trim().length() > 0 && lblMoTaMatHangQLSP.getText().trim().matches(
				"[AÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴĐaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵđA-Za-z\\d -]*"))) {
			dialog.setVisible(true);
			dialog.toFront();
			dialogThongBao.setVisible(true);
			lblDialogTitle.setText("Mô tả không đúng!");
			lblDialogContent.setText("Mô tả không được để trống và Chỉ chứa chữ!");

			XuLyBtnNoRegex(lblMoTaMatHangQLSP);

			return false;
		}
		if (lblPathHinhAnhQLSP.getText().trim().equals("") == true || lblPathHinhAnhQLSP.getText() == null) {
			dialog.setVisible(true);
			dialog.toFront();
			dialogThongBao.setVisible(true);
			lblDialogTitle.setText("Chưa chọn hình ảnh!");
			lblDialogContent.setText("Hình ảnh không được để trống.");

			XuLyBtnNoRegex(txtCaLamNVTT);

			return false;
		}

		return true;

	}

	private void XuLyBtnNoRegex(JFXTextField txt) {
		// btnYes.setDisable(true);

		btnNo.setText("Thoát");
		btnYes.setText("Đồng ý!");
		btnYes.setOnAction(e -> {
			dialog.toBack();
			dialog.setVisible(false);
			dialogThongBao.setVisible(false);
			btnYes.setDisable(false);
			txt.requestFocus();

		});

		btnNo.setOnAction(e -> {
			dialog.toBack();
			dialog.setVisible(false);
			dialogThongBao.setVisible(false);
			btnYes.setDisable(false);
			txt.requestFocus();

		});

	}

	private void XuLyXoaSQLSP() {

		dialog.setVisible(true);
		dialogThongBao.setVisible(true);
		dialogThongBao.toFront();
		dialog.toFront();
		lblDialogTitle.setText("Xác nhận xoá sản phẩm!");
		lblDialogContent.setText("Lưu ý: Sản phẩm sẽ được xoá hoàn toàn và có thể ảnh hưởng đến một số hoá đơn!");
		btnYes.setText("Xoá");
		btnYes.setOnAction(e1 -> {
			MatHang selectedItem = tableQLSP.getSelectionModel().getSelectedItem();
			MatHangDAO matHangDAO = new MatHangDAO();
			if (matHangDAO.XoaMatHang_TheoMa(selectedItem.getMaMatHang()) == true) {
				dialog.setVisible(true);
				dialogThongBao.setVisible(true);
				dialogThongBao.toFront();
				// tableQLSP.getItems().remove(tableQLSP.getSelectionModel().getSelectedItem());

				dialog.setVisible(false);
				tableQLSP.refresh();

				// dialog.setVisible(false);
				// xác nhận xoá

				btnYes.setVisible(false);
				lblDialogTitle.setText("XOÁ THÀNH CÔNG!");
				lblDialogContent.setText("Sản phẩm đã được xoá khỏi hệ thống!");
				dialog.setVisible(true);
				dialogThongBao.setVisible(true);
				dialogThongBao.toFront();
				// btnYes.setDisable(true);
				XuLybtnTimKiemQLSP();
				// tableQLSP.refresh();
				btnNo.setText("Thoát");

			}

		});
		btnNo.setOnAction(e2 -> {
			btnNo.setText("Thoát");
			dialog.setVisible(false);
			dialogThongBao.setVisible(false);
			dialog.toBack();
			btnYes.setText("Có");
			btnYes.setVisible(true);
		});

	}

	private void XuLyMenuHelp_DangXuat() {

		menuHelp.setVisible(true);
		menuHelpMini.setVisible(true);
		menuHelp.toFront();
		btnLogOut.setOnAction(e1 -> {
			dialogDangXuat();
			menuHelp.setVisible(false);
			menuHelp.toBack();

		});
		btnAbout.setOnAction(e2 -> { // Hiện about
			About.setVisible(true);
			About.toFront();
		});
		btnNoAbout.setOnAction(e3 -> {
			About.setVisible(false);
			About.toBack();

		});
		yamas.setOnMouseClicked(e2 -> {
			menuHelp.setVisible(false);
			menuHelp.toBack();
			menuHelpMini.setVisible(false);

		});

	}

	private void XuLyPhimTatChung() {

		yamas.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {

				if (ke.getCode().equals(KeyCode.F4)) {
					About.setVisible(true);
					About.toFront();
//					btnAbout.setOnAction(e2 -> { // Hiện about
//						About.setVisible(true);
//						About.toFront();
//					});
					btnNoAbout.setOnAction(e3 -> {
						About.setVisible(false);
						About.toBack();

					});
				}

				if (ke.getCode().equals(KeyCode.ESCAPE)) {
					XuLyMenuHelp_DangXuat();
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
			else
				DialogthemSP(mh, maSP);
			// btnThemSP.setDisable(true);

		});

	}

	private void XuLyBangQLHD_CTHD() {
		tableQLHD_CTHD.setPlaceholder(new Label("Chưa chọn hoá đơn!"));
		MatHangDAO matHangDAO = new MatHangDAO();

		maMH_QLHD_CTHDCol.setCellValueFactory(new PropertyValueFactory<>("maMatHang"));
		soLuongMH_QLHD_CTHDCol.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
		tenMH_QLHD_CTHDCol.setCellValueFactory(new PropertyValueFactory<>("maMatHang"));
		tenMH_QLHD_CTHDCol.setCellFactory(c -> {
			return new TableCell<CTHD, String>() {
				@Override
				protected void updateItem(String price, boolean empty) {
					super.updateItem(price, empty);
					if (empty) {
						setText(null);
					} else {
						// matHangDAO.getMatHangg()

						CTHD hd = c.getTableView().getItems().get(this.getIndex());

						MatHang mh = matHangDAO.getMatHangg(hd.getMaMatHang());

						setText(mh.getTenMatHang());
					}
				}
			};
		});

		thueMH_QLHD_CTHDCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		thueMH_QLHD_CTHDCol.setCellFactory(c -> {
			return new TableCell<CTHD, Double>() {
				@Override
				protected void updateItem(Double price, boolean empty) {
					super.updateItem(price, empty);
					if (empty) {
						setText(null);
					} else {
						// matHangDAO.getMatHangg()

						CTHD hd = c.getTableView().getItems().get(this.getIndex());

						MatHang mh = matHangDAO.getMatHangg(hd.getMaMatHang());
						Double tamTinh = mh.getThueBan() / 100 * hd.getDonGia();
						setText(mh.getThueBan() + " % = " + formatter.format(tamTinh));
					}
				}
			};
		});
		donGiaMH_QLHD_CTHDCol.setCellValueFactory(new PropertyValueFactory<>("donGia"));
		donGiaMH_QLHD_CTHDCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

		donGiaMH_QLHD_CTHDCol.setCellFactory(c -> {
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
		giamGiaMH_QLHD_CTHDCol.setCellValueFactory(new PropertyValueFactory<>("giamGia"));
		giamGiaMH_QLHD_CTHDCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

		giamGiaMH_QLHD_CTHDCol.setCellFactory(c -> {
			return new TableCell<CTHD, Double>() {

				@Override
				protected void updateItem(Double price, boolean empty) {

					super.updateItem(price, empty);
					if (empty) {
						setText(null);
					} else {
						if (price == 0)
							setText(null);
						else if (price < 100 && price > 0)
							setText(price + "%");
						else if (price > 0)
							setText(formatter.format(price));
					}
				}
			};

		});

		thanhTienMH_QLHD_CTHDCol.setCellValueFactory(new PropertyValueFactory<>("thanhTien"));
		thanhTienMH_QLHD_CTHDCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

		thanhTienMH_QLHD_CTHDCol.setCellFactory(c -> {
			return new TableCell<CTHD, Double>() {

				@Override
				protected void updateItem(Double price, boolean empty) {

					super.updateItem(price, empty);
					if (empty) {
						setText(null);
					} else {
						CTHD hd = c.getTableView().getItems().get(this.getIndex());

						MatHang mh = matHangDAO.getMatHangg(hd.getMaMatHang());
						// Double x = mh.getDongia() - (hd.getThanhTien() / hd.getSoLuong());
						Double tamTinh = mh.getThueBan() / 100 * hd.getDonGia();
						price = hd.getThanhTien() + tamTinh * hd.getSoLuong();

						// setText(formatter.format(price));
						setText(formatter.format(price));
					}
				}
			};

		});

	}

	private void XuLyBangQLHD_HD() {

		tableQLHD_HD.setPlaceholder(new Label("Không tìm thấy hoá đơn nào phù hợp!"));

		maHD_QLHD_HDCol.setCellValueFactory(new PropertyValueFactory<>("maHoaDon"));
		soCMNDKH_QLHD_HDCol.setCellValueFactory(new PropertyValueFactory<>("soCMND"));
		// hoKH_QLHD_HDCol.setCellValueFactory(new PropertyValueFactory<>("soCMND"));
		maNV_QLHD_HDCol.setCellValueFactory(new PropertyValueFactory<>("maNV"));
		ngayLapHD_QLHD_HDCol.setCellValueFactory(new PropertyValueFactory<>("ngayLapHD"));
		TongTienHD_QLHD_HDCol.setCellValueFactory(new PropertyValueFactory<>("tongTien"));
		TongTienHD_QLHD_HDCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

		TongTienHD_QLHD_HDCol.setCellFactory(c -> {
			return new TableCell<HoaDon, Double>() {

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
		hoKH_QLHD_HDCol.setCellValueFactory(new PropertyValueFactory<>("maHoaDon"));

		hoKH_QLHD_HDCol.setCellFactory(c -> {
			return new TableCell<HoaDon, String>() {
				@Override
				protected void updateItem(String price, boolean empty) {
					super.updateItem(price, empty);
					if (empty) {
						setText(null);
					} else {
						KhachHangDAO khDao = new KhachHangDAO();
						HoaDon hd = c.getTableView().getItems().get(this.getIndex());
						KhachHang kh = khDao.getKhachHang_MaKH(hd.getSoCMND());

						setText(kh.getHoKH());
					}
				}
			};
		});
		tenKH_QLHD_HDCol.setCellValueFactory(new PropertyValueFactory<>("maHoaDon"));

		tenKH_QLHD_HDCol.setCellFactory(c -> {
			return new TableCell<HoaDon, String>() {
				@Override
				protected void updateItem(String price, boolean empty) {
					super.updateItem(price, empty);
					if (empty) {
						setText(null);
					} else {
						KhachHangDAO khDao = new KhachHangDAO();
						HoaDon hd = c.getTableView().getItems().get(this.getIndex());
						KhachHang kh = khDao.getKhachHang_MaKH(hd.getSoCMND());

						setText(kh.getTenKH());

					}
				}
			};
		});
		soDTKH_QLHD_HDCol.setCellValueFactory(new PropertyValueFactory<>("maHoaDon"));

		soDTKH_QLHD_HDCol.setCellFactory(c -> {
			return new TableCell<HoaDon, String>() {
				@Override
				protected void updateItem(String price, boolean empty) {
					super.updateItem(price, empty);
					if (empty) {
						setText(null);
					} else {
						KhachHangDAO khDao = new KhachHangDAO();
						HoaDon hd = c.getTableView().getItems().get(this.getIndex());
						KhachHang kh = khDao.getKhachHang_MaKH(hd.getSoCMND());

						setText(kh.getSoDT());
					}
				}
			};
		});

		hoNV_QLHD_HDCol.setCellValueFactory(new PropertyValueFactory<>("maHoaDon"));

		hoNV_QLHD_HDCol.setCellFactory(c -> {
			return new TableCell<HoaDon, String>() {
				@Override
				protected void updateItem(String price, boolean empty) {
					super.updateItem(price, empty);
					if (empty) {
						setText(null);
					} else {
						NhanVienDAO nvDao = new NhanVienDAO();
						HoaDon hd = c.getTableView().getItems().get(this.getIndex());
						NhanVien nv = nvDao.getNhanVientheoMaNV(hd.getMaNV());

						setText(nv.getHoNV());
					}
				}
			};
		});
		tenNV_QLHD_HDCol.setCellValueFactory(new PropertyValueFactory<>("maHoaDon"));

		tenNV_QLHD_HDCol.setCellFactory(c -> {
			return new TableCell<HoaDon, String>() {
				@Override
				protected void updateItem(String price, boolean empty) {
					super.updateItem(price, empty);
					if (empty) {
						setText(null);
					} else {
						NhanVienDAO nvDao = new NhanVienDAO();
						HoaDon hd = c.getTableView().getItems().get(this.getIndex());
						NhanVien nv = nvDao.getNhanVientheoMaNV(hd.getMaNV());

						setText(nv.getTenNV());
					}
				}
			};
		});
		tableQLHD_HD.refresh();
		btnTimKiemQLHD.setOnAction(e -> {
			XuLyActionTimKiemHoaDon();

		});
		tableQLHD_HD.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			HoaDon selectHD = tableQLHD_HD.getSelectionModel().getSelectedItem();
			if (selectHD != null) {
				CTHDDAO cthdDao = new CTHDDAO();
				dsCTHD_QLHD = cthdDao.getDSGioGang_CTHD_MaHD(selectHD.getMaHoaDon());
				tableQLHD_CTHD.setItems(dsCTHD_QLHD);
				tableQLHD_CTHD.refresh();

			}

		});

	}

	private void XuLyActionTimKiemHoaDon() {
		HoaDonDAO hdDao = new HoaDonDAO();
		dsHoaDonQLHD.clear();
		hdDao.getDSHoaDon(txtSoCMNDKHQLHD, txtHoKHQLHD, txtTenKHQLHD, txtSoDTKHQLHD, txtMANVQLHD, txtMAHDQLHD,
				dpkFromQLHD, dpktoQLHD, tableQLHD_HD, dsHoaDonQLHD);
		tableQLHD_HD.refresh();

	}

	@SuppressWarnings("unchecked")
	private void XuLyBangSQLSP() {
		MatHangDAO matHangDAO = new MatHangDAO();
		matHangDAO.getDSMauSac_DongXe(listMauSac, listDongXe);
		// txtSearch.setPrefHeight(100);

		ObservableList<String> listLoaiXe2 = FXCollections.observableArrayList("Xe số", "Xe tay ga", "Xe thể thao");
		ObservableList<String> listDongXe2 = FXCollections.observableArrayList("Xe số", "Xe tay ga", "Xe thể thao");
		matHangDAO.getDSMauSac_DongXe(listMauSac, listDongXe2);
		cbbLoaiXeThemQLSP.setItems(listLoaiXe2);
		cbbDongXeThemQLSP.setItems(listDongXe2);
		cbbDongXeThemQLSP.getItems().remove(0);
		cbbLoaiXeThemQLSP.getItems().remove(0);
		cbbDongXeThemQLSP.getSelectionModel().selectFirst();
		cbbLoaiXeThemQLSP.getSelectionModel().selectFirst();
		// ====
		jcbLoaiXe.setItems(listLoaiXe);
		jcbLoaiXe.setValue("Tất cả");
		jcbPhanKhoi.setItems(listPhanKhoi);
		jcbPhanKhoi.setValue("Tất cả");
		jcbMauSac.setItems(listMauSac);
		jcbMauSac.setValue("Tất cả");
		jcbGiaThanh.setItems(listGiaThanh);
		jcbGiaThanh.setValue("Tất cả");
		jcbDongXe.setItems(listDongXe);
		jcbDongXe.setValue("Tất cả");

		// ====== XỬ LÝ COMBOBOX QL MẶT HÀNG=========
		jcbLoaiXe1.setItems(listLoaiXe);
		jcbLoaiXe1.setValue("Tất cả");
		jcbPhanKhoi1.setItems(listPhanKhoi);
		jcbPhanKhoi1.setValue("Tất cả");
		jcbMauSac1.setItems(listMauSac);
		jcbMauSac1.setValue("Tất cả");
		jcbGiaThanh1.setItems(listGiaThanh);
		jcbGiaThanh1.setValue("Tất cả");
		jcbDongXe1.setItems(listDongXe);
		jcbDongXe1.setValue("Tất cả");

		donGiaSPCol.setCellValueFactory(new PropertyValueFactory<>("dongia"));
		soLuongSPCol.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
		soLuongSPCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

		soLuongSPCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MatHang, Integer>>() {

			@Override
			public void handle(CellEditEvent<MatHang, Integer> e) {

				TablePosition<MatHang, Integer> pos = e.getTablePosition();

				int newValue = e.getNewValue();

				int row = pos.getRow();
				MatHang mh = e.getTableView().getItems().get(row);

				mh.setSoLuong(newValue);

			}
		});

		CountSPCol.setCellValueFactory(new Callback<CellDataFeatures<MatHang, MatHang>, ObservableValue<MatHang>>() {
			@Override
			public ObservableValue<MatHang> call(CellDataFeatures<MatHang, MatHang> p) {
				return new ReadOnlyObjectWrapper(p.getValue());
			}
		});

		CountSPCol.setCellFactory(new Callback<TableColumn<MatHang, MatHang>, TableCell<MatHang, MatHang>>() {
			@Override
			public TableCell<MatHang, MatHang> call(TableColumn<MatHang, MatHang> param) {
				return new TableCell<MatHang, MatHang>() {
					@Override
					protected void updateItem(MatHang item, boolean empty) {
						super.updateItem(item, empty);

						if (this.getTableRow() != null && item != null) {
							int i = this.getTableRow().getIndex();
							// MatHang mh = dsQLSP.get(i+start);
							// rowcount = dsQLSP.get
							setText(this.getTableRow().getIndex() + start + 1 + "");
						} else {
							setText("");
						}
					}
				};
			}
		});
		CountSPCol.setSortable(false);
		donGiaSPCol.setCellValueFactory(new PropertyValueFactory<>("dongia"));
		donGiaSPCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

		donGiaSPCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MatHang, Double>>() {

			@Override
			public void handle(CellEditEvent<MatHang, Double> e) {

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

				TablePosition<MatHang, Double> pos = e.getTablePosition();

				Double newValue = e.getNewValue();

				int row = pos.getRow();
				MatHang mh = e.getTableView().getItems().get(row);

				mh.setDauMay(newValue);
			}
		});
		dongSPCol.setCellValueFactory(new PropertyValueFactory<>("DongXe"));
		dongSPCol.setCellFactory(TextFieldTableCell.<MatHang>forTableColumn());
		dongSPCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MatHang, String>>() {

			@Override
			public void handle(CellEditEvent<MatHang, String> e) {

				TablePosition<MatHang, String> pos = e.getTablePosition();

				String newValue = e.getNewValue();

				int row = pos.getRow();
				MatHang mh = e.getTableView().getItems().get(row);

				mh.setDongXe(newValue);
			}
		});
		lyDongSPCol.setCellValueFactory(new PropertyValueFactory<>("lyDong"));
		lyDongSPCol.setCellFactory(TextFieldTableCell.<MatHang>forTableColumn());
		lyDongSPCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MatHang, String>>() {

			@Override
			public void handle(CellEditEvent<MatHang, String> e) {

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
//				
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

				TablePosition<MatHang, String> pos = e.getTablePosition();

				String newValue = e.getNewValue();

				int row = pos.getRow();
				MatHang mh = e.getTableView().getItems().get(row);

				mh.setMauXe(newValue);
			}
		});
		tenSPCol.setCellValueFactory(new PropertyValueFactory<>("tenMatHang"));
		tenSPCol.setCellFactory(TextFieldTableCell.<MatHang>forTableColumn());
		tenSPCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MatHang, String>>() {

			@Override
			public void handle(CellEditEvent<MatHang, String> e) {

				TablePosition<MatHang, String> pos = e.getTablePosition();

				String newValue = e.getNewValue();

				int row = pos.getRow();
				MatHang mh = e.getTableView().getItems().get(row);

				mh.setTenMatHang(newValue);
			}
		});
		maSPCol.setCellValueFactory(new PropertyValueFactory<>("maMatHang"));
		maSPCol.setCellFactory(TextFieldTableCell.<MatHang>forTableColumn());
		maSPCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MatHang, String>>() {

			@Override
			public void handle(CellEditEvent<MatHang, String> e) {

				TablePosition<MatHang, String> pos = e.getTablePosition();

				String newValue = e.getNewValue();

				int row = pos.getRow();
				MatHang mh = e.getTableView().getItems().get(row);

				mh.setMaMatHang(newValue);
			}
		});

	}

	private void dialogThemVaoGioHang_Trung() {
		dialog.setVisible(true);
		dialogThongBao.setVisible(true);
		dialogThongBao.toFront();
		dialog.toFront();
		lblDialogTitle.setText("Sản phẩm đã có trong giỏ hàng!");
		lblDialogContent.setText("Lưu ý: Có thể chỉnh sửa số lượng ngay trên bảng giỏ hàng?");
		btnYes.setText("Đồng ý");
		btnYes.setOnAction(e -> {
			dialog.setVisible(false);
			dialogThongBao.setVisible(false);
			btnYes.setText("Có");
			gioHang.toFront();
			chiTietSP.setVisible(false);
		});
		btnNo.setOnAction(e -> {
			dialog.setVisible(false);
			dialogThongBao.setVisible(false);
			dialog.toBack();
			btnYes.setText("Có");
		});

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
//		TextInputDialog inputdialog = new TextInputDialog();
//		// CTHD hd = new CTHD(mh.getTenMatHang(), maSP, 0, mh.getDongia());
//		
////		 CTHD hd = new CTHD(maHoaDon, maMatHang, soLuong, donGia, giamGia);
//
//		inputdialog.setTitle("THÊM SẢN PHẨM: " + hd.getMaMatHang());
//		inputdialog.setHeaderText("Lưu ý: Số lượng không vượt quá:  " + mh.getSoLuong());
//		inputdialog.setContentText("Nhập số lượng mua:");
//		((Button) inputdialog.getDialogPane().lookupButton(ButtonType.OK)).setText("Thêm");
//		((Button) inputdialog.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Thoát");

		CTHD hd = new CTHD(mh.getTenMatHang(), maSP, 0, mh.getDongia(), 0.0);
		lblDialogTitleVL.setText("THÊM SẢN PHẨM: " + hd.getMaMatHang());
		lblDialogVLContent.setText("Nhập số lượng mua:");
		lblDialogWarningVL.setText("Lưu ý: Số lượng không vượt quá:  " + mh.getSoLuong());
		btnYesVL.setText("Thêm");
		btnNoVL.setText("Thoát");
		dialog.toFront();
		dialog.setVisible(true);
		dialogValue.toFront();
		dialogValue.setVisible(true);

		// Optional<String> result = inputdialog.showAndWait();

		txtValueDialog.requestFocus();

		txtValueDialog.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					XuLyThemSanPhamVaoGioHang(hd, mh, maSP);
					txtValueDialog.setFocusTraversable(false);

				}
//				else if(ke.getCode().equals(KeyCode.ESCAPE)) {
//					txtValueDialog.setText("");
//					dialog.setVisible(false);
//					dialogValue.setVisible(false);
//					dialogValue.toBack();
//					
//				}

			}

		});

		btnYesVL.setOnAction(e -> {
			XuLyThemSanPhamVaoGioHang(hd, mh, maSP);
//			boolean check = regexDialog();
//			if (check == true) {
//				// nhập số lượng đúng
//				String sl = txtValueDialog.getText();
//				
//				if (Integer.parseInt(sl.toString()) <= mh.getSoLuong()) {
//					hd.setSoLuong(Integer.parseInt(sl.toString()));
//					hd.getThanhTien();
//
//					// System.out.println(mh.getSoLuong());
//
//					dsGioHang.add(hd);
//					thanhtienGioHang += hd.getThanhTien();
//					lblTongTienGH.setText(formatter.format(thanhtienGioHang));
//					tableGioHang.refresh();
//					tableLapHoaDon.refresh();
//					btnYesVL.setText("Có");
//					btnNoVL.setText("Thoát");
//					dialog.toBack();
//					dialog.setVisible(false);
//					dialogValue.toBack();
//					dialogValue.setVisible(false);
//					txtValueDialog.setText("");
//					gioHang.toFront();
//				}
//				else {
//					// nhập sai số lượng
//					dialog.setVisible(true);
//					dialogThongBao.setVisible(true);
//					dialogThongBao.toFront();
//					dialog.toFront();
//					dialogValue.setVisible(false);
//					lblDialogTitle.setText("SỐ LƯỢNG MUA VƯỢT QUÁ QUY ĐỊNH!");
//					lblDialogContent.setText("Bạn có muốn nhập lại không?");
//					btnNo.setText("Không");
//					btnYes.setOnAction(e2 -> {
//						dialog.setVisible(false);
//						dialogThongBao.setVisible(false);
//						DialogthemSP(mh, maSP);
//
//					});
//					btnNo.setOnAction(e2->{
//						dialog.setVisible(false);
//						dialog.toBack();
//						dialogThongBao.setVisible(false);
//					});
//					
//
//				}
//			} 

		});
		btnNoVL.setOnAction(e3 -> {
			txtValueDialog.setText("");
			dialog.setVisible(false);
			dialogValue.setVisible(false);
			dialogValue.toBack();
		});

//		result.ifPresent(sl -> {
//
//			if (Integer.parseInt(sl.toString()) <= mh.getSoLuong()) {
//				hd.setSoLuong(Integer.parseInt(sl.toString()));
//				hd.getThanhTien();
//
//				// System.out.println(mh.getSoLuong());
//
//				dsGioHang.add(hd);
//				thanhtienGioHang += hd.getThanhTien();
//				lblTongTienGH.setText(formatter.format(thanhtienGioHang));
//				tableGioHang.refresh();
//				tableLapHoaDon.refresh();
//			} else {
//
//				dialog.setVisible(true);
//				dialogThongBao.setVisible(true);
//				dialogThongBao.toFront();
//				dialog.toFront();
//				lblDialogTitle.setText("SỐ LƯỢNG MUA VƯỢT QUÁ QUY ĐỊNH!");
//				lblDialogContent.setText("Bạn có muốn nhập lại không?");
//				btnYes.setOnAction(e2 -> {
//					dialog.setVisible(false);
//					DialogthemSP(mh, maSP);
//
//				});
//				btnNo.setOnAction(e3 -> {
//					dialog.setVisible(false);
//					dialogThongBao.setVisible(false);
//					dialog.toBack();
//				});
//
//			}
//
//		});

	}

	private void XuLyThemSanPhamVaoGioHang(CTHD hd, MatHang mh, String maSP) {

		boolean check = regexDialog();
		if (check == true) {
			// nhập số lượng đúng
			String sl = txtValueDialog.getText();

			if (Integer.parseInt(sl.toString()) <= mh.getSoLuong()) {
				hd.setSoLuong(Integer.parseInt(sl.toString()));
				hd.getThanhTien();

				// System.out.println(mh.getSoLuong());

				dsGioHang.add(hd);
				thanhtienGioHang += hd.getThanhTien();

				Double tamTinh = mh.getThueBan() / 100 * hd.getDonGia();
				price += hd.getThanhTien() + tamTinh * hd.getSoLuong();
				txtTongTienThemHD.setText(formatter.format(price));
				lblTongTienGH.setText(formatter.format(thanhtienGioHang));

//					thanhtienGioHang += hd.getThanhTien();
//					MatHangDAO matHangDAO = new MatHangDAO();
//					MatHang mh2 = matHangDAO .getMatHangg(hd.getMaMatHang());
//					Double tamTinh = mh2.getThueBan()/100*hd.getDonGia();
//					price = cthd.getThanhTien()+tamTinh*cthd.getSoLuong();

				// CTHD hd = c.getTableView().getItems().get(this.getIndex());

				//

				tableGioHang.refresh();
				tableLapHoaDon.refresh();
				tableGioHang.refresh();
				tableLapHoaDon.refresh();
				btnYesVL.setText("Có");
				btnNoVL.setText("Thoát");
				dialog.toBack();
				dialog.setVisible(false);
				dialogValue.toBack();
				dialogValue.setVisible(false);
				txtValueDialog.setText("");
				gioHang.toFront();
				lblTrangThaiThemSP.setText("*Đã thêm sản phẩm " + mh.getMaMatHang() + " vào giỏ hàng.");
			} else {
				// nhập sai số lượng

				dialog.setVisible(true);
				dialogThongBao.setVisible(true);
				dialogThongBao.toFront();
				dialog.toFront();
				dialogValue.setVisible(false);
				dialogValue.toFront();
				lblDialogTitle.setText("SỐ LƯỢNG MUA VƯỢT QUÁ QUY ĐỊNH!");
				lblDialogContent.setText("Bạn có muốn nhập lại không?");
				btnNo.setText("Không");
				btnYes.setOnAction(e2 -> {
					dialog.setVisible(false);
					dialogThongBao.setVisible(false);
					DialogthemSP(mh, maSP);

				});
				btnNo.setOnAction(e2 -> {
					dialog.setVisible(false);
					dialog.toBack();
					dialogThongBao.setVisible(false);
				});

			}
		}

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
		dialogThongBao.setVisible(true);
		dialogThongBao.toFront();
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
//			Double thanhTien = 0.0;
//			for (CTHD cthd : dsGioHang) {
//				cthd.setMaHoaDon(maHD);
//				thanhTien += cthd.getThanhTien();
//
//			}
			Double tamTinh = 0.0;
			Double price = 0.0;
			// thanhtienGioHang = 0.0;
			for (CTHD cthd : dsGioHang) {
				// thanhtienGioHang += cthd.getThanhTien();
				MatHangDAO matHangDAO = new MatHangDAO();
				MatHang mh2 = matHangDAO.getMatHangg(cthd.getMaMatHang());
				tamTinh = mh2.getThueBan() / 100 * cthd.getDonGia();
				price += cthd.getThanhTien() + tamTinh * cthd.getSoLuong();
				cthd.setMaHoaDon(maHD);
			}
			// CTHD hd = c.getTableView().getItems().get(this.getIndex());

			txtTongTienThemHD.setText(formatter.format(price));
			//
			lblTongTienGH.setText(formatter.format(thanhtienGioHang));
			tableGioHang.refresh();
			tableLapHoaDon.refresh();
			HoaDon hd = new HoaDon(maHD, txtCMNDKHLHD.getText().trim(), txtMaNVLHD.getText(), date, price);
			hddao.themHoaDon(hd, dsGioHang);
			// dsGioHang.get(1);
			dialog.setVisible(false);
			dialogThongBao.setVisible(false);
			dialogThemHDThanhCong();

		});
		btnNo.setOnAction(e -> {
			dialog.setVisible(false);
			dialogThongBao.setVisible(false);
			dialog.toBack();
		});

		return true;

	}

	private void dialogThemHDThanhCong() {

		dialog.setVisible(true);
		dialogThongBao.setVisible(true);
		dialog.toFront();
		// lblDialogTitle.setText("Xác nhận lập hoá đơn");
		lblDialogTitle.setText(" THÀNH CÔNG");
		lblDialogContent.setText("Bạn có muốn lập hoá đơn mới không?");
		btnYes.setOnAction(e -> {
			dsGioHang.clear();
			tableGioHang.refresh();
			tableLapHoaDon.refresh();
			txtCMNDKHLHD.setText("");
			lblTrangThaiThemSP.setText("");
			timKiem_them_matHang.toFront();
			count = 0;
			// jlistView.getSelectionModel().select(0);
			dialog.setVisible(false);
			dialogThongBao.setVisible(false);
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
			dialog.setVisible(false);
			dialogThongBao.setVisible(false);

		});

	}

	private void dialogDangXuat() {
		dialog.setVisible(true);
		dialog.toFront();
		dialogThongBao.setVisible(true);
		dialogThongBao.toFront();
		lblDialogTitle.setText("Xác nhận đăng xuất");
		lblDialogContent.setText("Bạn có chắc chắn muốn đăng xuất khỏi tải khoản này không?");
		btnYes.setOnAction(e -> {
			dialog.setVisible(false);
			dialogThongBao.setVisible(false);
			MenuPane.setVisible(false);
			LoginPane.setVisible(true);
		});
		btnNo.setOnAction(e -> {
			dialog.setVisible(false);
			dialogThongBao.setVisible(false);
		});
	}

	@FXML
	private void dialogThoat() {
		dialog.setVisible(true);
		dialogThongBao.setVisible(true);
		dialogThongBao.toFront();
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
			dialogThongBao.setVisible(false);
		});
	}

	/**
	 * Xử lý đăng nhập
	 * 
	 */
	@SuppressWarnings("unchecked")
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
					menuCaiDat.setVisible(true);
					dialogDoiMatKhau.setVisible(false);
					lblLGStatus.setText("");
					lblLGStatus.setText("*Đăng nhập thành công!");
					ThongTinCaNhan(nv);
					menuHelp.setVisible(false);
					// if(nv.getChucVu().equals("Nhân viên")) tree_TK.setValue("");
					root.getChildren().clear();
					tree_QLHD.getChildren().clear();
					tree_QLHD.getChildren().addAll(HD_taoHD, HD_TaoGioHang, HD_QLHD);
					tree_QLHD.setGraphic(new ImageView(new Image("img/billSmall.png")));
					HD_taoHD.setGraphic(imgLuu);
					
					HD_TaoGioHang.setGraphic(new ImageView(new Image("img/giohangblack.png")));
					HD_QLHD.setGraphic(new ImageView(new Image("img/lookBill.png")));
					tree_TK.getChildren().addAll(TK_DoanhThu, TK_KhachHang, TK_NhanVien, TK_SanPham);
					root.getChildren().addAll(tree_QLHD, tree_QLKH, tree_QLSP);
					tree_QLSP.setGraphic(new ImageView(new Image("img/sanphamSmall.png")));
					tree_QLKH.setGraphic(new ImageView(new Image("img/group.png")));
					tree_TK.setGraphic(new ImageView(new Image("img/pie-chart.png")));
					
					TK_DoanhThu.setGraphic(new ImageView(new Image("img/dollar.png")));
					TK_SanPham.setGraphic(new ImageView(new Image("img/customer-feedback.png")));
					TK_NhanVien.setGraphic(new ImageView(new Image("img/group.png")));
					if (nv.getChucVu().equals("Nhân viên") == false)
						root.getChildren().addAll(tree_TK);
					tree.setShowRoot(false);

					tree.setRoot(root);

					tree.getSelectionModel().select(0);
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
		txtTienThuaLHD.setOnKeyTyped(e->{
			System.out.println(txtTienThuaLHD.getText());
			if(XuLyRegexTienThua()==true) {
				String DT = txtTienThuaLHD.getText();
				Double tienkhachtra = Double.parseDouble(DT);
				Double tienthua =  tienkhachtra - price ;
				if(tienthua>=0)
				txtTienThoiThemHD.setText(formatter.format(tienthua));
				else txtTienThoiThemHD.setText("Tiền khách trả chưa đủ!");
				
			}
			else {
				
				//XuLyDiaLogTienThoi_Loi("Tiền khách trả lỗi!!","Tiền khách trả chỉ chứa số từ 0 - 9.");
				
			}
			
		});
		txtMaNVLHD.setText(nv.getMaNV());
		txtTenNVLHD.setText(nv.getTenNV());
		txtHoNVLHD.setText(nv.getHoNV());
		// txtHoKHLHD.setText("");
		txtCMNDKHLHD.setText(txtsoCMNDQLKH.getText().trim());
		if (txtCMNDKHLHD.getText().trim().equals("") == true) {
			dialog.toFront();
			dialogThongBao.setVisible(true);
			dialogThongBao.toFront();
			dialog.setVisible(true);
			lblDialogTitle.setText("KHÁCH HÀNG TRỐNG! ");
			lblDialogContent.setText("Bạn có muốn chuyển sang chức năng chọn khách hàng không? ");
			btnNo.setDisable(true);
			btnYes.setOnAction(e -> {

				dialog.setVisible(false);
				dialogThongBao.setVisible(false);
				QLKhachHangPane.toFront();
				count = 0;
				// jlistView.getSelectionModel().select(2);
				TreeItem<String> x = tree.getSelectionModel().getSelectedItem().getParent().nextSibling();
				tree.getSelectionModel().select(x);
				btnNo.setDisable(false);
			});
			btnNo.setOnAction(e -> {
				dialog.setVisible(false);
				dialogThongBao.setVisible(false);
			});

		}
		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
		// System.out.println(date);
		txtNgayLHD.setText(date.toString());
		

	}

	private void XuLyDiaLogTienThoi_Loi(String title, String content) {
		dialog.setVisible(true);
		dialog.toFront();
		dialogThongBao.toFront();
		dialogThongBao.setVisible(true);
		lblDialogTitle.setText(title);
		lblDialogContent.setText(content);
		btnYes.setVisible(false);
		btnNo.setOnAction(e2->{
			dialog.setVisible(false);
			dialog.toBack();
			dialogThongBao.toBack();
			dialogThongBao.setVisible(false);
			btnYes.setVisible(true);
			
		});
		
	}

	private boolean XuLyRegexTienThua() {
		String DT = txtTienThuaLHD.getText();
		if (!(DT.length()>0 && DT.matches("[0-9]+"))) {
			XuLyDiaLogTienThoi_Loi("Tiền khách trả lỗi!", "Tiền khách trả phải là số lớn hơn 0 và lớn hơn giá tiền phải trả.");
			return false;
		}
		if (DT.length() > 0) {
			try {
				Double x = Double.parseDouble(DT);
				if (x <= 0) {
					XuLyDiaLogTienThoi_Loi("Tiền khách trả quá bé", "Tiền khách trả phải lớn hơn 0.");
					return false;
				}
			} catch (Exception e) {
				XuLyDiaLogTienThoi_Loi("Tiền khách trả lỗi!", "Tiền khách trả phải là số.");
				return false;
			}
			
		} else {
			XuLyDiaLogTienThoi_Loi("Tiền khách trả lỗi!", "Tiền khách trả phải là số.");
			return false;
		}
		 return true;
		
		
	}

	@FXML
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

	@FXML
	private void XuLybtnTimKiemQLSP() {

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
		int count = mhDao.getDSMatHang_Loc(jcbLoaiXe1, jcbPhanKhoi1, jcbMauSac1, jcbGiaThanh1, jcbDongXe1,
				txtSearchQLSP, dsQLSP, tableQLSP, start, end);
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
		NhanVienDAO nhanVienDao = new NhanVienDAO();
		dsGioiTinhNVTT = nhanVienDao.getDSGioiTinh();
		cbbGioiTinhNVTT.setItems(dsGioiTinhNVTT);
		cbbGioiTinhNVTT.requestFocus();
		cbbGioiTinhNVTT.setValue(nv.getGioiTinh());
		txthideMatKhauNVTT.setText(nv.getMatKhau());

		// txtMatKhauNVTT.setText(nv.getMatKhau());
		txtsoCMNDNVTT.setText(nv.getSoCMND());
		// password="";
		//btnUnMaskTTCN.setGraphic(imgVUnMask);
//		ImageView imgVUnMask = new ImageView(imageUnMask);
//		ImageView imgVMask = new ImageView(imageMask);
//		btnUnMaskTTCN.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
//			if (txthideMatKhauNVTT.getText().equals("") == false) {
//				password = txthideMatKhauNVTT.getText();
//				txthideMatKhauNVTT.clear();
//
//				txthideMatKhauNVTT.setPromptText(password);
//				btnUnMaskTTCN.setGraphic(imgVMask);
//				// Đang
//
//			} else {
//				txthideMatKhauNVTT.setText(password);
//				txthideMatKhauNVTT.setPromptText("Password");
//				btnUnMaskTTCN.setGraphic(imgVUnMask);
//			}
//
//		});
		txtMaNVTT.requestFocus();
//		 btnUnMaskTTCN.addEventFilter(MouseEvent.MOUSE_EXITED, e2 -> {
//             txthideMatKhauNVTT.setText(password);
//             txthideMatKhauNVTT.setPromptText("Password");
//         });
	}

	@SuppressWarnings("resource")
	@FXML
	public void XuLyCaiDat() throws URISyntaxException, FileNotFoundException {

		menuHelp.setVisible(true);

		menuCaiDat.setVisible(true);
		menuCaiDat.toFront();
		btnThemFilePath.setOnAction(e -> {
			FileChooser chooser = new FileChooser();
			FileChooser.ExtensionFilter txtFilte = new FileChooser.ExtensionFilter("TextFile", "*.txt");
			chooser.getExtensionFilters().add(txtFilte);

			File file2 = chooser.showOpenDialog(manHinhLamViecChinh.getScene().getWindow());

			if (file2 == null) {

			} else {

				txtThemFilePath.setText(file2.getPath().toString());

			}

		});
		btnThemFolderImg.setOnAction(e -> {
			DirectoryChooser chooser = new DirectoryChooser();

			File file2 = chooser.showDialog(manHinhLamViecChinh.getScene().getWindow());

			if (file2 == null) {

			} else {

				// Đang
				// InputStream inputStream = this.getClass().getResourceAsStream("/Path.txt");
				String path = "./Path.txt";
				File file = new File(path);
				FileWriter fw;
				try {
					fw = new FileWriter(file);
					BufferedWriter writer = new BufferedWriter(fw);
					writer.write(LocalPath = file2.getPath().toString());
					writer.close();
					LocalPath = file2.getPath().toString();
					System.out.println(LocalPath);
					txtThemFolderImg.setText(file2.getPath().toString());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});
		btnThemDatabase.setOnAction(e -> {
			DirectoryChooser chooser = new DirectoryChooser();

			File file2 = chooser.showDialog(manHinhLamViecChinh.getScene().getWindow());

			if (file2 == null) {

			} else {

				txtThemDatabase.setText(file2.getPath().toString());

			}

		});

	}

	@SuppressWarnings("unused")
	private String XuLyPath_DiChuyenAnhThemSP(String pathImg) throws IOException {
		String newName = java.time.LocalDateTime.now().toString();
		newName = newName.replace(".", "");
		newName = newName.replace("-", "");
		newName = newName.replace(":", "");
		String duoiFile = timDuoiFile(newName);

		URL imageURL = new URL(pathImg);
		BufferedImage bi = ImageIO.read(imageURL);

		if (duoiFile.equals("jpg"))
			ImageIO.write(bi, "jpg", new File(LocalPath + "\\" + newName + ".jpg"));
		else
			ImageIO.write(bi, "png", new File(LocalPath + "\\" + newName + ".png"));
		System.out.println(LocalPath + "\\" + newName + ".png");
		return LocalPath + "\\" + newName + ".png";

	}

	public String timDuoiFile(String word) {
		if (word.length() == 3) {
			return word;
		} else if (word.length() > 3) {
			return word.substring(word.length() - 3);
		} else {
			// whatever is appropriate in this case
			throw new IllegalArgumentException("word has less than 3 characters!");
		}
	}

}
