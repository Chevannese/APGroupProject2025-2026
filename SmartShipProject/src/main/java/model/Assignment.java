package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "assignment")
public class Assignment implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "assignID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Integer assignID;
	private Integer tripID;
	private Integer packageNo;
	private String custID;
	private String staffID;
	private String vehicleNo;
	private String driverID;
	private LocalDate date;
	private LocalTime time;
	
	public Assignment()
	{
		
		assignID = 0;
		tripID = 0;
		packageNo = 0;
		custID = "";
		staffID = "";
		vehicleNo = "";
		driverID = "";
		date = LocalDate.now();
		time = LocalTime.now();
	}

	public Assignment(Integer assignID, Integer tripID, Integer packageNo, String custID, String staffID, String vehicleNo,
			String driverID, LocalDate date, LocalTime time) {
		
		this.assignID = assignID;
		this.tripID = tripID;
		this.packageNo = packageNo;
		this.custID = custID;
		this.staffID = staffID;
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
		this.staffID = assign.staffID;
		this.vehicleNo = assign.vehicleNo;
		this.driverID = assign.driverID;
		this.date = assign.date;
		this.time = assign.time;
	}

	public Integer getAssignID() {
		return assignID;
	}

	public void setAssignID(Integer assignID) {
		this.assignID = assignID;
	}

	public Integer getTripID() {
		return tripID;
	}

	public void setTripID(Integer tripID) {
		this.tripID = tripID;
	}

	public Integer getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(Integer packageNo) {
		this.packageNo = packageNo;
	}

	public String getCustID() {
		return custID;
	}

	public void setCustID(String custID) {
		this.custID = custID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	

}
