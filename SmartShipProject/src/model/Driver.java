package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Driver extends User {

    private String licenseNumber;
    private int vehicleId;

    public Driver() {
        super();
    }

    public Driver(String trn, String firstName, String lastName, String password,
                  String contactNum, String email, String userType) {
        super(trn, firstName, lastName, password, contactNum, email, userType);
    }

    public Driver(String licenseNumber, String firstName, int vehicleId) {
        super();
        this.licenseNumber = licenseNumber;
        this.vehicleId = vehicleId;
    }

    public Driver(Driver driver) {
        super(driver);
        this.licenseNumber = driver.licenseNumber;
        this.vehicleId = driver.vehicleId;
    }

    
    
    public boolean login(String username, String password) {
        // TEMPORARY MOCK LOGIN FOR TESTING
        if (username.equalsIgnoreCase("driver") && password.equals("1234")) {
            return true;
        }

               return false;
    }

    /**
     * Retrieves the list of packages assigned to this driver.
     */
    public List<String> getPackages() {
        List<String> packages = new ArrayList<>();

        // Mock data for testing GUI
        packages.add("PKG001 - In Transit");
        packages.add("PKG002 - Delivered");
        packages.add("PKG003 - Pending");

        
        return packages;
    }

    /**
     * Updates the status of a specific package.
     */
    public void updatePackageStatus(String pkgId, String newStatus) {
        System.out.println("Updating package " + pkgId + " to status: " + newStatus);

        
    }

    /**
     * Returns the driver's current route information.
     */
    public String getRoute() {
        // Mock info
        return "Route A - Kingston → Montego Bay";

    }

    /**
     * Displays vehicle details assigned to the driver.
     */
    public String getVehicleInfo() {
        // Mock info
        return "Vehicle ID: " + vehicleId + "\nLicense Plate: 1234JA\nCapacity: 100kg";

        
    }

    // ====================== GETTERS/SETTERS =========================

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }
}
