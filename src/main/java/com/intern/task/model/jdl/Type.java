package com.intern.task.model.jdl;

import com.intern.task.model.jdl.list.TypeList;
import com.intern.task.util.CaseUtil;

public class Type {
    private String jvName;
    private String pgName;
    private String swName;
    private String jLibrary;

    public Type(String jvName, String pgName, String swName, String jLibrary){
        this.jvName = jvName;
        this.pgName = pgName;
        this.swName = swName;
        this.jLibrary = jLibrary;
    }

    public Type(String jvName){
        this.jvName = jvName;
        for(Type type: TypeList.TYPES)
            if(jvName.equals(type.jvName)){
                this.pgName = type.pgName;
                this.swName = type.swName;
                this.jLibrary = type.jLibrary;
            }
        if(pgName == null)
            pgName = CaseUtil.camelToSnake(jvName);
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

    public String getJLibrary() {
        return this.jLibrary;
    }

    public void setJLibrary(String jLibrary) {
        this.jLibrary = jLibrary;
    }
}
