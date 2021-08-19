package com.intern.task.model.jdl;

import java.util.List;

import com.intern.task.model.jdl.type.Type;

public class  Field {
    private List<String> javadoc;
    private List<String> annotations;
    private Type type;
    private Name name;
    private Validation validation;

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
    
    public Validation getValidation() {
        return validation;
    }

    public void setValidation(Validation validation) {
        this.validation = validation;
    }
}
