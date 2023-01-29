package com.intern.task.model.jdl;

import com.intern.task.util.CaseUtil;

public class Name {
    private String camelCase;   // camelCase
    private String pascalCase;  // PascalCase
    private String snakeCase;   // snake_case
    private String kebabCase;   // kebab-case
    private String upperCase;   // UPPER_CASE

    public Name(String name, String caseType) {
        switch (caseType) {
            case CaseUtil.CAMEL_CASE:
                this.camelCase = name;
                this.snakeCase = CaseUtil.camelToSnake(name);
                this.pascalCase = CaseUtil.camelToPascal(name);
                this.kebabCase = CaseUtil.camelToKebab(name);
                break;
            case CaseUtil.PASCAL_CASE:
                this.pascalCase = name;
                this.snakeCase = CaseUtil.pascalToSnake(name);
                this.camelCase = CaseUtil.pascalToCamel(name);
                this.kebabCase = CaseUtil.pascalToKebab(name);
                break;
            case CaseUtil.SNAKE_CASE:
                this.snakeCase = name;
                this.pascalCase = CaseUtil.snakeToPascal(name);
                this.camelCase = CaseUtil.snakeToCamel(name);
                this.kebabCase = CaseUtil.snakeToKebab(name);
                break;
        }

        if (snakeCase != null) {
            this.upperCase = snakeCase.toUpperCase();
        }
    }

    public String getCamelCase() {
        return this.camelCase;
    }

    public String getPascalCase() {
        return this.pascalCase;
    }

    public String getSnakeCase() {
        return this.snakeCase;
    }

    public String getKebabCase() {
        return this.kebabCase;
    }

    public String getUpperCase() {
        return upperCase;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Name other = (Name) obj;

        return other.camelCase.equals(this.camelCase);
    }
}
