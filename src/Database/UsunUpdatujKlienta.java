package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UsunUpdatujKlienta extends JFrame implements ActionListener {
    PracownikKinaEntity pracownikKinaEntity;
    AdministratorEntity administratorEntity;
    JButton returnBtn, delBtn, updateBtn, passwordReset;
    KlientKinaCRUD klientKinaCRUD = new KlientKinaCRUD();
    JTable table;
    Object[] seansObj;
    DefaultTableModel dtm, dtm2;
    ObservableList<KlientKinaEntity> klienci;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    KlientKinaEntity klientKinaEntity;
    int width = 900;
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

    public UsunUpdatujKlienta(AdministratorEntity administratorEntity) {
        this.administratorEntity = administratorEntity;
        build();
    }
    public UsunUpdatujKlienta(PracownikKinaEntity pracownikKinaEntity) {
        this.pracownikKinaEntity = pracownikKinaEntity;
        build();
    }

    public void build() {
        setLayout(null);
        setResizable(false);
        setLocation((int) screenSize.getWidth() / 2 - width / 2, (int) screenSize.getHeight() / 2 - height / 2);
        setSize(width, height);


        dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }

        };
        dtm.setColumnIdentifiers(new Object[]{"Email", "Imie", "Nazwisko"});
        table = new JTable();
        table.setModel(dtm);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        String myquery = "from KlientKinaEntity ";
        List lista = session.createQuery(myquery).list();
        klienci = FXCollections.observableArrayList(lista);
        for (KlientKinaEntity klient : klienci) {
            seansObj = new Object[]{klient.getEmail(), klient.getImie(), klient.getNazwisko()};
            dtm.addRow(seansObj);
        }

        JScrollPane pane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setBounds(0, 0, width, 300);
        add(pane);


        delBtn = new JButton("Usun rekord");
        delBtn.setBounds(width - 200, 320, 160, 30);
        delBtn.addActionListener(this);
        add(delBtn);

        updateBtn = new JButton("Update rekord");
        updateBtn.setBounds(width - 400, 320, 160, 30);
        updateBtn.addActionListener(this);
        add(updateBtn);


        passwordReset = new JButton("Resetuj hasło");
        passwordReset.setBounds(300, 320, 160, 30);
        passwordReset.addActionListener(this);
        add(passwordReset);


        returnBtn = new JButton("Powrót");
        returnBtn.setBounds(50, 320, 160, 30);
        returnBtn.addActionListener(this);
        add(returnBtn);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if(source == delBtn) {
            int row = table.getSelectedRow();
            if(row != -1){
                KlientKinaEntity klientKinaEntity = klienci.get(row);
                KlientKinaCRUD klientKinaCRUD = new KlientKinaCRUD();
                klientKinaCRUD.delete(klientKinaEntity.getIdKlienta());
                JOptionPane.showMessageDialog(this, "Nie można usunąć tabelki ze względu na powiązania" ,"Warning",JOptionPane.WARNING_MESSAGE);
            }

        }
        if(source == returnBtn){
            if(administratorEntity != null){
                AdminPanel adminPanel = new AdminPanel(administratorEntity);
                setVisible(false);
                adminPanel.setVisible(true);
            }
            else {
                PracownikPanel pracownikPanel = new PracownikPanel(pracownikKinaEntity);
                setVisible(false);
                pracownikPanel.setVisible(true);
            }
        }
        if(source == updateBtn){
            int row = table.getSelectedRow();
            if(row != -1){
                KlientKinaEntity klientKinaEntity = klienci.get(row);
                int id1 = klientKinaEntity.getIdKlienta();
                boolean wykonaj = true;
                if(!klientKinaEntity.getEmail().equals(table.getValueAt(row, 0)))
                {
                for(KlientKinaEntity klientKinaEntity1: klienci) {
                    if (klientKinaEntity1.getEmail().equals(table.getValueAt(row,0))) {
                        wykonaj = false;
                        JOptionPane.showMessageDialog(this, "Klient o takim emialu juz istnieje", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
                }
                if(wykonaj){
                if(!table.getValueAt(row,0).toString().isBlank()){klientKinaCRUD.updateEmail(id1,table.getValueAt(row,0).toString());} else {JOptionPane.showMessageDialog(this, "Email nie moze być pusty","Warning",JOptionPane.WARNING_MESSAGE);   wykonaj = false;}
                if(!table.getValueAt(row,1).toString().isBlank()){klientKinaCRUD.updateImie(id1,table.getValueAt(row,1).toString());} else {JOptionPane.showMessageDialog(this, "Imie nie moze być puste","Warning",JOptionPane.WARNING_MESSAGE);   wykonaj = false;}
                if(!table.getValueAt(row,2).toString().isBlank()){klientKinaCRUD.updateNazwisko(id1,table.getValueAt(row,2).toString());} else {JOptionPane.showMessageDialog(this, "Nazwisko nie moze być puste","Warning",JOptionPane.WARNING_MESSAGE);   wykonaj = false;}

                    JOptionPane.showMessageDialog(this,"Update wykonany!", "OK", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        if(source == passwordReset){
            int row = table.getSelectedRow();
            if(row != -1){
                KlientKinaEntity klientKinaEntity = klienci.get(row);
                int id1 = klientKinaEntity.getIdKlienta();
                klientKinaCRUD.updateSha1(id1, table.getValueAt(row,1).toString() + table.getValueAt(row,2).toString());
                setVisible(false);
                setVisible(true);
            }
        }

    }

}
