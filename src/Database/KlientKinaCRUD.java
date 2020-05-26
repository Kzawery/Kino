package Database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.PersistenceException;
import javax.swing.*;
import java.text.ParseException;

public class KlientKinaCRUD extends JFrame {
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


    public void create(String email, String imie, String nazwisko, String sha1){
        final Session session = getSession();
        try {
            session.beginTransaction();
            KlientKinaEntity klientKinaEntity  = new KlientKinaEntity();
            klientKinaEntity.setEmail(email);
            //pracownikKinaEntity.setIdPracownika();
            klientKinaEntity.setImie(imie);
            klientKinaEntity.setNazwisko(nazwisko);

            klientKinaEntity.setSha1(sha1);
            session.save(klientKinaEntity);
            session.getTransaction().commit();
            System.out.println("Created Successfully");

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    public String delete(int id1){
        final Session session = getSession();
        try {
            Transaction transaction = session.beginTransaction();
            KlientKinaEntity klientKinaEntity = session.get(KlientKinaEntity.class, id1);
            session.delete(klientKinaEntity);
            try{
                transaction.commit();
            }
            catch (PersistenceException p){
                return p.toString();
            }
            return "Deleted Successfully";

        } catch (HibernateException e) {
            //JOptionPane.showMessageDialog(this,e,"Warning",JOptionPane.WARNING_MESSAGE);
            //e.printStackTrace();
            return e.toString();
        } finally {
            session.close();
        }
    }

    public void updateEmail(int id1, String email){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            KlientKinaEntity klientKinaEntity;
            klientKinaEntity = session.byId(KlientKinaEntity.class).getReference(id1);
            klientKinaEntity.setEmail(email);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateImie(int id1, String imie){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            KlientKinaEntity  klientKinaEntity;
            klientKinaEntity = session.byId(KlientKinaEntity.class).getReference(id1);
            klientKinaEntity.setImie(imie);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateNazwisko(int id1, String nazwisko){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            KlientKinaEntity klientKinaEntity;
            klientKinaEntity = session.byId(KlientKinaEntity.class).getReference(id1);
            klientKinaEntity.setNazwisko(nazwisko);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateSha1(int id1, String sha1){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            KlientKinaEntity klientKinaEntity;
            klientKinaEntity = session.byId(KlientKinaEntity.class).getReference(id1);
            klientKinaEntity.setSha1(sha1);
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
            KlientKinaEntity klientKinaEntity;
            klientKinaEntity = session.byId(KlientKinaEntity.class).getReference(id1);
            System.out.println(klientKinaEntity.toString());
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void main(String[] args) throws ParseException {
        KlientKinaCRUD klientKinaCRUD = new KlientKinaCRUD();
        klientKinaCRUD.delete(1);

    }

}
