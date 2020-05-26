package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.sound.midi.SysexMessage;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class Repertuar extends JFrame implements ActionListener {
    JButton dodajFilm, dodajSeans, dodajPracownika, returnBtn, kupBilet;
    JTable table;
    Object[] seansObj;
    DefaultTableModel dtm, dtm2;
    ObservableList<SeansEntity> seansy;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    KlientKinaEntity klientKinaEntity;
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

    public Repertuar(KlientKinaEntity klientKinaEntity){
        this.klientKinaEntity = klientKinaEntity;
        setLayout(null);
        setResizable(false);
        setLocation(  (int) screenSize.getWidth()/2 - width/2, (int) screenSize.getHeight() / 2 -height/2);
        setSize(width, height);


        dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        dtm.setColumnIdentifiers(new Object[]{"Tytul", "opis", "Data i godzina", "Czas trwania" ,"cena"});
        table = new JTable();
        table.setModel(dtm);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        //System.out.println(now.format(dtf));

        String myquery = "from SeansEntity where czas >= '" + now.format(dtf) +"' order by czas";
        List lista = session.createQuery(myquery.toString()).list();
        seansy = FXCollections.observableArrayList(lista);
        for(SeansEntity seans : seansy){
            FilmEntity filmEntity = seans.getIdFilmu();
            seansObj = new Object[]{filmEntity.getTytul(), filmEntity.getOpis(), seans.getCzas().toString().substring(0,16), filmEntity.getCzasTrwania().toString().substring(0,5) ,seans.getCena()};
            dtm.addRow(seansObj);
        }

        JScrollPane pane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setBounds(0,0,width,300);
        add(pane);

        System.out.println(klientKinaEntity);

        kupBilet = new JButton("Kup Bilet");
        kupBilet.setBounds(width - 180, 320 ,160, 30);
        kupBilet .addActionListener(this);
        add(kupBilet);

        returnBtn = new JButton("Powrót do panelu klienta");
        returnBtn.setBounds(50, 320, 220, 30);
        returnBtn.addActionListener(this);
        add(returnBtn);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if(source == kupBilet) {
             int row = table.getSelectedRow();
             if(row != -1){
                 SeansEntity seansEntity = seansy.get(row);
                 System.out.println(seansEntity.getIdSeansu());
                 SeansView seansView = new SeansView(seansEntity,klientKinaEntity);
                 setVisible(false);
                 seansView.setVisible(true);
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
