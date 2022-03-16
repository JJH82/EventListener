package com.jjh.eventlistener.core.type;

public enum FindKeywordMethod implements CodeData {
    
    startWith("시작문자열")
    ,simpleMatch("간단한패턴");
    
    private String value;
    
    private FindKeywordMethod(String value) {
        this.value = value;
    }
    
    public String getValue(){
        return value;
    }
                
        
}
