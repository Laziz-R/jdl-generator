package com.intern.task.maker;

import java.util.ArrayList;
import java.util.List;

import com.intern.task.model.jdl.Name;
import com.intern.task.model.jdl.RelationType;
import com.intern.task.model.jdl.Relationship;
import com.intern.task.model.jdl.Validation;
import com.intern.task.model.sql.ForeignKey;
import com.intern.task.util.CaseUtil;
import com.intern.task.util.CodeUtil;

public class RelationMaker {

    public static List<Relationship> make(String head, String body) {
        return bodySolver(body, headSolver(head));
    }

    private static RelationType headSolver(String head) {
        String name = head.substring(12).trim();       // example, head = "relationship OneToMany";
        switch (name.toLowerCase()) {
            case "onetoone":
                return RelationType.ONE_TO_ONE;
            case "onetomany":
                return RelationType.ONE_TO_MANY;
            case "manytoone":
                return RelationType.MANY_TO_ONE;
            case "manytomany":
                return RelationType.MANY_TO_MANY;
            default:
                break;
        }
        return null;
    }

    private static List<Relationship> bodySolver(String body, RelationType type) {
        List<Relationship> relationships = new ArrayList<>();

        for (String line : body.split("\n")) {
            Relationship rel = new Relationship();

            line = line.replace(",", "").trim();

            String[] items = CodeUtil.remove2Probels(line).split(" to ");
            if (items.length > 1) {
                ForeignKey fromFK = makeFK(items[0]);
                ForeignKey toFK = makeFK(items[1]);
                if (fromFK == null || toFK == null)
                    continue;
                fromFK.setToEntityName(toFK.getFromEntityName());
                toFK.setToEntityName(fromFK.getFromEntityName());

                if (fromFK.getFromField().getName() == null) {
                    String str = toFK.getFromEntityName().getCamelCase() + "Id";
                    fromFK.getFromField().setName(new Name(str, CaseUtil.CAMEL_CASE));
                }
                if (toFK.getFromField().getName() == null) {
                    String str = fromFK.getFromEntityName().getCamelCase() + "Id";
                    toFK.getFromField().setName(new Name(str, CaseUtil.CAMEL_CASE));
                }
                if (type.equals(RelationType.MANY_TO_MANY)) {
                    String name1 = fromFK.getFromEntityName().getPascalCase();
                    String name2 = toFK.getFromEntityName().getPascalCase();
                    Name composedName = new Name(name1 + name2, CaseUtil.PASCAL_CASE);
                    fromFK.setFromEntityName(composedName);
                    toFK.setFromEntityName(composedName);
                }
                rel
                        .setFromFK(fromFK)
                        .setToFK(toFK)
                        .setType(type);
                relationships.add(rel);
            }
        }
        return relationships;
    }

    private static ForeignKey makeFK(String str) {
        try {
            ForeignKey fk = new ForeignKey();

            String[] items = str.split("\\{");
            Name name = new Name(items[0].trim(), CaseUtil.PASCAL_CASE);
            fk.setFromEntityName(name);

            if (items.length > 1) {
                items[1] = items[1].replace("}", "").trim();
                if (items[1].endsWith("required")) {
                    Validation v = new Validation().setRequired(true);
                    fk.getFromField().setValidation(v);
                    items[1] = items[1].replace("required", "").trim();
                }
                if (items[1].contains("(")) {
                    String[] items2 = items[1].split("\\(");
                    name = new Name(items2[0].trim(), CaseUtil.CAMEL_CASE);
                    fk.setToEntityName(name);
                    items[1] = items2[1].replace(")", "").trim();
                }
                if (!items[1].equals("")) {
                    name = new Name(items[1], CaseUtil.CAMEL_CASE);
                    fk.getFromField().setName(name);
                }
            }
            return fk;
        } catch (Exception e) {
            return null;
        }
    }

}
