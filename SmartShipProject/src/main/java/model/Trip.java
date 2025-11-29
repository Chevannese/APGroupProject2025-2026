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
@Table(name = "trip")
public class Trip implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tripID")
    private Integer tripID; 

    private String vehicleNo;

    private String driverID;

    private String clerkID;

    private Integer routeID;

    private String status = "Pending"; 

    private LocalDate date;

    private LocalTime departureTime;

    private LocalTime arrivalTime;

    public Trip() {
        this.tripID = 0;
        this.vehicleNo = "";
        this.clerkID = "";
        this.routeID = 0;
        this.date = LocalDate.now();
        this.departureTime = LocalTime.now();
        this.arrivalTime = LocalTime.now();
        this.status = "Pending";    // Ensures consistent default
    }

    public Trip(Integer tripID, String vehicleNo, String clerkID, Integer routeID,
                LocalDate date, LocalTime departureTime, LocalTime arrivalTime) {

        this.tripID = tripID;
        this.vehicleNo = vehicleNo;
        this.clerkID = clerkID;
        this.routeID = routeID;
        this.date = date;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.status = "Pending";  // Required for CHECK constraint
    }

    public Trip(Trip trip) {
        this.tripID = trip.tripID;
        this.vehicleNo = trip.vehicleNo;
        this.clerkID = trip.clerkID;
        this.routeID = trip.routeID;
        this.date = trip.date;
        this.departureTime = trip.departureTime;
        this.arrivalTime = trip.arrivalTime;
        this.status = trip.status;
    }

    public Integer getTripID() {
        return tripID;
    }

    public void setTripID(Integer tripID) {   // <--- FIXED
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

    public Integer getRouteID() {
        return routeID;
    }

    public void setRouteID(Integer routeID) {
        this.routeID = routeID;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {    // Optional future use
        this.status = status;
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
