package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import connectDATA.Connect_Data;
import entity.CTHD;
import entity.KhachHang;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CTHDDAO {
	public ObservableList<CTHD> getDSGioGang_CTHD_MaHD(String maHD) {
		
		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();
		
		ObservableList<CTHD> list =  FXCollections.observableArrayList();
		try {
			String query = "SELECT [MaMatHang] ,[SoLuong] ,[DonGia] ,[GiamGia] ,[ThanhTien] FROM [dbo].[CTHD]"
					+ " Where MaHoaDon = \'" + maHD + "\' ";
			// System.out.println(query);
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(query);
			
			while (rs.next()) {
				CTHD cthd = new CTHD(maHD, rs.getString(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4));
		
				list.add(cthd);
		 
				
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

		
	}
	public boolean themCTHD(CTHD cthd) {
		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();
		PreparedStatement stmt = null;
		try {

			String sql = " USE [QLXeMay] INSERT INTO [dbo].[CTHD] ([MaHoaDon] ,[MaMatHang] ,[SoLuong] ,[DonGia] ,[GiamGia],[ThanhTien])"
					+ "     VALUES (?,?,?,?,?,?)";
			stmt = conn.prepareStatement(sql);

			stmt.setString(1, cthd.getMaHoaDon());
			stmt.setString(2, cthd.getMaMatHang());
			stmt.setInt(3, cthd.getSoLuong());
			stmt.setDouble(4, cthd.getDonGia());
			stmt.setDouble(5, cthd.getGiamGia());
			stmt.setDouble(6, cthd.getThanhTien());

			// stmt.setString(11, nv.getMaKhachHang());
			// stmt.executeUpdate();
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
			// TODO: handle exception

		}
		return true;

	}

}
