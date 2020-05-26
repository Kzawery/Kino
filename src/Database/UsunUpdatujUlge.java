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
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class UsunUpdatujUlge extends JFrame implements ActionListener {
    PracownikKinaEntity pracownikKinaEntity;
    AdministratorEntity administratorEntity;
    JButton returnBtn, delBtn, updateBtn;
    UlgaCRUD ulgaCRUD = new UlgaCRUD();
    JTable table;
    Object[] seansObj;
    DefaultTableModel dtm, dtm2;
    ObservableList<UlgaEntity> ulgi;
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

    public UsunUpdatujUlge(AdministratorEntity administratorEntity) {
        this.administratorEntity = administratorEntity;
        build();
    }

    public UsunUpdatujUlge(PracownikKinaEntity pracownikKinaEntity) {
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
        dtm.setColumnIdentifiers(new Object[]{"idUlgi", "Znizka", "Opis"});
        table = new JTable();
        table.setModel(dtm);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        String myquery = "from UlgaEntity ";
        List lista = session.createQuery(myquery).list();
        ulgi = FXCollections.observableArrayList(lista);
        for (UlgaEntity ulga : ulgi) {
            seansObj = new Object[]{ulga.getIdUlgi(), ulga.getZnizka(), ulga.getOpis()};
            dtm.addRow(seansObj);
        }

        JScrollPane pane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setBounds(0, 0, width, 300);
        add(pane);

        System.out.println(klientKinaEntity);

        delBtn = new JButton("Usun rekord");
        delBtn.setBounds(width - 200, 320, 160, 30);
        delBtn.addActionListener(this);
        add(delBtn);

        updateBtn = new JButton("Update rekord");
        updateBtn.setBounds(width - 400, 320, 160, 30);
        updateBtn.addActionListener(this);
        add(updateBtn);


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
                UlgaEntity ulgaEntity = ulgi.get(row);
                ulgaCRUD.delete(ulgaEntity.getIdUlgi());
                JOptionPane.showMessageDialog(this,"Nie można usunąć tabelki ze względu na powiązania" ,"Warning",JOptionPane.WARNING_MESSAGE);
                returnBtn.doClick();
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
            boolean wykonaj = true;
            int row = table.getSelectedRow();
            if(row != -1) {
                UlgaEntity ulgaEntity = ulgi.get(row);
                String id1 = ulgaEntity.getIdUlgi();
                if(!ulgaEntity.getIdUlgi().equals(table.getValueAt(row, 0).toString())){
                    wykonaj=false;
                    JOptionPane.showMessageDialog(this, "Nie możesz zmienić Id Ulgi", "Warning", JOptionPane.WARNING_MESSAGE);
                }
                if (wykonaj){
                    if (table.getValueAt(row, 1).toString().matches("0\\.[0-9][0-9]")) {
                        if (ulgaCRUD.updateZnizka(id1, Double.parseDouble(table.getValueAt(row, 1).toString()))) {
                            if (ulgaCRUD.updateOpis(id1, table.getValueAt(row, 2).toString())) {
                                JOptionPane.showMessageDialog(this, "Zupdatowano Ulge", "Info", JOptionPane.INFORMATION_MESSAGE);
                                returnBtn.doClick();
                            } else {
                                JOptionPane.showMessageDialog(this, "Coś poszło nie tak przy ustawianiu opisu", "Warning", JOptionPane.WARNING_MESSAGE);
                            }

                        } else {
                            JOptionPane.showMessageDialog(this, "Coś poszło nie tak przy ustawianiu zniżki, zniżka musi być liczba miedzy 0 a 1", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Coś poszło nie tak przy ustawianiu zniżki, zniżka musi być liczba miedzy 0 a 1", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        }

    }

}
