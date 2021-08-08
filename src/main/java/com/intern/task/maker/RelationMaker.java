package com.intern.task.maker;

import java.util.ArrayList;
import java.util.List;

import com.intern.task.model.jdl.RelationType;
import com.intern.task.model.jdl.Relationship;
import com.intern.task.util.CodeUtil;

public class RelationMaker extends Maker<List<Relationship>>{

    private RelationType type;
    
    public RelationMaker() {
        object = new ArrayList<>();
    }

    @Override
    protected void headSolver(String head) {
        String name = head.substring(12).trim();       // example, head = "relationship OneToMany";
        switch (name.toLowerCase()) {
            case "onetoone":
                type = RelationType.ONE_TO_ONE;
                break;
            case "onetomany":
                type = RelationType.ONE_TO_MANY;
                break;
            case "manytoone":
                type = RelationType.MANY_TO_ONE;
                break;
            case "manytomany":
                type = RelationType.MANY_TO_MANY;
                break;            
            default:
                break;
        }
    }

    @Override
    protected void bodySolver(String body) {
        for(String line: body.split("\n")){
            line = line.trim();
            if(line.equals(""))
                continue;
            String fromEntity = "", toEntity = "";

            String[] items = CodeUtil.remove2Probels(line).split(" to ");
            if(items.length>1){
                int brct = items[0].indexOf("{");
                fromEntity = (brct>-1 ? items[0].substring(0, brct) : items[0]).trim();
                
                brct = items[1].indexOf("{");
                toEntity = (brct>-1 ? items[1].substring(0, brct) : items[1]).trim();
                if(toEntity.endsWith(","))
                    toEntity = toEntity.substring(0, toEntity.length()-1).trim();
                object.add(new Relationship()
                    .setFromEntity(fromEntity)
                    .setToEntity(toEntity)
                    .setType(type));
            }
        }
    }
}
