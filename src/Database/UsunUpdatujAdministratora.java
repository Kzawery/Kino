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

public class UsunUpdatujAdministratora extends JFrame implements ActionListener {
    PracownikKinaEntity pracownikKinaEntity;
    AdministratorEntity administratorEntity;
    AdministratorCRUD administratorCRUD = new AdministratorCRUD();
    JButton returnBtn, delBtn, updateBtn, passwordReset;
    JTable table;
    Object[] seansObj;
    DefaultTableModel dtm;
    ObservableList<AdministratorEntity> administratorzy;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
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

    public UsunUpdatujAdministratora(AdministratorEntity administratorEntity) {
        this.administratorEntity = administratorEntity;
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

        String myquery = "from AdministratorEntity ";
        List lista = session.createQuery(myquery).list();
        administratorzy = FXCollections.observableArrayList(lista);
        for (AdministratorEntity admin : administratorzy) {
            seansObj = new Object[]{admin.getEmail(), admin.getImie(), admin.getNazwisko()};
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
                AdministratorEntity administratorEntity = administratorzy.get(row);
                if(!administratorEntity.equals(this.administratorEntity)) {
                    administratorCRUD.delete(administratorEntity.getId());
                    JOptionPane.showMessageDialog(this,"Nie można usunąć tabelki ze względu na powiązania" , "Warning", JOptionPane.WARNING_MESSAGE);
                }
                else {
                JOptionPane.showMessageDialog(this, "Nie możesz usunać siebie samego!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
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
                AdministratorEntity administratorEntity = administratorzy.get(row);
                int id1 = administratorEntity.getId();
                boolean wykonaj = true;
                if(!administratorEntity.getEmail().equals(table.getValueAt(row, 0)))
                {
                    for(AdministratorEntity administratorEntity1: administratorzy) {
                        if (administratorEntity1.getEmail().equals(table.getValueAt(row,0))) {
                            wykonaj = false;
                            JOptionPane.showMessageDialog(this, "Administrator o takim emialu juz istnieje", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
                if(table.getValueAt(row,0).toString().isBlank()){JOptionPane.showMessageDialog(this, "Pole email nie moze byc puste","Warning",JOptionPane.WARNING_MESSAGE); wykonaj = false;}
                if(table.getValueAt(row,1).toString().isBlank()){JOptionPane.showMessageDialog(this, "Pole imie nie moze byc puste","Warning",JOptionPane.WARNING_MESSAGE); wykonaj = false;}
                if(table.getValueAt(row,2).toString().isBlank()){JOptionPane.showMessageDialog(this, "Pole nazwisko nie moze byc puste","Warning",JOptionPane.WARNING_MESSAGE); wykonaj = false;}
                if(wykonaj) {
                    administratorCRUD.updateEmail(id1, table.getValueAt(row, 0).toString());
                    administratorCRUD.updateImie(id1, table.getValueAt(row, 1).toString());
                    administratorCRUD.updateNazwisko(id1, table.getValueAt(row, 2).toString());

                    JOptionPane.showMessageDialog(this, "Update udany!","Update",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        if(source == passwordReset){
            int row = table.getSelectedRow();
            if(row != -1){
                AdministratorEntity administratorEntity = administratorzy.get(row);
                int id1 = administratorEntity.getId();
                administratorCRUD.updateSha1(id1, table.getValueAt(row,1).toString() + table.getValueAt(row,2).toString());
                setVisible(false);
                setVisible(true);
            }
        }
    }
}
