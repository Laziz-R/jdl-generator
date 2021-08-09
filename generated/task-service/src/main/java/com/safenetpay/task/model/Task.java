package com.safenetpay.task.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
* The type Task
*
* @author laziz
* @since 2021-08-09
*/
public class Task extends BaseDataContract {

    public static final String TASK = "task";

    public static final String TASK_ID = "task_id";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(TASK_ID)
    private Long taskId;

    public static final String TITLE = "title";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(TITLE)
    private String title;

    public static final String DESCRIPTION = "description";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(DESCRIPTION)
    private String description;

    /**
    * taskId getter.
    *
    * @return the taskId
    */
    public Long getTaskId() {
        return this.taskId;
    }

    /**
    * * taskId setter.
    *
    * @param taskId the taskId to set
    * @return this
    */
    public Task setTaskId(Long taskId) {
        this.taskId = taskId;
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
    public Task setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
    * description getter.
    *
    * @return the description
    */
    public String getDescription() {
        return this.description;
    }

    /**
    * description setter.
    *
    * @param description the description to set
    * @return this
    */
    public Task setDescription(String description) {
        this.description = description;
        return this;
    }


    /*
    * (non-Javadoc)
    *
    * @see java.lang.Object#hashCode()
    */
    @Override
    public int hashCode() {
        return Objects.hash(title, description,  taskId);
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
        Task other = (Task) obj;
        return
            Objects.equals(title, other.title) &&
            Objects.equals(description, other.description) &&
            Objects.equals(taskId, other.taskId);
    }
}