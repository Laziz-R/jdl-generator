package com.safenetpay.task.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
* The type Job
*
* @author laziz
* @since 2021-08-09
*/
public class Job extends BaseDataContract {

    public static final String JOB = "job";

    public static final String JOB_ID = "job_id";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(JOB_ID)
    private Long jobId;

    public static final String JOB_TITLE = "job_title";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(JOB_TITLE)
    private String jobTitle;

    public static final String MIN_SALARY = "min_salary";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(MIN_SALARY)
    private Long minSalary;

    public static final String MAX_SALARY = "max_salary";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(MAX_SALARY)
    private Long maxSalary;

    public static final String EMPLOYEE_ID = "employee_id";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(EMPLOYEE_ID)
    private Long employeeId;

    /**
    * jobId getter.
    *
    * @return the jobId
    */
    public Long getJobId() {
        return this.jobId;
    }

    /**
    * * jobId setter.
    *
    * @param jobId the jobId to set
    * @return this
    */
    public Job setJobId(Long jobId) {
        this.jobId = jobId;
        return this;
    }

    /**
    * jobTitle getter.
    *
    * @return the jobTitle
    */
    public String getJobTitle() {
        return this.jobTitle;
    }

    /**
    * jobTitle setter.
    *
    * @param jobTitle the jobTitle to set
    * @return this
    */
    public Job setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    /**
    * minSalary getter.
    *
    * @return the minSalary
    */
    public Long getMinSalary() {
        return this.minSalary;
    }

    /**
    * minSalary setter.
    *
    * @param minSalary the minSalary to set
    * @return this
    */
    public Job setMinSalary(Long minSalary) {
        this.minSalary = minSalary;
        return this;
    }

    /**
    * maxSalary getter.
    *
    * @return the maxSalary
    */
    public Long getMaxSalary() {
        return this.maxSalary;
    }

    /**
    * maxSalary setter.
    *
    * @param maxSalary the maxSalary to set
    * @return this
    */
    public Job setMaxSalary(Long maxSalary) {
        this.maxSalary = maxSalary;
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
    public Job setEmployeeId(Long employeeId) {
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
        return Objects.hash(jobTitle, minSalary, maxSalary, employeeId,  jobId);
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
        Job other = (Job) obj;
        return
            Objects.equals(jobTitle, other.jobTitle) &&
            Objects.equals(minSalary, other.minSalary) &&
            Objects.equals(maxSalary, other.maxSalary) &&
            Objects.equals(employeeId, other.employeeId) &&
            Objects.equals(jobId, other.jobId);
    }
}