package Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PracownikPanel extends JFrame implements ActionListener {
    JButton dodajFilm, dodajSeans, dodajUlge,dodajPracownika, dodajSale, dodajMiejsce, usunKlienta, usunPracownika,usunAdministratora,usunFilm, wyloguj,usunSeans, usunSale, usunMiejsce, usunUlge;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    PracownikKinaEntity pracownikKinaEntity;
    int width = 500;
    int height = 400;

    public PracownikPanel(PracownikKinaEntity pracownikKinaEntity){
        this.pracownikKinaEntity = pracownikKinaEntity;
        setLayout(null);
        setResizable(false);
        setLocation(  (int) screenSize.getWidth()/2 - width/2, (int) screenSize.getHeight() / 2 -height/2);
        setSize(width, height);

        dodajFilm = new JButton("Dodaj Film");
        dodajFilm.setBounds(30, 50 ,200, 30);
        dodajFilm.addActionListener(this);
        add(dodajFilm);

        dodajSeans = new JButton("Dodaj Seans");
        dodajSeans.setBounds(30, 90 ,200, 30);
        dodajSeans .addActionListener(this);
        add(dodajSeans);

        dodajPracownika = new JButton("Dodaj Pracownika");
        dodajPracownika.setBounds(30, 130 ,200, 30);
        dodajPracownika .addActionListener(this);
        add(dodajPracownika);


        dodajUlge = new JButton("Dodaj Ulge");
        dodajUlge.setBounds(30, 170 ,200, 30);
        dodajUlge.addActionListener(this);
        add(dodajUlge);



        usunFilm = new JButton("Usun/Zupdatuj Film");
        usunFilm.setBounds(250, 50 ,200, 30);
        usunFilm.addActionListener(this);
        add(usunFilm);

        usunSeans = new JButton("Usun/Zupdatuj Seans");
        usunSeans.setBounds(250, 90 ,200, 30);
        usunSeans.addActionListener(this);
        add(usunSeans);

        usunMiejsce = new JButton("Usun/Zupdatuj Miejsce");
        usunMiejsce.setBounds(250, 130 ,200, 30);
        usunMiejsce.addActionListener(this);
        add(usunMiejsce);

        wyloguj = new JButton("Wyloguj");
        wyloguj.setBounds(250, 170 ,200, 30);
        wyloguj.addActionListener(this);
        add(wyloguj);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();

        if(source == dodajFilm){
            DodajFilm dodajFilm = new DodajFilm(pracownikKinaEntity);
            setVisible(false);
            dodajFilm.setVisible(true);
        }
        if(source == dodajSeans){
            DodajSeans dodajSeans = new DodajSeans(pracownikKinaEntity);
            setVisible(false);
            dodajSeans.setVisible(true);
        }
        if(source == dodajUlge){
            DodajUlge dodajUlge = new DodajUlge(pracownikKinaEntity);
            setVisible(false);
            dodajUlge.setVisible(true);
        }

        if(source == usunFilm){
            UsunUpdatujFilm usunFilm = new UsunUpdatujFilm(pracownikKinaEntity);
            setVisible(false);
            usunFilm.setVisible(true);
        }
        if(source == wyloguj){
            Login login = new Login();
            setVisible(false);
            login.setVisible(true);
        }
        if(source == usunSeans){
            UsunUpdatujSeans usunUpdatujSeans = new UsunUpdatujSeans(pracownikKinaEntity);
            setVisible(false);
            usunUpdatujSeans.setVisible(true);
        }

        if(source == usunMiejsce){
            UsunUpdatujMiejsce usunUpdatujMijesce = new UsunUpdatujMiejsce(pracownikKinaEntity);
            setVisible(false);
            usunUpdatujMijesce.setVisible(true);
        }

        if(source == dodajPracownika){
            DodajPracownika dodajPracownika = new DodajPracownika(pracownikKinaEntity);
            setVisible(false);
            dodajPracownika.setVisible(true);
        }


    }
}
