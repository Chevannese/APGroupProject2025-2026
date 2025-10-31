package model;

public class Driver extends User 
{
	public Driver()
	{
		super();
	}
	
	public Driver(String trn, String firstName, String lastName, String password, String contactNum, String email)
	{
		super(trn, firstName, lastName, password,contactNum, email);
	}
	
	public Driver(Driver driver)
	{
		super(driver);
	}

    public List<String> getPackages() {
       
        return List.of("PKG001 - In Transit", "PKG002 - Delivered");
    }

    public boolean updatePackageStatus(String trackingNumber, String newStatus) {
        System.out.println("Updating " + trackingNumber + " to " + newStatus);
        return true;
    }

    public String getRoute() {
        return "Zone 3 - Kingston East";
    }

    public String getVehicleInfo() {
        return "Vehicle: Toyota Hiace (Reg #V-102)";
    }
	
}
