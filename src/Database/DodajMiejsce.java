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

public class DodajMiejsce extends JFrame implements ActionListener {
    JButton dodajSale, returnBtn;
    JTextField rzadTxtField, numerTxtField, numer_sali;
    JCheckBox czyKanapa;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    AdministratorEntity administratorEntity;
    SalaEntity salaEntity;
    ObservableList<SalaEntity> sale;
    JComboBox saleDropDown;
    int width = 350;
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
    public DodajMiejsce(AdministratorEntity administratorEntity){
        this.administratorEntity = administratorEntity;

        setLayout(null);
        setResizable(false);
        setLocation(  (int) screenSize.getWidth()/2 - width/2, (int) screenSize.getHeight() / 2 -height/2);
        setSize(width, height);

        JLabel registerTxt = new JLabel("Dodaj Miejsce!");
        registerTxt.setBounds(150, 10 ,150, 40);
        add(registerTxt);

        JLabel rzadTxt = new JLabel("Rzad:");
        rzadTxt.setBounds(50, 50 ,60, 30);
        add(rzadTxt);

        rzadTxtField = new JTextField();
        rzadTxtField.setBounds(120, 50, 150, 30);
        add(rzadTxtField);

        JLabel nrMiejsca = new JLabel("Numer miejsca:");
        nrMiejsca.setBounds(30, 90 ,100, 30);
        add(nrMiejsca);

        numerTxtField = new JTextField();
        numerTxtField.setBounds(120, 90, 150, 30);
        add(numerTxtField);

        JLabel czyKanapaTxt = new JLabel("Czy kanapa:");
        czyKanapaTxt.setBounds(50, 130 ,150, 30);
        add(czyKanapaTxt);

        czyKanapa = new JCheckBox();
        czyKanapa.setBounds(120, 130, 150, 30);
        add(czyKanapa);

        JLabel nrSali = new JLabel("Numer Sali:");
        nrSali.setBounds(50, 170 ,150, 30);
        add(nrSali);

        numer_sali = new JTextField();
        numer_sali.setBounds(120, 170, 150, 30);
        //add(numer_sali);
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
        saleDropDown.setBounds(120, 170, 120,30);
        add(saleDropDown);

        dodajSale = new JButton("Dodaj miejsce!");
        dodajSale.setBounds(120, 210, 150, 30);
        dodajSale.addActionListener(this);
        add(dodajSale);



        returnBtn = new JButton("Cofnij");
        returnBtn.setBounds(120, 250, 150, 30);
        returnBtn.addActionListener(this);
        add(returnBtn);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if(source == saleDropDown){
            for(SalaEntity salaEntity : sale){
                if(salaEntity.getNumerSali() == saleDropDown.getSelectedItem()) {
                    this.salaEntity = salaEntity;
                }
            }
        }
        if(source == dodajSale){
            boolean czyKanapa2 = false;
            String myquery;
            List lista;
            MiejsceCRUD miejsceCRUD = new MiejsceCRUD();
            if(czyKanapa.isSelected()){czyKanapa2= true;}

                    if (rzadTxtField.getText().matches("[A-Z]") && !rzadTxtField.getText().isBlank()) {
                        if (numerTxtField.getText().matches("[0-9]*") && !numerTxtField.getText().isBlank()) {
                            myquery = "from MiejsceEntity where idSali=" + salaEntity.getIdSali();
                            lista = session.createQuery(myquery.toString()).list();
                            ObservableList<MiejsceEntity> miejsceEntities = FXCollections.observableArrayList(lista);
                            boolean wynik = true;
                            for (MiejsceEntity miejsceEntity : miejsceEntities) {
                                if (miejsceEntity.getRzad().equals(rzadTxtField.getText()) && miejsceEntity.getNumerMiejsca() == Integer.parseInt(numerTxtField.getText()) && miejsceEntity.getIdSali().equals(salaEntity)) {
                                    wynik = false;
                                }
                            }
                            if (wynik) {
                                if (miejsceCRUD.create(rzadTxtField.getText(), Integer.parseInt(numerTxtField.getText()), czyKanapa2, salaEntity)) {
                                    JOptionPane.showMessageDialog(this, "Dodano Miejsce", "Dodano Miejsce", JOptionPane.INFORMATION_MESSAGE);
                                    returnBtn.doClick();
                                } else {
                                    JOptionPane.showMessageDialog(this, "Coś poszło nie tak", "Warning", JOptionPane.WARNING_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "Takie miejsce juz istnieje", "Warning", JOptionPane.WARNING_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Numer miejsca musi byc liczba całkowitą", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Rzad musi być znakiem pomiedzy A-Z", "Warning", JOptionPane.WARNING_MESSAGE);
                    }

        }
        if(source == returnBtn){
            if(administratorEntity != null){
                AdminPanel adminPanel = new AdminPanel(administratorEntity);
                adminPanel.setVisible(true);
                setVisible(false);
            }
        }
    }
}
