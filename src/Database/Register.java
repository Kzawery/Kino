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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class Register extends JFrame implements ActionListener {
    JButton registerBtn, loginBtn;

    JTextField emialTxtField, nameTxtField, surnameTxtField;
    JPasswordField passwordField;
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
    public Register(){
        setLayout(null);
        setResizable(false);

        setLocation(  (int) screenSize.getWidth()/2 - width/2, (int) screenSize.getHeight() / 2 -height/2);
        setSize(width, height);

        JLabel registerTxt = new JLabel("Zarejestruj sie!");
        registerTxt.setBounds(150, 10 ,150, 40);
        add(registerTxt);

        JLabel emailTxt = new JLabel("Email");
        emailTxt.setBounds(50, 50 ,60, 30);
        add(emailTxt);

        emialTxtField = new JTextField();
        emialTxtField.setBounds(120, 50, 150, 30);
        add(emialTxtField);

        JLabel lhaslo = new JLabel("Haslo");
        lhaslo.setBounds(50, 90 ,60, 30);
        add(lhaslo);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 90, 150, 30);
        add(passwordField);

        JLabel limie = new JLabel("Imie");
        limie.setBounds(50, 130 ,150, 30);
        add(limie);

        nameTxtField = new JTextField();
        nameTxtField.setBounds(120, 130, 150, 30);
        add(nameTxtField);

        JLabel lnazwisko = new JLabel("Nazwisko");
        lnazwisko.setBounds(50, 170 ,150, 30);
        add(lnazwisko);

        surnameTxtField = new JTextField();
        surnameTxtField.setBounds(120, 170, 150, 30);
        add(surnameTxtField);


        registerBtn = new JButton("Zarejestruj");
        registerBtn.setBounds(120, 230, 150, 30);
        registerBtn.addActionListener(this);
        add(registerBtn);

        JLabel loginTxt = new JLabel("Masz juz konto? Zaloguj sie!");
        loginTxt.setBounds(110, 270 ,200, 20);
        loginTxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Login login = new Login();
                setVisible(false);
                login.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Font font = loginTxt.getFont();
                Map attributes = font.getAttributes();
                attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                loginTxt.setFont(font.deriveFont(attributes));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Font font = registerBtn.getFont();
                Map attributes = font.getAttributes();
                loginTxt.setFont(font.deriveFont(attributes));
            }
        });
        add(loginTxt);

        loginBtn = new JButton("Zaloguj sie");
        loginBtn.setBounds(75, 290, 150, 30);
        loginBtn.addActionListener(this);
        //add(loginBtn);
        setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        Object zrodlo = e.getSource();
        if (zrodlo == registerBtn) {
            if(emialTxtField.getText().isEmpty()){JOptionPane.showMessageDialog(this,"Uzupełnij email","Warning",JOptionPane.WARNING_MESSAGE);}
            if(passwordField.getText().isEmpty()){JOptionPane.showMessageDialog(this,"Uzupełnij haslo","Warning",JOptionPane.WARNING_MESSAGE);}
            if(nameTxtField.getText().isEmpty()){JOptionPane.showMessageDialog(this,"Uzupełnij imie","Warning",JOptionPane.WARNING_MESSAGE);}
            if(surnameTxtField.getText().isEmpty()){JOptionPane.showMessageDialog(this,"Uzupełnij nazwisko","Warning",JOptionPane.WARNING_MESSAGE);}
            else {

                String myquery = " from KlientKinaEntity  where email='" + emialTxtField.getText()+"'";
                List lista = session.createQuery(myquery.toString()).list();
                if(lista.size() == 0){
                    KlientKinaCRUD klientCRUD = new KlientKinaCRUD();
                    klientCRUD.create(emialTxtField.getText(),nameTxtField.getText(),surnameTxtField.getText(),passwordField.getText());
                    lista = session.createQuery(myquery.toString()).list();
                    ObservableList<KlientKinaEntity> klientKinaEntities = FXCollections.observableArrayList(lista);
                    KlientPanel panel = new KlientPanel(klientKinaEntities.get(0));
                    setVisible(false);
                    panel.setVisible(true);
                }
                else{
                    JOptionPane.showMessageDialog(this,"Konto o tym adresie email juz istnieje","Warning",JOptionPane.WARNING_MESSAGE);
                }


            }
        }
    }
}