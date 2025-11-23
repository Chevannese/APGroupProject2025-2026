package model;
import jakarta.persistence.*;
@Entity
@DiscriminatorValue("Driver")
public class Driver extends User {
   /**
	 * 
	 */
private static final long serialVersionUID = 1L;
public Driver() {
       super();
   }
   public Driver(String trn, String firstName, String lastName,
                 String password, String contactNum, String email) {
       super(trn, firstName, lastName, password, contactNum, email);
   }
   public Driver(Driver driver) {
       super(driver);
   }
}