package model;

public class TrackPackage 
{
	private String trackingNo;
	private String packageNo;
	private String custNo;
	private String date;
	private String time;
	
	public TrackPackage()
	{
		trackingNo = "";
		packageNo = "";
		custNo = "";
		date = "";
		time = "";
	}

	public TrackPackage(String trackingNo, String packageNo, String custNo, String date, String time) {
		this.trackingNo = trackingNo;
		this.packageNo = packageNo;
		this.custNo = custNo;
		this.date = date;
		this.time = time;
	}
	
	public TrackPackage(TrackPackage tp) {
		this.trackingNo = tp.trackingNo;
		this.packageNo = tp.packageNo;
		this.custNo = tp.custNo;
		this.date = tp.date;
		this.time = tp.time;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	
	
	
	

}
