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
@Table (name = "trip")

public class Trip implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "tripID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Integer tripID;
	private String vehicleNo;
	private String clerkID;
	private String routeID;
	private LocalDate date;
	private LocalTime departureTime;
	private LocalTime arrivalTime;
	
	
	public Trip()
	{
		tripID = 0;
		vehicleNo = "";
		clerkID = "";
		routeID = "";
		date = LocalDate.now();
		departureTime = LocalTime.now();
		arrivalTime = LocalTime.now();
	}


	public Trip(Integer tripID, String vehicleNo, String clerkID, String routeID, LocalDate date, LocalTime departureTime,
			LocalTime arrivalTime) {
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


	public Integer getTripID() {
		return tripID;
	}


	public void setTripID(Integer tripID) {
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


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public LocalTime getDepartureTime() {
		return departureTime;
	}


	public void setDepartureTime(LocalTime departureTime) {
		this.departureTime = departureTime;
	}


	public LocalTime getArrivalTime() {
		return arrivalTime;
	}


	public void setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}


	
	

}
