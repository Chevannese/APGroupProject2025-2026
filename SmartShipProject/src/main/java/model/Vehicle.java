package model;

public class Vehicle 
{
	private String vehicleNo;
	private String vehicleName;
	private int quantityCap;
	private double weightCap;
	
	/* 
	 * Name: ISUZU Elf Box Van Truck
	 Weight Limit: 2600 pounds
	 Quantity Limit: 20
	 */
	
	public Vehicle()
	{
		vehicleNo = "";
		vehicleName = "";
		quantityCap = 0;
		weightCap = 0;
	}


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


	public String getVehicleNo() {
		return vehicleNo;
	}


	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}


	public String getVehicleName() {
		return vehicleName;
	}


	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}


	public int getQuantityCap() {
		return quantityCap;
	}


	public void setQuantityCap(int quantityCap) {
		this.quantityCap = quantityCap;
	}


	public double getWeightCap() {
		return weightCap;
	}


	public void setWeightCap(double weightCap) {
		this.weightCap = weightCap;
	}
	
	
	
	
}
