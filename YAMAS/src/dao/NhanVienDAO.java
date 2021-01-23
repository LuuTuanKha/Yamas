package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import connectDATA.Connect_Data;
import entity.KhachHang;
import entity.NhanVien;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class NhanVienDAO {

	public NhanVien getNhanVientheoMaNV_Login(String maNV, String matkhau) {

		Connect_Data.getInstance();
		Connection con = Connect_Data.getConnection();
		PreparedStatement statement = null;
		NhanVien nv = new NhanVien();
		try {

			String sql = "Select [maNhanVien],[hoNhanVien],[tenNhanVien],[matKhau],[Luong],[DiaChi],[GioiTinh],[ChucVu],[CaLam],[NgayLam],[CMND] from NhanVien where maNhanVien = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, maNV);

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				maNV = rs.getString(1);

				String hoNV = rs.getString(2);
				String tenNV = rs.getString(3);
				String matKhau = rs.getString(4);
				float luong = rs.getFloat(5);
				String diaChi = rs.getString(6);
				String gioiTinh = rs.getString(7);
				String chucVu = rs.getString(8);
				String caLam = rs.getString(9);
				Date ngayLam = rs.getDate(10);
				String soCMND = rs.getString(11);
				nv = new NhanVien(maNV, soCMND, hoNV, tenNV, matKhau, luong, diaChi, gioiTinh, chucVu, caLam, ngayLam);

			} else
				return null;

		} catch (Exception e) {

			e.printStackTrace();
		}

		return nv;
	}

	public NhanVien getNhanVientheoMaNV(String maNV) {

		Connect_Data.getInstance();
		Connection con = Connect_Data.getConnection();
		PreparedStatement statement = null;
		NhanVien nv = new NhanVien();
		try {

			String sql = "Select [hoNhanVien],[tenNhanVien] from NhanVien where maNhanVien = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, maNV);

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				// maNV = rs.getString(1);

				String hoNV = rs.getString(1);
				String tenNV = rs.getString(2);

				nv = new NhanVien(maNV, hoNV, tenNV);

			} else
				return null;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return nv;
	}

	public int getNhanVien_QLNV(ObservableList<NhanVien> dsQLNhanVien, TableView<NhanVien> tableQLNV,
			JFXTextField txtsoCMNDQLNV, JFXTextField txttenQLNV, JFXTextField txthoQLNV,
			JFXComboBox<String> txtgioiTinhQLNV, JFXComboBox<String> txtChucVuQLNV, JFXTextField txtDiaChiQLNV,
			JFXTextField txtLuongQLNV, JFXTextField txtmaQLNV, JFXTextField txtmatKhauQLNV, JFXTextField txtcalamQLNV,
			JFXDatePicker txtngayVaoLamQLNV,int startQLNV, int endQLNV) {
		Connect_Data.getInstance();
		Connection con = Connect_Data.getConnection();
		PreparedStatement statement = null;
		dsQLNhanVien.clear();
		try {

			String query = "SELECT [maNhanVien] ,[CMND] ,[hoNhanVien] ,[tenNhanVien] ,[matKhau] ,"
					+ "[Luong] ,[DiaChi] ,[GioiTinh] ,[ChucVu] ,[CaLam] ,[NgayLam] FROM [dbo].[NhanVien] where maNhanVien is not null ";
			if (txtmaQLNV.getText().equals("") == false)
				query += "and maNhanVien like \'%" + txtmaQLNV.getText().trim() + "%\' ";
			if (txtsoCMNDQLNV.getText().equals("") == false)
				query += "and CMND like \'%" + txtsoCMNDQLNV.getText().trim() + "%\' ";
			if (txthoQLNV.getText().equals("") == false)
				query += "and hoNhanVien like N\'%" + txthoQLNV.getText().trim() + "%\' ";
			if (txttenQLNV.getText().equals("") == false)
				query += "and tenNhanVien like N\'%" + txttenQLNV.getText().trim() + "%\' ";
			if (txtmatKhauQLNV.getText().equals("") == false)
				query += "and matKhau like \'%" + txtmatKhauQLNV.getText().trim() + "%\' ";
			if (txtLuongQLNV.getText().equals("") == false)
				query += "and Luong =" + txtLuongQLNV.getText().trim() + " ";

			if (txtDiaChiQLNV.getText().equals("") == false)
				query += "and diaChi like N\'%" + txtDiaChiQLNV.getText().trim() + "%\' ";
			if (txtgioiTinhQLNV.getSelectionModel().getSelectedIndex() != 0) {

				query += "and GioiTinh like N\'%" + txtgioiTinhQLNV.getSelectionModel().getSelectedItem() + "%\' ";

			}
			if (txtcalamQLNV.getText().equals("") == false)
				query += "and caLam like N\'%" + txtcalamQLNV.getText()+ "%\' ";
			if (txtChucVuQLNV.getSelectionModel().getSelectedIndex() != 0)
				query += "and chucVu like N\'%" + txtChucVuQLNV.getSelectionModel().getSelectedItem() + "%\' ";
			if (txtngayVaoLamQLNV.getValue()!=null)
				query += "and ngayLam = \'" + txtngayVaoLamQLNV.getValue() + "\' ";
			System.out.println(query);
			statement = con.prepareStatement(query);
			// statement.setString(1, maNV);

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				// maNV = rs.getString(1);

				NhanVien nv = new NhanVien(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getFloat(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),rs.getDate(11));
				dsQLNhanVien.add(nv);
				tableQLNV.refresh();
			} 
			int count = dsQLNhanVien.size();
			// if(count<)
			if (count > startQLNV) {
				if (count >= endQLNV)
					tableQLNV.setItems(FXCollections.observableArrayList(dsQLNhanVien.subList(startQLNV, endQLNV)));
				else
					tableQLNV.setItems(FXCollections.observableArrayList(dsQLNhanVien.subList(startQLNV, count)));
				tableQLNV.refresh();
				
			}
				

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsQLNhanVien.size();
		
	}

	public ObservableList<String> getDSGioiTinh() {
		ObservableList<String> ds = FXCollections.observableArrayList();
		Connect_Data.getInstance();
		Connection con = Connect_Data.getConnection();
		PreparedStatement statement = null;
		NhanVien nv = new NhanVien();
		try {

			String sql = "Select Distinct [GioiTinh] from [dbo].[NhanVien] nv";
			statement = con.prepareStatement(sql);
			// statement.setString(1, maNV);

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				ds.add(rs.getString(1));

			}
			// else return null;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return ds;
	}

	public void CapNhat_MatKhau(NhanVien nv) {

		Connect_Data.getInstance();
		Connection con = Connect_Data.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {

			String sql = "update NhanVien set MatKhau=? where maNhanVien = ?";
			stmt = con.prepareStatement(sql);

			stmt.setString(1, nv.getMatKhau());
			stmt.setString(2, nv.getMaNV());
			stmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void CapNhat_QLNV(NhanVien nv) {

		Connect_Data.getInstance();
		Connection con = Connect_Data.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {

			String sql = "update NhanVien set MatKhau=? where maNhanVien = ?";
			stmt = con.prepareStatement(sql);

			stmt.setString(1, nv.getMatKhau());
			stmt.setString(2, nv.getMaNV());
			stmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void CapNhat_NhanVien(NhanVien nv) {

		Connect_Data.getInstance();
		Connection con = Connect_Data.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {

			String sql = "UPDATE [dbo].[NhanVien] SET [hoNhanVien] = ?, [tenNhanVien] = ?, [DiaChi] = ?, [GioiTinh] = ? WHERE [maNhanVien] = ?";
			stmt = con.prepareStatement(sql);

			stmt.setString(1, nv.getHoNV());
			stmt.setString(2, nv.getTenNV());
			stmt.setString(3, nv.getDiaChi());
			stmt.setString(4, nv.getGioiTinh());
			stmt.setString(5, nv.getMaNV());
			stmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	public void CapNhat_NhanVien_QLNV(NhanVien nv) {

		Connect_Data.getInstance();
		Connection con = Connect_Data.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
//			[CMND] ,[hoNhanVien] ,[tenNhanVien] ,[matKhau] ,"
//		+ "[Luong] ,[DiaChi] ,[GioiTinh] ,[ChucVu] ,[CaLam] ,[NgayLam] FROM [dbo].[NhanVien] where maNhanVien is not n
			String sql = "UPDATE [dbo].[NhanVien] SET [hoNhanVien] = ?, [tenNhanVien] = ?, [DiaChi] = ?, [GioiTinh] = ?,"
					+ " [matKhau] = ?, [Luong] = ?,[ChucVu]=?,[CaLam] =?, [NgayLam] =?, [CMND] = ? "
					+ " WHERE [maNhanVien] = ?";
			stmt = con.prepareStatement(sql);

			stmt.setString(1, nv.getHoNV());
			stmt.setString(2, nv.getTenNV());
			stmt.setString(3, nv.getDiaChi());
			stmt.setString(4, nv.getGioiTinh());
			
			stmt.setString(5, nv.getMatKhau());
			stmt.setFloat(6, nv.getLuong());
			stmt.setString(7, nv.getChucVu());
			stmt.setString(8, nv.getCaLam());
			java.sql.Date sqlDate = java.sql.Date.valueOf( nv.getNgayLam().toString() );
			stmt.setDate(9, sqlDate);
			stmt.setString(10, nv.getSoCMND());
			stmt.setString(11, nv.getMaNV());
			stmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	public boolean XoaNhanVien_TheoMa(String ma) {
		Connect_Data.getInstance();
		Connection con = Connect_Data.getConnection();
		PreparedStatement stmt = null;
		// int n = 0;
		try {
			String sql = "use QLXeMay DELETE FROM NhanVien where MaNhanVien = ?";
			stmt = con.prepareStatement(sql);

			stmt.setString(1, ma);

			// stmt.setString(11, nv.getMaKhachHang());
			stmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
			
			return false;
		}
		return true;
	}
	public boolean themNhanVien(NhanVien nv) {
		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "USE [QLXeMay]  INSERT INTO [dbo].[NhanVien] ([maNhanVien] ,[CMND] ,[hoNhanVien] ,[tenNhanVien] ,"
					+ "[matKhau] ,[Luong] ,[DiaChi] ,[GioiTinh] ,[ChucVu] ,[CaLam] ,[NgayLam]) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
			stmt = conn.prepareStatement(sql);

			stmt.setString(1, nv.getMaNV());
			stmt.setString(2, nv.getSoCMND());
			stmt.setString(3, nv.getHoNV());
			stmt.setString(4, nv.getTenNV());
			stmt.setString(5, nv.getMatKhau());
			stmt.setDouble(6, nv.getLuong());
			stmt.setString(7, nv.getDiaChi());
			stmt.setString(8, nv.getGioiTinh());
			stmt.setString(9, nv.getChucVu());
			stmt.setString(10, nv.getCaLam());
			java.sql.Date sqlDate = java.sql.Date.valueOf( nv.getNgayLam().toString() );
			stmt.setDate(11, sqlDate);
			
			
			// stmt.setString(11, nv.getMaKhachHang());
			// stmt.executeUpdate();
			stmt.executeUpdate();
		} catch (Exception e) {
		
			e.printStackTrace();
			return false;
		}
		return true;

	}


}
