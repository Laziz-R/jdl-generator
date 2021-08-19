package com.intern.task.model.jdl;

public class Validation {
    private boolean required;
    private boolean unique;
    private Long max;
    private Long min;
    private String pattern;

    public boolean isRequired() {
        return this.required;
    }

    public Validation setRequired(boolean required) {
        this.required = required;
        return this;
    }

    public boolean isUnique() {
        return this.unique;
    }

    public Validation setUnique(boolean unique) {
        this.unique = unique;
        return this;
    }

    public Long getMax() {
        return this.max;
    }

    public Validation setMax(Long max) {
        this.max = max;
        return this;
    }

    public Long getMin() {
        return this.min;
    }

    public Validation setMin(Long min) {
        this.min = min;
        return this;
    }

    public String getPattern() {
        return this.pattern;
    }

    public Validation setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

}
