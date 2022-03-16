package com.jjh.eventlistener.core.type;

public interface CodeData {
    
    public String name();
    
    public default String getCode() {
        return name();
    }
    
    public String getValue();
}