package com.safenetpay.task.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
* The type Region
*
* @author laziz
* @since 2021-08-09
*/
public class Region extends BaseDataContract {

    public static final String REGION = "region";

    public static final String REGION_ID = "region_id";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(REGION_ID)
    private Long regionId;

    public static final String REGION_NAME = "region_name";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(REGION_NAME)
    private String regionName;

    /**
    * regionId getter.
    *
    * @return the regionId
    */
    public Long getRegionId() {
        return this.regionId;
    }

    /**
    * * regionId setter.
    *
    * @param regionId the regionId to set
    * @return this
    */
    public Region setRegionId(Long regionId) {
        this.regionId = regionId;
        return this;
    }

    /**
    * regionName getter.
    *
    * @return the regionName
    */
    public String getRegionName() {
        return this.regionName;
    }

    /**
    * regionName setter.
    *
    * @param regionName the regionName to set
    * @return this
    */
    public Region setRegionName(String regionName) {
        this.regionName = regionName;
        return this;
    }


    /*
    * (non-Javadoc)
    *
    * @see java.lang.Object#hashCode()
    */
    @Override
    public int hashCode() {
        return Objects.hash(regionName,  regionId);
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
        Region other = (Region) obj;
        return
            Objects.equals(regionName, other.regionName) &&
            Objects.equals(regionId, other.regionId);
    }
}