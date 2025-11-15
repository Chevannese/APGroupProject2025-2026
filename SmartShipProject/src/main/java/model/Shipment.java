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
	private String supplierFName;
	private String supplierLName;
	private String supplierAddr;
	private String receiverFName;
	private String receiverLName;
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
		supplierFName = "";
		supplierLName = "";
		supplierAddr = "";
		receiverFName = "";
		receiverLName = "";
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

	public Shipment(String packageNo, String custNo, String supplierFName, String supplierLName, String supplierAddr,
			String receiverFName, String receiverLName, String receiverAddr, String packageName, String packageType,
			String status, int distance, String destination, double weight, double length, double width, double height,
			double cost) {
		
		this.packageNo = packageNo;
		this.custNo = custNo;
		this.supplierFName = supplierFName;
		this.supplierLName = supplierLName;
		this.supplierAddr = supplierAddr;
		this.receiverFName = receiverFName;
		this.receiverLName = receiverLName;
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
		this.supplierFName = pack.supplierFName;
		this.supplierLName = pack.supplierLName;
		this.supplierAddr = pack.supplierAddr;
		this.receiverFName = pack.receiverFName;
		this.receiverLName = pack.receiverLName;
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

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustNo() {
		return custNo;
	}

	

	public String getsupplierLName() {
		return supplierLName;
	}

	public void setsupplierLName(String supplierLName) {
		this.supplierLName = supplierLName;
	}

	public String getSupplierAddr() {
		return supplierAddr;
	}

	public void setSupplierAddr(String supplierAddr) {
		this.supplierAddr = supplierAddr;
	}

	public String getreceiverFName() {
		return receiverFName;
	}

	public void setreceiverFName(String receiverFName) {
		this.receiverFName = receiverFName;
	}

	public String getreceiverLName() {
		return receiverLName;
	}

	public void setreceiverLName(String receiverLName) {
		this.receiverLName = receiverLName;
	}

	public String getReceiverAddr() {
		return receiverAddr;
	}

	public void setReceiverAddr(String receiverAddr) {
		this.receiverAddr = receiverAddr;
	}

	public String getSupplierFName() {
		return supplierFName;
	}

	public void setSupplierFName(String supplierFName) {
		this.supplierFName = supplierFName;
	}

	public String getSupplierLName() {
		return supplierLName;
	}

	public void setSupplierLName(String supplierLName) {
		this.supplierLName = supplierLName;
	}

	public String getReceiverFName() {
		return receiverFName;
	}

	public void setReceiverFName(String receiverFName) {
		this.receiverFName = receiverFName;
	}

	public String getReceiverLName() {
		return receiverLName;
	}

	public void setReceiverLName(String receiverLName) {
		this.receiverLName = receiverLName;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}


		
	
	
	

}
