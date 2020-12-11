package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import connectDATA.Connect_Data;
import entity.NhanVien;

public class NhanVienDAO {
	public NhanVien getNhanVientheoMaNV_Login(String maNV, String matkhau){
		
		Connect_Data.getInstance();
		Connection con = Connect_Data.getConnection();
		PreparedStatement statement = null;
		NhanVien nv = new NhanVien();
		try {
			
		String sql = "Select * from NhanVien where maNhanVien = ?" ;
		statement = con.prepareStatement(sql);
		statement.setString(1, maNV);

		ResultSet rs = statement.executeQuery();
		if(rs.next()){
			 maNV= rs.getString(1);
			 String hoNV = rs.getString(2);
			 String tenNV= rs.getString(3);
			 String matKhau= rs.getString(4);
			 float luong= rs.getFloat(5);
			 String diaChi= rs.getString(6);
			 String gioiTinh= rs.getString(7);
			 String chucVu= rs.getString(8);
			 String caLam = rs.getString(9);
			 Date ngayLam= rs.getDate(10);
			  nv = new NhanVien(maNV, hoNV, tenNV, matKhau, luong, diaChi, gioiTinh, chucVu, caLam, ngayLam);
			 
			 
			 
		}
		else return null;
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
		
	return nv;
}
}
