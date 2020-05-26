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
import java.sql.Date;
import java.sql.Time;
import java.time.Year;
import java.util.List;

public class UsunUpdatujFilm extends JFrame implements ActionListener {
    PracownikKinaEntity pracownikKinaEntity;
    AdministratorEntity administratorEntity;
    JButton returnBtn, delBtn, updateBtn;
    JTable table;
    Object[] seansObj;
    DefaultTableModel dtm, dtm2;
    ObservableList<FilmEntity> filmy;
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

    public UsunUpdatujFilm(AdministratorEntity administratorEntity) {
        this.administratorEntity = administratorEntity;
        build();
    }

    public UsunUpdatujFilm(PracownikKinaEntity pracownikKinaEntity) {
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
        dtm.setColumnIdentifiers(new Object[]{"Tytul", "Rezyser", "Opis","Czas trwania", "Gatunek", "Rok Produkcji"});
        table = new JTable();
        table.setModel(dtm);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        String myquery = "from FilmEntity ";
        List lista = session.createQuery(myquery).list();
        filmy = FXCollections.observableArrayList(lista);
        for (FilmEntity film : filmy) {
            seansObj = new Object[]{film.getTytul(), film.getRezyser(), film.getOpis(), film.getCzasTrwania(), film.getGatunek(), film.getRokProdukcji()};
            dtm.addRow(seansObj);
        }

        JScrollPane pane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setBounds(0, 0, width, 300);
        add(pane);

        System.out.println(klientKinaEntity);

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
            if(row != -1){
                FilmEntity filmEntity = filmy.get(row);
                FilmCRUD filmCRUD = new FilmCRUD();
                filmCRUD.delete(filmEntity.getIdFilmu());
                JOptionPane.showMessageDialog(this,"Nie można usunąć tabelki ze względu na powiązania" ,"Warning",JOptionPane.WARNING_MESSAGE);
            }

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
            int row = table.getSelectedRow();
            //int column = table.getSelectedColumnCount();
            if(row != -1){
                FilmEntity filmEntity = filmy.get(row);
                int id1 = filmEntity.getIdFilmu();
                FilmCRUD filmCRUD = new FilmCRUD();
                boolean wykonaj = true;
                if(table.getValueAt(row,0).toString().isBlank()){JOptionPane.showMessageDialog(this, "Tytuł filmu nie może być pusty", "Warning", JOptionPane.WARNING_MESSAGE); wykonaj = false;}
                if(table.getValueAt(row,1).toString().isBlank()){JOptionPane.showMessageDialog(this, "Pole reżyser filmu nie może być puste", "Warning", JOptionPane.WARNING_MESSAGE); wykonaj = false;}
                if(table.getValueAt(row,2).toString().isBlank()){JOptionPane.showMessageDialog(this, "Pole opis filmu nie może być puste", "Warning", JOptionPane.WARNING_MESSAGE); wykonaj = false;}
                if(!table.getValueAt(row,3).toString().matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$")){JOptionPane.showMessageDialog(this, "Czas trwania musi byc podany w formacie HH:mm:ss", "Warning", JOptionPane.WARNING_MESSAGE); wykonaj = false;}
                if(table.getValueAt(row,4).toString().isBlank()){JOptionPane.showMessageDialog(this, "Pole gatunek nie może być puste", "Warning", JOptionPane.WARNING_MESSAGE); wykonaj = false;}
                if(!table.getValueAt(row,5).toString().matches("^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$")){JOptionPane.showMessageDialog(this, "Data musi być podana w formacie yyyy-mm-dd", "Warning", JOptionPane.WARNING_MESSAGE); wykonaj = false;}

                if(wykonaj){

                    String myquery = " from FilmEntity  where tytul='" + table.getValueAt(row,0).toString()+"'";
                    List lista = session.createQuery(myquery.toString()).list();
                    if(lista.size() == 0 || filmy.get(row).getTytul().equals(table.getValueAt(row, 0).toString())) {
                        filmCRUD.updateTytul(id1, table.getValueAt(row, 0).toString());//
                        filmCRUD.updateRezyser(id1, table.getValueAt(row, 1).toString());//
                        filmCRUD.updateOpis(id1, table.getValueAt(row, 2).toString());
                        filmCRUD.updateCzasTrwania(id1, Time.valueOf(table.getValueAt(row, 3).toString()));
                        filmCRUD.updateGatunek(id1, table.getValueAt(row, 4).toString());
                        filmCRUD.updateRokProdukcji(id1, Date.valueOf(table.getValueAt(row, 5).toString()));
                        JOptionPane.showMessageDialog(this, "Update wykonany!", "OK", JOptionPane.INFORMATION_MESSAGE);
                        returnBtn.doClick();
                    } else {
                        JOptionPane.showMessageDialog(this, "Film o takim tytule juz istnieje", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        }

    }

}
