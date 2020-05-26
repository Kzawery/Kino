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

public class UsunUpdatujSale extends JFrame implements ActionListener {
    PracownikKinaEntity pracownikKinaEntity;
    AdministratorEntity administratorEntity;
    SalaCRUD salaCRUD = new SalaCRUD();
    JButton returnBtn, delBtn, updateBtn;
    JTable table;
    Object[] seansObj;
    DefaultTableModel dtm, dtm2;
    ObservableList<SalaEntity> sale;
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

    public UsunUpdatujSale(AdministratorEntity administratorEntity) {
        this.administratorEntity = administratorEntity;
        build();
    }

    public UsunUpdatujSale(PracownikKinaEntity pracownikKinaEntity) {
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
        dtm.setColumnIdentifiers(new Object[]{"Ułatwienia dla niepełnosprawnych", "Możliwość 3D", "Numer Sali"});
        table = new JTable();
        table.setModel(dtm);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        String myquery = "from SalaEntity ";
        List lista = session.createQuery(myquery).list();
        sale = FXCollections.observableArrayList(lista);
        for (SalaEntity sala : sale) {
            seansObj = new Object[]{sala.getUłatwieniaDlaNiepełnosprawnych(), sala.getMozliwosc3D(), sala.getNumerSali()};
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
                SalaEntity salaEntity = sale.get(row);
                salaCRUD.delete(salaEntity.getIdSali());
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
                SalaEntity salaEntity = sale.get(row);
                int id1 = salaEntity.getIdSali();
                boolean wykonaj = true;
                if(!table.getValueAt(row,0).toString().matches("(true|false)")){JOptionPane.showMessageDialog(this,"Pole ułatwienia musi byc true albo false","Warning",JOptionPane.WARNING_MESSAGE); wykonaj = false;}
                if(!table.getValueAt(row,1).toString().matches("(true|false)")){JOptionPane.showMessageDialog(this,"Pole możliwość 3D musi byc true albo false","Warning",JOptionPane.WARNING_MESSAGE); wykonaj = false;}
                if(!table.getValueAt(row,2).toString().matches("[0-9]*")){JOptionPane.showMessageDialog(this,"Pole numer sali musi być liczba całkowita","Warning",JOptionPane.WARNING_MESSAGE); wykonaj = false;}
                if(wykonaj) {
                    if(!salaEntity.getNumerSali().toString().equals(table.getValueAt(row,2).toString())){
                        String query = "from SalaEntity where numerSali="+table.getValueAt(row,2).toString();
                        List lista = session.createQuery(query).list();
                        if(lista.size() != 0) {
                            JOptionPane.showMessageDialog(this,"Sala o takim numerze już istnieje!","Warning",JOptionPane.WARNING_MESSAGE);
                            wykonaj = false;
                        }
                    }
                    if(wykonaj) {
                        salaCRUD.updateUlatwienia(id1, strToBool(table.getValueAt(row, 0).toString()));
                        salaCRUD.update3D(id1, strToBool(table.getValueAt(row, 1).toString()));
                        salaCRUD.updateNumerSali(id1, Integer.parseInt(table.getValueAt(row, 2).toString()));
                        JOptionPane.showMessageDialog(this, "Update wykonany!", "OK", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }

    }
    public boolean strToBool(String str){
        if(str.equals("false") || str.equals("nie")) return false;
        else return true;
    }
}
