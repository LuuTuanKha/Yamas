package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.jfoenix.controls.JFXTextField;

import connectDATA.Connect_Data;
import entity.KhachHang;
import entity.MatHang;
import entity.KhachHang;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class KhachHangDAO {

	public void themKhachHang(KhachHang nv) {
		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "use QLXeMay INSERT INTO [dbo].[KhachHang]\r\n" + "           ([soCMND]\r\n"
					+ "           ,[HoKH]\r\n" + "           ,[TenKH]\r\n" + "           ,[GioiTinh]\r\n"
					+ "           ,[DiaChi]\r\n" + "           ,[SDT])\r\n" + "     VALUES (?,?,?,?,?,?)";
			stmt = conn.prepareStatement(sql);

			stmt.setString(1, nv.getSoCMND());
			stmt.setString(2, nv.getHoKH());
			stmt.setString(3, nv.getTenKH());
			stmt.setString(4, nv.getDiaChi());
			stmt.setString(5, nv.getSoCMND());
			stmt.setString(6, nv.getGioiTinh());
			// stmt.setString(11, nv.getMaKhachHang());
			// stmt.executeUpdate();
			stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	@SuppressWarnings("null")
	public ObservableList<KhachHang> getDSKhachHang() {
		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();
		ObservableList<KhachHang> list = FXCollections.observableArrayList();
		try {
			String query = "SELECT TOP(15) [soCMND] ,[HoKH] ,[TenKH] ,[DiaChi] ,[SDT],[GioiTinh] FROM [dbo].[KhachHang]"

					+ "WHERE [soCMND] is not null\r\n" + "ORDER BY [soCMND] DESC ";
			System.out.println(query);
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(query);

			while (rs.next()) {
				// tenSP = rs.getString(1);
				// byte[] bytes = rs.getString(14);
//				private String soCMND;
//				private String hoKH;
//				private String tenKH;
//				private String diaChi;
//				private String soDT;
//				private String gioiTinh; 
				KhachHang mh = new KhachHang(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6));
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

	public int getDSKhachHang_Loc(JFXTextField txtsoCMNDQLKH, JFXTextField txttenQLKH, JFXTextField txthoQLKH,
			JFXTextField txtgioiTinhQLKH, JFXTextField txtDiaChiQLKH, JFXTextField txtSoDTQLKH,
			ObservableList<KhachHang> list, TableView<KhachHang> tableQLKH, int start, int end) {
		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();
		
		try {
			String query = "USE [QLXeMay] SELECT [soCMND] ,[HoKH] ,[TenKH] ,[DiaChi],[SDT],[GioiTinh]   FROM [dbo].[KhachHang] where soCMND not null ";
			if (txtsoCMNDQLKH.getText().equals("") == false) {
				query+="and soCMND like N\\'%+"+txtsoCMNDQLKH.getText().trim()+"%\\' ";
			}
			if (txttenQLKH.getText().equals("") == false) {
				query+="and tenKH like N\\'%+"+txttenQLKH.getText().trim()+"%\\' ";
			}
			if (txthoQLKH.getText().equals("") == false) {
				query+="and hoKH like N\\'%+"+txthoQLKH.getText().trim()+"%\\' ";
			}
			if (txtgioiTinhQLKH.getText().equals("") == false) {
				query+="and gioiTinh like N\\'%+"+txtgioiTinhQLKH.getText().trim()+"%\\' ";
			}
			if (txtDiaChiQLKH.getText().equals("") == false) {
				query+="and DiaChi like N\\'%+"+txtDiaChiQLKH.getText().trim()+"%\\' ";
			}
			if (txtSoDTQLKH.getText().equals("") == false) {
				query+="and SDT like N\\'%+"+txtSoDTQLKH.getText().trim()+"%\\' ";
			}
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(query);
			while (rs.next()) {
				
				list.add(new KhachHang(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
				tableQLKH.refresh();

				

			}		
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		int count = list.size();
		if (count >= end)
			tableQLKH.setItems(FXCollections.observableArrayList(list.subList(start, end)));
		else
			tableQLKH.setItems(FXCollections.observableArrayList(list.subList(start, count)));
		tableQLKH.refresh();
		return count;
		
			

	}

	/*
	 * JFXTextField txtsoCMNDQLKH,
	 * 
	 * JFXTextField txttenQLKH,
	 * 
	 * JFXTextField txthoQLKH,
	 * 
	 * JFXTextField txtgioiTinhQLKH,
	 * 
	 * JFXTextField txtDiaChiQLKH,
	 * 
	 * JFXTextField txtSoDTQLKH
	 */

	public static void main(String[] args) {
		try {
			Connect_Data.getInstance().connect();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		KhachHangDAO khDao = new KhachHangDAO();
//		String s = "s";
//		KhachHang nv= new KhachHang(s, s, s, s, s, s);
//		khDao.themKhachHang(nv);
		System.out.println(java.time.LocalDateTime.now());

	}

}
