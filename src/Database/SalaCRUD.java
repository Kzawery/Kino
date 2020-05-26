package Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.PersistenceException;
import java.util.List;


public class SalaCRUD {
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


    public boolean create(boolean ulatwienia, boolean is3D, int numer_sali){
        final Session session = getSession();
        try {
            session.beginTransaction();

            String myquery = "from SalaEntity order by idSali desc";
            List lista = session.createQuery(myquery).list();
            ObservableList<SalaEntity> salaEntities = FXCollections.observableArrayList(lista);
            SalaEntity salaEntity1 = salaEntities.get(0);
            SalaEntity salaEntity = new SalaEntity();
            salaEntity.setIdSali(salaEntity1.getIdSali() +1);
            salaEntity.setMozliwosc3D(is3D);
            salaEntity.setUłatwieniaDlaNiepełnosprawnych(ulatwienia);
            salaEntity.setNumerSali(numer_sali);
            session.save(salaEntity);
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
            SalaEntity salaEntity = session.get(SalaEntity.class, id1);
            session.delete(salaEntity);
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

    public void updateUlatwienia(int id1, boolean ulatwienia){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            SalaEntity salaEntity;
            salaEntity = session.byId(SalaEntity.class).getReference(id1);
            salaEntity.setUłatwieniaDlaNiepełnosprawnych(ulatwienia);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void update3D(int id1, boolean is3D){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            SalaEntity salaEntity;
            salaEntity = session.byId(SalaEntity.class).getReference(id1);
            salaEntity.setMozliwosc3D(is3D);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void updateNumerSali(int id1, int numer){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            SalaEntity salaEntity;
            salaEntity = session.byId(SalaEntity.class).getReference(id1);
            salaEntity.setNumerSali(numer);
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
            SalaEntity salaEntity;
            salaEntity = session.byId(SalaEntity.class).getReference(id1);
            System.out.println(salaEntity.toString());
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void main(String[] args)
    {
        SalaCRUD sala = new SalaCRUD();
        //sala.create(true,true,1);
        sala.updateNumerSali(0,1);
        sala.update3D(0,false);
        sala.read(0);

    }

}




