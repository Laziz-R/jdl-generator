package com.safenetpay.task.model.list;

import com.safenetpay.task.model.JobHistory;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;

public class JobHistoryList extends ArrayList<JobHistory> {

    private static final long serialVersionUID = 1L;
    private final String label = "jobHistoryList";

    public String getLabel() {
        return label;
    }

    public JsonArray toJsonArray(){
        JsonArray array = new JsonArray();
        this.forEach(e -> array.add(JsonObject.mapFrom(e)));
        return array;
    }

}