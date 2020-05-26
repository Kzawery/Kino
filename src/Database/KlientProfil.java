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

public class KlientProfil extends JFrame implements ActionListener {
    JButton returnBtn, updateHaslo;
    SeansEntity seansEntity;
    KlientKinaEntity klientKinaEntity;
    JLabel info,info2,info3,info4,info5;
    JPasswordField stareHaslo, noweHaslo;
    double znizka = 0.0;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = 400;
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

    public KlientProfil(KlientKinaEntity klientKinaEntity){
        this.klientKinaEntity = klientKinaEntity;
        setLayout(null);
        setResizable(false);
        setLocation((int) screenSize.getWidth()/2 - width/2, (int) screenSize.getHeight() / 2 -height/2);
        setSize(width, height);

        info = new JLabel("Email: " + klientKinaEntity.getEmail());
        info2 = new JLabel("Imie: " + klientKinaEntity.getImie());
        info3 = new JLabel("Nazwisko: "+klientKinaEntity.getNazwisko());
        info4 = new JLabel("Stare haslo:");
        info5 = new JLabel("Nowe haslo:");

        stareHaslo = new JPasswordField();
        stareHaslo.setBounds(90,115,100,20);
        noweHaslo = new JPasswordField();
        noweHaslo.setBounds(90,155,100,20);

        info.setBounds(10,10,width, 30);
        info2.setBounds(10,40,width, 30);
        info3.setBounds(10,70, width, 30);
        info4.setBounds(10,110, 100, 30);
        info5.setBounds(10,150, 100, 30);

        add(info);
        add(info2);
        add(info3);
        add(info4);
        add(info5);

        add(stareHaslo);
        add(noweHaslo);

        updateHaslo = new JButton("Zmień hasło");
        updateHaslo.setBounds(10,200, 200,30);
        updateHaslo.addActionListener(this);
        add(updateHaslo);

        returnBtn = new JButton("Powrót do panelu klienta");
        returnBtn.setBounds(10, 320, 220, 30);
        returnBtn.addActionListener(this);
        add(returnBtn);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if(source == returnBtn){
            System.out.println(klientKinaEntity);
            KlientPanel klientPanel = new KlientPanel(klientKinaEntity);
            setVisible(false);
            klientPanel.setVisible(true);
        }

        if(source == updateHaslo){
            if(klientKinaEntity.getSha1().equals(stareHaslo.getText()) && !noweHaslo.getText().isEmpty()){
                KlientKinaCRUD klientKinaCRUD = new KlientKinaCRUD();
                klientKinaCRUD.updateSha1(klientKinaEntity.getIdKlienta(),noweHaslo.getText());
                JOptionPane.showMessageDialog(this,"Hasło zostało zmienione","Information",JOptionPane.INFORMATION_MESSAGE);
                this.klientKinaEntity = session.get(KlientKinaEntity.class, klientKinaEntity.getIdKlienta());
            }
            else {
                JOptionPane.showMessageDialog(this,"Niepoprawne haslo, lub brak nowego hasła","Warning",JOptionPane.WARNING_MESSAGE);
            }

        }
    }

}
