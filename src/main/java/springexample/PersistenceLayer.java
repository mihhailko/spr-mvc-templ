package springexample;
import java.util.List; 
import java.util.Date;
import java.util.Iterator;
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class PersistenceLayer {
    private static SessionFactory factory;

    public void initSession(){
         try {
             factory = new Configuration().configure().buildSessionFactory();
         } catch (Throwable ex) { 
             System.err.println("Failed to create sessionFactory object." + ex);
             throw new ExceptionInInitializerError(ex); 
         }
    }
    
    /* CREATE message, stuff*/
    
    public Integer addStuff(HelloFromSpring data){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer helloID = null;
        
        try {
            tx = session.beginTransaction();
            helloID = (Integer) session.save(data); 
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace(); 
        } finally {
            session.close(); 
        }
        return helloID;
    }

    /* Method to SELECT*FROM */
   public String listEmployees( ){
       Session session = factory.openSession();
       Transaction tx = null;
       String response = new String();
      
       try {
           tx = session.beginTransaction();
           List messages = session.createQuery("FROM HELLOFROMSPRING").list(); 
           for (Iterator iterator = messages.iterator(); iterator.hasNext();){
               HelloFromSpring hello = (HelloFromSpring) iterator.next(); 
               response += new String("Message: " + hello.getMessage()); 
               response += new String("  Stuff: " + hello.getStuff()); 
           }
           tx.commit();
       } catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           e.printStackTrace(); 
       } finally {
           session.close();
           return response;
       }
   }
}
