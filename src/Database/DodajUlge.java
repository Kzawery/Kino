package Database;

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
import java.util.List;

public class DodajUlge extends JFrame implements ActionListener {
    JButton dodajUlge, returnBtn;
    JTextField idUlgiTxtField, znizkaTxtField, rezyserTxtField, gatunekTxtField, czasTrwaniaTxtField, opisTxtField;
    JPasswordField passwordField;
    AdministratorEntity administratorEntity;
    PracownikKinaEntity pracownikKinaEntity;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = 350;
    int height = 370;
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
    public DodajUlge(PracownikKinaEntity pracownikKinaEntity) {
        this.pracownikKinaEntity = pracownikKinaEntity;
        build();
    }
    public DodajUlge(AdministratorEntity administratorEntity) {
        this.administratorEntity = administratorEntity;
        build();
    }
    private void build(){
        setLayout(null);
        setResizable(false);
        setLocation(  (int) screenSize.getWidth()/2 - width/2, (int) screenSize.getHeight() / 2 -height/2);
        setSize(width, height);

        JLabel addMovie = new JLabel("Dodaj Ulge");
        addMovie.setBounds(120, 20 ,60, 30);
        add(addMovie);

        JLabel idUlgi = new JLabel("id Ulgi");
        idUlgi.setBounds(30, 50 ,60, 30);
        add(idUlgi);

        idUlgiTxtField = new JTextField();
        idUlgiTxtField.setBounds(120, 50, 150, 30);
        add(idUlgiTxtField);

        JLabel znizka = new JLabel("Zniżka (0-0.99)");
        znizka.setBounds(10, 90 ,100, 30);
        add(znizka);

        znizkaTxtField = new JTextField();
        znizkaTxtField.setBounds(120, 90, 150, 30);
        add(znizkaTxtField);

        JLabel opis = new JLabel("Opis:");
        opis.setBounds(30, 130 ,80, 30);
        add(opis);

        opisTxtField = new JTextField();
        opisTxtField.setBounds(120, 130, 150, 30);
        add(opisTxtField);

        dodajUlge = new JButton("Dodaj Ulge!");
        dodajUlge.setBounds(120, 170, 150, 30);
        dodajUlge.addActionListener(this);
        add(dodajUlge);

        returnBtn = new JButton("Cofnij");
        returnBtn.setBounds(120, 210, 150, 30);
        returnBtn.addActionListener(this);
        add(returnBtn);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        String ulga;
        Double znizka;
        String opis;
        Object source = actionEvent.getSource();
        if (source == dodajUlge) {
            ulga = idUlgiTxtField.getText();
            if(znizkaTxtField.getText().matches("0\\.[0-9][0-9]")) {
                znizka = Double.parseDouble(znizkaTxtField.getText());
                if(!opisTxtField.getText().isBlank()) {
                    opis = opisTxtField.getText();
                    if (ulga.length() <= 5) {
                        String query =  "from UlgaEntity where idUlgi='"+ idUlgiTxtField.getText() + "'";
                        List lista = session.createQuery(query.toString()).list();
                        if(lista.size() == 0) {
                            UlgaCRUD ulgaCRUD = new UlgaCRUD();
                            ulgaCRUD.create(ulga, znizka, opis);
                            JOptionPane.showMessageDialog(this, "Ulga została utworzona", "Dodano Ulge", JOptionPane.INFORMATION_MESSAGE);
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
                        }else {
                            JOptionPane.showMessageDialog(this, "Ulga o tym id juz istnieje", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Id ulgi musi miec maks 5 znakow", "Warning", JOptionPane.WARNING_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Opis nie moze byc pusty", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }else {
                JOptionPane.showMessageDialog(this, "Zniżka musi byc liczba z przecinkiem mniejsza od 1 ale wieksza od 0 np. 0.21", "Warning", JOptionPane.WARNING_MESSAGE);
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
}
