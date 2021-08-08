package com.intern.task.model.jdl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Entity {
    private List<String> javadoc;
    private List<String> annotations;
    private Name name;
    private String tableName;
    private List<Field> fields = new ArrayList<>();

    public Set<String> getLibraries(){
        Set<String> libraries = new HashSet<>();
        for(Field field: fields){
            String lib = field.getType().getJLibrary();
            if(lib!=null)
                libraries.add(lib);
        }        
        return libraries;
    }

    public List<String> getJavadoc() {
        return this.javadoc;
    }

    public Entity setJavadoc(List<String> javadoc) {
        this.javadoc = javadoc;
        return this;
    }

    public List<String> getAnnotations() {
        return this.annotations;
    }

    public Entity setAnnotations(List<String> annotations) {
        this.annotations = annotations;
        return this;
    }

    public Name getName() {
        return this.name;
    }

    public Entity setName(Name name) {
        this.name = name;
        return this;
    }

    public List<Field> getFields() {
        return this.fields;
    }

    public Entity setFields(List<Field> fields) {
        this.fields = fields;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public Entity setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }
}
