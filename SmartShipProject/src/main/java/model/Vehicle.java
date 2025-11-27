package model;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "vehicle")
public class Vehicle implements Serializable {

    @Id
    @Column(name = "vehicleNo")
    private String vehicleNo;

    @Column(name = "driverID")
    private String driverID;

    @Column(name = "vehicleName")
    private String vehicleName;

    @Column(name = "quantityCap")
    private int quantityCap;

    @Column(name = "weightCap")
    private double weightCap;

    @Column(name = "currentWeight")
    private double currentWeight;

    @Column(name = "currentQuantity")
    private int currentQuantity;
    public Vehicle() {}
    public Vehicle(String vehicleNo, String vehicleName, int quantityCap, double weightCap) {
        this.vehicleNo = vehicleNo;
        this.vehicleName = vehicleName;
        this.quantityCap = quantityCap;
        this.weightCap = weightCap;
    }

    public Vehicle(Vehicle vehicle) {
        this.vehicleNo = vehicle.vehicleNo;
        this.vehicleName = vehicle.vehicleName;
        this.quantityCap = vehicle.quantityCap;
        this.weightCap = vehicle.weightCap;
    }

    public String getVehicleNo() { return vehicleNo; }
    public void setVehicleNo(String vehicleNo) { this.vehicleNo = vehicleNo; }

    public String getVehicleName() { return vehicleName; }
    public String getDriverID() {
		return driverID;
	}

	public void setDriverID(String driverID) {
		this.driverID = driverID;
	}

	public void setVehicleName(String vehicleName) { this.vehicleName = vehicleName; }

    public int getQuantityCap() { return quantityCap; }
    public void setQuantityCap(int quantityCap) { this.quantityCap = quantityCap; }

    public double getWeightCap() { return weightCap; }
    public void setWeightCap(double weightCap) { this.weightCap = weightCap; }
    public double getCurrentWeight() { return currentWeight; }
    public void setCurrentWeight(double currentWeight) { this.currentWeight = currentWeight; }

    public int getCurrentQuantity() { return currentQuantity; }
    public void setCurrentQuantity(int currentQuantity) { this.currentQuantity = currentQuantity; }
}
