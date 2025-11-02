package model;

public class Invoice 
{
	private String invoiceNo;
	private String packageNo;
	private String custNo;
	private String staffNo;
	private String paymentMethod;
	private String paymentStatus;
	private double discount;
	private double surcharge;
	private double total;
	
	public Invoice()
	{
		invoiceNo = "";
		packageNo = "";
		custNo = "";
		staffNo = "";
		paymentMethod = "";
		paymentStatus = "";
		discount  = 0;
		surcharge = 0;
		total = 0;
	}

	public Invoice(String invoiceNo, String packageNo, String custNo, String staffNo, String paymentMethod,
			String paymentStatus, double discount, double surcharge, double total) {
		this.invoiceNo = invoiceNo;
		this.packageNo = packageNo;
		this.custNo = custNo;
		this.staffNo = staffNo;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.discount = discount;
		this.surcharge = surcharge;
		this.total = total;
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
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
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

	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}

	
	
	
	
	
	
	
	
	

}
