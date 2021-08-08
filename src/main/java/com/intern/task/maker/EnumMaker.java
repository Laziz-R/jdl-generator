package com.intern.task.maker;

import java.util.ArrayList;

import com.intern.task.model.jdl.Enum;
import com.intern.task.model.jdl.Name;
import com.intern.task.util.CaseUtil;
import com.intern.task.util.CodeUtil;

public class EnumMaker extends Maker<Enum>{

    public EnumMaker() {
        object = new Enum();
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
        if(head.startsWith("enum")){
            object.setName(new Name(head.substring(4).trim(), CaseUtil.PASCAL_CASE));
        }
    }
    
    @Override
    protected void bodySolver(String body) { 
        if(body.indexOf(",")>-1){
            body = body.replaceAll(",", "\n").trim();
        }
        for(String line: body.split("\n")){
            line = line.trim();
            if(!line.equals("")){
                object.getFields().add(line);
            }
        }
    }

}
