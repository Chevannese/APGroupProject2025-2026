package model;

public class Supplier 
{
	private String supplierID;
	private String supplierName;
	private String supplierAddr;
	
	public Supplier()
	{
		supplierID = "";
		supplierName = "";
		supplierAddr = "";
	}

	public Supplier(String supplierID, String supplierName, String supplierAddr) {
		this.supplierID = supplierID;
		this.supplierName = supplierName;
		this.supplierAddr = supplierAddr;
	}
	
	public Supplier(Supplier sup) {
		this.supplierID = sup.supplierID;
		this.supplierName = sup.supplierName;
		this.supplierAddr = sup.supplierAddr;
	}
	 




	public String getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(String supplierID) {
		this.supplierID = supplierID;
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
	
	

}
