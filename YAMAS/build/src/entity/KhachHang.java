package entity;

public class KhachHang {
	private String soCMND;
	private String hoKH;
	private String tenKH;
	private String diaChi;
	private String soDT;
	private String gioiTinh; 
	public String getSoCMND() {
		return soCMND;
	}
	public void setSoCMND(String soCMND) {
		this.soCMND = soCMND;
	}
	public String getHoKH() {
		return hoKH;
	}
	public void setHoKH(String hoKH) {
		this.hoKH = hoKH;
	}
	public String getTenKH() {
		return tenKH;
	}
	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	public String getSoDT() {
		return soDT;
	}
	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}
	
	
	public String getGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public KhachHang() {
		super();
	}
	public KhachHang(String soCMND) {
		super();
		this.soCMND = soCMND;
	}
	/**
	 * 
	 * @param soCMND
	 * @param hoKH
	 * @param tenKH
	 * @param diaChi
	 * @param soDT
	 * @param gioiTinh
	 */
	public KhachHang(String soCMND, String hoKH, String tenKH, String diaChi, String soDT, String gioiTinh) {
		super();
		this.soCMND = soCMND;
		this.hoKH = hoKH;
		this.tenKH = tenKH;
		this.diaChi = diaChi;
		this.soDT = soDT;
		this.gioiTinh = gioiTinh;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
}
