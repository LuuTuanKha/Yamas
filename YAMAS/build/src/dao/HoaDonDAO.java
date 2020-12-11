package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import connectDATA.Connect_Data;
import entity.CTHD;
import entity.HoaDon;
import entity.KhachHang;
import javafx.collections.ObservableList;

public class HoaDonDAO {
	public void themHoaDon(HoaDon hd, ObservableList<CTHD> dsGioHang) {
		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();
		PreparedStatement stmt = null;
		MatHangDAO mhDao = new MatHangDAO();
		try {
			
			String sql =" USE [QLXeMay] INSERT INTO [dbo].[HoaDon] ([MaHoaDon] ,[soCMND] ,[maNhanVien] ,[NgayLapHD] ,[TongTien])"
					+ "     VALUES (?,?,?,?,?)";
			 stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, hd.getMaHoaDon());
			stmt.setString(2, hd.getSoCMND());
			stmt.setString(3, hd.getMaNV());
			stmt.setDate(4, hd.getNgayLapHD());
			stmt.setDouble(5,hd.getTongTien());
			
			
			// stmt.setString(11, nv.getMaKhachHang());
			//stmt.executeUpdate();
			stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		for (CTHD cthd : dsGioHang) {
			themCTHD(cthd);
			mhDao.CapnhatMH_TaoHD(cthd);
			
			
		}
	}
	public void themCTHD(CTHD cthd) {
		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();
		PreparedStatement stmt = null;
		try {
			
			String sql =" USE [QLXeMay] INSERT INTO [dbo].[CTHD] ([MaHoaDon] ,[MaMatHang] ,[SoLuong] ,[DonGia] ,[ThanhTien])"
					+ "     VALUES (?,?,?,?,?)";
			 stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, cthd.getMaHoaDon());
			stmt.setString(2, cthd.getMaMatHang());
			stmt.setInt(3, cthd.getSoLuong());
			stmt.setDouble(4, cthd.getDonGia());
			stmt.setDouble(5,cthd.getThanhTien());
			
			// stmt.setString(11, nv.getMaKhachHang());
			//stmt.executeUpdate();
			stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	
	
	
	 @SuppressWarnings("deprecation")
	public static void main(String[] args) {
		 try {
				Connect_Data.getInstance().connect();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		 HoaDonDAO hddDao = new  HoaDonDAO();
		 //HoaDon hd = new HoaDon("XX", "213514526", "NV003", new Date(2000, 10, 12), 30.0);
		 //hddDao.themKhachHang(hd);
		 
		 
	}

}
