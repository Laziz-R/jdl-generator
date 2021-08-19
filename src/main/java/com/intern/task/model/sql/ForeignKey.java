package com.intern.task.model.sql;

import com.intern.task.model.jdl.Field;
import com.intern.task.model.jdl.Name;

public class ForeignKey {
    private Name fromEntityName;
    private Name toEntityName;
    private Field fromField = new Field();
    private Field toField = new Field();

    public Name getFromEntityName() {
        return this.fromEntityName;
    }

    public void setFromEntityName(Name fromEntityName) {
        this.fromEntityName = fromEntityName;
    }

    public Name getToEntityName() {
        return this.toEntityName;
    }

    public void setToEntityName(Name toEntityName) {
        this.toEntityName = toEntityName;
    }

    public Field getFromField() {
        return this.fromField;
    }

    public void setFromField(Field fromField) {
        this.fromField = fromField;
    }

    public Field getToField() {
        return this.toField;
    }

    public void setToField(Field toField) {
        this.toField = toField;
    }

}
