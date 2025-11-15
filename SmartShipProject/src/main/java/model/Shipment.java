package model;


import jakarta.persistence.*;

@Entity
@Table (name = "shipment")

public class Shipment 
{
	@Id
	@Column(name = "packageNo")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private String packageNo;
	private String custNo;
	private String supplierName;
	private String supplierAddr;
	private String receiverName;
	private String receiverAddr;
	private String packageName;
	private String packageType;
	private String status;
	private int distance;
	private String destination;
	private double weight;
	private double length;
	private double width;
	private double height;
	private double cost;
	
	public Shipment()
	{
		packageNo = "";
		custNo = "";
		supplierName = "";
		supplierAddr = "";
		receiverName = "";
		receiverAddr = "";
		packageName = "";
		packageType = "";
		status = "";
		distance = 0;
		destination = "";
		weight = 0;
		length = 0;
		width = 0;
		height = 0;
		cost = 0;	
	}

	public Shipment(String packageNo, String custNo, String supplierName, String supplierAddr,
			String receiverName, String receiverAddr, String packageName, String packageType,
			String status, int distance, String destination, double weight, double length, double width, double height,
			double cost) {
		
		this.packageNo = packageNo;
		this.custNo = custNo;
		this.supplierName = supplierName;
		this.supplierAddr = supplierAddr;
		this.receiverName = receiverName;
		this.receiverAddr = receiverAddr;
		this.packageName = packageName;
		this.packageType = packageType;
		this.status = status;
		this.distance = distance;
		this.destination = destination;
		this.weight = weight;
		this.length = length;
		this.width = width;
		this.height = height;
		this.cost = cost;
	}



	public Shipment(Shipment pack) 
	{
		this.packageNo = pack.packageNo;
		this.custNo = pack.custNo;
		this.supplierName = pack.supplierName;
		this.supplierAddr = pack.supplierAddr;
		this.receiverName = pack.receiverName;
		this.receiverAddr = pack.receiverAddr;
		this.packageName = pack.packageName;
		this.packageType = pack.packageType;
		this.status = pack.status;
		this.distance = pack.distance;
		this.destination = pack.destination;
		this.weight = pack.weight;
		this.length = pack.length;
		this.width = pack.width;
		this.height = pack.height;
		this.cost = pack.cost;
	}
	
	public double packageTypeRate()
	{
		if(packageType.equals("Standard"))
		{
			return 1;
		}
		else if(packageType.equals("Express"))
		{
			return 1.5;
		}
		else if(packageType.equals("Fragile"))
		{
			return 1.75;
		}
		return 0;	
	}
	
	public double shippingZoneRate(String input)
	{
		if(input.equals("Local"))
		{
			return 1;
		}
		else if(input.equals("International"))
		{
			return 1.5;
		}
		return 0;
	}
	
	 public double calculateShippingCost() {

		double ratePerPound = 2.0;  // $2 per lb
		double ratePerZone = 0.10;    // $0.10 per zone #
		
		double baseCost = (weight * ratePerPound)
		+ (distance * ratePerZone);
		
		return cost = 150 * baseCost * packageTypeRate();
		}

	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierAddr() {
		return supplierAddr;
	}

	public void setSupplierAddr(String supplierAddr) {
		this.supplierAddr = supplierAddr;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverAddr() {
		return receiverAddr;
	}

	public void setReceiverAddr(String receiverAddr) {
		this.receiverAddr = receiverAddr;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	
	

		
	
	
	

}
