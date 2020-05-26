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
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class UsunUpdatujSeans extends JFrame implements ActionListener {
    PracownikKinaEntity pracownikKinaEntity;
    AdministratorEntity administratorEntity;
    SeansCRUD seansCRUD = new SeansCRUD();
    JButton returnBtn, delBtn, updateBtn, passwordReset;
    JTable table;
    Object[] seansObj;
    DefaultTableModel dtm;
    ObservableList<SeansEntity> seansy;
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

    public UsunUpdatujSeans(AdministratorEntity administratorEntity) {
        this.administratorEntity = administratorEntity;
        build();
    }
    public UsunUpdatujSeans(PracownikKinaEntity pracownikKinaEntity) {
        this.pracownikKinaEntity = pracownikKinaEntity;
        build();
    }

    public void build() {
        setLayout(null);
        setResizable(false);
        setLocation((int) screenSize.getWidth() / 2 - width / 2, (int) screenSize.getHeight() / 2 - height / 2);
        setSize(width, height);


        dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }

        };
        dtm.setColumnIdentifiers(new Object[]{"Tytul","Czas", "Cena","Numer Sali"});
        table = new JTable();
        table.setModel(dtm);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        String myquery = "from SeansEntity";
        List lista = session.createQuery(myquery).list();
        seansy = FXCollections.observableArrayList(lista);
        for (SeansEntity seans : seansy) {
            FilmEntity filmEntity = seans.getIdFilmu();
            SalaEntity salaEntity = seans.getIdSali();
            seansObj = new Object[]{filmEntity.getTytul(), seans.getCzas(), seans.getCena(), salaEntity.getNumerSali()};
            dtm.addRow(seansObj);
        }

        JScrollPane pane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setBounds(0, 0, width, 300);
        add(pane);


        delBtn = new JButton("Usun rekord");
        delBtn.setBounds(width - 200, 320, 160, 30);
        delBtn.addActionListener(this);
        add(delBtn);

        updateBtn = new JButton("Update rekord");
        updateBtn.setBounds(width - 400, 320, 160, 30);
        updateBtn.addActionListener(this);
        add(updateBtn);

        returnBtn = new JButton("Powrót");
        returnBtn.setBounds(50, 320, 160, 30);
        returnBtn.addActionListener(this);
        add(returnBtn);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if(source == delBtn) {
            int row = table.getSelectedRow();
            SeansEntity seansEntity = seansy.get(row);
            seansCRUD.delete(seansEntity.getIdSeansu());
            JOptionPane.showMessageDialog(this, "Nie można usunąć tabelki ze względu na powiązania", "Warning", JOptionPane.WARNING_MESSAGE);

        }
        if(source == returnBtn){
            if(administratorEntity != null){
                AdminPanel adminPanel = new AdminPanel(administratorEntity);
                setVisible(false);
                adminPanel.setVisible(true);
            }
            else {
                PracownikPanel pracownikPanel = new PracownikPanel(pracownikKinaEntity);
                setVisible(false);
                pracownikPanel.setVisible(true);
            }
        }
        if(source == updateBtn){
            boolean wynik = true;
            int row = table.getSelectedRow();
            if(row != -1){
                SeansEntity seansEntity = seansy.get(row);
                int id1 = seansEntity.getIdSeansu();
                String myquerry = "from FilmEntity where tytul= '" + table.getValueAt(row,0).toString() + "'";
                List lista = session.createQuery(myquerry.toString()).list();

                if(lista.size() != 0) {
                    System.out.println(lista.size());
                    ObservableList<FilmEntity> filmEntities = FXCollections.observableArrayList(lista);
                    seansCRUD.updateidFilmu(id1, filmEntities.get(0));
                    if(table.getValueAt(row,3).toString().matches("[0-9]*")){
                    myquerry = "from SalaEntity where numerSali=" +table.getValueAt(row,3).toString();
                    lista = session.createQuery(myquerry).list();
                    if(lista.size() != 0) {
                        ObservableList<SalaEntity> salaEntities = FXCollections.observableArrayList(lista);
                        seansCRUD.updateidSali(id1, salaEntities.get(0));
                    } else {
                        JOptionPane.showMessageDialog(this, "Brak sali o takim numerze", "Warning", JOptionPane.WARNING_MESSAGE);
                        wynik = false;
                    }

                    if(!table.getValueAt(row,1).toString().matches("[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9].*")){JOptionPane.showMessageDialog(this, "Czas musi byc w formacie yyyy-mm-dd HH:mm", "Warning", JOptionPane.WARNING_MESSAGE);wynik=false;} else {seansCRUD.updateCzas(id1,convertStringToTimestamp(table.getValueAt(row,1).toString())); }
                    if(!table.getValueAt(row,2).toString().matches("[0-9]*\\.[0-9][0-9]?")){JOptionPane.showMessageDialog(this, "Cena musi byc liczba z przecinkiem", "Warning", JOptionPane.WARNING_MESSAGE); wynik=false;} else {seansCRUD.updateCena(id1, Float.valueOf(table.getValueAt(row,2).toString())); }
                    if(wynik){JOptionPane.showMessageDialog(this,"Update wykonany!","OK",JOptionPane.INFORMATION_MESSAGE);}
                } else {
                        JOptionPane.showMessageDialog(this, "Numer sali musi być liczba całkowita", "Warning", JOptionPane.WARNING_MESSAGE);

                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Brak Filmu o takim tytule", "Warning", JOptionPane.WARNING_MESSAGE);
                }

            }
        }
    }
    public static Timestamp convertStringToTimestamp(String strDate) {
        //2020-12-30 13:12
        int rok = 0;
        int miesiac = 0;
        int dzien = 0;
        int godzina = 0;
        int minuta = 0;
        String[] tab = strDate.split("[-| |:]");
        rok = Integer.parseInt(tab[0]) -1900;
        miesiac = Integer.parseInt(tab[1]) -1;
        dzien = Integer.parseInt(tab[2]);
        godzina = Integer.parseInt(tab[3]);
        minuta = Integer.parseInt(tab[4]);
        return new Timestamp(rok,miesiac,dzien,godzina,minuta,0,0);
    }
}

