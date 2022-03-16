package com.jjh.eventlistener.type;

import com.jjh.eventlistener.core.type.CodeData;

public enum RequestType implements CodeData {
   
    직원번호("1")
   ,전화번호("2")
   ,어플리케이션코드("3")
   ,직무코드("4")
   ,직무코드어플리케이션코드("5")
   ,전파그룹("6")
   ,없음("7");

    private String value;
    
    private RequestType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
    
    
}

