package com.intern.task.util;

public class CodeUtil {

    public static String removeComments(String text){
        String newStr = "";
        for(String line: text.split("\n")){
            if(line.trim().equals("")) continue;
            if(line.trim().startsWith("//")) continue;
            int i = line.indexOf("//");
            if(i>-1){
                line = line.substring(0, i);
            }
            newStr += line +"\n";
        }
        text = "";
        for(int i=0; i < newStr.length(); i++){
            if(i+2 < newStr.length() && 
                newStr.substring(i, i+2).equals("/*") && 
                    newStr.charAt(i+2)!='*'){
                i += 2;
                while(i+1 < newStr.length() && 
                    !newStr.substring(i, i+2).equals("*/")){
                    i++;
                }
                i += 2;
            }
            if(i<newStr.length())
                text += newStr.charAt(i);
        }
        return text;
    }

    public static String remove2Probels(String content){
        content.trim();
        while(content.indexOf("  ")>-1){
            content = content.replaceAll("  ", " ");
        }
        return content;
    }

    public static int findCloseBkt(String str, int openIndex){
        try{
            char openBkt = str.charAt(openIndex);
            char closeBkt;
            switch(openBkt){
                case '{':
                    closeBkt = '}'; break;
                case '(':
                    closeBkt = ')'; break;
                case '[':
                    closeBkt = ']'; break;
                default:
                    return -1;
            }
            int v = 1;
            int cursor = openIndex + 1;
            while(cursor < str.length()){
                if(str.charAt(cursor) == openBkt)
                    v++;
                else if(str.charAt(cursor) == closeBkt)
                    v--;
                if(v == 0)
                    return cursor;
                cursor++;
            }
            return -1;
        } catch(Exception e){
            return -1;
        }
    }

    public static String extractJavadoc(String text){
        if(text.startsWith("/**")){
            int endOfComment = text.indexOf("*/", 3) + 2;
            return endOfComment==1 
                ? text
                : text.substring(0, endOfComment);
        }
        return "";
    }

}
