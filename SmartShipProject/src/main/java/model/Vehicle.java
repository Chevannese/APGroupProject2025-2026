package model;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicle")
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "vehicleNo")
    private String vehicleNo;

    @Column(name = "vehicleName")
    private String vehicleName;

    @Column(name = "quantityCap")
    private int quantityCap;

    @Column(name = "weightCap")
    private double weightCap;

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
    public void setVehicleName(String vehicleName) { this.vehicleName = vehicleName; }

    public int getQuantityCap() { return quantityCap; }
    public void setQuantityCap(int quantityCap) { this.quantityCap = quantityCap; }

    public double getWeightCap() { return weightCap; }
    public void setWeightCap(double weightCap) { this.weightCap = weightCap; }
}
