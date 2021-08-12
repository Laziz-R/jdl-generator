package com.intern.task.model.jdl.type;

import com.intern.task.model.jdl.type.Type;

public class Type {
    private String jvName;
    private String  pgName;
    private String swName;
    private String library;
    private boolean unknown = false;

    public Type(String jvName, String pgName, String swName, String library){
        this.jvName = jvName;
        this.pgName = pgName;
        this.swName = swName;
        this.library = library;
    }

    public Type(String jvName){
        this.jvName = jvName;
        for(Type type: TypeList.TYPES)
            if(jvName.equals(type.jvName)){
                this.pgName = type.pgName;
                this.swName = type.swName;
                this.library = type.library;
            }
        if(pgName == null){
            this.unknown = true;
            this.pgName = "TEXT";
            this.swName = "string";
        }
    }

    public String getJvName() {
        return this.jvName;
    }

    public void setJvName(String jvName) {
        this.jvName = jvName;
    }

    public String getPgName() {
        return this.pgName;
    }

    public void setPgName(String pgName) {
        this.pgName = pgName;
    }

    public String getSwName() {
        return this.swName;
    }

    public void setSwName(String swName) {
        this.swName = swName;
    }

    public String getLibrary() {
        return this.library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }
    
    public boolean isUnknown() {
        return unknown;
    }
}
