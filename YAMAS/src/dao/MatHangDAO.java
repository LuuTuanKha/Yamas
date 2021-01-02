package dao;

import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.Vector;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import connectDATA.Connect_Data;
import entity.CTHD;
import entity.MatHang;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import manHinhLamViecChinh.MHLVChinhController;

/**
 * 
 * @author Mr BonSss
 */

public class MatHangDAO {
	// Lấy đường dẫn
	String DataPath = "T:\\ptud\\img";
	 manHinhLamViecChinh.MHLVChinhController mh = new MHLVChinhController();
	 String LocalPath = mh.getLocalPath();
	NumberFormat db = new DecimalFormat("#0.00");
	Path currentRelativePath = Paths.get("");
	String path = currentRelativePath.toAbsolutePath().toString();
	public void themNhanVien(MatHang mh) throws SQLException, UnsupportedEncodingException {
		Connect_Data.getInstance();
		Connection con = Connect_Data.getConnection();
		PreparedStatement stmt = null;
		int n=0;
		
			String sql = "INSERT INTO [dbo].[MatHang] "
					+ "([MaMatHang] ,[TenMatHang] ,[MaLoai] ,[Dong] ,[SoLuong] ,[DTBinhXang] ,[DTDauMay] ,[HTLyDong] ,[MoTa] ,[MauXe]"
					+ " ,[DongCo] ,[ThueBan] ,[KichThuoc] ,[KhungXe] ,[DonGia] ,[img]) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, mh.getMaMatHang());
			stmt.setString(2, mh.getTenMatHang());
			stmt.setString(3, mh.getMaLoai());
			stmt.setString(4, mh.getDongXe());
			stmt.setInt(5, mh.getSoLuong());
			stmt.setDouble(6, mh.getBinhXang());
			stmt.setDouble(7, mh.getDauMay());
			stmt.setString(8, mh.getLyDong());
			stmt.setString(9,mh.getMoTa());
			stmt.setString(10, mh.getMauXe());
			stmt.setDouble(11, mh.getDongCo());
			stmt.setDouble(12, mh.getThueBan());
			stmt.setString(13, mh.getKichThuoc());
			stmt.setString(14, mh.getKhungxe());
			stmt.setDouble(15, mh.getDongia());
			String path = mh.getImage().toString();
			//path = path.replace(LocalPath, DataPath);
			System.out.println(path);
			byte[] fin = path.getBytes("UTF-8");

			stmt.setBytes(16, fin);
			
			
			stmt.executeUpdate();
			
		
		
	}

