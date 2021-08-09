package com.safenetpay.task.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
* The type Location
*
* @author laziz
* @since 2021-08-09
*/
public class Location extends BaseDataContract {

    public static final String LOCATION = "location";

    public static final String LOCATION_ID = "location_id";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(LOCATION_ID)
    private Long locationId;

    public static final String STREET_ADDRESS = "street_address";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(STREET_ADDRESS)
    private String streetAddress;

    public static final String POSTAL_CODE = "postal_code";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(POSTAL_CODE)
    private String postalCode;

    public static final String CITY = "city";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(CITY)
    private String city;

    public static final String STATE_PROVINCE = "state_province";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(STATE_PROVINCE)
    private String stateProvince;

    public static final String COUNTRY_ID = "country_id";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(COUNTRY_ID)
    private Long countryId;

    /**
    * locationId getter.
    *
    * @return the locationId
    */
    public Long getLocationId() {
        return this.locationId;
    }

    /**
    * * locationId setter.
    *
    * @param locationId the locationId to set
    * @return this
    */
    public Location setLocationId(Long locationId) {
        this.locationId = locationId;
        return this;
    }

    /**
    * streetAddress getter.
    *
    * @return the streetAddress
    */
    public String getStreetAddress() {
        return this.streetAddress;
    }

    /**
    * streetAddress setter.
    *
    * @param streetAddress the streetAddress to set
    * @return this
    */
    public Location setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    /**
    * postalCode getter.
    *
    * @return the postalCode
    */
    public String getPostalCode() {
        return this.postalCode;
    }

    /**
    * postalCode setter.
    *
    * @param postalCode the postalCode to set
    * @return this
    */
    public Location setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    /**
    * city getter.
    *
    * @return the city
    */
    public String getCity() {
        return this.city;
    }

    /**
    * city setter.
    *
    * @param city the city to set
    * @return this
    */
    public Location setCity(String city) {
        this.city = city;
        return this;
    }

    /**
    * stateProvince getter.
    *
    * @return the stateProvince
    */
    public String getStateProvince() {
        return this.stateProvince;
    }

    /**
    * stateProvince setter.
    *
    * @param stateProvince the stateProvince to set
    * @return this
    */
    public Location setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
        return this;
    }

    /**
    * countryId getter.
    *
    * @return the countryId
    */
    public Long getCountryId() {
        return this.countryId;
    }

    /**
    * countryId setter.
    *
    * @param countryId the countryId to set
    * @return this
    */
    public Location setCountryId(Long countryId) {
        this.countryId = countryId;
        return this;
    }


    /*
    * (non-Javadoc)
    *
    * @see java.lang.Object#hashCode()
    */
    @Override
    public int hashCode() {
        return Objects.hash(streetAddress, postalCode, city, stateProvince, countryId,  locationId);
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
        Location other = (Location) obj;
        return
            Objects.equals(streetAddress, other.streetAddress) &&
            Objects.equals(postalCode, other.postalCode) &&
            Objects.equals(city, other.city) &&
            Objects.equals(stateProvince, other.stateProvince) &&
            Objects.equals(countryId, other.countryId) &&
            Objects.equals(locationId, other.locationId);
    }
}