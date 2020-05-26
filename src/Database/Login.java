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
import java.util.List;
import java.util.Map;

public class Login extends JFrame implements ActionListener {

    JButton loginBtn;
    JTextField emialTxtField;
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
    Login(){

        setLayout(null);
        setResizable(false);

        setLocation(  (int) screenSize.getWidth()/2 - width/2, (int) screenSize.getHeight() / 2 -height/2);
        setSize(width, height);

        JLabel loginTxt = new JLabel("Zaloguj sie!");
        loginTxt.setBounds(150, 10 ,150, 40);
        add(loginTxt);

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

        loginBtn = new JButton("Zaloguj sie");
        loginBtn.setBounds(120, 160, 150, 30);
        loginBtn.addActionListener(this);
        add(loginBtn);

        JLabel registerTxt = new JLabel("Nie masz jeszcze konta? Zarejestruj sie!");
        registerTxt.setBounds(90, 130 ,250, 20);
        registerTxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Register register = new Register();
                setVisible(false);
                register.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Font font = registerTxt.getFont();
                Map attributes = font.getAttributes();
                attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                registerTxt.setFont(font.deriveFont(attributes));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Font font = loginBtn.getFont();
                Map attributes = font.getAttributes();
                registerTxt.setFont(font.deriveFont(attributes));
            }
        });
        add(registerTxt);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if(source == loginBtn){
            if(emialTxtField.getText().isEmpty()){JOptionPane.showMessageDialog(this,"Uzupełnij email","Warning",JOptionPane.WARNING_MESSAGE);}
            if(passwordField.getText().isEmpty()){JOptionPane.showMessageDialog(this,"Uzupełnij haslo","Warning",JOptionPane.WARNING_MESSAGE);}
            else{

                String myquery = " from KlientKinaEntity  where email='" + emialTxtField.getText() + "' AND CONVERT(VARCHAR, sha1)='" + passwordField.getText()+ "'";
                //System.out.println(myquery);
                List lista = session.createQuery(myquery.toString()).list();
                if(lista.size() == 0){
                    myquery = " from PracownikKinaEntity  where email='" + emialTxtField.getText() + "' AND CONVERT(VARCHAR, sha1)='" + passwordField.getText()+ "'";
                    lista = session.createQuery(myquery.toString()).list();
                    if(lista.size() == 0){
                        myquery = " from AdministratorEntity  where email='" + emialTxtField.getText() + "' AND CONVERT(VARCHAR, sha1)='" + passwordField.getText()+ "'";
                        lista = session.createQuery(myquery.toString()).list();
                        if(lista.size() == 0){
                            JOptionPane.showMessageDialog(this,"Niepoprawny login lub hasło","Warning",JOptionPane.WARNING_MESSAGE);
                        }
                        else{
                            ObservableList<AdministratorEntity> konta = FXCollections.observableArrayList(lista);
                            AdministratorEntity konto = konta.get(0);
                            System.out.println(konto.getEmail() + konto.getSha1());
                            AdminPanel adminPanel = new AdminPanel(konto);
                            adminPanel.setVisible(true);
                            setVisible(false);
                        }
                    }
                    else {
                        ObservableList<PracownikKinaEntity> konta = FXCollections.observableArrayList(lista);
                        PracownikKinaEntity konto = konta.get(0);
                        System.out.println(konto.getEmail() + konto.getSha1());
                        setVisible(false);
                        PracownikPanel pracownikPanel = new PracownikPanel(konto);
                        pracownikPanel.setVisible(true);
                    }

                }
                else {
                    ObservableList<KlientKinaEntity> konta = FXCollections.observableArrayList(lista);
                    KlientKinaEntity konto = konta.get(0);
                    System.out.println(konto.getEmail() + konto.getSha1());
                    KlientPanel klientPanel = new KlientPanel(konto);
                    setVisible(false);
                    klientPanel.setVisible(true);
                }
            }
        }
    }
}