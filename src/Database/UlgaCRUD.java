package Database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.PersistenceException;
import java.text.ParseException;

public class UlgaCRUD {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();

        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }


    public void create(String ulga, double znizka, String opis){
        final Session session = getSession();
        try {
            session.beginTransaction();
            UlgaEntity ulgaEntity = new UlgaEntity();
            ulgaEntity.setIdUlgi(ulga);
            ulgaEntity.setZnizka(znizka);
            ulgaEntity.setOpis(opis);
            session.save(ulgaEntity);
            session.getTransaction().commit();
            System.out.println("Created Successfully");

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    public String delete(String id1){
        final Session session = getSession();
        try {
            Transaction transaction = session.beginTransaction();
            UlgaEntity ulgaEntity = session.get(UlgaEntity.class, id1);
            session.delete(ulgaEntity);
            try{
                transaction.commit();
            }
            catch (PersistenceException p){
                return p.toString();
            }
            return "Deleted Successfully";

        } catch (HibernateException e) {
            return e.toString();
        } finally {
            session.close();
        }
    }

    public boolean updateZnizka(String id1, double ulga){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            UlgaEntity ulgaEntity;
            ulgaEntity = session.byId(UlgaEntity.class).getReference(id1);
            ulgaEntity.setZnizka(ulga);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            return false;
        } finally {
            session.close();
        }
    }

    public boolean updateOpis(String id1, String opis){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            UlgaEntity ulgaEntity;
            ulgaEntity = session.byId(UlgaEntity.class).getReference(id1);
            ulgaEntity.setOpis(opis);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            return false;
        } finally {
            session.close();
        }
    }
    public void updateidUlgi(String id1, String id){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            UlgaEntity ulgaEntity;
            ulgaEntity = session.byId(UlgaEntity.class).getReference(id1);
            ulgaEntity.setIdUlgi(id);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void read(int id1){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            UlgaEntity ulgaEntity;
            ulgaEntity = session.byId(UlgaEntity.class).getReference(id1);
            System.out.println(ulgaEntity.toString());
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void main(String[] args) throws ParseException {
        SeansCRUD seansCRUD = new SeansCRUD();
        //seansCRUD.create(12.99F ,new Timestamp(119,12,12,12,30,0,0));
        //seansCRUD.updateCzas(1,new Timestamp(119,12,12,12,30,0,0));
        //seansCRUD.updateidSali(1, 1);
        //seansCRUD.read(1);

        //seansCRUD.create(12.12,);
        //sala.update3D(0,false);
        //sala.read(0);

    }

}
