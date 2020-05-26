package Database;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class ZajeteMiejscaCRUD {
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


    public void create(MiejsceEntity miejsceEntity, SeansEntity seansEntity){
        final Session session = getSession();
        try {
            session.beginTransaction();
            ZajeteMiejscaEntity zajeteMiejscaEntity = new ZajeteMiejscaEntity();
            zajeteMiejscaEntity.setIdMiejsca(miejsceEntity);
            zajeteMiejscaEntity.setIdSeansu(seansEntity);
            session.save(zajeteMiejscaEntity);
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
            session.delete(session.get(ZajeteMiejscaEntity.class,id1));
            transaction.commit();
            System.out.println("Deleted zajete miejsce Successfully");


        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateSeans(int id1, SeansEntity seansEntity){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            ZajeteMiejscaEntity zajeteMiejscaEntity;
            zajeteMiejscaEntity = session.byId(ZajeteMiejscaEntity.class).getReference(id1);
            zajeteMiejscaEntity.setIdSeansu(seansEntity);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    public void updateMiejsce(int id1, MiejsceEntity miejsceEntity){
        final Session session = getSession();
        try {
            session.beginTransaction();;
            ZajeteMiejscaEntity zajeteMiejscaEntity;
            zajeteMiejscaEntity = session.byId(ZajeteMiejscaEntity.class).getReference(id1);
            zajeteMiejscaEntity.setIdMiejsca(miejsceEntity);
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
            ZajeteMiejscaEntity zajeteMiejscaEntity;
            zajeteMiejscaEntity = session.byId(ZajeteMiejscaEntity.class).getReference(id1);
            System.out.println(zajeteMiejscaEntity.toString());
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




