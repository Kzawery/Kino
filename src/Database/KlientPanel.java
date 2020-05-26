package Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KlientPanel extends JFrame implements ActionListener {
    JButton repertuar, koszyk, historia, profil, wyloguj;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    KlientKinaEntity klientKinaEntity;
    int width = 300;
    int height = 400;

    public KlientPanel(KlientKinaEntity klientKinaEntity){
        this.klientKinaEntity = klientKinaEntity;
        setLayout(null);
        setResizable(false);
        setLocation((int) screenSize.getWidth()/2 - width/2, (int) screenSize.getHeight() / 2 -height/2);
        setSize(width, height);

        profil = new JButton("Mój profil");
        profil.setBounds(30,90,220,30);
        profil.addActionListener(this);
        add(profil);

        koszyk = new JButton("Koszyk");
        koszyk.setBounds(30, 130 ,220, 30);
        koszyk.addActionListener(this);
        add(koszyk);

        repertuar = new JButton("Repertuar");
        repertuar.setBounds(30, 170 ,220, 30);
        repertuar.addActionListener(this);
        add(repertuar);

        historia = new JButton("Poprzednie zamowienia");
        historia.setBounds(30, 210 ,220, 30);
        historia.addActionListener(this);
        add(historia);

        wyloguj = new JButton("Wyloguj");
        wyloguj.setBounds(30, 250 ,220, 30);
        wyloguj.addActionListener(this);
        add(wyloguj);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if(source == repertuar) {
            Repertuar repertuar = new Repertuar(this.klientKinaEntity);
            setVisible(false);
            repertuar.setVisible(true);
        }
        if(source == koszyk){
            System.out.println(klientKinaEntity);
            KoszykView koszykView = new KoszykView(klientKinaEntity);
            setVisible(false);
            koszykView.setVisible(true);
        }
        if(source == historia){
            Historia historia = new Historia(klientKinaEntity);
            setVisible(false);
            historia.setVisible(true);
        }
        if(source == profil){
            KlientProfil klientProfil = new KlientProfil(klientKinaEntity);
            setVisible(false);
            klientProfil.setVisible(true);
        }
        if(source == wyloguj){
            Login login = new Login();
            setVisible(false);
            login.setVisible(true);
        }
    }
}
