package com.safenetpay.task.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;
import java.time.LocalDate;

/**
* The type JobHistory
*
* @author laziz
* @since 2021-08-09
*/
public class JobHistory extends BaseDataContract {

    public static final String JOB_HISTORY = "job_history";

    public static final String JOB_HISTORY_ID = "job_history_id";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(JOB_HISTORY_ID)
    private Long jobHistoryId;

    public static final String START_DATE = "start_date";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(START_DATE)
    private LocalDate startDate;

    public static final String END_DATE = "end_date";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(END_DATE)
    private LocalDate endDate;

    public static final String LANGUAGE = "language";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(LANGUAGE)
    private Language language;

    public static final String JOB_ID = "job_id";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(JOB_ID)
    private Long jobId;

    public static final String DEPARTMENT_ID = "department_id";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(DEPARTMENT_ID)
    private Long departmentId;

    public static final String EMPLOYEE_ID = "employee_id";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(EMPLOYEE_ID)
    private Long employeeId;

    /**
    * jobHistoryId getter.
    *
    * @return the jobHistoryId
    */
    public Long getJobHistoryId() {
        return this.jobHistoryId;
    }

    /**
    * * jobHistoryId setter.
    *
    * @param jobHistoryId the jobHistoryId to set
    * @return this
    */
    public JobHistory setJobHistoryId(Long jobHistoryId) {
        this.jobHistoryId = jobHistoryId;
        return this;
    }

    /**
    * startDate getter.
    *
    * @return the startDate
    */
    public LocalDate getStartDate() {
        return this.startDate;
    }

    /**
    * startDate setter.
    *
    * @param startDate the startDate to set
    * @return this
    */
    public JobHistory setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    /**
    * endDate getter.
    *
    * @return the endDate
    */
    public LocalDate getEndDate() {
        return this.endDate;
    }

    /**
    * endDate setter.
    *
    * @param endDate the endDate to set
    * @return this
    */
    public JobHistory setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    /**
    * language getter.
    *
    * @return the language
    */
    public Language getLanguage() {
        return this.language;
    }

    /**
    * language setter.
    *
    * @param language the language to set
    * @return this
    */
    public JobHistory setLanguage(Language language) {
        this.language = language;
        return this;
    }

    /**
    * jobId getter.
    *
    * @return the jobId
    */
    public Long getJobId() {
        return this.jobId;
    }

    /**
    * jobId setter.
    *
    * @param jobId the jobId to set
    * @return this
    */
    public JobHistory setJobId(Long jobId) {
        this.jobId = jobId;
        return this;
    }

    /**
    * departmentId getter.
    *
    * @return the departmentId
    */
    public Long getDepartmentId() {
        return this.departmentId;
    }

    /**
    * departmentId setter.
    *
    * @param departmentId the departmentId to set
    * @return this
    */
    public JobHistory setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
        return this;
    }

    /**
    * employeeId getter.
    *
    * @return the employeeId
    */
    public Long getEmployeeId() {
        return this.employeeId;
    }

    /**
    * employeeId setter.
    *
    * @param employeeId the employeeId to set
    * @return this
    */
    public JobHistory setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
        return this;
    }


    /*
    * (non-Javadoc)
    *
    * @see java.lang.Object#hashCode()
    */
    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, language, jobId, departmentId, employeeId,  jobHistoryId);
    }

    /*
    * (non-Javadoc)
    *
    * @see java.lang.Object#equals(java.lang.Object)
    */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        JobHistory other = (JobHistory) obj;
        return
            Objects.equals(startDate, other.startDate) &&
            Objects.equals(endDate, other.endDate) &&
            Objects.equals(language, other.language) &&
            Objects.equals(jobId, other.jobId) &&
            Objects.equals(departmentId, other.departmentId) &&
            Objects.equals(employeeId, other.employeeId) &&
            Objects.equals(jobHistoryId, other.jobHistoryId);
    }
}