package com.intern.task.common;

import java.util.ArrayList;
import java.util.List;

import com.intern.task.maker.EntityMaker;
import com.intern.task.maker.EnumMaker;
import com.intern.task.maker.RelationMaker;
import com.intern.task.model.jdl.Entity;
import com.intern.task.model.jdl.Enum;
import com.intern.task.model.jdl.Field;
import com.intern.task.model.jdl.Name;
import com.intern.task.model.jdl.Relationship;
import com.intern.task.model.jdl.type.Type;
import com.intern.task.model.sql.ForeignKey;
import com.intern.task.util.CaseUtil;
import com.intern.task.util.CodeUtil;


public class JdlCode {
    private String code;
    private List<Entity> entities = new ArrayList<>();
    private List<Enum> enums = new ArrayList<>();
    private List<ForeignKey> foreignKeys = new ArrayList<>();
    private List<String> others = new ArrayList<>();

    public JdlCode(String code) {
        this.code = CodeUtil.removeComments(code);
    }

    public String getCode() {
        return code;
    }

    /**
     * Defines all entities, enums, relationships and others.
     */
    public void define() {
        int cursor = 0;
        while (cursor < code.length()) {
            int openIndex = code.indexOf("{", cursor);
            if (openIndex < 0)
                return;
            int closeIndex = CodeUtil.getClosedIndex(code, openIndex);
            if (closeIndex < 0)
                return;
            String head = code.substring(cursor, openIndex).trim();
            String body = code.substring(openIndex + 1, closeIndex).trim();

            if (head.contains("entity")) {
                entities.add(EntityMaker.make(head, body));
            } else if (head.contains("enum")) {
                enums.add(EnumMaker.make(head, body));
            } else if (head.contains("relationship")) {
                for (Relationship rel : RelationMaker.make(head, body)) {
                    relate(rel);
                }
            }
            cursor = closeIndex + 1;
        }
    }

    private void relate(Relationship rel) {
        Entity fromEntity = null, toEntity = null;
        ForeignKey fromFK = rel.getFromFK();
        ForeignKey toFK = rel.getToFK();

        for (Entity e : entities) {
            if (e.getName().equals(toFK.getToEntityName())) {
                fromEntity = e;
                for (Field f : e.getFields()) {
                    if (f.getName().equals(toFK.getFromField().getName())) {
                        toFK.getFromField().setType(f.getType());
                        toFK.setToField(f);
                    }
                }
                if (toFK.getToField().getName() == null) {
                    toFK.setToField(new Field()
                            .setName(new Name(e.getName().getCamelCase() + "Id", CaseUtil.CAMEL_CASE))
                            .setType(new Type("Long")));
                    toFK.getFromField().setType(new Type("Long"));
                }
            }
            if (e.getName().equals(fromFK.getToEntityName())) {
                toEntity = e;
                for (Field f : e.getFields()) {
                    if (f.getName().equals(fromFK.getFromField().getName())) {
                        fromFK.getFromField().setType(f.getType());
                        fromFK.setToField(f);
                    }
                }
                if (fromFK.getToField().getName() == null) {
                    fromFK.setToField(new Field()
                            .setName(new Name(e.getName().getCamelCase() + "Id", CaseUtil.CAMEL_CASE))
                            .setType(new Type("Long")));
                    fromFK.getFromField().setType(new Type("Long"));
                }
            }
        }
        if (fromEntity == null || toEntity == null)
            return;
        switch (rel.getType()) {
            case ONE_TO_ONE:
            case MANY_TO_ONE:
                fromEntity.getFields().add(fromFK.getFromField());
                foreignKeys.add(fromFK);
                break;
            case ONE_TO_MANY:
                toEntity.getFields().add(toFK.getFromField());
                foreignKeys.add(toFK);
                break;
            case MANY_TO_MANY:
                Entity e = new Entity();
                e.setName(fromFK.getFromEntityName());
                e.getFields().add(fromFK.getFromField());
                e.getFields().add(toFK.getFromField());
                entities.add(e);

                foreignKeys.add(fromFK);
                foreignKeys.add(toFK);
        }
    }

    public List<Entity> getEntities() {
        return this.entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public List<Enum> getEnums() {
        return this.enums;
    }

    public void setEnums(List<Enum> enums) {
        this.enums = enums;
    }

    public List<String> getOthers() {
        return this.others;
    }

    public List<ForeignKey> getForeignKeys() {
        return foreignKeys;
    }

    public void setOthers(List<String> others) {
        this.others = others;
    }

}
