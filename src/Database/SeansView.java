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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SeansView extends JFrame implements ActionListener {
    JButton dodajFilm, dodajSeans, dodajPracownika, returnBtn, zarezerwujMiejsce;
    JTable table;
    Object[] seansObj;
    DefaultTableModel dtm, dtm2;
    ObservableList<SeansEntity> seansy;
    ObservableList<MiejsceEntity> miejsca;
    ObservableList<ZajeteMiejscaEntity> zajeteMiejsca;
    KlientKinaEntity klientKinaEntity;
    SeansEntity seansEntity;
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

    public SeansView(SeansEntity seansEntity, KlientKinaEntity klientKinaEntity){
        this.klientKinaEntity = klientKinaEntity;
        this.seansEntity = seansEntity;
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
        dtm.setColumnIdentifiers(new Object[]{"Rzad", "Numer" ,"Czy kanapa" ,"Czy zajete"});
        table = new JTable();
        table.setModel(dtm);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        SalaEntity salaEntity = seansEntity.getIdSali();

        String myquery2 ="from MiejsceEntity where idSali="+salaEntity.getIdSali();
        List lista2 = session.createQuery(myquery2.toString()).list(); //lista miejsc na tej sali
        miejsca = FXCollections.observableArrayList(lista2);

        String myquery3 ="from ZajeteMiejscaEntity where idSeansu="+seansEntity.getIdSeansu();
        List lista3 = session.createQuery(myquery3.toString()).list(); //lista zajetych miejsc na tym seansie
        zajeteMiejsca = FXCollections.observableArrayList(lista3);

        for(MiejsceEntity miejsce : miejsca){
            boolean czyZajete = false;

            for(ZajeteMiejscaEntity zajeteMiejsce : zajeteMiejsca){

                if(zajeteMiejsce.getIdMiejsca().getIdMiejsca() == miejsce.getIdMiejsca() && zajeteMiejsce.getIdSeansu().equals(seansEntity));
                {
                        //System.out.println(zajeteMiejsce.getIdMiejsca().getIdMiejsca());
                        //System.out.println(miejsce.getIdMiejsca());
                        if(zajeteMiejsce.getIdMiejsca().getIdMiejsca() == miejsce.getIdMiejsca()){
                        //System.out.println(miejsce.toString());
                        //System.out.println(zajeteMiejsce);
                        //System.out.println(zajeteMiejsce.getIdSeansu().getIdSeansu());
                        czyZajete = true;
                    }
                }
            }
            seansObj = new Object[]{miejsce.getRzad(), miejsce.getNumerMiejsca(), boolToStr(miejsce.getCzyKanapa()), boolToStr(czyZajete) ,miejsce};
            dtm.addRow(seansObj);
        }

        JScrollPane pane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setBounds(0,0,width,300);
        add(pane);



        zarezerwujMiejsce = new JButton("Zarezerwuj miejsce");
        zarezerwujMiejsce.setBounds(width - 180, 320 ,160, 30);
        zarezerwujMiejsce.addActionListener(this);
        add(zarezerwujMiejsce);

        returnBtn = new JButton("Powrót do panelu klienta");
        returnBtn.setBounds(50, 320, 200, 30);
        returnBtn.addActionListener(this);
        add(returnBtn);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if(source == zarezerwujMiejsce) {
            int row = table.getSelectedRow();
            if(row != -1 && dtm.getValueAt(row,3) != "tak"){
                BiletZakup biletZakup = new BiletZakup(miejsca.get(row), seansEntity, this.klientKinaEntity);
                setVisible(false);
                biletZakup.setVisible(true);
            }
        }
        if(source == returnBtn){
            System.out.println(klientKinaEntity);
            KlientPanel klientPanel = new KlientPanel(klientKinaEntity);
            setVisible(false);
            klientPanel.setVisible(true);
        }

    }
    public String boolToStr(boolean bool){
        if(bool) return "tak";
        else return "nie";
    }
}
