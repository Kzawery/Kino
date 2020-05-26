    package Database;
    import org.hibernate.HibernateException;
    import org.hibernate.Session;
    import org.hibernate.SessionFactory;
    import org.hibernate.Transaction;
    import org.hibernate.cfg.Configuration;

    import javax.persistence.PersistenceException;
    import java.sql.Date;
    import java.sql.Time;

public class FilmCRUD{
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


    public void create(String tytul,Date rokProdukcji,String rezyser, String gatunek,Time czasTrwania,String opis){
        final Session session = getSession();
        try {
            session.beginTransaction();
            FilmEntity filmEntity = new FilmEntity();
            filmEntity.setTytul(tytul);
            filmEntity.setRokProdukcji(rokProdukcji);
            filmEntity.setRezyser(rezyser);
            filmEntity.setGatunek(gatunek);
            filmEntity.setCzasTrwania(czasTrwania);
            filmEntity.setOpis(opis);
            session.save(filmEntity);
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
            FilmEntity filmEntity = session.get(FilmEntity.class, id1);
            session.delete(filmEntity);
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


    public void  updateRokProdukcji(int id1, Date rokProdukcji){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            FilmEntity filmEntity;
            filmEntity = session.byId(FilmEntity.class).getReference(id1);
            filmEntity.setRokProdukcji(rokProdukcji);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void  updateTytul(int id1, String tytul){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            FilmEntity filmEntity;
            filmEntity = session.byId(FilmEntity.class).getReference(id1);
            filmEntity.setTytul(tytul);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void  updateGatunek(int id1, String gatunek){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            FilmEntity filmEntity;
            filmEntity = session.byId(FilmEntity.class).getReference(id1);
            filmEntity.setGatunek(gatunek);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void  updateRezyser(int id1, String rezyser){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            FilmEntity filmEntity;
            filmEntity = session.byId(FilmEntity.class).getReference(id1);
            filmEntity.setRezyser(rezyser);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void  updateCzasTrwania(int id1, Time czas){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            FilmEntity filmEntity;
            filmEntity = session.byId(FilmEntity.class).getReference(id1);
            filmEntity.setCzasTrwania(czas);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateOpis(int id1, String opis){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            FilmEntity filmEntity;
            filmEntity = session.byId(FilmEntity.class).getReference(id1);
            filmEntity.setOpis(opis);
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
            FilmEntity filmEntity;
            filmEntity = session.byId(FilmEntity.class).getReference(id1);
            System.out.println(filmEntity.toString());
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void main(String[] args)
    {
        FilmCRUD nowy = new FilmCRUD();
        //Date help = new Date(2015-12-06);
        //Time help2 = new Time(2,30,0);
        //nowy.create("Robert historia prawdziwa",help,"Robert Wenkser","Dokument",help2,"ajgfkadgd");
        //nowy.updateOpis(1,"Nowy opis");
        nowy.read(1);
        //    nowy.saveCar("Benz","Black",new Date(2015-12-06), 20500.5,"Klasa C");
        //nowy.UpdateAdressStreet(3,"Fresh");
        // nowy.UpdateAdressCity(3,"Gdynia");
        // nowy.UpdateAdressPostCode(3,"99-999");
        // nowy.UpdateAdressHouse(3,"Domeczek");
    }

}




