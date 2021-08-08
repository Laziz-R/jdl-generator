package com.intern.task.model.jdl;

public class Relationship {
    private RelationType type;
    private String fromEntity;
    private String toEntity;

    public RelationType getType() {
        return this.type;
    }

    public Relationship setType(RelationType type) {
        this.type = type;
        return this;
    }

    public String getFromEntity() {
        return this.fromEntity;
    }

    public Relationship setFromEntity(String fromEntity) {
        this.fromEntity = fromEntity;
        return this;
    }

    public String getToEntity() {
        return this.toEntity;
    }

    public Relationship setToEntity(String toEntity) {
        this.toEntity = toEntity;
        return this;
    }


}
