package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BiletZakup extends JFrame implements ActionListener {
    JButton dodajBilet, returnBtn;
    int kanapa =1;
    ObservableList<UlgaEntity> ulgi;
    MiejsceEntity miejsceEntity;
    SeansEntity seansEntity;
    KlientKinaEntity klientKinaEntity;
    UlgaEntity ulgaEntity;
    JComboBox ulgiDropDown;
    JLabel info2;
    double znizka = 0.0;
    int seansId;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = 900;
    int height = 400;

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
    final Session session = getSession();

    public BiletZakup(MiejsceEntity miejsceEntity, SeansEntity seansEntity, KlientKinaEntity klientKinaEntity){
        this.miejsceEntity = miejsceEntity;
        this.seansEntity = seansEntity;
        this.klientKinaEntity = klientKinaEntity;
        if(miejsceEntity.getCzyKanapa()){
            kanapa =2;
        }
        setLayout(null);
        setResizable(false);
        setLocation((int) screenSize.getWidth()/2 - width/2, (int) screenSize.getHeight() / 2 -height/2);
        setSize(width, height);

        System.out.println(miejsceEntity);
        System.out.println(seansEntity);
        System.out.println(this.klientKinaEntity);

        JLabel info = new JLabel("Kupujesz bilet na film: " + seansEntity.getIdFilmu().getTytul() );
        info2 = new JLabel("Cena pełnego biletu wynosi: " + String.format("%.2f", seansEntity.getCena() * (1 - znizka) * kanapa));
        JLabel info3 = new JLabel("Jeżeli przysługuje ci znizka wybierz odpowiednie pole z listy:");
        info.setBounds(10,10,width, 30);
        info2.setBounds(10,40,width, 30);
        info3.setBounds(10,70,width, 30);
        add(info);
        add(info2);
        add(info3);

        String myquery ="from UlgaEntity";
        List lista2 = session.createQuery(myquery).list(); //lista miejsc na tej sali
        ulgi = FXCollections.observableArrayList(lista2);

        ArrayList<String> ulgiString = new ArrayList<>();

        for(UlgaEntity ulgaEntity : ulgi){
            ulgiString.add(ulgaEntity.getOpis());
        }

        ulgiDropDown = new JComboBox(ulgiString.toArray());
        ulgiDropDown.setSelectedIndex(0);
        ulgiDropDown.addActionListener(this);
        ulgiDropDown.setBounds(10, 110, width/3,30);
        add(ulgiDropDown);

        dodajBilet = new JButton("Dodaj bilet");
        dodajBilet.setBounds(width - 180, 320 ,160, 30);
        dodajBilet.addActionListener(this);
        add(dodajBilet);
        this.ulgaEntity = ulgi.get(0);

        returnBtn = new JButton("Powrót do panelu klienta");
        returnBtn.setBounds(50, 320, 160, 30);
        returnBtn.addActionListener(this);
        add(returnBtn);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if(source == dodajBilet) {
            BiletCRUD biletCRUD = new BiletCRUD();
            //trzeba dodac czy zamowienie jest zakonczone
            String myquery2 ="from ZamowienieEntity where Zakonczone=false and idKlienta="+this.klientKinaEntity.getIdKlienta();
            List lista2 = session.createQuery(myquery2.toString()).list();
            if(lista2.size() == 0){
                ZamowienieCRUD zamowienieCRUD = new ZamowienieCRUD();
                zamowienieCRUD.create(klientKinaEntity,false);
                lista2 = session.createQuery(myquery2.toString()).list();
            }
            ObservableList<ZamowienieEntity> zamowienieEntities = FXCollections.observableArrayList(lista2);
            ZamowienieEntity zamowienieEntity = zamowienieEntities.get(0);
            biletCRUD.create(zamowienieEntity,miejsceEntity,this.ulgaEntity, this.seansEntity);
            ZajeteMiejscaCRUD zajeteMiejscaCRUD = new ZajeteMiejscaCRUD();
            zajeteMiejscaCRUD.create(miejsceEntity, seansEntity);
            KoszykView koszykView = new KoszykView(klientKinaEntity);
            setVisible(false);
            koszykView.setVisible(true);
        }
        if(source == ulgiDropDown){
            for(UlgaEntity ulgaEntity : ulgi){
                if(ulgaEntity.getOpis() == ulgiDropDown.getSelectedItem()) {
                    this.ulgaEntity = ulgaEntity;
                    znizka = ulgaEntity.getZnizka();
                    info2.setText("Cena pełnego biletu wynosi: " + String.format("%.2f", seansEntity.getCena() * (1 - znizka) * kanapa));
                }
            }
        }
        if(source == returnBtn){
            System.out.println(klientKinaEntity);
            KlientPanel klientPanel = new KlientPanel(klientKinaEntity);
            setVisible(false);
            klientPanel.setVisible(true);
        }

    }

}
