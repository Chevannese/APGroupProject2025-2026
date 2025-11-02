package model;

public class Assignment 
{
	
	private String assignID;
	private String tripID;
	private String packageNo;
	private String custID;
	private String supplierID;
	private String vehicleNo;
	private String driverID;
	private String date;
	private String time;
	
	public Assignment()
	{
		
		assignID = "";
		tripID = "";
		packageNo = "";
		custID = "";
		supplierID = "";
		vehicleNo = "";
		driverID = "";
		date = "";
		time = "";
	}

	public Assignment(String assignID, String tripID, String packageNo, String custID, String supplierID, String vehicleNo,
			String driverID, String date, String time) {
		
		this.assignID = assignID;
		this.tripID = tripID;
		this.packageNo = packageNo;
		this.custID = custID;
		this.supplierID = supplierID;
		this.vehicleNo = vehicleNo;
		this.driverID = driverID;
		this.date = date;
		this.time = time;
	}
	
	public Assignment(Assignment assign) {
		
		this.assignID = assign.assignID;
		this.tripID = assign.tripID;
		this.packageNo = assign.packageNo;
		this.custID = assign.custID;
		this.supplierID = assign.supplierID;
		this.vehicleNo = assign.vehicleNo;
		this.driverID = assign.driverID;
		this.date = assign.date;
		this.time = assign.time;
	}

	

	public String getAssignID() {
		return assignID;
	}

	public void setAssignID(String assignID) {
		this.assignID = assignID;
	}

	public String getCustID() {
		return custID;
	}

	public void setCustID(String custID) {
		this.custID = custID;
	}

	public String getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(String supplierID) {
		this.supplierID = supplierID;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getDriverID() {
		return driverID;
	}

	public void setDriverID(String driverID) {
		this.driverID = driverID;
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

	public String getTripID() {
		return tripID;
	}

	public void setTripID(String tripID) {
		this.tripID = tripID;
	}

	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
	
	
	
	

}

