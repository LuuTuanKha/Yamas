package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import connectDATA.Connect_Data;
import entity.CTHD;
import entity.HoaDon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;


public class HoaDonDAO {
	public int getDSHoaDon(JFXTextField txtSoCMNDKHQLHD, JFXTextField txtHoKHQLHD, JFXTextField txtTenKHQLHD,
			JFXTextField txtSoDTKHQLHD, JFXTextField txtMANVQLHD, JFXTextField txtMAHDQLHD, JFXDatePicker dpkFromQLHD,
			JFXDatePicker dpktoQLHD, TableView<HoaDon> tableQLHD_HD, ObservableList<HoaDon> list, int start, int end) {

		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();
		list.clear();
		try {
			String query = "SELECT [MaHoaDon], [dbo].[HoaDon].[soCMND], [dbo].[HoaDon].[maNhanVien], [NgayLapHD], [TongTien] FROM [dbo].[HoaDon] LEFT  JOIN [dbo].[KhachHang] as kh ON [dbo].[HoaDon].[soCMND] = kh.[soCMND] LEFT  JOIN [dbo].[NhanVien] as nv ON [dbo].[HoaDon].[maNhanVien] = nv.maNhanVien Where [dbo].[HoaDon].MaHoaDon is not NULL  ";
			if (txtSoCMNDKHQLHD.getText().equals("") == false && txtSoCMNDKHQLHD.getText() != null)

				query += "and kh.soCMND  like \'%" + txtSoCMNDKHQLHD.getText().trim() + "%\' ";
			if (txtHoKHQLHD.getText().equals("") == false && txtHoKHQLHD.getText() != null) {
				

				query += "and kh.HoKH like N\'%" + txtHoKHQLHD.getText().trim() + "%\'  ";
				// + "or kh.HoKH like \'%" + s + "%\' ";
			}
			if (txtTenKHQLHD.getText().equals("") == false && txtTenKHQLHD.getText() != null)
				query += "and kh.tenKH like N\'%" + txtTenKHQLHD.getText().trim() + "%\' ";
			if (txtSoDTKHQLHD.getText().equals("") == false && txtSoDTKHQLHD.getText() != null)
				query += "and kh.SDT like N\'%" + txtSoDTKHQLHD.getText().trim() + "%\' ";
			if (txtMANVQLHD.getText().equals("") == false && txtMANVQLHD.getText() != null)
				query += "and nv.maNhanVien like \'%" + txtMANVQLHD.getText().trim() + "%\' ";
			if (txtMAHDQLHD.getText().equals("") == false && txtMAHDQLHD.getText() != null)
				query += "and  [dbo].[HoaDon].MaHoaDon like \'%" + txtMAHDQLHD.getText().trim() + "%\' ";
			if (dpkFromQLHD.getValue() != null && dpktoQLHD.getValue() != null) {

				query += "and [dbo].[HoaDon].[NgayLapHD] >= \'" + dpkFromQLHD.getValue().toString() + "\' ";
				query += "and [dbo].[HoaDon].[NgayLapHD] <= \'" + dpktoQLHD.getValue().toString() + "\' ";
			}

			System.out.println(query);
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(query);

			while (rs.next()) {

				HoaDon hd = new HoaDon(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4),
						rs.getDouble(5));

				list.add(hd);
				tableQLHD_HD.refresh();
				// OutputStream targetFile = new FileOutputStream("d://new"+i+".png");

				// targetFile.write(fileBytes);
				// targetFile.close();

				// ImageIO.write(bImage2, "png", new File(pathname) );

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		int count = list.size();
		if (count >= end)
			tableQLHD_HD.setItems(FXCollections.observableArrayList(list.subList(start, end)));
		else
			tableQLHD_HD.setItems(FXCollections.observableArrayList(list.subList(start, count)));
		tableQLHD_HD.refresh();
		return count;

	}

	public void getDSHoaDon_ThongKeDoanhThu_Ngay(XYChart.Series<String, Double> listDT,
			XYChart.Series<String, Double> listLN, JFXDatePicker from, JFXDatePicker to) {

		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();

		try {
			String query = "SELECT  [dbo].[HoaDon].[NgayLapHD]  , sum(TongTien) as b2  FROM [dbo].[HoaDon] ";

			if (from.getValue() != null && to.getValue() != null) {

				System.out.println("Đã");
				query += "where [dbo].[HoaDon].[NgayLapHD] >= \'" + from.getValue().toString() + "\' ";
				query += "and [dbo].[HoaDon].[NgayLapHD] <= \'" + to.getValue().toString() + "\' ";
			}
			query += " group by [dbo].[HoaDon].[NgayLapHD] ORDER BY CONVERT(DATE, [NgayLapHD]) ASC";

			System.out.println(query);
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(query);

			while (rs.next()) {

				listDT.getData().add(new XYChart.Data<String, Double>(rs.getDate(1).toString(), rs.getDouble(2)));
				listLN.getData().add(new XYChart.Data<String, Double>(rs.getDate(1).toString(), rs.getDouble(2) / 10));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void getDSHoaDon_ThongKeDoanhThu_Thang(XYChart.Series<String, Double> listDT,
			XYChart.Series<String, Double> listLN, JFXDatePicker from, JFXDatePicker to) {
		
		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();
		
		try {
			
			String query = "SELECT  sum(TongTien),MONTH([dbo].[HoaDon].[NgayLapHD]), YEAR([dbo].[HoaDon].[NgayLapHD]) as b2  FROM [dbo].[HoaDon]";
			
			if (from.getValue() != null && to.getValue() != null) {
				
				System.out.println("Đã");
				query += "where [dbo].[HoaDon].[NgayLapHD] >= \'" + from.getValue().toString() + "\' ";
				query += "and [dbo].[HoaDon].[NgayLapHD] <= \'" + to.getValue().toString() + "\' ";
			}
			query += " group by  MONTH([dbo].[HoaDon].[NgayLapHD]), YEAR([dbo].[HoaDon].[NgayLapHD])";
			
			System.out.println(query);
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(query);
			
			while (rs.next()) {
				
				listDT.getData().add(new XYChart.Data<String, Double>(rs.getString(2).toString()+ "-" + rs.getString(3), rs.getDouble(1)));
				listLN.getData().add(new XYChart.Data<String, Double>(rs.getString(2).toString()+ "-" + rs.getString(3), rs.getDouble(1) / 10));
				//listLN.getData().add(new XYChart.Data<String, Double>(rs.getDate(1).toString(), rs.getDouble(1) / 10));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void getDSHoaDon_ThongKeDoanhThu_Nam(XYChart.Series<String, Double> listDT,
			XYChart.Series<String, Double> listLN, JFXDatePicker from, JFXDatePicker to) {
		
		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();
		
		try {
			
			String query = "SELECT  sum(TongTien),YEAR([dbo].[HoaDon].[NgayLapHD]) as b2  FROM [dbo].[HoaDon]";
			
			if (from.getValue() != null && to.getValue() != null) {
				
				System.out.println("Đã");
				query += "where [dbo].[HoaDon].[NgayLapHD] >= \'" + from.getValue().toString() + "\' ";
				query += "and [dbo].[HoaDon].[NgayLapHD] <= \'" + to.getValue().toString() + "\' ";
			}
			query += " group by  YEAR([dbo].[HoaDon].[NgayLapHD])";
			
			System.out.println(query);
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(query);
			
			while (rs.next()) {
				
				listDT.getData().add(new XYChart.Data<String, Double>(rs.getString(2).toString(), rs.getDouble(1)));
				listLN.getData().add(new XYChart.Data<String, Double>(rs.getString(2).toString(), rs.getDouble(1) / 10));
				//listLN.getData().add(new XYChart.Data<String, Double>(rs.getDate(1).toString(), rs.getDouble(1) / 10));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void getDSHoaDon_ThongKeDoanhThu_Quy(XYChart.Series<String, Double> listDT,
			XYChart.Series<String, Double> listLN, JFXDatePicker from, JFXDatePicker to) {
		
		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();
		
		try {
			
			String query = "SELECT DATEPART(YEAR,[dbo].[HoaDon].[NgayLapHD]) [Year],\r\n"
					+ " DATEPART(QUARTER,[dbo].[HoaDon].[NgayLapHD]) [Quarter], sum(TongTien) FROM [dbo].[HoaDon]";
			
			if (from.getValue() != null && to.getValue() != null) {
				
				System.out.println("Đã");
				query += "where [dbo].[HoaDon].[NgayLapHD] >= \'" + from.getValue().toString() + "\' ";
				query += "and [dbo].[HoaDon].[NgayLapHD] <= \'" + to.getValue().toString() + "\' ";
			}
			query += " GROUP BY DATEPART(YEAR,[dbo].[HoaDon].[NgayLapHD]),DATEPART(QUARTER,[dbo].[HoaDon].[NgayLapHD])\r\n"
					+ "ORDER BY 1,2";
			
			System.out.println(query);
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(query);
			
			while (rs.next()) {
				
				listDT.getData().add(new XYChart.Data<String, Double>("Quý "+rs.getString(2).toString()+ "-" + rs.getString(1), rs.getDouble(3)));
				listLN.getData().add(new XYChart.Data<String, Double>("Quý " +rs.getString(2).toString()+ "-" + rs.getString(1), rs.getDouble(3) / 10));
				//listLN.getData().add(new XYChart.Data<String, Double>(rs.getDate(1).toString(), rs.getDouble(1) / 10));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void themHoaDon(HoaDon hd, ObservableList<CTHD> dsGioHang) {
		Connect_Data.getInstance();
		Connection conn = Connect_Data.getConnection();
		PreparedStatement stmt = null;
		MatHangDAO mhDao = new MatHangDAO();
		CTHDDAO cthdDao = new CTHDDAO();
		try {

			String sql = " USE [QLXeMay] INSERT INTO [dbo].[HoaDon] ([MaHoaDon] ,[soCMND] ,[maNhanVien] ,[NgayLapHD] ,[TongTien])"
					+ "     VALUES (?,?,?,?,?)";
			stmt = conn.prepareStatement(sql);

			stmt.setString(1, hd.getMaHoaDon());
			stmt.setString(2, hd.getSoCMND());
			stmt.setString(3, hd.getMaNV());
			stmt.setDate(4, hd.getNgayLapHD());
			stmt.setDouble(5, hd.getTongTien());

			// stmt.setString(11, nv.getMaKhachHang());
			// stmt.executeUpdate();
			stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		for (CTHD cthd : dsGioHang) {
			System.out.println(cthd.toString());
			if (cthdDao.themCTHD(cthd) == true)
				System.out.println("Đã thêm: " + cthd.getMaHoaDon() + cthd.getMaMatHang());
			mhDao.CapnhatMH_TaoHD(cthd);

		}
	}

	
}
