package dao;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

/**
 * 
 * @author Mr BonSss
 */

public class MatHangDAO {
	// Lấy đường dẫn
	String DataPath ="T:\\ptud\\" ;
	String LocalPath = "D:\\BTL\\";
	NumberFormat db = new DecimalFormat("#0.00");
	Path currentRelativePath = Paths.get("");
	String path = currentRelativePath.toAbsolutePath().toString();

	public java.util.Map<String, String[]> getImageData(JFXComboBox<String> jcbLoaiXe, JFXComboBox<String> jcbPhanKhoi,
			JFXComboBox<String> jcbMauSac, JFXComboBox<String> jcbGiaThanh, JFXTextField txt) {

		int i = 0;
		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();
		String[] list = { "name", "path" };
		byte[] fileBytes;
		String query;
		java.util.Map<String, String[]> map = new LinkedHashMap<>();
		String path;
		String maSp;
		String tenSP;
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
			 //System.out.println(query);
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(query);

			while (rs.next()) {
				list = list.clone();
				
				maSp = rs.getString(1);
				
				list[0] = rs.getString(2);
				fileBytes = rs.getBytes(3);

				
				path = new String(fileBytes, "UTF-8");
				path = path.replace(DataPath, "D:\\BTL\\");
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
			String query = "SELECT *" + "FROM [dbo].[MatHang]\r\n" + "WHERE [MaMatHang] is not null and soLuong>0\r\n"
					+ "ORDER BY [MaMatHang] DESC ";
			// System.out.println(query);
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(query);

			while (rs.next()) {
				// tenSP = rs.getString(1);
				// byte[] bytes = rs.getString(14);
				
				MatHang mh = new MatHang(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4),
						rs.getDouble(5), rs.getDouble(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getDouble(10), rs.getString(11), rs.getString(12), rs.getDouble(13),"updateting..");
				path =new String(rs.getBytes(14), "UTF-8");
				path = path.replace(DataPath, "D:\\BTL\\");
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
			JFXComboBox<String> jcbMauSac, JFXComboBox<String> jcbGiaThanh, JFXTextField txt,
			ObservableList<MatHang> list, TableView<MatHang> tableQLSP, int start, int end) {
		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();

		try {
			// String Count = "SELECT count(mamathang) " + "FROM [dbo].[MatHang]" + "WHERE
			// [MaMatHang] is not null";
			String query = "SELECT  [MaMatHang],[TenMatHang],[MaLoai],[SoLuong],[DTBinhXang],[DTDauMay],[HTLyDong],[MoTa],[MauXe],[DongCo],[KichThuoc],[KhungXe],[DonGia],[img] " + "FROM [dbo].[MatHang]" + "WHERE [MaMatHang] is not null and soLuong>0 ";

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

			 //System.out.println(query);
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(query);

			while (rs.next()) {
				// tenSP = rs.getString(1);
				// byte[] bytes = rs.getString(14);
				MatHang mh = new MatHang(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4),
						rs.getDouble(5), rs.getDouble(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getDouble(10), rs.getString(11), rs.getString(12), rs.getDouble(13),
						"updateting...");
				path =new String(rs.getBytes(14), "UTF-8");
				path = path.replace(DataPath, "D:\\BTL\\");
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

			String query = "SELECT [MaMatHang],[TenMatHang],[MaLoai],[SoLuong],[DTBinhXang],[DTDauMay],[HTLyDong],[MoTa],[MauXe],[DongCo],[KichThuoc],[KhungXe],[DonGia],[img]\r\n" + "FROM [dbo].[MatHang]\r\n" + "WHERE [MaMatHang] =" + "\'" + maSP + "\'";
			//System.out.println(query);
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(query);

			// tenSP = rs.getString(1);
			// byte[] bytes = rs.getString(14);
			if (rs.next())
				mh = new MatHang(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDouble(5),
						rs.getDouble(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getDouble(10),
						rs.getString(11), rs.getString(12), rs.getDouble(13),
						new String(rs.getBytes(14), "UTF-8").replace(DataPath, "D:\\BTL\\"));

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
		int i = 0;
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
			//System.out.println(query);
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
				path = path.replace(DataPath, "D:\\BTL\\");

				vector.add(maSp);
				// ImageIO.write(bImage2, "png", new File(pathname) );
				i++;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// Image img = new Image("img/new.png", 200, 160, false, false);
		// Image img = new Image("img/new.png");
		return vector;

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

	public void CapNhat_QLSP(ObservableList<MatHang> list, TableView<MatHang> tableQLSP, int start ,int end) {
		Connect_Data.getInstance();
		Connection con = Connect_Data.getConnection();
		PreparedStatement stmt = null;
		if(end>list.size()==true) end= list.size();
		for (int i = start; i < end; i++) {
			MatHang mh = list.get(i);
			try {
				String sql = " USE [QLXeMay]  UPDATE [dbo].[MatHang] SET [TenMatHang] = ? ,[MaLoai] = ? ,[SoLuong] = ? ,[DTBinhXang] = ? ,[DTDauMay] = ? ,[HTLyDong] = ? ,[MoTa] = ? ,[MauXe] = ? ,[DongCo] = ? ,[KichThuoc] = ? ,[KhungXe] = ? ,[DonGia] = ? ,[img] = ? WHERE [MaMatHang] = ? ";
				stmt = con.prepareStatement(sql);
				//,[MauXe] = ? ,[DongCo] = ? ,[KichThuoc] = ? ,[KhungXe] = ? ,[DonGia] = ? ,[img] = ?
				stmt.setString(1,mh.getTenMatHang());
				stmt.setString(2,mh.getMaLoai());
				stmt.setInt(3,mh.getSoLuong());
				stmt.setDouble(4,mh.getBinhXang());
				stmt.setDouble(5,mh.getDauMay());
				stmt.setString(6,mh.getLyDong());
				stmt.setString(7,mh.getMoTa());
				stmt.setString(8,mh.getMauXe());
				stmt.setDouble(9,mh.getDongCo());
				stmt.setString(10,mh.getKichThuoc());
				stmt.setString(11,mh.getKhungxe());
				stmt.setDouble(12,mh.getDongia());
				//System.out.println(mh.getImage());
				String path = mh.getImage().toString();
				path = path.replace( "D:\\BTL\\",DataPath);
				 byte[] fin=path.getBytes("UTF-8");
				
				stmt.setBytes(13, fin);
				stmt.setString(14,mh.getMaMatHang());
				
				//INSERT [dbo].[MatHang] ([MaMatHang], [TenMatHang], [MaLoai], [DTBinhXang], [DTDauMay], [HTLyDong], [MoTa], [MauXe], [DongCo], [KichThuoc], [KhungXe], [DonGia], [img],SoLuong) VALUES (N'YZF321B', N'YZF-R3 xanh bạc', N'Xe thể thao', 14.0, 2.4, N'Đa đĩa, ly tâm loại ướt', N'4 thì, 2 xy lanh, 8 van, làm mát bằng dung dịch, DOHC', N'Xanh Dương', 321, N'2090 mm x 730 mm x 1140 mm', N'Thép biên dạng kim cương',129000000,'T:\ptud\img\2020_YZF320-A_DPBMC_USA_S3_RGB.png',10 )
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
		//int n = 0;
		try {
			String sql = "use QLXeMay DELETE FROM MatHang where MaMatHang = ?";
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1,maMatHang);
			
			// stmt.setString(11, nv.getMaKhachHang());
			stmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}

	public static void main(String[] args) {
//		try {
//			Connect_Data.getInstance().connect();
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		MatHangDAO dao = new MatHangDAO();
//		java.util.Map<String, String[]> map = dao.getImageData();
//		for (String key: map.keySet()) {
//		    System.out.println("key : " + key);
//		    System.out.println("name : " + map.get(key)[0]);
//		    System.out.println("path : " + map.get(key)[1]);
//		    System.out.println("=================== " );
//		}
		System.out.println("\'\'");
	}

}
