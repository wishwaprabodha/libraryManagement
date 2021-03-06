package Controller;

import DataAccess.GuaranterPersistantDAO;
import Model.Guaranter;
import View.AppDetails;
import View.AddGuaranterWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Wishwa
 */
public class AddGuarantorWindowControl {

    GuaranterPersistantDAO gua;
    AddGuaranterWindow window;

    public AddGuarantorWindowControl(AddGuaranterWindow window) {

        gua = new GuaranterPersistantDAO();
        this.window = window;
        initialize();
        setID();
        control();
    }

    public void control() {

        window.getBtnSubmit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initialize();
                SignUp();
            }
        });

        window.getBtnHome().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Home();
            }
        });

        window.getTxtContactNumber().addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                if (!txtValidate(window.getTxtContactNumber().getText(), e.getKeyChar(), 10)) {
                    e.consume();
                }
            }
        });
        
          window.getTxt_memberId().addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                if (!txtValidate(e.getKeyChar())) {
                    e.consume();
                }
            }
        });

    }

    private void Home() {
        window.dispose();

    }

    private boolean txtValidate(char c) {
        return Character.isDigit(c);
    }

    private void SignUp() {

        int id = Integer.parseInt(window.getLblId().getText());
        String name = window.getTxtName().getText();
        String contactno = window.getTxtContactNumber().getText();
        String address = window.getTxtAddress().getText();
        String fk_memberId = window.getTxt_memberId().getText();

        Guaranter guaranter = new Guaranter(id, name, contactno, address, fk_memberId);
        gua.addGuarantor(guaranter);

        if (guaranter != null) {
            JOptionPane.showMessageDialog(window.getComponent(0), "Guarantor Added!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {

            JOptionPane.showMessageDialog(window.getComponent(0), "Guarantor not Added!", "Unsuccessful", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean txtValidate(String text, char c, int size) {
        if (!Character.isDigit(c)) {
            return false;
        }
        return ((text + c).length() <= size);
    }

    private boolean idValidate(char c) {
        return Character.isDigit(c);
    }

    private void setID() {
        window.getLblId().setText(String.valueOf(gua.setId() + 1));
    }

    private void initialize() {

        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        Date d = new Date();
                        SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yyyy");
                        String stDate = sd.format(d);
                        String strTime = d.toString();
                        String[] time = strTime.split(" ");
                        window.getLblTime().setText(time[3]);
                        window.getLblDate().setText(stDate);
                        window.getLblWelcome().setText("Welcome: " + AppDetails.loggedOnUser.getUserName());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {

                        }
                    }
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
