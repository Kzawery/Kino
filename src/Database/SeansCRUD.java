package Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.PersistenceException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

public class SeansCRUD {
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


    public boolean create(float cena, SalaEntity idSali, FilmEntity idFilmu, Timestamp czas){
        final Session session = getSession();
        try {
            session.beginTransaction();
            SeansEntity seansEntity = new SeansEntity();
            seansEntity.setCena(cena);
            seansEntity.setCzas(czas);
            seansEntity.setIdSali(idSali);
            seansEntity.setIdFilmu(idFilmu);
            session.save(seansEntity);
            session.getTransaction().commit();
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
            SeansEntity seansEntity = session.get(SeansEntity.class, id1);
            session.delete(seansEntity);
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

    public void updateCzas(int id1, Timestamp czas){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            SeansEntity seansEntity;
            seansEntity = session.byId(SeansEntity.class).getReference(id1);
            seansEntity.setCzas(czas);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateCena(int id1, Float cena){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            SeansEntity seansEntity;
            seansEntity = session.byId(SeansEntity.class).getReference(id1);
            seansEntity.setCena(cena);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void updateidSali(int id1, SalaEntity idSali){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            SeansEntity seansEntity;
            seansEntity = session.byId(SeansEntity.class).getReference(id1);
            seansEntity.setIdSali(idSali);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void updateidFilmu(int id1, FilmEntity idFilmu){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            SeansEntity seansEntity;
            seansEntity = session.byId(SeansEntity.class).getReference(id1);
            seansEntity.setIdFilmu(idFilmu);
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
            SeansEntity seansEntity;
            seansEntity = session.byId(SeansEntity.class).getReference(id1);
            System.out.println(seansEntity.toString());
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void main(String[] args) throws ParseException {
        final Session session = getSession();
        SeansCRUD seansCRUD = new SeansCRUD();
        //seansCRUD.create(12.99F ,new Timestamp(119,12,12,12,30,0,0));
        // seansCRUD.updateCzas(1,new Timestamp(119,11,12,12,30,0,0));

        String myquery = " from SalaEntity where idSali='0'";
        List lista = session.createQuery(myquery.toString()).list();
        ObservableList<SalaEntity> salaEntities = FXCollections.observableArrayList(lista);
        SalaEntity salaEntity = salaEntities.get(0);

        String myquery2 = " from FilmEntity where idFilmu='2'";
        List lista2 = session.createQuery(myquery2.toString()).list();
        ObservableList<FilmEntity> filmEntities = FXCollections.observableArrayList(lista2);
        FilmEntity filmEntity = filmEntities.get(0);

        //seansCRUD.create(20.00F,salaEntity,filmEntity,new Timestamp(119,12,12,12,30,0,0));
        //seansCRUD.updateidFilmu(3,filmEntity);
        seansCRUD.updateidSali(3,salaEntity);

//        seansCRUD.updateidSali(1, konto);

//        String myquery = " from FilmEntity where idFilmu='1'";
//        List lista = session.createQuery(myquery.toString()).list();
//        ObservableList<FilmEntity> konta = FXCollections.observableArrayList(lista);
//        FilmEntity konto = konta.get(0);
//
//        seansCRUD.updateidFilmu(1, konto);
//
//
//        seansCRUD.read(1);

        //seansCRUD.create(12.12,);
        //sala.update3D(0,false);
        //sala.read(0);

    }

}




