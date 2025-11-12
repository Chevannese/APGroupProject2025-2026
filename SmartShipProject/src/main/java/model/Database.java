package model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Database 
{
	
	private static final Logger logger = LogManager.getLogger(Database.class);

	private static SessionFactory sessionFactory = null;

	
	static {
        try {
            // Build once when class is loaded
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            logger.error("Initial SessionFactory creation failed." + ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
	
	
	
	public void createAccount(User newUser) 
	{
		 Session session = null;
	        try {
	        	session = Database.getSessionFactory().openSession();
	            session.beginTransaction();
	            session.persist(newUser);
	            session.getTransaction().commit();
	            logger.info("Account created with trn:" +newUser.getTrn());
	            
		        }catch (Exception e) 
		        {
		        	if (session != null)
		        	{
		        		session.getTransaction().rollback();
		        	}
		            logger.error("Error creating account: " + e.getMessage());
		        
		        }finally 
		        {
		            if (session != null) session.close(); // ensure session always closes
		        }
	}
		
    

}