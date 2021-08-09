package com.safenetpay.task.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
* The type Department
*
* @author laziz
* @since 2021-08-09
*/
public class Department extends BaseDataContract {

    public static final String DEPARTMENT = "department";

    public static final String DEPARTMENT_ID = "department_id";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(DEPARTMENT_ID)
    private Long departmentId;

    public static final String DEPARTMENT_NAME = "department_name";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(DEPARTMENT_NAME)
    private String departmentName;

    public static final String LOCATION_ID = "location_id";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(LOCATION_ID)
    private Long locationId;

    /**
    * departmentId getter.
    *
    * @return the departmentId
    */
    public Long getDepartmentId() {
        return this.departmentId;
    }

    /**
    * * departmentId setter.
    *
    * @param departmentId the departmentId to set
    * @return this
    */
    public Department setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
        return this;
    }

    /**
    * departmentName getter.
    *
    * @return the departmentName
    */
    public String getDepartmentName() {
        return this.departmentName;
    }

    /**
    * departmentName setter.
    *
    * @param departmentName the departmentName to set
    * @return this
    */
    public Department setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
        return this;
    }

    /**
    * locationId getter.
    *
    * @return the locationId
    */
    public Long getLocationId() {
        return this.locationId;
    }

    /**
    * locationId setter.
    *
    * @param locationId the locationId to set
    * @return this
    */
    public Department setLocationId(Long locationId) {
        this.locationId = locationId;
        return this;
    }


    /*
    * (non-Javadoc)
    *
    * @see java.lang.Object#hashCode()
    */
    @Override
    public int hashCode() {
        return Objects.hash(departmentName, locationId,  departmentId);
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
        Department other = (Department) obj;
        return
            Objects.equals(departmentName, other.departmentName) &&
            Objects.equals(locationId, other.locationId) &&
            Objects.equals(departmentId, other.departmentId);
    }
}