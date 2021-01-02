package entity;

import java.sql.Date;

public class HoaDon {
	private String maHoaDon;
	private String soCMND;
	private String maNV;
	private Date ngayLapHD;
	private Double tongTien;
	public String getMaHoaDon() {
		return maHoaDon;
	}
	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}
	public String getSoCMND() {
		return soCMND;
	}
	public void setSoCMND(String soCMND) {
		this.soCMND = soCMND;
	}
	public String getMaNV() {
		return maNV;
	}
	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}
	public java.sql.Date getNgayLapHD() {
		return ngayLapHD;
	}
	public void setNgayLapHD(java.sql.Date ngayLapHD) {
		this.ngayLapHD = ngayLapHD;
	}
	public Double getTongTien() {
		return tongTien;
	}
	public void setTongTien(Double tongTien) {
		this.tongTien = tongTien;
	}
	public HoaDon(String maHoaDon, String soCMND, String maNV, java.sql.Date ngayLapHD, Double tongTien) {
		super();
		this.maHoaDon = maHoaDon;
		this.soCMND = soCMND;
		this.maNV = maNV;
		this.ngayLapHD = ngayLapHD;
		this.tongTien = tongTien;
	}
	public HoaDon() {
		super();
	}
	@Override
	public String toString() {
		return "HoaDon [maHoaDon=" + maHoaDon + ", soCMND=" + soCMND + ", maNV=" + maNV + ", ngayLapHD=" + ngayLapHD
				+ ", tongTien=" + tongTien + "]";
	}
	
	
	
}
