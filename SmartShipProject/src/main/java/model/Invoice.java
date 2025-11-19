package model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "invoice")


public class Invoice implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "invoiceNo")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Integer invoiceNo;
	private Integer packageNo;
	private String custNo;
	private String staffNo;
	private String paymentMethod;
	private String paymentStatus;
	private double discount;
	private double surcharge;
	private double total;
	private double remainingCost;
	
	public Invoice()
	{
		invoiceNo = 0;
		packageNo = 0;
		custNo = "";
		staffNo = "";
		paymentMethod = "";
		paymentStatus = "";
		discount  = 0;
		surcharge = 0;
		total = 0;
		remainingCost = 0;
	}

	public Invoice(Integer invoiceNo, Integer packageNo, String custNo, String staffNo, String paymentMethod,
			String paymentStatus, double discount, double surcharge, double total, double remainingCost) {
		this.invoiceNo = invoiceNo;
		this.packageNo = packageNo;
		this.custNo = custNo;
		this.staffNo = staffNo;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.discount = discount;
		this.surcharge = surcharge;
		this.total = total;
		this.remainingCost = remainingCost;
	}
	
	public Invoice(Invoice invo) {
		this.invoiceNo = invo.invoiceNo;
		this.packageNo = invo.packageNo;
		this.custNo = invo.custNo;
		this.staffNo = invo.staffNo;
		this.paymentMethod = invo.paymentMethod;
		this.paymentStatus = invo.paymentStatus;
		this.discount = invo.discount;
		this.surcharge = invo.surcharge;
		this.total = invo.total;
		this.remainingCost = invo.remainingCost;
	}

	public Integer getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(Integer invoiceNo) {
		this.invoiceNo = invoiceNo;
	}


	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getSurcharge() {
		return surcharge;
	}

	public void setSurcharge(double surcharge) {
		this.surcharge = surcharge;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Integer getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(Integer packageNo) {
		this.packageNo = packageNo;
	}

	public double getRemainingCost() {
		return remainingCost;
	}

	public void setRemainingCost(double remainingCost) {
		this.remainingCost = remainingCost;
	}

	
	
	
	
	
	
	
	
	

}
