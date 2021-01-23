package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import connectDATA.Connect_Data;
import entity.KhachHang;
import entity.MatHang;
import entity.NhanVien;
import entity.KhachHang;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;

public class KhachHangDAO {
	DecimalFormat formatter = new DecimalFormat("###,###,### VNĐ");
	public boolean themKhachHang(KhachHang nv) {
		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "INSERT INTO [dbo].[KhachHang] ([soCMND] ,[HoKH] ,[TenKH] ,[GioiTinh] ,[DiaChi] ,[SDT]) VALUES (?,?,?,?,?,?)";
			stmt = conn.prepareStatement(sql);

			stmt.setString(1, nv.getSoCMND());
			stmt.setString(2, nv.getHoKH());
			stmt.setString(3, nv.getTenKH());
			stmt.setString(4, nv.getGioiTinh());
			stmt.setString(5, nv.getDiaChi());
			stmt.setString(6, nv.getSoDT());
			
			// stmt.setString(11, nv.getMaKhachHang());
			// stmt.executeUpdate();
			stmt.executeUpdate();
		} catch (Exception e) {
		
			e.printStackTrace();
			return false;
		}
		return true;

	}

	@SuppressWarnings("null")
	public ObservableList<KhachHang> getDSKhachHang() {
		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();
		ObservableList<KhachHang> list = FXCollections.observableArrayList();
		try {
			String query = "SELECT [soCMND] ,[HoKH] ,[TenKH] ,[DiaChi] ,[SDT],[GioiTinh] FROM [dbo].[KhachHang]"

					+ "WHERE [soCMND] is not null\r\n" + "ORDER BY [soCMND] DESC ";
			// System.out.println(query);
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(query);

			while (rs.next()) {
			
				KhachHang mh = new KhachHang(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6));
				list.add(mh);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	public KhachHang getKhachHang_MaKH(String maKH) {
		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();
		KhachHang kh = new KhachHang(maKH);
		try {
			String query = "SELECT   [HoKH] ,[TenKH], [SDT] FROM [dbo].[KhachHang]"

					+ "WHERE [soCMND] =  \'" + maKH + "\' ";
			// System.out.println(query);
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(query);

			if (rs.next()) {
		 
				
				kh.setSoCMND(maKH);
				kh.setHoKH(rs.getString(1));
				kh.setTenKH(rs.getString(2));
				kh.setSoDT(rs.getString(3));
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return kh;

	}

	public int getDSKhachHang_Loc(JFXTextField txtsoCMNDQLKH, JFXTextField txttenQLKH, JFXTextField txthoQLKH,
			JFXTextField txtgioiTinhQLKH, JFXTextField txtDiaChiQLKH, JFXTextField txtSoDTQLKH,
			ObservableList<KhachHang> list, TableView<KhachHang> tableQLKH, int start, int end) {
		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();
		list.clear();
		try {
			String query = "USE [QLXeMay] SELECT [soCMND] ,[HoKH] ,[TenKH] ,[DiaChi],[SDT],[GioiTinh]   FROM [dbo].[KhachHang] where soCMND is not null ";
			if (txtsoCMNDQLKH.getText().equals("") == false) {
				query += "and soCMND like \'%" + txtsoCMNDQLKH.getText().trim() + "%\' ";
			}
			if (txttenQLKH.getText().equals("") == false) {
				query += "and tenKH like N\'%" + txttenQLKH.getText().trim() + "%\' ";
			}
			if (txthoQLKH.getText().equals("") == false) {
				query += "and hoKH like N\'%" + txthoQLKH.getText().trim() + "%\' ";
			}
			if (txtgioiTinhQLKH.getText().equals("") == false) {
				query += "and gioiTinh like N\'%" + txtgioiTinhQLKH.getText().trim() + "%\' ";
			}
			if (txtDiaChiQLKH.getText().equals("") == false) {
				query += "and DiaChi like N\'%" + txtDiaChiQLKH.getText().trim() + "%\' ";
			}
			if (txtSoDTQLKH.getText().equals("") == false) {
				query += "and SDT like N\'%" + txtSoDTQLKH.getText().trim() + "%\' ";
			}
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(query);
			while (rs.next()) {

				list.add(new KhachHang(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6)));
				tableQLKH.refresh();

			}
			System.out.println(query);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		int count = list.size();
		if (count >= end)
			tableQLKH.setItems(FXCollections.observableArrayList(list.subList(start, end)));
		else
			tableQLKH.setItems(FXCollections.observableArrayList(list.subList(start, count)));
		tableQLKH.refresh();
		return count;

	}

	public boolean getDSKH_ThongKe( JFXDatePicker from, JFXDatePicker to, JFXTextField txt, JFXComboBox<String > cbb,ObservableList<KhachHang> list, TableView<KhachHang> table) {
		
		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();
		list.clear();
		try {
			
			String query = "SELECT  top " + txt.getText().trim()+ " hd.soCMND,  sum(hd.[TongTien]) as tt, count (hd.soCMND), kh.hoKh, kh.TenKH from [dbo].[HoaDon] as hd\r\n"
					+ "LEFT JOIN [dbo].[KhachHang] as kh\r\n"
					+ "ON hd.soCMND = kh.soCMND ";
			
			if (from.getValue() != null && to.getValue() != null) {
				
				System.out.println("Đã");
				query += "where hd.[NgayLapHD] >= \'" + from.getValue().toString() + "\' ";
				query += "and hd.[NgayLapHD] <= \'" + to.getValue().toString() + "\' ";
			}
			if (cbb.getSelectionModel().getSelectedIndex()!=0) {
				query += "and gioiTinh like N\'%" + cbb.getSelectionModel().getSelectedItem() + "%\' ";
			}
			query += " group by hd.soCMND , kh.hoKh, kh.TenKH\r\n"
					+ "ORDER BY 2 desc";
			
			System.out.println(query);
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(query);
			int i =0;
			while (rs.next()) {
				
				i++;
				KhachHang kh = new KhachHang(rs.getString(1), rs.getString(4), rs.getString(5), i+"", rs.getInt(3)+"", formatter.format(rs.getDouble(2)));
				list.add(kh);
				table.refresh();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	public void CapNhat_KhachHang(KhachHang kh) {

		Connect_Data.getInstance();
		Connection con = Connect_Data.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
//			[CMND] ,[hoNhanVien] ,[tenNhanVien] ,[matKhau] ,"
//		+ "[Luong] ,[DiaChi] ,[GioiTinh] ,[ChucVu] ,[CaLam] ,[NgayLam] FROM [dbo].[NhanVien] where maNhanVien is not n
			String sql = "UPDATE [dbo].[KhachHang] SET [HoKH] =?,[TenKH] =?,[GioiTinh]=? ,[DiaChi]=? ,[SDT]=?"
					+ " WHERE [soCMND] = ?";
			stmt = con.prepareStatement(sql);

			stmt.setString(1, kh.getHoKH());
			stmt.setString(2, kh.getTenKH());
			stmt.setString(3, kh.getGioiTinh());
			stmt.setString(4, kh.getDiaChi());
			stmt.setString(5, kh.getSoDT());
			stmt.setString(6, kh.getSoCMND());
			stmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

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
