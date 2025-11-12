package model;

public class Trip 
{
	private String tripID;
	private String vehicleNo;
	private String clerkID;
	private String routeID;
	private String date;
	private String departureTime;
	private String arrivalTime;
	
	
	public Trip()
	{
		tripID = "";
		vehicleNo = "";
		clerkID = "";
		routeID = "";
		date = "";
		departureTime = "";
		arrivalTime = "";
	}


	public Trip(String tripID, String vehicleNo, String clerkID, String routeID, String date, String departureTime,
			String arrivalTime) {
		this.tripID = tripID;
		this.vehicleNo = vehicleNo;
		this.clerkID = clerkID;
		this.routeID = routeID;
		this.date = date;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
	}
	
	public Trip(Trip trip) {
		this.tripID = trip.tripID;
		this.vehicleNo = trip.vehicleNo;
		this.clerkID = trip.clerkID;
		this.routeID = trip.routeID;
		this.date = trip.date;
		this.departureTime = trip.departureTime;
		this.arrivalTime = trip.arrivalTime;
	}


	public String getTripID() {
		return tripID;
	}


	public void setTripID(String tripID) {
		this.tripID = tripID;
	}


	public String getVehicleNo() {
		return vehicleNo;
	}


	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}


	public String getClerkID() {
		return clerkID;
	}


	public void setClerkID(String clerkID) {
		this.clerkID = clerkID;
	}


	public String getRouteID() {
		return routeID;
	}


	public void setRouteID(String routeID) {
		this.routeID = routeID;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getDepartureTime() {
		return departureTime;
	}


	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}


	public String getArrivalTime() {
		return arrivalTime;
	}


	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	
	
	
	

}
