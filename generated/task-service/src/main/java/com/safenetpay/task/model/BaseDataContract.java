package com.safenetpay.task.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;
import io.vertx.core.json.JsonObject;
import java.sql.Date;
import java.sql.Timestamp;

/**
* Base data contract.
*
* @author laziz
* @since 2021-08-09
*/
public abstract class BaseDataContract {

    @Expose(serialize = false, deserialize = false)
    private Timestamp createdOn;

    @Expose(serialize = false, deserialize = false)
    private Long createdBy;

    @Expose(serialize = false, deserialize = false)
    private Timestamp modifiedOn;

    @Expose(serialize = false, deserialize = false)
    private Long modifiedBy;

    @Expose(serialize = false, deserialize = false)
    private Timestamp deletedOn;

    @Expose(serialize = false, deserialize = false)
    private Long deletedBy;

    @Expose(serialize = false, deserialize = false)
    private Boolean deleted;

    public Timestamp getCreatedOn() {
        return this.createdOn;
    }

    public BaseDataContract setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public BaseDataContract setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Timestamp getModifiedOn() {
        return this.modifiedOn;
    }

    public BaseDataContract setModifiedOn(Timestamp modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public Long getModifiedBy() {
        return this.modifiedBy;
    }

    public BaseDataContract setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public Timestamp getDeletedOn() {
        return this.deletedOn;
    }

    public BaseDataContract setDeletedOn(Timestamp deletedOn) {
        this.deletedOn = deletedOn;
        return this;
    }

    public Long getDeletedBy() {
        return this.deletedBy;
    }

    public BaseDataContract setDeletedBy(Long deletedBy) {
        this.deletedBy = deletedBy;
        return this;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public BaseDataContract setDeleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    JsonSerializer<Date> ser =
        (src, typeOfSrc, context) -> src == null ? null : new JsonPrimitive(src.toString());

    /**
    * Converting object.
    *
    * @return JSonObject
    */
    public JsonObject toJsonObject() {
        Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(java.sql.Date.class, ser)
            .serializeNulls()
            .setDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ")
            .create();
        return new JsonObject(gson.toJson(this));
    }

    /**
    * Converting Object.
    *
    * @param <T> - class
        * @param param - Json to Convert
        * @param type - class type
        * @return
        */
    public static <T extends BaseDataContract> T fromJsonObject(String param, Class<T> type) {
            Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ")
            .create();
            return type.cast(gson.fromJson(param, type));
    }
}
