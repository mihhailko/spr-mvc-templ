package springexample;
import java.util.List; 
import java.util.Date;
import java.time.LocalDateTime;
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

    public void closeSessionFactory() { 
        
        this.factory.close();
    }

     
    /* CREATE session */


    // polymorphic methods

    public Integer insertWhatever(IPersistence stuff){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer response = null;

        try {
            tx = session.beginTransaction();
            response = (Integer) session.save(stuff);
            tx.commit();
            session.disconnect();
            session.close();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace(); 
        } finally { 
        }
        return response;
    }

    public List<IPersistence> queryWhatever(IPersistence stuff, String query){
        Session session = factory.openSession();
        Transaction tx = null;
        String response = new String();
        List messages;
      
        try {
            tx = session.beginTransaction();
            messages = session.createQuery(query).list(); 
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

    public String deleteWhatever(IPersistence data){
        Session session = factory.openSession();
        Transaction tx = null;
        String response = null;
        
        try {
            tx = session.beginTransaction();
            session.delete(data);
            tx.commit();
            session.disconnect();
            session.close();
            response = "deleted";
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace(); 
        } finally { 
        }
        return response;
    }
    
}
