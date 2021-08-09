package com.safenetpay.task.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
* The type JobTask
*
* @author laziz
* @since 2021-08-09
*/
public class JobTask extends BaseDataContract {

    public static final String JOB_TASK = "job_task";

    public static final String JOB_TASK_ID = "job_task_id";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(JOB_TASK_ID)
    private Long jobTaskId;

    public static final String JOB_TITLE = "job_title";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(JOB_TITLE)
    private String jobTitle;

    public static final String TITLE = "title";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(TITLE)
    private String title;

    /**
    * jobTaskId getter.
    *
    * @return the jobTaskId
    */
    public Long getJobTaskId() {
        return this.jobTaskId;
    }

    /**
    * * jobTaskId setter.
    *
    * @param jobTaskId the jobTaskId to set
    * @return this
    */
    public JobTask setJobTaskId(Long jobTaskId) {
        this.jobTaskId = jobTaskId;
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
    public JobTask setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    /**
    * title getter.
    *
    * @return the title
    */
    public String getTitle() {
        return this.title;
    }

    /**
    * title setter.
    *
    * @param title the title to set
    * @return this
    */
    public JobTask setTitle(String title) {
        this.title = title;
        return this;
    }


    /*
    * (non-Javadoc)
    *
    * @see java.lang.Object#hashCode()
    */
    @Override
    public int hashCode() {
        return Objects.hash(jobTitle, title,  jobTaskId);
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
        JobTask other = (JobTask) obj;
        return
            Objects.equals(jobTitle, other.jobTitle) &&
            Objects.equals(title, other.title) &&
            Objects.equals(jobTaskId, other.jobTaskId);
    }
}