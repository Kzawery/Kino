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

public class DodajAdmina extends JFrame implements ActionListener {
    JButton registerBtn, returnBtn;

    JTextField emialTxtField, nameTxtField, surnameTxtField, roleTxtField;
    JPasswordField passwordField;
    AdministratorEntity administratorEntity;
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

    public DodajAdmina(AdministratorEntity administratorEntity) {
        this.administratorEntity = administratorEntity;
        build();
    }

    public void build() {
        setLayout(null);
        setResizable(false);

        setLocation((int) screenSize.getWidth() / 2 - width / 2, (int) screenSize.getHeight() / 2 - height / 2);
        setSize(width, height);

        JLabel registerTxt = new JLabel("Dodaj Admina!");
        registerTxt.setBounds(150, 10, 150, 40);
        add(registerTxt);

        JLabel emailTxt = new JLabel("Email:");
        emailTxt.setBounds(50, 50, 60, 30);
        add(emailTxt);

        emialTxtField = new JTextField();
        emialTxtField.setBounds(120, 50, 150, 30);
        add(emialTxtField);

        JLabel lhaslo = new JLabel("Haslo:");
        lhaslo.setBounds(50, 90, 60, 30);
        add(lhaslo);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 90, 150, 30);
        add(passwordField);

        JLabel limie = new JLabel("Imie:");
        limie.setBounds(50, 130, 150, 30);
        add(limie);

        nameTxtField = new JTextField();
        nameTxtField.setBounds(120, 130, 150, 30);
        add(nameTxtField);

        JLabel lnazwisko = new JLabel("Nazwisko:");
        lnazwisko.setBounds(50, 170, 150, 30);
        add(lnazwisko);

        surnameTxtField = new JTextField();
        surnameTxtField.setBounds(120, 170, 150, 30);
        add(surnameTxtField);


        registerBtn = new JButton("Dodaj admina!");
        registerBtn.setBounds(120, 250, 150, 30);
        registerBtn.addActionListener(this);
        add(registerBtn);


        returnBtn = new JButton("Cofnij");
        returnBtn.setBounds(120, 290, 150, 30);
        returnBtn.addActionListener(this);
        add(returnBtn);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();
        if (source == registerBtn) {
            if (emialTxtField.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Uzupełnij email ", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            if (passwordField.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Uzupełnij haslo", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            if (nameTxtField.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Uzupełnij imie", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            if (surnameTxtField.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Uzupełnij nazwisko", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            String myquery = " from AdministratorEntity where email='" + emialTxtField.getText() + "'";
            List lista = session.createQuery(myquery.toString()).list();
            if (lista.size() == 0) {
                if (!passwordField.getText().isBlank()) {

                    AdministratorCRUD administratorCRUD = new AdministratorCRUD();
                    if (administratorCRUD.create(emialTxtField.getText(), nameTxtField.getText(), surnameTxtField.getText(), passwordField.getText() )) {
                        JOptionPane.showMessageDialog(this, "Dodano Administratora", "Dodano administratora", JOptionPane.INFORMATION_MESSAGE);
                        returnBtn.doClick();
                    } else {
                        JOptionPane.showMessageDialog(this, "Coś poszło nie tak sprawdź dane", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Musisz podać hasło", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Konto o tym adresie email juz istnieje", "Warning", JOptionPane.WARNING_MESSAGE);
            }


        }
        if (source == returnBtn) {
                AdminPanel adminPanel = new AdminPanel(administratorEntity);
                adminPanel.setVisible(true);
                setVisible(false);

        }
    }
}