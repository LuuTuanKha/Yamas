package entity;

import java.util.Date;

public class NhanVien {
	private String maNV;
	private String hoNV;
	private String tenNV;
	private String matKhau;
	private float luong;
	private String diaChi;
	private String gioiTinh;
	private String chucVu;
	private String caLam;
	private Date ngayLam;
	public String getMaNV() {
		return maNV;
	}
	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}
	public String getHoNV() {
		return hoNV;
	}
	public void setHoNV(String hoNV) {
		this.hoNV = hoNV;
	}
	public String getTenNV() {
		return tenNV;
	}
	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}
	public String getMatKhau() {
		return matKhau;
	}
	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}
	public float getLuong() {
		return luong;
	}
	public void setLuong(float luong) {
		this.luong = luong;
	}
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	public String getGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public String getChucVu() {
		return chucVu;
	}
	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}
	public String getCaLam() {
		return caLam;
	}
	public void setCaLam(String caLam) {
		this.caLam = caLam;
	}
	public Date getNgayLam() {
		return ngayLam;
	}
	public void setNgayLam(Date ngayLam) {
		this.ngayLam = ngayLam;
	}
	public NhanVien(String maNV, String hoNV, String tenNV, String matKhau, float luong, String diaChi, String gioiTinh,
			String chucVu, String caLam, Date ngayLam) {
		super();
		this.maNV = maNV;
		this.hoNV = hoNV;
		this.tenNV = tenNV;
		this.matKhau = matKhau;
		this.luong = luong;
		this.diaChi = diaChi;
		this.gioiTinh = gioiTinh;
		this.chucVu = chucVu;
		this.caLam = caLam;
		this.ngayLam = ngayLam;
	}
	public NhanVien() {
		super();
	}
	/**
	 * @param maNV
	 */
	public NhanVien(String maNV) {
		super();
		this.maNV = maNV;
	}
	
}
