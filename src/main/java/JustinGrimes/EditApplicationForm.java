package JustinGrimes;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Properties;

public class EditApplicationForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel inputPanel;
    private JTextField companyNameInput;
    private JTextPane notesInput;
    private JDatePickerImpl dateInput;
    private JLabel notesLabel;
    private JLabel statusLabel;
    private JLabel dateLabel;
    private JLabel companyNameLabel;
    private JComboBox<JobStatus> statusComboBox;
    private JobApplication currentApplication = null;

    public EditApplicationForm() {
        setUp();
    }


    public EditApplicationForm(Frame owner) {
        super(owner);
        setUp();
    }

    public EditApplicationForm(Frame owner, JobApplication application) {
        super(owner);
        setUp();
        currentApplication = application;
    }


    private void setUp(){
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        Dimension size = new Dimension(300, 270);
        setResizable(false);
        setSize(size);
        setTitle("Add Application");
        setLocationRelativeTo(getOwner());


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

        // prefill will current records info
        if (currentApplication != null) {
            companyNameInput.setText(currentApplication.getCompanyName());
            LocalDate appDate = currentApplication.getApplicationDate();
            dateInput.getModel().setDate(appDate.getYear(), appDate.getMonthValue(), appDate.getDayOfMonth());
            statusComboBox.setSelectedItem(currentApplication.getStatus());
            notesInput.setText(currentApplication.getNotes());
        }

    }

    private void createUIComponents() {
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");



        UtilDateModel dateModel = new UtilDateModel();
        dateInput = new JDatePickerImpl(new JDatePanelImpl(dateModel, p), new DateLabelFormatter());
        dateModel.setValue(Date.from(Instant.now()));

        notesInput = new JTextPane();

        // sets notes border to default of the current look and feel
        notesInput.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));

        statusComboBox = new JComboBox<>(JobStatus.values());
    }

    public JobApplication getValue() {
        return currentApplication;
    }

    private void onOK() {
        // add your code here
        try {
            currentApplication = new JobApplication(
                    companyNameInput.getText(),
                    LocalDate.parse(dateInput.getJFormattedTextField().getText()),
                    (JobStatus) statusComboBox.getSelectedItem(),
                    notesInput.getText()
            );
        } catch (DateTimeParseException | IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println("invalid Date");
        }

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        EditApplicationForm dialog = new EditApplicationForm();
        dialog.setTitle("Add Application");
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
