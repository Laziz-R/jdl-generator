package com.intern.task.model.jdl;

import java.util.ArrayList;
import java.util.List;

public class Enum {
    private List<String> javadoc;
    private Name name;
    private List<String> fields = new ArrayList<>();

    public Name getName() {
        return this.name;
    }

    public Enum setName(Name name) {
        this.name = name;
        return this;
    }

    public List<String> getFields() {
        return this.fields;
    }

    public Enum setFields(List<String> fields) {
        this.fields = fields;
        return this;
    }

    public List<String> getJavadoc() {
        return javadoc;
    }

    public Enum setJavadoc(List<String> javadoc) {
        this.javadoc = javadoc;
        return this;
    }

}
