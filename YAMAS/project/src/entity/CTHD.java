package entity;

public class CTHD {
	private String maHoaDon;	
	private String maMatHang;
	private int soLuong;
	private Double donGia;
	
	//ThanhTien thuộc tính dẫn xuất ko có get set ko có khởi tạo.
	protected Double thanhTien;
	
	
	
	public String getMaHoaDon() {
		return maHoaDon;
	}
	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}
	public String getMaMatHang() {
		return maMatHang;
	}
	public void setMaMatHang(String maMatHang) {
		this.maMatHang = maMatHang;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public Double getDonGia() {
		return donGia;
	}
	public void setDonGia(Double donGia) {
		this.donGia = donGia;
	}
	
	public CTHD(String maHoaDon, String maMatHang, int soLuong, Double donGia) {
		super();
		this.maHoaDon = maHoaDon;
		this.maMatHang = maMatHang;
		this.soLuong = soLuong;
		this.donGia = donGia;
		
	}
	/**
	 * @param maHoaDon
	 */
	public CTHD(String maHoaDon) {
		super();
		this.maHoaDon = maHoaDon;
	}
	/**
	 * 
	 */
	public CTHD() {
		super();
	}
	public Double getThanhTien() {
		return thanhTien;
	}
	public void setThanhTien() {
		this.thanhTien = this.soLuong*this.donGia;
	}
	
	
}
