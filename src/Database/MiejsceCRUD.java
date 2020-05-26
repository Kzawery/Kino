package Database;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.PersistenceException;


public class MiejsceCRUD {
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


    public boolean create(String rzad, int numer, boolean czyKanapa, SalaEntity idSali){
        final Session session = getSession();
        try {
            session.beginTransaction();
            MiejsceEntity miejsceEntity = new MiejsceEntity();
            miejsceEntity.setRzad(rzad);
            miejsceEntity.setNumerMiejsca(numer);
            miejsceEntity.setCzyKanapa(czyKanapa);
            miejsceEntity.setIdSali(idSali);
            session.save(miejsceEntity);
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
            MiejsceEntity miejsceEntity = session.get(MiejsceEntity.class, id1);
            session.delete(miejsceEntity);
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

    public void updateRzad(int id1, String rzad){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            MiejsceEntity miejsceEntity;
            miejsceEntity = session.byId(MiejsceEntity.class).getReference(id1);
            miejsceEntity.setRzad(rzad);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateNumerMiejsca(int id1, int numer){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            MiejsceEntity miejsceEntity;
            miejsceEntity = session.byId(MiejsceEntity.class).getReference(id1);
            miejsceEntity.setNumerMiejsca(numer);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateKanapa(int id1,boolean czyKanapa){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            MiejsceEntity miejsceEntity;
            miejsceEntity = session.byId(MiejsceEntity.class).getReference(id1);
            miejsceEntity.setCzyKanapa(czyKanapa);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void updateIdSali(int id1, SalaEntity idSali){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            MiejsceEntity miejsceEntity;
            miejsceEntity = session.byId(MiejsceEntity.class).getReference(id1);
            miejsceEntity.setIdSali(idSali);
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
            MiejsceEntity miejsceEntity;
            miejsceEntity = session.byId(MiejsceEntity.class).getReference(id1);
            System.out.println(miejsceEntity.toString());
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




