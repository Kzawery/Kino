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
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;

public class DodajSale extends JFrame implements ActionListener {
    JButton dodajSale, returnBtn;
    JTextField idSali;
    JCheckBox ulatwieniaDlaNiepelnosprawnych, mozliwosc3D;
    AdministratorEntity administratorEntity;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
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
    public DodajSale(AdministratorEntity administratorEntity){
        this.administratorEntity = administratorEntity;
        setLayout(null);
        setResizable(false);
        setLocation(  (int) screenSize.getWidth()/2 - width/2, (int) screenSize.getHeight() / 2 -height/2);
        setSize(width, height);
        JLabel addMovie = new JLabel("Dodaj Sale");
        addMovie.setBounds(120, 20 ,60, 30);
        add(addMovie);

        JLabel ulatwienia = new JLabel("Ułatwienia dla niepełnosprawnych:");
        ulatwienia.setBounds(10, 50 ,200, 30);
        add(ulatwienia);

        ulatwieniaDlaNiepelnosprawnych = new JCheckBox();
        ulatwieniaDlaNiepelnosprawnych.setBounds(220, 55, 20,20);
        add(ulatwieniaDlaNiepelnosprawnych);

        JLabel mozliwosc3DL = new JLabel("Mozliwosc 3D:");
        mozliwosc3DL.setBounds(10, 90 ,200, 30);
        add(mozliwosc3DL);

        mozliwosc3D = new JCheckBox();
        mozliwosc3D.setBounds(100, 95, 20,20);
        add(mozliwosc3D);

        JLabel numerSali = new JLabel("Podaj numer sali:");
        numerSali.setBounds(10, 130, 200,20);
        add(numerSali);

        idSali = new JTextField();
        idSali.setBounds(120, 130, 100,20);
        add(idSali);

        dodajSale = new JButton("Dodaj Sale!");
        dodajSale.setBounds(70, 200, 150, 30);
        dodajSale.addActionListener(this);
        add(dodajSale);

        returnBtn = new JButton("Cofnij");
        returnBtn.setBounds(70, 250, 150, 30);
        returnBtn.addActionListener(this);
        add(returnBtn);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if(source == dodajSale){
            boolean m3D = false;
            boolean ulatwienia = false;
            boolean wynik = true;
            if(idSali.getText().toString().matches("[0-9]*") && !idSali.getText().isBlank()){
            Integer nrSali = Integer.parseInt(idSali.getText());
            if(mozliwosc3D.isSelected()){m3D = true;}
            if(ulatwieniaDlaNiepelnosprawnych.isSelected()){ulatwienia = true;}

            SalaCRUD salaCRUD = new SalaCRUD();
            String myquery ="from SalaEntity ";
            List lista2 = session.createQuery(myquery).list(); //lista miejsc na tej sali
            ObservableList<SalaEntity> salaEntities = FXCollections.observableArrayList(lista2);
            for(SalaEntity salaEntity: salaEntities){
                if(salaEntity.getNumerSali().equals(nrSali)){
                    wynik = false;
                }
            }
            if(wynik) {

                if (salaCRUD.create(ulatwienia, m3D, nrSali)) {
                    returnBtn.doClick();
                } else {
                    JOptionPane.showMessageDialog(this, "Coś poszło nie tak", "Warning", JOptionPane.WARNING_MESSAGE);
                }

            } else{
                JOptionPane.showMessageDialog(this, "Ten numer sali jest już zajęty!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Numer sali musi byc liczba całkowita", "Warning", JOptionPane.WARNING_MESSAGE);
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
