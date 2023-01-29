package com.intern.task.util;

public class CaseUtil {
    public static final String
            CAMEL_CASE = "camelCase",
            PASCAL_CASE = "PascalCase",
            SNAKE_CASE = "snake_case",
            KEBAB_CASE = "kebab-case";

    public static String camelToSnake(String camel) {
        if (camel == null) return null;
        String snake = "";
        for (int i = 0; i < camel.length(); i++) {
            char c = camel.charAt(i);
            if ('A' <= c && c <= 'Z') {
                snake += "_" + (char) (c + 32);
            } else {
                snake += c;
            }
        }
        return snake;
    }

    public static String camelToPascal(String camel) {
        if (camel == null || camel.equals("")) return camel;
        char c = camel.charAt(0);
        if ('a' <= c && c <= 'z')
            c -= 32;
        return c + camel.substring(1);
    }

    public static String camelToKebab(String camel) {
        if (camel == null)
            return null;
        String kebab = "";
        for (int i = 0; i < camel.length(); i++) {
            char c = camel.charAt(i);
            if ('A' <= c && c <= 'Z') {
                kebab += "-" + (char) (c + 32);
            } else {
                kebab += c;
            }
        }
        return kebab;
    }

    public static String kebabToPascal(String kebab) {
        if (kebab == null) return null;
        kebab = "-" + kebab.toLowerCase();
        String pascal = "";
        for (int i = 0; i < kebab.length(); i++) {
            char c = kebab.charAt(i);
            if (c == '-') continue;
            if (kebab.charAt(i - 1) == '-')
                if ('a' <= c && c <= 'z')
                    c -= 32;
            pascal += c;
        }
        return pascal;
    }

    public static String pascalToSnake(String pascal) {
        if (pascal == null) return null;
        String snake = "";
        for (int i = 0; i < pascal.length(); i++) {
            char c = pascal.charAt(i);
            if ('A' <= c && c <= 'Z')
                snake += "_" + (char) (c + 32);
            else
                snake += c;
        }
        if (snake.startsWith("_"))
            snake = snake.substring(1);
        return snake;
    }

    public static String pascalToCamel(String pascal) {
        if (pascal == null || pascal.equals("")) return pascal;
        char c = pascal.charAt(0);
        if ('A' <= c && c <= 'Z')
            c += 32;
        return c + pascal.substring(1);
    }

    public static String pascalToKebab(String pascal) {
        if (pascal == null) return pascal;
        String kebab = "";
        for (int i = 0; i < pascal.length(); i++) {
            char c = pascal.charAt(i);
            if ('A' <= c && c <= 'Z')
                kebab += "-" + (char) (c + 32);
            else
                kebab += c;
        }
        if (kebab.startsWith("-"))
            kebab = kebab.substring(1);
        return kebab;
    }

    public static String snakeToCamel(String snake) {
        if (snake == null || snake.equals("")) return snake;
        snake = snake.toLowerCase();
        String camel = snake.charAt(0) + "";
        for (int i = 1; i < snake.length(); i++) {
            char c = snake.charAt(i);
            if (c == '_') continue;
            if (snake.charAt(i - 1) == '_') {
                if ('a' <= c && c <= 'z')
                    c -= 32;
            }
            camel += c;
        }
        return camel;
    }

    public static String snakeToPascal(String snake) {
        if (snake == null) return null;
        snake = "_" + snake.toLowerCase();
        String pascal = "";
        for (int i = 1; i < snake.length(); i++) {
            char c = snake.charAt(i);
            if (c == '_') continue;
            if (snake.charAt(i - 1) == '_') {
                if ('a' <= c && c <= 'z')
                    c -= 32;
            }
            pascal += c;
        }
        return pascal;
    }

    public static String snakeToKebab(String snake) {
        return snake.toLowerCase().replaceAll("_", "-");
    }
}
