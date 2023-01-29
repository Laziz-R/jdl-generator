package com.intern.task.maker;

import java.util.ArrayList;

import com.intern.task.model.jdl.Enum;
import com.intern.task.model.jdl.Name;
import com.intern.task.util.CaseUtil;
import com.intern.task.util.CodeUtil;

public class EnumMaker {

    public static Enum make(String head, String body) {
        Enum enum1 = new Enum();
        headSolver(head, enum1);
        bodySolver(body, enum1);
        return enum1;
    }

    private static void headSolver(String head, Enum enum1) {
        String docLines = CodeUtil.extractJavadoc(head).trim();
        if (!docLines.equals("")) {
            enum1.setJavadoc(new ArrayList<>());
            for (String line : docLines.split("\n")) {
                enum1.getJavadoc().add(line.trim());
            }
        }
        head = head.substring(docLines.length()).trim();
        if (head.startsWith("enum")) {
            enum1.setName(new Name(head.substring(4).trim(), CaseUtil.PASCAL_CASE));
        }
    }

    private static void bodySolver(String body, Enum enum1) {
        if (body.contains(",")) {
            body = body.replaceAll(",", "\n").trim();
        }
        for (String line : body.split("\n")) {
            line = line.trim();
            if (!line.equals("")) {
                enum1.getFields().add(line);
            }
        }
    }

}
