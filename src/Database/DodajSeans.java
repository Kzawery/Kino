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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static Database.UsunUpdatujSeans.convertStringToTimestamp;

public class DodajSeans extends JFrame implements ActionListener {
    JButton dodajSeans, returnBtn;
    JTextField tytulTxtField,  czasTrwaniaTxtField, cenaTxtField, numerSaliTxtField;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = 350;
    int height = 400;
    PracownikKinaEntity pracownikKinaEntity;
    AdministratorEntity administratorEntity;
    JComboBox ulgiDropDown, saleDropDown;
    SalaEntity salaEntity;
    FilmEntity filmEntity;
    ObservableList<FilmEntity> filmy;
    ObservableList<SalaEntity> sale;
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
    public DodajSeans(PracownikKinaEntity pracownikKinaEntity) {
        this.pracownikKinaEntity = pracownikKinaEntity;
        build();
    }
    public DodajSeans(AdministratorEntity administratorEntity) {
        this.administratorEntity = administratorEntity;
        build();
    }
    private void build(){
        setLayout(null);
        setResizable(false);

        setLocation((int) screenSize.getWidth()/2 - width/2, (int) screenSize.getHeight() / 2 -height/2);
        setSize(width, height);
        JLabel addMovie = new JLabel("Dodaj Seans:");
        addMovie.setBounds(120, 20 ,120, 30);
        add(addMovie);

        JLabel idSali = new JLabel("Numer Sali:");
        idSali.setBounds(20, 50 ,120, 30);
        add(idSali);

        String myquery ="from SalaEntity ";
        List lista2 = session.createQuery(myquery).list(); //lista miejsc na tej sali
        sale = FXCollections.observableArrayList(lista2);
        this.salaEntity = sale.get(0);
        ArrayList<Integer> saleString = new ArrayList<>();

        for(SalaEntity salaEntity1 : sale){
            saleString.add(salaEntity1.getNumerSali());
        }

        saleDropDown = new JComboBox(saleString.toArray());
        saleDropDown.setSelectedIndex(0);
        saleDropDown.addActionListener(this);
        saleDropDown.setBounds(120, 50, 120,30);
        add(saleDropDown);


        numerSaliTxtField = new JTextField();
        numerSaliTxtField.setBounds(120, 50, 150, 30);
        //add(numerSaliTxtField);

        JLabel idFilmu = new JLabel("Tytul filmu:");
        idFilmu.setBounds(30, 90 ,120, 30);
        add(idFilmu);

        myquery ="from FilmEntity ";
        lista2 = session.createQuery(myquery).list(); //lista miejsc na tej sali
        filmy = FXCollections.observableArrayList(lista2);
        this.filmEntity = filmy.get(0);
        ArrayList<String> ulgiString = new ArrayList<>();

        for(FilmEntity filmEntity1 : filmy){
            ulgiString.add(filmEntity1.getTytul());
        }


        ulgiDropDown = new JComboBox(ulgiString.toArray());
        ulgiDropDown.setSelectedIndex(0);
        ulgiDropDown.addActionListener(this);
        ulgiDropDown.setBounds(120, 90, 120,30);
        add(ulgiDropDown);

        tytulTxtField = new JTextField();
        tytulTxtField.setBounds(120, 90, 150, 30);
        //add(tytulTxtField);

        JLabel czasTrwania = new JLabel("Data i godzina:");
        czasTrwania.setBounds(10, 130 ,120, 30);
        add(czasTrwania);

        czasTrwaniaTxtField = new JTextField();
        czasTrwaniaTxtField.setBounds(120, 130, 150, 30);
        add(czasTrwaniaTxtField);

        JLabel cena = new JLabel("Pełna cena:");
        cena.setBounds(30, 170 ,120, 30);
        add(cena);

        cenaTxtField = new JTextField();
        cenaTxtField.setBounds(120, 170, 150, 30);
        add(cenaTxtField);

        dodajSeans = new JButton("Dodaj Seans!");
        dodajSeans.setBounds(120, 210, 150, 30);
        dodajSeans.addActionListener(this);
        add(dodajSeans);

        returnBtn = new JButton("Cofnij");
        returnBtn.setBounds(120, 250, 150, 30);
        returnBtn.addActionListener(this);
        add(returnBtn);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();
        if(source == ulgiDropDown){
            for(FilmEntity filmEntity : filmy){
                if(filmEntity.getTytul() == ulgiDropDown.getSelectedItem()) {
                    this.filmEntity = filmEntity;
                }
            }
        }
        if(source == saleDropDown){
            for(SalaEntity salaEntity : sale){
                if(salaEntity.getNumerSali() == saleDropDown.getSelectedItem()) {
                    this.salaEntity = salaEntity;
                }
            }
        }
        if (source == dodajSeans) {
            float cena;
            int nrSali;
            if (cenaTxtField.getText().matches("[0-9]*\\.[0-9][0-9]") && !cenaTxtField.getText().isBlank()) {
                cena = Float.parseFloat(cenaTxtField.getText());

                    if(czasTrwaniaTxtField.getText().matches("[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]")) {
                        Timestamp timestamp = convertStringToTimestamp(czasTrwaniaTxtField.getText());
                        SeansCRUD seansCRUD = new SeansCRUD();
                        if (seansCRUD.create(cena, salaEntity, filmEntity, timestamp)) {
                            JOptionPane.showMessageDialog(this, "Dodano nowy seans", "Warning", JOptionPane.INFORMATION_MESSAGE);
                            returnBtn.doClick();
                        }
                    }else {
                        JOptionPane.showMessageDialog(this, "Czas musi byc w formacie yyyy-mm-dd HH:mm", "Warning", JOptionPane.WARNING_MESSAGE);
                    }


            } else {
                JOptionPane.showMessageDialog(this, "Cena musi byc liczba z przecinkiem", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }

        if(source == returnBtn){
            if(pracownikKinaEntity != null){
                PracownikPanel pracownikPanel = new PracownikPanel(pracownikKinaEntity);
                pracownikPanel.setVisible(true);
                setVisible(false);
            }
            if(administratorEntity != null){
                AdminPanel adminPanel = new AdminPanel(administratorEntity);
                adminPanel.setVisible(true);
                setVisible(false);
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
