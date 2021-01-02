package entity;

public class CTHD {
	private String maHoaDon;
	private String maMatHang;
	private int soLuong;
	private Double donGia;
	private Double giamGia;

	// ThanhTien thuộc tính dẫn xuất ko có get set ko có khởi tạo.
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

	public Double getGiamGia() {
		return giamGia;
	}

	public void setGiamGia(Double giamGia) {
		if (this.giamGia == null)
			giamGia = 0.0;
		else
			this.giamGia = giamGia;
	}

	public CTHD(String maHoaDon, String maMatHang, int soLuong, Double donGia, Double giamGia) {
		super();
		this.maHoaDon = maHoaDon;
		this.maMatHang = maMatHang;
		this.soLuong = soLuong;
		this.donGia = donGia;
		this.giamGia = giamGia;
		this.getThanhTien();
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
		if (this.giamGia > 0) {

			if (this.getGiamGia() <= 100)
				this.thanhTien = this.soLuong * (this.donGia * (100 - this.giamGia) / 100);
			else
				this.thanhTien = this.soLuong * (this.donGia - this.giamGia );
		} else
			this.thanhTien = this.soLuong * this.donGia;
		return thanhTien;
	}

	@Override
	public String toString() {
		return "CTHD [maHoaDon=" + maHoaDon + ", maMatHang=" + maMatHang + ", soLuong=" + soLuong + ", donGia=" + donGia
				+ ", giamGia=" + giamGia + ", thanhTien=" + thanhTien + "]";
	}
	

}
