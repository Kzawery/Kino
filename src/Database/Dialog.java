package Database;

import javax.swing.*;

public class Dialog extends JFrame {
    Dialog(int type,String msg,String title){
        if(type == 1){
            JOptionPane.showMessageDialog(this,msg,title,JOptionPane.WARNING_MESSAGE);
        }
    }
}
