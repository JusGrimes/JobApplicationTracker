package JustinGrimes;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Properties;

public class AddDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel inputPanel;
    private JTextField companyNameInput;
    private JTextField statusInput;
    private JTextPane notesInput;
    private JDatePickerImpl dateInput;
    private JLabel notesLabel;
    private JLabel statusLabel;
    private JLabel dateLabel;
    private JLabel companyNameLabel;

    private JobApplication addedJobApp = null;

    public AddDialog() {
        setUp();
    }


    public AddDialog(Frame owner) {
        super(owner);
        setUp();
    }

    private void setUp(){
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,
                        0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        Dimension size = new Dimension(300, 270);
        setResizable(false);
        setSize(size);
        setTitle("Add Application");
        setLocationRelativeTo(getOwner());

    }

    private void createUIComponents() {
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        dateInput = new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel(), p), new DateLabelFormatter());
        notesInput = new JTextPane();

        // sets notes border to default of the current look and feel
        notesInput.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
    }

    public JobApplication getValue() {
        return addedJobApp;
    }

    private void onOK() {
        // add your code here
        try{
            addedJobApp = new JobApplication(
                    companyNameInput.getText(),
                    LocalDate.parse(dateInput.getJFormattedTextField().getText()),
                    JobStatus.valueOf(statusInput.getText()),
                    notesInput.getText()
            );
        } catch (DateTimeParseException e) {
            System.out.println("invalid Date");
        } catch (IllegalArgumentException e){
            System.out.println("Invalid data");
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        AddDialog dialog = new AddDialog();
        dialog.setTitle("Add Application");
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
