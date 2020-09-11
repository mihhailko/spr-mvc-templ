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
            session.disconnect();
            session.close();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace(); 
        } finally { 
        }
        return helloID;
    }

    /* Method to SELECT*FROM */
   public List listStuff(){
       Session session = factory.openSession();
       Transaction tx = null;
       String response = new String();
       List messages;
      
       try {
           tx = session.beginTransaction();
           messages = session.createQuery("FROM springexample.HelloFromSpring").list(); 
           tx.commit();
           session.disconnect();
           session.close();
        
           return messages;   
       } catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           e.printStackTrace(); 
       }
       return null;
   }
}
