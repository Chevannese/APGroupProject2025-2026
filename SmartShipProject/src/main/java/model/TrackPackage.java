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
@Table (name = "trackPackage")

public class TrackPackage implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "trackingNo")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	
	
	private Integer trackingNo;
	private Integer packageNo;
	private String custNo;
	private LocalDate date;
	private LocalTime time;
	
	public TrackPackage()
	{
		trackingNo = 0;
		packageNo = 0;
		custNo = "";
		date = LocalDate.now();
		time = LocalTime.now();
	}

	public TrackPackage(Integer trackingNo, Integer packageNo, String custNo, LocalDate date, LocalTime time) {
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

	public Integer getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(Integer trackingNo) {
		this.trackingNo = trackingNo;
	}

	public Integer getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(Integer packageNo) {
		this.packageNo = packageNo;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
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
