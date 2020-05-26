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

public class DodajPracownika extends JFrame implements ActionListener {
    JButton registerBtn, returnBtn;

    JTextField emialTxtField, nameTxtField, surnameTxtField, roleTxtField;
    JPasswordField passwordField;
    AdministratorEntity administratorEntity;
    PracownikKinaEntity pracownikKinaEntity;
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

    public DodajPracownika(AdministratorEntity administratorEntity) {
        this.administratorEntity = administratorEntity;
        build();
    }

    public DodajPracownika(PracownikKinaEntity pracownikKinaEntity) {
        this.pracownikKinaEntity = pracownikKinaEntity;
        build();
    }

    public void build() {
        setLayout(null);
        setResizable(false);

        setLocation((int) screenSize.getWidth() / 2 - width / 2, (int) screenSize.getHeight() / 2 - height / 2);
        setSize(width, height);

        JLabel registerTxt = new JLabel("Dodaj Pracownika!");
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

        JLabel rola = new JLabel("Rola:");
        rola.setBounds(50, 210, 150, 30);
        add(rola);

        roleTxtField = new JTextField();
        roleTxtField.setBounds(120, 210, 150, 30);
        add(roleTxtField);

        registerBtn = new JButton("Dodaj pracownika!");
        registerBtn.setBounds(120, 250, 150, 30);
        registerBtn.addActionListener(this);
        add(registerBtn);

        setVisible(true);

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
            if (roleTxtField.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Uzupełnij role", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {

                String myquery = " from PracownikKinaEntity where email='" + emialTxtField.getText() + "'";
                List lista = session.createQuery(myquery.toString()).list();
                if (lista.size() == 0) {
                    if (!passwordField.getText().isBlank()) {

                        PracownikKinaCRUD pracownikKinaCRUD = new PracownikKinaCRUD();
                        if (pracownikKinaCRUD.create(emialTxtField.getText(), nameTxtField.getText(), surnameTxtField.getText(), passwordField.getText(), roleTxtField.getText())) {
                            JOptionPane.showMessageDialog(this, "Dodano Pracownika", "Dodano pracownika", JOptionPane.INFORMATION_MESSAGE);
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

        }
        if (source == returnBtn) {
            if (administratorEntity != null) {
                AdminPanel adminPanel = new AdminPanel(administratorEntity);
                adminPanel.setVisible(true);
                setVisible(false);
            } else {
                PracownikPanel pracownikPanel = new PracownikPanel(pracownikKinaEntity);
                pracownikPanel.setVisible(true);
                setVisible(false);
            }
        }
    }
}