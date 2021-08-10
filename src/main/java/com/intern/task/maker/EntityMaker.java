package com.intern.task.maker;

import java.util.ArrayList;
import java.util.List;

import com.intern.task.model.jdl.Entity;
import com.intern.task.model.jdl.Field;
import com.intern.task.model.jdl.Name;
import com.intern.task.model.jdl.type.Type;
import com.intern.task.util.CaseUtil;
import com.intern.task.util.CodeUtil;

public class EntityMaker extends Maker<Entity>{

    public EntityMaker() {
        object = new Entity();
    }

    @Override
    protected void headSolver(String head) {
        String docLines = CodeUtil.extractJavadoc(head).trim();
        if (!docLines.equals("")){
            object.setJavadoc(new ArrayList<>());
            for (String line : docLines.split("\n")) {
                object.getJavadoc().add(line.trim());
            }
        }
        head = head.substring(docLines.length()).trim();
        for (String line : head.split("\n")) {
            line = line.trim();
            if (line.startsWith("@")){
                if(object.getAnnotations()==null)
                    object.setAnnotations(new ArrayList<>());
                object.getAnnotations().add(line);
            }
            else {
                if (line.startsWith("entity")) {
                    line = line.substring(6);
                    String pair[] = line.split("\\(");
                    object.setName(new Name(pair[0].trim(), CaseUtil.PASCAL_CASE));
                    // if (pair.length > 1) {
                    //     object.setTableName(pair[1].split("\\)")[0].trim());
                    // }
                }
                break;
            }
        }
    }

    @Override
    protected void bodySolver(String body) {
        List<String> javadoc = null;
        List<String> annos = null;
        if(body == null || body.trim().equals("")){
            return;
        }
        for(String line: body.split("\n")){
            line = line.trim();
            if(line.startsWith("/**") || line.startsWith("*")){
                if(javadoc == null)
                    javadoc = new ArrayList<>();
                javadoc.add(line);
            } else if(line.startsWith("@")){
                if(annos == null)
                    annos = new ArrayList<>();
                annos.add(line);
            } else if(!line.equals("")){
                if(line.endsWith(",")){
                    line = line.substring(0, line.length()-1);
                }

                object.getFields().add(makeField(line)
                    .setAnnotations(annos)
                    .setJavadoc(javadoc));

                annos = null;
                javadoc = null;
            }
        }
    }

    private Field makeField(String content){
        Field field = new Field();
        content = CodeUtil.remove2Probels(content.replaceAll("\\s", " "));
        String[] items = content.trim().split(" ");
        for(int i=0; i<items.length; i++){
            items[i] = items[i].trim();
            switch(i){
                case 0: field.setName(new Name(items[0], CaseUtil.CAMEL_CASE)); break;
                case 1: field.setType(new Type(items[1])); break;
                default:
                    if(field.getValidations() == null)
                        field.setValidations(new ArrayList<>());
                    field.getValidations().add(items[i]);
                    if(items[i].equals("required"))
                        field.setRequired(true);
            }
        }
        return field;
    }

}
