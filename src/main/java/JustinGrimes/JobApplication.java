package JustinGrimes;

import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobApplication that = (JobApplication) o;
        return companyName.equals(that.companyName) &&
                applicationDate.equals(that.applicationDate) &&
                status == that.status &&
                notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName, applicationDate, status, notes);
    }
}
