package JustinGrimes;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Map;

public class AppTableModel extends AbstractTableModel {

    private final String[] columnNames = {"Company Name", "Application Date", "Status", "Notes"};
    private final List<JobApplication> applications;

    private final Map<Integer, fieldMapping> colIdToFieldMap = Map.of(
            0, fieldMapping.COMPANY_NAME,
            1, fieldMapping.APP_DATE,
            2, fieldMapping.JOB_STATUS,
            3, fieldMapping.NOTES
    );


    private enum fieldMapping {
        COMPANY_NAME,
        APP_DATE,
        JOB_STATUS,
        NOTES
    }

    public AppTableModel(List<JobApplication> applications) {
        this.applications = applications;
    }

    private Object colIndexToField(JobApplication application, int col) {
        fieldMapping currentField = colIdToFieldMap.get(col);
        switch (currentField) {

            case COMPANY_NAME:
                return application.getCompanyName();
            case APP_DATE:
                return application.getApplicationDate();
            case JOB_STATUS:
                return application.getStatus();
            case NOTES:
                return application.getNotes();
            default:
                return "Bad Data";
        }
    }

    @Override
    public int getRowCount() {
        return applications.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return colIndexToField(applications.get(rowIndex), columnIndex);
    }
}
