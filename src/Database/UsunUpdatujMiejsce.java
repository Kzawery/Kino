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

public class UsunUpdatujMiejsce extends JFrame implements ActionListener {
    PracownikKinaEntity pracownikKinaEntity;
    AdministratorEntity administratorEntity;
    MiejsceCRUD miejsceCRUD = new MiejsceCRUD();
    JButton returnBtn, delBtn, updateBtn;
    JTable table;
    Object[] seansObj;
    DefaultTableModel dtm, dtm2;
    ObservableList<MiejsceEntity> miejsca;
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

    public UsunUpdatujMiejsce(AdministratorEntity administratorEntity) {
        this.administratorEntity = administratorEntity;
        build();
    }

    public UsunUpdatujMiejsce(PracownikKinaEntity pracownikKinaEntity) {
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
        dtm.setColumnIdentifiers(new Object[]{"Rzad", "Numer Miejsca", "Czy Kanapa", "Numer Sali"});
        table = new JTable();
        table.setModel(dtm);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        String myquery = "from MiejsceEntity ";
        List lista = session.createQuery(myquery).list();
        miejsca = FXCollections.observableArrayList(lista);
        for (MiejsceEntity miejsce : miejsca) {
            SalaEntity salaEntity = miejsce.getIdSali();
            seansObj = new Object[]{miejsce.getRzad(),miejsce.getNumerMiejsca(),miejsce.getCzyKanapa(), salaEntity.getNumerSali()};
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
                MiejsceEntity miejsceEntity = miejsca.get(row);
                miejsceCRUD.delete(miejsceEntity.getIdMiejsca());
                JOptionPane.showMessageDialog(this,"Nie można usunąć tabelki ze względu na powiązania" ,"Warning",JOptionPane.WARNING_MESSAGE);
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
                MiejsceEntity miejsceEntity = miejsca.get(row);
                int id1 = miejsceEntity.getIdMiejsca();
                boolean wykonaj = true;
                if(table.getValueAt(row,0).toString().matches("[A-Z]")){miejsceCRUD.updateRzad(id1,table.getValueAt(row,0).toString());}else {JOptionPane.showMessageDialog(this, "Rzad musi być wartością pomiędzy A-Z","Warning",JOptionPane.WARNING_MESSAGE); wykonaj = false;}
                if(table.getValueAt(row,1).toString().matches("[0-9]*")){miejsceCRUD.updateNumerMiejsca(id1,Integer.parseInt(table.getValueAt(row,1).toString()));}else {JOptionPane.showMessageDialog(this, "Numer miejsca musi być wartością liczba całkowita","Warning",JOptionPane.WARNING_MESSAGE); wykonaj = false;}
                if(table.getValueAt(row,2).toString().matches("(true|false)")){ miejsceCRUD.updateKanapa(id1, strToBool(table.getValueAt(row,2).toString()));}else{JOptionPane.showMessageDialog(this, "Czy kanapa musi być albo true albo false","Warning",JOptionPane.WARNING_MESSAGE); wykonaj = false;}
                if(!table.getValueAt(row,3).toString().matches("[0-9]*")){JOptionPane.showMessageDialog(this, "Numer sali musi być liczba całkowita","Warning",JOptionPane.WARNING_MESSAGE); wykonaj = false;}
                if(wykonaj){
                    String myquery = "from SalaEntity where numerSali="+table.getValueAt(row,3).toString();
                    List lista = session.createQuery(myquery.toString()).list();
                    if(lista.size() != 0){
                        myquery = "from MiejsceEntity";
                        List lista2 = session.createQuery(myquery).list();
                        ObservableList<MiejsceEntity> miejsceEntities = FXCollections.observableArrayList(lista2);
                        for(MiejsceEntity miejsceEntity1: miejsceEntities){
                            if(miejsceEntity1.getRzad().equals(table.getValueAt(row,0))  && miejsceEntity1.getNumerMiejsca() == Integer.parseInt(table.getValueAt(row,1).toString()) && miejsceEntity1.getIdSali().getNumerSali().equals(table.getValueAt(row,3))){
                                JOptionPane.showMessageDialog(this, "Takie miejsce juz istnieje!","Warning",JOptionPane.WARNING_MESSAGE);
                                wykonaj=false;
                            }
                        }
                        if(wykonaj) {
                            ObservableList<SalaEntity> salaEntities = FXCollections.observableArrayList(lista);
                            miejsceCRUD.updateIdSali(id1, salaEntities.get(0));
                            JOptionPane.showMessageDialog(this, "Update udany!", "OK", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }else {
                        JOptionPane.showMessageDialog(this, "Nie ma sali o takim numerze","Warning",JOptionPane.WARNING_MESSAGE);
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
