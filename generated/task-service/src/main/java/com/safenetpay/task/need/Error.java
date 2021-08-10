package com.safenetpay.task.need;

public enum Error {
    APPLICATION(0),
    DATABASE(1);
    
    private int code;

    Error(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}