package com.jjh.eventlistener.type;

import com.jjh.eventlistener.core.type.CodeData;

public enum OnlySmsMsg implements CodeData {
    
    서버패스워드변경지원시스템("서버패스워드변경지원시스템")
   ,통합계정권한관리("통합계정권한관리")
   ,내부Cloud("내부Cloud")
   ,외부Cloud("외부Cloud")
   ,wisenet("wisenet")
   ,CloudPC("[CloudPC]")
  ;
    private String value;
    
    private OnlySmsMsg(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
}