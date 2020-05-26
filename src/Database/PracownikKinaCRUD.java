package Database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.PersistenceException;
import java.text.ParseException;

public class PracownikKinaCRUD {
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


    public boolean create(String email, String imie, String nazwisko, String sha1, String rola){
        final Session session = getSession();
        try {
            session.beginTransaction();
            PracownikKinaEntity pracownikKinaEntity  = new PracownikKinaEntity();
            pracownikKinaEntity.setEmail(email);
            //pracownikKinaEntity.setIdPracownika();
            pracownikKinaEntity.setImie(imie);
            pracownikKinaEntity.setNazwisko(nazwisko);
            pracownikKinaEntity.setRola(rola);
            pracownikKinaEntity.setSha1(sha1);
            session.save(pracownikKinaEntity);
            session.getTransaction().commit();
            System.out.println("Created Successfully");
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }


    public String delete(int id1){
        final Session session = getSession();
        try {
            Transaction transaction = session.beginTransaction();
            PracownikKinaEntity pracownikKinaEntity = session.get(PracownikKinaEntity.class, id1);
            session.delete(pracownikKinaEntity);
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

    public void updateEmail(int id1, String email){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            PracownikKinaEntity  pracownikKinaEntity;
            pracownikKinaEntity = session.byId(PracownikKinaEntity.class).getReference(id1);
            pracownikKinaEntity.setEmail(email);
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
            PracownikKinaEntity  pracownikKinaEntity;
            pracownikKinaEntity = session.byId(PracownikKinaEntity.class).getReference(id1);
            pracownikKinaEntity.setImie(imie);
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
            PracownikKinaEntity  pracownikKinaEntity;
            pracownikKinaEntity = session.byId(PracownikKinaEntity.class).getReference(id1);
            pracownikKinaEntity.setNazwisko(nazwisko);
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
            PracownikKinaEntity  pracownikKinaEntity;
            pracownikKinaEntity = session.byId(PracownikKinaEntity.class).getReference(id1);
            pracownikKinaEntity.setSha1(sha1);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateRola(int id1, String rola){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            PracownikKinaEntity  pracownikKinaEntity;
            pracownikKinaEntity = session.byId(PracownikKinaEntity.class).getReference(id1);
            pracownikKinaEntity.setRola(rola);
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
            PracownikKinaEntity pracownikKinaEntity;
            pracownikKinaEntity = session.byId(PracownikKinaEntity.class).getReference(id1);
            System.out.println(pracownikKinaEntity.toString());
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
        seansCRUD.read(1);

        //seansCRUD.create(12.12,);
        //sala.update3D(0,false);
        //sala.read(0);

    }

}
