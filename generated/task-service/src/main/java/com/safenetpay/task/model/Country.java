package com.safenetpay.task.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
* The type Country
*
* @author laziz
* @since 2021-08-09
*/
public class Country extends BaseDataContract {

    public static final String COUNTRY = "country";

    public static final String COUNTRY_ID = "country_id";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(COUNTRY_ID)
    private Long countryId;

    public static final String COUNTRY_NAME = "country_name";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(COUNTRY_NAME)
    private String countryName;

    public static final String REGION_ID = "region_id";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(REGION_ID)
    private Long regionId;

    /**
    * countryId getter.
    *
    * @return the countryId
    */
    public Long getCountryId() {
        return this.countryId;
    }

    /**
    * * countryId setter.
    *
    * @param countryId the countryId to set
    * @return this
    */
    public Country setCountryId(Long countryId) {
        this.countryId = countryId;
        return this;
    }

    /**
    * countryName getter.
    *
    * @return the countryName
    */
    public String getCountryName() {
        return this.countryName;
    }

    /**
    * countryName setter.
    *
    * @param countryName the countryName to set
    * @return this
    */
    public Country setCountryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    /**
    * regionId getter.
    *
    * @return the regionId
    */
    public Long getRegionId() {
        return this.regionId;
    }

    /**
    * regionId setter.
    *
    * @param regionId the regionId to set
    * @return this
    */
    public Country setRegionId(Long regionId) {
        this.regionId = regionId;
        return this;
    }


    /*
    * (non-Javadoc)
    *
    * @see java.lang.Object#hashCode()
    */
    @Override
    public int hashCode() {
        return Objects.hash(countryName, regionId,  countryId);
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
        Country other = (Country) obj;
        return
            Objects.equals(countryName, other.countryName) &&
            Objects.equals(regionId, other.regionId) &&
            Objects.equals(countryId, other.countryId);
    }
}