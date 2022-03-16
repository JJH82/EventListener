package com.jjh.eventlistener.type;

import com.jjh.eventlistener.core.type.CodeData;

public enum Severity implements CodeData {
    
    Critical("1")
   ,Major("2")
   ,Minor("3")
   ,Warning("4")
   ,Normal("5")
    ;
    
    private String value;
    
    private Severity(String value) {
        this.value = value;
    }
    
    public String getValue() {
        
        return value;
    }
}
