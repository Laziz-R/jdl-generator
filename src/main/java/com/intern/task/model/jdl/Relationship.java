package com.intern.task.model.jdl;

import com.intern.task.model.sql.ForeignKey;

public class Relationship {
    private RelationType type;
    private ForeignKey fromFK;
    private ForeignKey toFK;

    public RelationType getType() {
        return this.type;
    }

    public Relationship setType(RelationType type) {
        this.type = type;
        return this;
    }

    public ForeignKey getFromFK() {
        return this.fromFK;
    }

    public Relationship setFromFK(ForeignKey fromFK) {
        this.fromFK = fromFK;
        return this;
    }

    public ForeignKey getToFK() {
        return this.toFK;
    }

    public Relationship setToFK(ForeignKey toFK) {
        this.toFK = toFK;
        return this;
    }

}