	public java.util.Map<String, String[]> getImageData(JFXComboBox<String> jcbLoaiXe, JFXComboBox<String> jcbPhanKhoi,
			JFXComboBox<String> jcbMauSac,JFXComboBox<String> jcbDongXe, JFXComboBox<String> jcbGiaThanh, JFXTextField txt) {

		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();
		String[] list = { "name", "path" };
		byte[] fileBytes;
		String query;
		java.util.Map<String, String[]> map = new LinkedHashMap<>();
		String path;
		String maSp;

		// where MaMatHang =" + "\'ERC150B\'
		try {
			query = "select maMatHang,TenMatHang, img from [dbo].[MatHang] where mamathang is not null and soluong >0 ";
			if (jcbLoaiXe.getSelectionModel().getSelectedIndex() != 0) {
				if (jcbLoaiXe.getSelectionModel().getSelectedIndex() == 1)
					query += "and maLoai like N\'%số%\' ";
				if (jcbLoaiXe.getSelectionModel().getSelectedIndex() == 2)
					query += "and maLoai like N\'%ga%\' ";
				if (jcbLoaiXe.getSelectionModel().getSelectedIndex() == 3)
					query += "and maLoai like N\'%thao%\' ";

			}
			if (jcbPhanKhoi.getSelectionModel().getSelectedIndex() != 0) {
				if (jcbPhanKhoi.getSelectionModel().getSelectedIndex() == 1)
					query += "and dongco <100 ";
				if (jcbPhanKhoi.getSelectionModel().getSelectedIndex() == 2)
					query += "and dongco >=100 and dongco <135 ";
				if (jcbPhanKhoi.getSelectionModel().getSelectedIndex() == 3)
					query += "and dongco >=135 and dongco <175 ";
				if (jcbPhanKhoi.getSelectionModel().getSelectedIndex() == 4)
					query += "and dongco >=175 ";
			}
			
			if (jcbMauSac.getSelectionModel().getSelectedIndex() != 0) {
				String s = jcbMauSac.getSelectionModel().getSelectedItem();

				query += "and mauXe like N\'%" + s + "%\' ";

			}
			if (jcbDongXe.getSelectionModel().getSelectedIndex() != 0) {
				String s = jcbDongXe.getSelectionModel().getSelectedItem();
				
				query += "and Dong like N\'%" + s + "%\' ";
				
			}
			if (jcbGiaThanh.getSelectionModel().getSelectedIndex() != 0) {
				if (jcbGiaThanh.getSelectionModel().getSelectedIndex() == 1)
					query += "and dongia <30000000 ";
				if (jcbGiaThanh.getSelectionModel().getSelectedIndex() == 2)
					query += "and dongia >=30000000 and dongia<50000000 ";
				if (jcbGiaThanh.getSelectionModel().getSelectedIndex() == 3)
					query += "and dongia >=50000000 and dongia<80000000 ";
				if (jcbGiaThanh.getSelectionModel().getSelectedIndex() == 4)
					query += "and dongia >=80000000 and dongia<100000000 ";
				if (jcbGiaThanh.getSelectionModel().getSelectedIndex() == 5)
					query += "and dongia >=100000000 ";
			}
			if (txt.getText().equals("") == false)
				query += "and tenmathang like N\'%" + txt.getText().trim() + "%\' ";
			// System.out.println(query);
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(query);

			while (rs.next()) {
				list = list.clone();

				maSp = rs.getString(1);

				list[0] = rs.getString(2);
				fileBytes = rs.getBytes(3);

				path = new String(fileBytes, "UTF-8");
				path = path.replace(DataPath, LocalPath);
				list[1] = path;

				map.put(maSp, list);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;

	}

	/**
	 * 
	 * @return Danh sách các mặt hàng tìm được
	 */
	@SuppressWarnings("null")
	public ObservableList<MatHang> getDSMatHang() {
		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();
		ObservableList<MatHang> list = FXCollections.observableArrayList();
		try {
			String query = "SELECT [MaMatHang],[TenMatHang],[MaLoai],[Dong],[SoLuong],[DTBinhXang],[DTDauMay],[HTLyDong],[MoTa],[MauXe],[DongCo],[KichThuoc],[KhungXe],[DonGia],[img],[ThueBan]"
					+ "FROM [dbo].[MatHang]\r\n" + "WHERE [MaMatHang] is not null and soLuong>0\r\n"
					+ "ORDER BY [MaMatHang] DESC ";
			// System.out.println(query);
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(query);

			while (rs.next()) {
				// tenSP = rs.getString(1);
				// byte[] bytes = rs.getString(14);

				MatHang mh = new MatHang(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getInt(5), rs.getDouble(6), rs.getDouble(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getDouble(11), rs.getString(12), rs.getString(13), rs.getDouble(14),
						"updateting..", rs.getDouble(16));
				path = new String(rs.getBytes(15), "UTF-8");
				path = path.replace(DataPath, LocalPath);
				mh.setImage(path);
				list.add(mh);

				// OutputStream targetFile = new FileOutputStream("d://new"+i+".png");

				// targetFile.write(fileBytes);
				// targetFile.close();

				// ImageIO.write(bImage2, "png", new File(pathname) );

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	@SuppressWarnings("null")
	public int getDSMatHang_Loc(JFXComboBox<String> jcbLoaiXe, JFXComboBox<String> jcbPhanKhoi,
			JFXComboBox<String> jcbMauSac, JFXComboBox<String> jcbGiaThanh,JFXComboBox<String> jcbDongXe, JFXTextField txt,
			ObservableList<MatHang> list, TableView<MatHang> tableQLSP, int start, int end) {
		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();

		try {
			// String Count = "SELECT count(mamathang) " + "FROM [dbo].[MatHang]" + "WHERE
			// [MaMatHang] is not null";
			String query = "SELECT  [MaMatHang],[TenMatHang],[MaLoai],[Dong],[SoLuong],[DTBinhXang],[DTDauMay],[HTLyDong],[MoTa],[MauXe],[DongCo],[KichThuoc],[KhungXe],[DonGia],[img],[ThueBan],[Dong] "
					+ "FROM [dbo].[MatHang]" + "WHERE [MaMatHang] is not null and soLuong>0 ";

			if (jcbLoaiXe.getSelectionModel().getSelectedIndex() != 0) {
				if (jcbLoaiXe.getSelectionModel().getSelectedIndex() == 1)
					query += "and maLoai like N\'%số%\' ";
				if (jcbLoaiXe.getSelectionModel().getSelectedIndex() == 2)
					query += "and maLoai like N\'%ga%\' ";
				if (jcbLoaiXe.getSelectionModel().getSelectedIndex() == 3)
					query += "and maLoai like N\'%thao%\' ";

			}
			if (jcbPhanKhoi.getSelectionModel().getSelectedIndex() != 0) {
				if (jcbPhanKhoi.getSelectionModel().getSelectedIndex() == 1)
					query += "and dongco <100 ";
				if (jcbPhanKhoi.getSelectionModel().getSelectedIndex() == 2)
					query += "and dongco >=100 and dongco<135 ";
				if (jcbPhanKhoi.getSelectionModel().getSelectedIndex() == 3)
					query += "and dongco >=135 and dongco<175 ";
				if (jcbPhanKhoi.getSelectionModel().getSelectedIndex() == 4)
					query += "and dongco >=175 ";
			}
			if (jcbMauSac.getSelectionModel().getSelectedIndex() != 0) {
				String s = jcbMauSac.getSelectionModel().getSelectedItem();

				query += "and mauXe like N\'%" + s + "%\' ";

			}
			if (jcbDongXe.getSelectionModel().getSelectedIndex() != 0) {
				String s = jcbDongXe.getSelectionModel().getSelectedItem();
				
				query += "and Dong like N\'%" + s + "%\' ";
				
			}
			if (jcbGiaThanh.getSelectionModel().getSelectedIndex() != 0) {
				if (jcbGiaThanh.getSelectionModel().getSelectedIndex() == 1)
					query += "and dongia <30000000 ";
				if (jcbGiaThanh.getSelectionModel().getSelectedIndex() == 2)
					query += "and dongia >=30000000 and dongia<50000000 ";
				if (jcbGiaThanh.getSelectionModel().getSelectedIndex() == 3)
					query += "and dongia >=50000000 and dongia<80000000 ";
				if (jcbGiaThanh.getSelectionModel().getSelectedIndex() == 4)
					query += "and dongia >=80000000 and dongia<100000000 ";
				if (jcbGiaThanh.getSelectionModel().getSelectedIndex() == 5)
					query += "and dongia >=100000000 ";
			}
			if (txt.getText().equals("") == false)
				query += "and tenmathang like N\'%" + txt.getText().trim() + "%\' ";

			// System.out.println(query);
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(query);

			while (rs.next()) {

				MatHang mh = new MatHang(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getInt(5), rs.getDouble(6), rs.getDouble(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getDouble(11), rs.getString(12), rs.getString(13), rs.getDouble(14),
						"updateting..", rs.getDouble(16));
				path = new String(rs.getBytes(15), "UTF-8");
				path = path.replace(DataPath, LocalPath);
				mh.setImage(path);
				list.add(mh);
				tableQLSP.refresh();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// tableQLSP.setItems(list);
		int count = list.size();
		if (count >= end)
			tableQLSP.setItems(FXCollections.observableArrayList(list.subList(start, end)));
		else
			tableQLSP.setItems(FXCollections.observableArrayList(list.subList(start, count)));
		tableQLSP.refresh();
		return count;

	}

	public MatHang getMatHangg(String maSP) {
		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();
		MatHang mh = new MatHang();
		// ObservableList<MatHang> list= FXCollections.observableArrayList();
		try {

			String query = "SELECT [MaMatHang],[TenMatHang],[MaLoai],[Dong],[SoLuong],[DTBinhXang],[DTDauMay],[HTLyDong],[MoTa],[MauXe],[DongCo],[KichThuoc],[KhungXe],[DonGia],[img],[ThueBan]	\r\n"
					+ "FROM [dbo].[MatHang]\r\n" + "WHERE [MaMatHang] =" + "\'" + maSP + "\'";
			// System.out.println(query);
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(query);

			// tenSP = rs.getString(1);
			// byte[] bytes = rs.getString(14);
			if (rs.next())
				mh = new MatHang(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
						rs.getDouble(6), rs.getDouble(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getDouble(11), rs.getString(12), rs.getString(13), rs.getDouble(14),
						new String(rs.getBytes(15), "UTF-8").replace(DataPath, LocalPath), rs.getDouble(16));

			// OutputStream targetFile = new FileOutputStream("d://new"+i+".png");

			// targetFile.write(fileBytes);
			// targetFile.close();

			// ImageIO.write(bImage2, "png", new File(pathname) );

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mh;

	}

	public Vector<String> getImageData2() {

		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();

		byte[] fileBytes;
		String query;
		Vector<String> vector = new Vector<>();
		String path;
		String maSp;
		String tenSP;
		// where MaMatHang =" + "\'ERC150B\'
		try {
			query = "select MaMatHang,TenMatHang, img from [dbo].[MatHang] order by Mamathang asc ";
			// System.out.println(query);
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(query);

			while (rs.next()) {

				tenSP = rs.getString(1);
				maSp = rs.getString(2);
				fileBytes = rs.getBytes(2);

				// OutputStream targetFile = new FileOutputStream("d://new"+i+".png");

				// targetFile.write(fileBytes);
				// targetFile.close();
				path = new String(fileBytes, "UTF-8");
				path = path.replace(DataPath, LocalPath);

				vector.add(maSp);
				// ImageIO.write(bImage2, "png", new File(pathname) );

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// Image img = new Image("img/new.png", 200, 160, false, false);
		// Image img = new Image("img/new.png");
		return vector;

	}

	public void getDSMauSac_DongXe(ObservableList<String> listMauSac, ObservableList<String> listDongXe) {

		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();
		listDongXe.clear();
		listMauSac.clear();
		listDongXe.add("Tất cả");
		listMauSac.add("Tất cả");
		
		String query;

		// where MaMatHang =" + "\'ERC150B\'
		try {
			query = "select distinct [MauXe] from [dbo].[MatHang] ";
			// System.out.println(query);
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(query);

			while (rs.next()) {

				listMauSac.add(rs.getString(1));

			}
			
			query = "select distinct [Dong] from [dbo].[MatHang] ";
			// System.out.println(query);
			 state = conn.createStatement();
			 rs = state.executeQuery(query);

			while (rs.next()) {

				listDongXe.add(rs.getString(1));
				

			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		// Image img = new Image("img/new.png", 200, 160, false, false);
		// Image img = new Image("img/new.png");

	}

	public void CapnhatMH_TaoHD(CTHD nv) {
		Connect_Data.getInstance();
		Connection con = Connect_Data.getConnection();
		PreparedStatement stmt = null;
		// int n = 0;
		try {
			String sql = "USE [QLXeMay] UPDATE [dbo].[MatHang] SET [SoLuong] = [SoLuong] - " + nv.getSoLuong()
					+ " WHERE [MaMatHang] = " + "\'" + nv.getMaMatHang().trim() + "\'";
			stmt = con.prepareStatement(sql);
			// stmt.setString(11, nv.getMaKhachHang());
			stmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void CapNhat_QLSP(ObservableList<MatHang> list, TableView<MatHang> tableQLSP, int start, int end) {
		Connect_Data.getInstance();
		Connection con = Connect_Data.getConnection();
		PreparedStatement stmt = null;
		if (end > list.size() == true)
			end = list.size();
		for (int i = start; i < end; i++) {
			MatHang mh = list.get(i);
			try {
				String sql = " USE [QLXeMay]  UPDATE [dbo].[MatHang] SET [TenMatHang] = ? ,[MaLoai] = ? ,[Dong] = ? ,[SoLuong] = ? ,[DTBinhXang] = ? ,[DTDauMay] = ? ,[HTLyDong] = ? ,[MoTa] = ? ,[MauXe] = ? ,[DongCo] = ? ,[KichThuoc] = ? ,[KhungXe] = ? ,[DonGia] = ? ,[img] = ?,[ThueBan] =? WHERE [MaMatHang] = ? ";
				stmt = con.prepareStatement(sql);
				// ,[MauXe] = ? ,[DongCo] = ? ,[KichThuoc] = ? ,[KhungXe] = ? ,[DonGia] = ?
				// ,[img] = ?
				stmt.setString(1, mh.getTenMatHang());
				stmt.setString(2, mh.getMaLoai());
				stmt.setInt(4, mh.getSoLuong());
				stmt.setString(3, mh.getDongXe());
				stmt.setDouble(5, mh.getBinhXang());
				stmt.setDouble(6, mh.getDauMay());
				stmt.setString(7, mh.getLyDong());
				stmt.setString(8, mh.getMoTa());
				stmt.setString(9, mh.getMauXe());
				stmt.setDouble(10, mh.getDongCo());
				stmt.setString(11, mh.getKichThuoc());
				stmt.setString(12, mh.getKhungxe());
				stmt.setDouble(13, mh.getDongia());
				// System.out.println(mh.getImage());
				String path = mh.getImage().toString();
				path = path.replace(LocalPath, DataPath);
				byte[] fin = path.getBytes("UTF-8");

				stmt.setBytes(14, fin);
				stmt.setDouble(15, mh.getThueBan());
				stmt.setString(16, mh.getMaMatHang());

				// INSERT [dbo].[MatHang] ([MaMatHang], [TenMatHang], [MaLoai], [DTBinhXang],
				// [DTDauMay], [HTLyDong], [MoTa], [MauXe], [DongCo], [KichThuoc], [KhungXe],
				// [DonGia], [img],SoLuong) VALUES (N'YZF321B', N'YZF-R3 xanh bạc', N'Xe thể
				// thao', 14.0, 2.4, N'Đa đĩa, ly tâm loại ướt', N'4 thì, 2 xy lanh, 8 van, làm
				// mát bằng dung dịch, DOHC', N'Xanh Dương', 321, N'2090 mm x 730 mm x 1140 mm',
				// N'Thép biên dạng kim
				// cương',129000000,'T:\ptud\img\2020_YZF320-A_DPBMC_USA_S3_RGB.png',10 )
				// stmt.setString(11, nv.getMaKhachHang());
				stmt.executeUpdate();

			} catch (Exception e) {
				// TODO: handle exception

				e.printStackTrace();
			}

		}

	}

	public boolean XoaMatHang_TheoMa(String maMatHang) {
		Connect_Data.getInstance();
		Connection con = Connect_Data.getConnection();
		PreparedStatement stmt = null;
		// int n = 0;
		try {
			String sql = "use QLXeMay DELETE FROM MatHang where MaMatHang = ?";
			stmt = con.prepareStatement(sql);

			stmt.setString(1, maMatHang);

			// stmt.setString(11, nv.getMaKhachHang());
			stmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}

	public static void main(String[] args) {

//		}
		System.out.println("\'\'");
	}

}
