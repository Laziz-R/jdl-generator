package com.safenetpay.task.model.list;

import com.safenetpay.task.model.BookAuthor;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;

public class BookAuthorList extends ArrayList<BookAuthor> {

    private static final long serialVersionUID = 1L;
    private final String label = "bookAuthorList";

    public String getLabel() {
        return label;
    }

    public JsonArray toJsonArray(){
        JsonArray array = new JsonArray();
        this.forEach(e -> array.add(JsonObject.mapFrom(e)));
        return array;
    }

}