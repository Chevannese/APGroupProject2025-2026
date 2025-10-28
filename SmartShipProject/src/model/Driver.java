package smartship.model;

import java.util.List;

public class Driver {
    private String name;
    private String licenseNumber;
    private int driverId;

    public Driver(String name, String licenseNumber, int driverId) {
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.driverId = driverId;
    }

    public Driver(Driver d) {
        this(d.name, d.licenseNumber, d.driverId);
    }


    public boolean login(String username, String password) {
       
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

    public String getName() { return name; }
    public String getLicenseNumber() { return licenseNumber; }
    public int getDriverId() { return driverId; }
}
