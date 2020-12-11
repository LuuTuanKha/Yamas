package entity;

public class MatHang {
	private String maMatHang;
	private String tenMatHang;
	private String maLoai;
	private int soLuong;
	private double binhXang;
	private double dauMay;
	private String lyDong;
	private String moTa;
	private String mauXe;
	private double dongCo;
	private String kichThuoc;
	private String khungxe;
	private double dongia;
	private String image;
	public String getMaMatHang() {
		return maMatHang;
	}
	public void setMaMatHang(String maMatHang) {
		this.maMatHang = maMatHang;
	}
	public String getTenMatHang() {
		return tenMatHang;
	}
	public void setTenMatHang(String tenMatHang) {
		this.tenMatHang = tenMatHang;
	}
	public String getMaLoai() {
		return maLoai;
	}
	public void setMaLoai(String maLoai) {
		this.maLoai = maLoai;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public double getBinhXang() {
		return binhXang;
	}
	public void setBinhXang(double binhXang) {
		this.binhXang = binhXang;
	}
	public double getDauMay() {
		return dauMay;
	}
	public void setDauMay(double dauMay) {
		this.dauMay = dauMay;
	}
	public String getLyDong() {
		return lyDong;
	}
	public void setLyDong(String lyDong) {
		this.lyDong = lyDong;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	public String getMauXe() {
		return mauXe;
	}
	public void setMauXe(String mauXe) {
		this.mauXe = mauXe;
	}
	public double getDongCo() {
		return dongCo;
	}
	public void setDongCo(double dongCo) {
		this.dongCo = dongCo;
	}
	public String getKichThuoc() {
		return kichThuoc;
	}
	public void setKichThuoc(String kichThuoc) {
		this.kichThuoc = kichThuoc;
	}
	public String getKhungxe() {
		return khungxe;
	}
	public void setKhungxe(String khungxe) {
		this.khungxe = khungxe;
	}
	public double getDongia() {
		return dongia;
	}
	public void setDongia(double dongia) {
		this.dongia = dongia;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public MatHang(String maMatHang, String tenMatHang, String maLoai, int soLuong, double binhXang, double dauMay,
			String lyDong, String moTa, String mauXe, double dongCo, String kichThuoc, String khungxe, double dongia,
			String image) {
		super();
		this.maMatHang = maMatHang;
		this.tenMatHang = tenMatHang;
		this.maLoai = maLoai;
		this.soLuong = soLuong;
		this.binhXang = binhXang;
		this.dauMay = dauMay;
		this.lyDong = lyDong;
		this.moTa = moTa;
		this.mauXe = mauXe;
		this.dongCo = dongCo;
		this.kichThuoc = kichThuoc;
		this.khungxe = khungxe;
		this.dongia = dongia;
		this.image = image;
	}
	
	public MatHang() {
		super();
	}
	@Override
	public String toString() {
		return "MatHang [maMatHang=" + maMatHang + ", tenMatHang=" + tenMatHang + ", maLoai=" + maLoai + ", soLuong="
				+ soLuong + ", binhXang=" + binhXang + ", dauMay=" + dauMay + ", lyDong=" + lyDong + ", moTa=" + moTa
				+ ", mauXe=" + mauXe + ", dongCo=" + dongCo + ", kichThuoc=" + kichThuoc + ", khungxe=" + khungxe
				+ ", dongia=" + dongia + ", image=" + image + "]";
	}
	
	
	
	

}
