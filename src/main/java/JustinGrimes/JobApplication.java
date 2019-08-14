package JustinGrimes;

import java.time.LocalDate;

public class JobApplication {
    private int id;
    private String companyName;
    private LocalDate applicationDate;
    private JobStatus status;
    private String notes;

    public JobApplication(String companyName, LocalDate applicationDate, JobStatus status, String notes) {
        this.companyName = companyName;
        this.applicationDate = applicationDate;
        this.status = status;
        this.notes = notes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public JobStatus getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }
}
