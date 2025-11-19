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
@Table(name = "report")

public class Report implements Serializable
{
	/**
	 * 
	 */
	@Id
	@Column(name = "reportID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private static final long serialVersionUID = 1L;
	private Integer reportID;
	private String reportType;
	private LocalDate date;
	private LocalTime time;
	
	
	public Report()
	{
		reportID = 0;
		reportType = "";
		date = LocalDate.now();
		time = LocalTime.now();
	}

	public Report(Integer reportID, String reportType, LocalDate date, LocalTime time) {
		this.reportID = reportID;
		this.reportType = reportType;
		this.date = date;
		this.time = time;
	}
	
	public Report(Report rep) {
		this.reportID = rep.reportID;
		this.reportType = rep.reportType;
		this.date = rep.date;
		this.time = rep.time;
	}

	public Integer getReportID() {
		return reportID;
	}

	public void setReportID(Integer reportID) {
		this.reportID = reportID;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
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
