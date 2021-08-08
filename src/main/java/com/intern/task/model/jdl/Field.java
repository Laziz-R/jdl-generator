package com.intern.task.model.jdl;

import java.util.List;

public class  Field {
    private List<String> javadoc;
    private List<String> annotations;
    private Type type;
    private Name name;
    private List<String> validations;

    public List<String> getJavadoc() {
        return this.javadoc;
    }

    public Field setJavadoc(List<String> javadoc) {
        this.javadoc = javadoc;
        return this;
    }

    public List<String> getAnnotations() {
        return this.annotations;
    }

    public Field setAnnotations(List<String> annotations) {
        this.annotations = annotations;
        return this;
    }

    public Type getType() {
        return this.type;
    }

    public Field setType(Type type) {
        this.type = type;
        return this;
    }

    public Name getName() {
        return this.name;
    }

    public Field setName(Name name) {
        this.name = name;
        return this;
    }

    public List<String> getValidations() {
        return this.validations;
    }

    public Field setValidations(List<String> validations) {
        this.validations = validations;
        return this;
    }
}
