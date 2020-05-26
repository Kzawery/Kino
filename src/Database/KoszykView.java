package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class KoszykView extends JFrame implements ActionListener {
    JButton payBtn, delBtn, returnBtn;
    int kanapa = 1;
    JTable table;
    Object[] seansObj;
    DefaultTableModel dtm;
    ObservableList<UlgaEntity> ulgi;
    ObservableList<ZamowienieEntity> zamowienia;
    ObservableList<BiletEntity> bilety;
    MiejsceEntity miejsceEntity;
    SeansEntity seansEntity;
    KlientKinaEntity klientKinaEntity;
    UlgaEntity ulgaEntity;
    JComboBox ulgiDropDown;
    ZamowienieEntity zamowienie;
    JLabel info2;
    Double znizka;
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

    public KoszykView(KlientKinaEntity klientKinaEntity) {
        this.klientKinaEntity = klientKinaEntity;
        setLayout(null);
        setResizable(false);
        setLocation((int) screenSize.getWidth() / 2 - width / 2, (int) screenSize.getHeight() / 2 - height / 2);
        setSize(width, height);

        System.out.println(klientKinaEntity);

        dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        dtm.setColumnIdentifiers(new Object[]{"Film", "Data", "Sala", "Rzad", "Numer miejsca", "Ulga", "Cena"});


        String myquery = "from ZamowienieEntity where Zakonczone = false and idKlienta=" + klientKinaEntity.getIdKlienta();
        List lista = session.createQuery(myquery.toString()).list();
        zamowienia = FXCollections.observableArrayList(lista);

        if (zamowienia.size() != 0) {
            zamowienie = zamowienia.get(0);
            String myquery2 = "from BiletEntity where idZamownia=" + zamowienie.getIdZamowienia();
            List lista2 = session.createQuery(myquery2.toString()).list();
            bilety = FXCollections.observableArrayList(lista2);
            if (bilety.size() != 0) {

                for (int i = 0; i < bilety.size(); i++) {
                    BiletEntity bilet = bilety.get(i);
                    SeansEntity seans = bilet.getIdSeansu();
                    FilmEntity film = seans.getIdFilmu();
                    SalaEntity sala = seans.getIdSali();
                    MiejsceEntity miejsce = bilet.getIdMiejsca();

                    System.out.println(seans);
                    System.out.println(miejsce);
                    //String myquery3 = "from UlgaEntity where idUlgi =" + bilet.getUlga().getIdUlgi();
                    //List lista3 = session.createQuery(myquery3.toString()).list();
                    UlgaEntity ulgaEntity = session.get(UlgaEntity.class, bilet.getUlga().getIdUlgi());
                    znizka = ulgaEntity.getZnizka();

                    seansObj = new Object[]{film.getTytul(), seans.getCzas(), sala.getNumerSali(), miejsce.getRzad(), miejsce.getNumerMiejsca(), ulgaEntity.getOpis(), String.format("%.2f", seans.getCena() * (1 - znizka) * ( miejsce.getCzyKanapa()?2:1) ) };
                    dtm.addRow(seansObj);
                }

            }
        }

        table = new JTable();
        table.setModel(dtm);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane pane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setBounds(0,0,width,300);
        add(pane);

        payBtn = new JButton("Zapłać");
        payBtn.setBounds(width - 180, 320, 160, 30);
        payBtn.addActionListener(this);
        add(payBtn);

        returnBtn = new JButton("Powrót do panelu klienta");
        returnBtn.setBounds(50, 320, 220, 30);
        returnBtn.addActionListener(this);
        add(returnBtn);

        delBtn = new JButton("Usuń bilet");
        delBtn.setBounds(250, 370, 160, 30);
        delBtn.addActionListener(this);
        add(delBtn);


    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source == payBtn) {
            //System.out.println(zamowienie);
            //zamowienie.setZakonczone(true);
            //.out.println(zamowienie);
            ZamowienieCRUD zamowienieCRUD = new ZamowienieCRUD();
            zamowienieCRUD.updateZakonczone(zamowienie.getIdZamowienia(),true);
            KlientPanel klientPanel = new KlientPanel(klientKinaEntity);
            setVisible(false);
            klientPanel.setVisible(true);
        }

        if(source == returnBtn){
            System.out.println(klientKinaEntity);
            KlientPanel klientPanel = new KlientPanel(klientKinaEntity);
            setVisible(false);
            klientPanel.setVisible(true);
        }

        if(source == delBtn){
            int row = table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(this,"Wybierz bilet do usuniecia","Warning",JOptionPane.WARNING_MESSAGE);
            }
            else {
                BiletEntity delBilet = bilety.get(row);
                System.out.println(delBilet);
                String myquery3 = "from ZajeteMiejscaEntity where idSeansu =" + delBilet.getIdSeansu().getIdSeansu() + " and idMiejsca="  + delBilet.getIdMiejsca().getIdMiejsca();
                List lista3 = session.createQuery(myquery3.toString()).list();
                if(lista3.size() != 0) {
                    ObservableList<ZajeteMiejscaEntity> zajeteMiejscaEntities = FXCollections.observableArrayList(lista3);
                    ZajeteMiejscaEntity zajeteMiejscaEntity = zajeteMiejscaEntities.get(0);
                    ZajeteMiejscaCRUD zajeteMiejscaCRUD = new ZajeteMiejscaCRUD();
                    System.out.println(zajeteMiejscaEntity.getId());
                    zajeteMiejscaCRUD.delete(zajeteMiejscaEntity.getId());
                    //session.get(ZajeteMiejscaEntity.class, zajeteMiejscaEntity.getId());
                    System.out.println(delBilet.getIdBiletu());
                    BiletCRUD biletCRUD = new BiletCRUD();
                    biletCRUD.delete(delBilet.getIdBiletu());

                    this.setVisible(false);
                    KoszykView koszykView = new KoszykView(klientKinaEntity);
                    koszykView.setVisible(true);
                }
        }
        }

    }
}
