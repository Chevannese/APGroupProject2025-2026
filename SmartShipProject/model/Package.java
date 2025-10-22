package model;

public class Package 
{
	private int orderNum;
	private String packageName;
	private String packageType;
	private int distance;
	private String destination;
	private double weight;
	private double length;
	private double width;
	private double height;
	private double cost;
	
	public Package()
	{
		orderNum = 0;
		packageName = "";
		packageType = "";
		distance = 0;
		destination = "";
		weight = 0;
		length = 0;
		width = 0;
		height = 0;
		cost = 0;
	}

	//Primary Constructor 1 - To read and write data to database
	public Package(int orderNum, String packageName, String packageType, int distance, String destination, double weight, double length, double width, double height, double cost) 
	{
		this.orderNum = orderNum;
		this.packageName = packageName;
		this.packageType = packageType;
		this.distance = distance;
		this.destination = destination;
		this.weight = weight;
		this.length = length;
		this.width = width;
		this.height = height;
		this.cost = cost;
	}
	
	//Primary Constructor 2 - To create a new Package object without distance, destination, cost
	public Package(int orderNum, String packageName, String packageType, double weight, double length, double width, double height) 
	{
		this.orderNum = orderNum;
		this.packageName = packageName;
		this.packageType = packageType;
		this.weight = weight;
		this.length = length;
		this.width = width;
		this.height = height;
	}
	
	public Package(Package pack) 
	{
		this.orderNum = pack.orderNum;
		this.packageName = pack.packageName;
		this.packageType = pack.packageType;
		this.weight = pack.weight;
		this.length = pack.length;
		this.width = pack.width;
		this.height = pack.height;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
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
	
	
	
	

}
