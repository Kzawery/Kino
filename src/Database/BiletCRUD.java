package Database;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.beans.Transient;


public class BiletCRUD {
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


    public void create(ZamowienieEntity zamowienie, Database.MiejsceEntity miejsce, UlgaEntity ulga, SeansEntity seans){
        final Session session = getSession();
        try {
            session.beginTransaction();
            BiletEntity biletEntity = new BiletEntity();
            biletEntity.setIdZamownia(zamowienie);
            biletEntity.setIdMiejsca(miejsce);
            biletEntity.setUlga(ulga);
            biletEntity.setIdSeansu(seans);
            session.save(biletEntity);
            session.getTransaction().commit();
            System.out.println("Created Successfully");

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    public void delete(int id1){
        final Session session = getSession();
        try {
            Transaction transaction = session.beginTransaction();
            //TODO
            session.delete(session.get(BiletEntity.class,id1));
            transaction.commit();
            System.out.println("Deleted bilet Successfully");

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateUlga(int id1, UlgaEntity ulga){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            BiletEntity biletEntity;
            biletEntity = session.byId(BiletEntity.class).getReference(id1);
            biletEntity.setUlga(ulga);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void updateMiejsce(int id1, MiejsceEntity miejsce){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            BiletEntity biletEntity;
            biletEntity = session.byId(BiletEntity.class).getReference(id1);
            biletEntity.setIdMiejsca(miejsce);
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
            BiletEntity biletEntity;
            biletEntity = session.byId(BiletEntity.class).getReference(id1);
            System.out.println(biletEntity.toString());
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void main(String[] args)
    {
        BiletCRUD biletCRUD = new BiletCRUD();
        biletCRUD.delete(1);
    }

}




