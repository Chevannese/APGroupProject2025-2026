package model;

public class Report 
{
	private String reportID;
	private String reportType;
	private String date;
	private String time;
	
	public Report()
	{
		reportID = "";
		reportType = "";
		date = "";
		time = "";
	}

	public Report(String reportID, String reportType, String date, String time) {
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

	public String getReportID() {
		return reportID;
	}

	public void setReportID(String reportID) {
		this.reportID = reportID;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
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
