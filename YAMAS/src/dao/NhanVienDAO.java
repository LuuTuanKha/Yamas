package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import connectDATA.Connect_Data;
import entity.NhanVien;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
				//maNV = rs.getString(1);

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
public void CapNhat_MatKhau(NhanVien nv){
		
		Connect_Data.getInstance();
		Connection con = Connect_Data.getConnection();
		PreparedStatement stmt = null;
		int n=0;
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
public void CapNhat_QLNV(NhanVien nv){
	
	Connect_Data.getInstance();
	Connection con = Connect_Data.getConnection();
	PreparedStatement stmt = null;
	int n=0;
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
	
}
