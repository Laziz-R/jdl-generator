package com.intern.task.maker;

public abstract class Maker<T> {
    protected T object;

    public T make(String head, String body){
        headSolver(head);
        bodySolver(body);
        return object;
    }

    abstract protected void headSolver(String head);
    abstract protected void bodySolver(String body);
}
