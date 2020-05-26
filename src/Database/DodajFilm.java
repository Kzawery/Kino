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
import java.text.SimpleDateFormat;
import java.util.List;

public class DodajFilm extends JFrame implements ActionListener {
    JButton dodajFilm, returnBtn;
    JTextField tytulTxtField, rokProdukcjiTxtField, rezyserTxtField, gatunekTxtField, czasTrwaniaTxtField, opisTxtField;
    JPasswordField passwordField;
    AdministratorEntity administratorEntity;
    PracownikKinaEntity pracownikKinaEntity;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = 350;
    int height = 420;
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
    public DodajFilm(PracownikKinaEntity pracownikKinaEntity) {
        this.pracownikKinaEntity = pracownikKinaEntity;
        build();
    }
    public DodajFilm(AdministratorEntity administratorEntity) {
        this.administratorEntity = administratorEntity;
        build();
    }
    private void build(){
        setLayout(null);
        setResizable(false);
        setLocation(  (int) screenSize.getWidth()/2 - width/2, (int) screenSize.getHeight() / 2 -height/2);
        setSize(width, height);

        JLabel addMovie = new JLabel("Dodaj Film");
        addMovie.setBounds(120, 20 ,60, 30);
        add(addMovie);

        JLabel tytul = new JLabel("Tytul");
        tytul.setBounds(30, 50 ,60, 30);
        add(tytul);

        tytulTxtField = new JTextField();
        tytulTxtField.setBounds(120, 50, 150, 30);
        add(tytulTxtField);

        JLabel rokProdukcji = new JLabel("Rok produkcji");
        rokProdukcji.setBounds(10, 90 ,80, 30);
        add(rokProdukcji);

        rokProdukcjiTxtField = new JTextField();
        rokProdukcjiTxtField.setBounds(120, 90, 150, 30);
        add(rokProdukcjiTxtField);

        JLabel rezyser = new JLabel("Rezyser:");
        rezyser.setBounds(30, 130 ,60, 30);
        add(rezyser);

        rezyserTxtField = new JTextField();
        rezyserTxtField.setBounds(120, 130, 150, 30);
        add(rezyserTxtField);

        JLabel gatunek = new JLabel("Gatunek:");
        gatunek.setBounds(30, 170 ,60, 30);
        add(gatunek);

        gatunekTxtField = new JTextField();
        gatunekTxtField.setBounds(120, 170, 150, 30);
        add(gatunekTxtField);


        JLabel czasTrwania = new JLabel("Czas trwania:");
        czasTrwania.setBounds(10, 210 ,80, 30);
        add(czasTrwania);

        czasTrwaniaTxtField = new JTextField();
        czasTrwaniaTxtField.setBounds(120, 210, 150, 30);
        add(czasTrwaniaTxtField);

        JLabel opis = new JLabel("Opis:");
        opis.setBounds(30, 250 ,80, 30);
        add(opis);

        opisTxtField = new JTextField();
        opisTxtField.setBounds(120, 250, 150, 30);
        add(opisTxtField);

        dodajFilm = new JButton("Dodaj Film!");
        dodajFilm.setBounds(120, 290, 150, 30);
        dodajFilm.addActionListener(this);
        add(dodajFilm);

        returnBtn = new JButton("Cofnij");
        returnBtn.setBounds(120, 330, 150, 30);
        returnBtn.addActionListener(this);
        add(returnBtn);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

         String tytul;
         Date rokProdukcji;
         String rezyser;
         String gatunek;
         Time czasTrwania;
         String opis;
        Object source = actionEvent.getSource();
        if (source == dodajFilm) {
            tytul = tytulTxtField.getText();
            String str= rokProdukcjiTxtField.getText();;

            rezyser = rezyserTxtField.getText();

            opis = opisTxtField.getText();
            gatunek = gatunekTxtField.getText();
            FilmCRUD filmCRUD = new FilmCRUD();
            if(!tytul.isBlank()) {
                String myquery = " from FilmEntity  where tytul='" + tytul+"'";
                List lista = session.createQuery(myquery.toString()).list();
                System.out.println(lista.size());
                if(lista.size() == 0){
                    if(str.matches("^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$")) {
                        if(!rezyser.isBlank()){
                            if(!gatunek.isBlank()) {
                                if(czasTrwaniaTxtField.getText().matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                                    if(!opis.isBlank()){
                                        czasTrwania = java.sql.Time.valueOf(czasTrwaniaTxtField.getText() + ":00");
                                        rokProdukcji = Date.valueOf(str);
                                        filmCRUD.create(tytul, rokProdukcji, rezyser, gatunek, czasTrwania, opis);
                                        JOptionPane.showMessageDialog(this,"Dodano film","Dodano film",JOptionPane.INFORMATION_MESSAGE);
                                        returnBtn.doClick();
                                    }else {
                                        JOptionPane.showMessageDialog(this,"Opis nie moze byc pusty","Warning",JOptionPane.WARNING_MESSAGE);
                                    }

                                }else {
                                    JOptionPane.showMessageDialog(this,"Czas Trwania musi być w formacie HH:mm","Warning",JOptionPane.WARNING_MESSAGE);
                                }
                            }else {
                                JOptionPane.showMessageDialog(this,"Gatunek nie moze byc pusty","Warning",JOptionPane.WARNING_MESSAGE);
                            }
                        }else {
                            JOptionPane.showMessageDialog(this,"Reżyser nie moze byc pusty","Warning",JOptionPane.WARNING_MESSAGE);
                        }
                    }else {
                        JOptionPane.showMessageDialog(this,"Data musi byc w formacie yyyy-mm-dd","Warning",JOptionPane.WARNING_MESSAGE);
                    }

                }else {
                    JOptionPane.showMessageDialog(this,"Film o tym tytule juz istnieje","Warning",JOptionPane.WARNING_MESSAGE);
                }

            }else {
                JOptionPane.showMessageDialog(this,"Tytul filmu nie moze byc pusty","Warning",JOptionPane.WARNING_MESSAGE);
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
