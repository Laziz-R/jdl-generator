package com.safenetpay.task.model.list;

import com.safenetpay.task.model.Country;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;

public class CountryList extends ArrayList<Country> {

    private static final long serialVersionUID = 1L;
    private final String label = "countryList";

    public String getLabel() {
        return label;
    }

    public JsonArray toJsonArray(){
        JsonArray array = new JsonArray();
        this.forEach(e -> array.add(JsonObject.mapFrom(e)));
        return array;
    }

}