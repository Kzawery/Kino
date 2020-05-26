package Database;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.swing.*;


public class ZamowienieCRUD extends JFrame {
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


    public void create(KlientKinaEntity idKlienta, boolean zakonczone){
        final Session session = getSession();
        try {
            session.beginTransaction();
            ZamowienieEntity zamowienieEntity = new ZamowienieEntity();
            zamowienieEntity.setIdKlienta(idKlienta);
            zamowienieEntity.setZakonczone(zakonczone);
            session.save(zamowienieEntity);
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
            ZamowienieEntity zamowienieEntity = session.get(ZamowienieEntity.class, id1);
            session.delete(zamowienieEntity);
            System.out.println("Deleted Successfully");
            transaction.commit();

        } catch (HibernateException e) {
            JOptionPane.showMessageDialog(this,e,"Warning",JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateZakonczone(int id1, boolean zakonczone){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            ZamowienieEntity zamowienieEntity;
            zamowienieEntity = session.byId(ZamowienieEntity.class).getReference(id1);
            zamowienieEntity.setZakonczone(zakonczone);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateIdKlienta(int id1, KlientKinaEntity idklienta){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            ZamowienieEntity zamowienieEntity;
            zamowienieEntity = session.byId(ZamowienieEntity.class).getReference(id1);
            zamowienieEntity.setIdKlienta(idklienta);
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
            ZamowienieEntity zamowienieEntity;
            zamowienieEntity = session.byId(ZamowienieEntity.class).getReference(id1);
            System.out.println(zamowienieEntity.toString());
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void main(String[] args)
    {
        ZamowienieEntity zamowienieEntity = new ZamowienieEntity();
        ZamowienieCRUD zamowienieCRUD = new ZamowienieCRUD();
        KlientKinaEntity klientKinaEntity = getSession().get(KlientKinaEntity.class,1);
        zamowienieCRUD.create(klientKinaEntity,false);
        //zamowienieCRUD.delete(5);
    }

}




