package Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPanel extends JFrame implements ActionListener {
    JButton dodajFilm, dodajSeans, dodajUlge,dodajPracownika, dodajSale, dodajAdmina, dodajMiejsce, usunKlienta, usunPracownika,usunAdministratora,usunFilm, wyloguj,usunSeans, usunSale, usunMiejsce, usunUlge;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    AdministratorEntity administratorEntity;
    int width = 500;
    int height = 500;

    public AdminPanel(AdministratorEntity administratorEntity){
        this.administratorEntity = administratorEntity;
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

        dodajSale = new JButton("Dodaj Sale");
        dodajSale.setBounds(30, 170 ,200, 30);
        dodajSale .addActionListener(this);
        add(dodajSale);

        dodajMiejsce = new JButton("Dodaj Miejsce");
        dodajMiejsce.setBounds(30, 210 ,200, 30);
        dodajMiejsce .addActionListener(this);
        add(dodajMiejsce);

        dodajUlge = new JButton("Dodaj Ulge");
        dodajUlge.setBounds(30, 250 ,200, 30);
        dodajUlge.addActionListener(this);
        add(dodajUlge);

        usunKlienta = new JButton("Usun/Updatuj Klienta");
        usunKlienta.setBounds(30, 330 ,200, 30);
        usunKlienta.addActionListener(this);
        add(usunKlienta);

        usunPracownika = new JButton("Usun/Updatuj Pracownika");
        usunPracownika.setBounds(250, 130 ,200, 30);
        usunPracownika.addActionListener(this);
        add(usunPracownika);

        usunAdministratora = new JButton("Usun/Updatuj Administratora");
        usunAdministratora.setBounds(250, 290 ,200, 30);
        usunAdministratora.addActionListener(this);
        add(usunAdministratora);

        usunFilm = new JButton("Usun/Zupdatuj Film");
        usunFilm.setBounds(250, 50 ,200, 30);
        usunFilm.addActionListener(this);
        add(usunFilm);

        usunSeans = new JButton("Usun/Zupdatuj Seans");
        usunSeans.setBounds(250, 90 ,200, 30);
        usunSeans.addActionListener(this);
        add(usunSeans);

        usunSale = new JButton("Usun/Zupdatuj Sale");
        usunSale.setBounds(250, 170 ,200, 30);
        usunSale.addActionListener(this);
        add(usunSale);

        usunMiejsce = new JButton("Usun/Zupdatuj Miejsce");
        usunMiejsce.setBounds(250, 210 ,200, 30);
        usunMiejsce.addActionListener(this);
        add(usunMiejsce);

        usunUlge = new JButton("Usun/Zupdatuj Ulge");
        usunUlge.setBounds(250, 250 ,200, 30);
        usunUlge.addActionListener(this);
        add(usunUlge);

        dodajAdmina = new JButton("Dodaj Administratora");
        dodajAdmina.setBounds(30, 290 ,200, 30);
        dodajAdmina.addActionListener(this);
        add(dodajAdmina);
        wyloguj = new JButton("Wyloguj");
        wyloguj.setBounds(250, 330 ,200, 30);
        wyloguj.addActionListener(this);
        add(wyloguj);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();

        if(source == dodajFilm){
            DodajFilm dodajFilm = new DodajFilm(administratorEntity);
            setVisible(false);
            dodajFilm.setVisible(true);
        }
        if(source == dodajSeans){
            DodajSeans dodajSeans = new DodajSeans(administratorEntity);
            setVisible(false);
            dodajSeans.setVisible(true);
        }
        if(source == dodajUlge){
            DodajUlge dodajUlge = new DodajUlge(administratorEntity);
            setVisible(false);
            dodajUlge.setVisible(true);
        }
        if(source == dodajAdmina){
            DodajAdmina dodajAdmina = new DodajAdmina(administratorEntity);
            setVisible(false);
            dodajAdmina.setVisible(true);
        }

        if(source == dodajSale){
            DodajSale dodajSale = new DodajSale(administratorEntity);
            setVisible(false);
            dodajSale.setVisible(true);
        }
        if(source == dodajPracownika){
            DodajPracownika dodajPracownika = new DodajPracownika(administratorEntity);
            setVisible(false);
            dodajPracownika.setVisible(true);
        }
        if(source == dodajMiejsce){
            DodajMiejsce dodajMiejsce = new DodajMiejsce(administratorEntity);
            setVisible(false);
            dodajMiejsce.setVisible(true);
        }
        if(source == usunKlienta){
            UsunUpdatujKlienta usunKlienta = new UsunUpdatujKlienta(administratorEntity);
            setVisible(false);
            usunKlienta.setVisible(true);
        }
        if(source == usunPracownika){
            UsunUpdatujPracownika usunKlienta = new UsunUpdatujPracownika(administratorEntity);
            setVisible(false);
            usunKlienta.setVisible(true);
        }
        if(source == usunAdministratora){
            UsunUpdatujAdministratora usunAdministratora = new UsunUpdatujAdministratora(administratorEntity);
            setVisible(false);
            usunAdministratora.setVisible(true);
        }
        if(source == usunFilm){
            UsunUpdatujFilm usunFilm = new UsunUpdatujFilm(administratorEntity);
            setVisible(false);
            usunFilm.setVisible(true);
        }
        if(source == wyloguj){
            Login login = new Login();
            setVisible(false);
            login.setVisible(true);
        }
        if(source == usunSeans){
            UsunUpdatujSeans usunUpdatujSeans = new UsunUpdatujSeans(administratorEntity);
            setVisible(false);
            usunUpdatujSeans.setVisible(true);
        }
        if(source == usunSale){
            UsunUpdatujSale usunUpdatujSale = new UsunUpdatujSale(administratorEntity);
            setVisible(false);
            usunUpdatujSale.setVisible(true);
        }
        if(source == usunMiejsce){
            UsunUpdatujMiejsce usunUpdatujMijesce = new UsunUpdatujMiejsce(administratorEntity);
            setVisible(false);
            usunUpdatujMijesce.setVisible(true);
        }
        if(source == usunUlge){
            UsunUpdatujUlge usunUpdatujUlge = new UsunUpdatujUlge(administratorEntity);
            setVisible(false);
            usunUpdatujUlge.setVisible(true);
        }
    }
}
