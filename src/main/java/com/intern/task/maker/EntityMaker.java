package com.intern.task.maker;

import java.util.ArrayList;
import java.util.List;

import com.intern.task.model.jdl.Entity;
import com.intern.task.model.jdl.Field;
import com.intern.task.model.jdl.Name;
import com.intern.task.model.jdl.Validation;
import com.intern.task.model.jdl.type.Type;
import com.intern.task.util.CaseUtil;
import com.intern.task.util.CodeUtil;

import org.apache.log4j.Logger;

public class EntityMaker {
    private static final Logger LOGGER = Logger.getLogger(EntityMaker.class);

    public static Entity make(String head, String body) {
        Entity entity = new Entity();
        headSolver(head, entity);
        bodySolver(body, entity);
        return entity;
    }

    protected static void headSolver(String head, Entity entity) {
        String docLines = CodeUtil.extractJavadoc(head);
        if (!docLines.isEmpty()) {
            entity.setJavadoc(new ArrayList<>());
            for (String line : docLines.split("\n")) {
                entity.getJavadoc().add(line.trim());
            }
        }

        head = head.substring(docLines.length()).trim();
        for (String line : head.split("\n")) {
            line = line.trim();
            if (line.startsWith("@")) {
                if (entity.getAnnotations() == null)
                    entity.setAnnotations(new ArrayList<>());
                entity.getAnnotations().add(line);
            } else {
                if (line.startsWith("entity")) {
                    line = line.substring(6);
                    String[] pair = line.split("\\(");
                    entity.setName(new Name(pair[0].trim(), CaseUtil.PASCAL_CASE));
                    // if (pair.length > 1) {
                    //     entity.setTableName(pair[1].split("\\)")[0].trim());
                    // }
                }
                break;
            }
        }
    }

    protected static void bodySolver(String body, Entity entity) {
        List<String> javadoc = null;
        List<String> annos = null;
        if (body == null || body.trim().equals("")) {
            return;
        }
        for (String line : body.split("\n")) {
            line = line.trim();
            if (line.startsWith("/**") || line.startsWith("*")) {
                if (javadoc == null)
                    javadoc = new ArrayList<>();
                javadoc.add(line);
            } else if (line.startsWith("@")) {
                if (annos == null)
                    annos = new ArrayList<>();
                annos.add(line);
            } else if (!line.equals("")) {
                line = line.replace(",", "");

                entity.getFields().add(makeField(line)
                        .setAnnotations(annos)
                        .setJavadoc(javadoc));

                annos = null;
                javadoc = null;
            }
        }
    }

    private static Field makeField(String content) {
        Field field = new Field();
        content = CodeUtil.remove2Probels(content.replaceAll("\\s", " "));
        String[] items = content.trim().split(" ");
        Validation validation = null;
        for (int i = 0; i < items.length; i++) {
            items[i] = items[i].trim();
            switch (i) {
                case 0:
                    field.setName(new Name(items[0], CaseUtil.CAMEL_CASE));
                    break;
                case 1:
                    field.setType(new Type(items[1]));
                    break;
                default:
                    try {
                        if (validation == null)
                            validation = new Validation();
                        if (items[i].equals("required")) {
                            validation.setRequired(true);
                        } else if (items[i].equals("unique")) {
                            validation.setUnique(true);
                        } else if (items[i].startsWith("max")) {
                            String number = items[i].split("\\(")[1].split("\\)")[0].trim();
                            validation.setMax(Long.parseLong(number));
                        } else if (items[i].startsWith("min")) {
                            String number = items[i].split("\\(")[1].split("\\)")[0].trim();
                            validation.setMin(Long.parseLong(number));
                        } else if (items[i].startsWith("pattern")) {
                            String pattern = items[i].split("\\(")[1].split("\\)")[0].trim();
                            validation.setPattern(pattern);
                        }
                    } catch (Exception e) {
                        LOGGER.error(e);
                    }
            }
        }
        field.setValidation(validation);
        return field;
    }

}
