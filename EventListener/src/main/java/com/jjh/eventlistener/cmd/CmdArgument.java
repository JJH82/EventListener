package com.jjh.eventlistener.cmd;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;

import com.jjh.eventlistener.type.Severity;
import lombok.Getter;
import lombok.ToString;
import java.util.Map;

@ToString(exclude = {"msgMap"})
public class CmdArgument {
    
    private final Map<String,String> msgMap;
    
    @Getter
    private Severity 이벤트등급;
    
    @Getter
    private String 메세지그룹;
    
    @Getter
    private String 업무코드;
    
    @Getter
    private String 모니터링대상;
    
    @Getter
    private String 서버명;
    
    @Getter
    private String 메세지;
    
    public static class format {
        private final Map<String,String> msgMap;
        
        private String 이벤트등급KeyNm = "이벤트등급";
        
        private Severity 이벤트등급;
        private String 메세지그룹;
        private String 업무코드;
        private String 모니터링대상;
        private String 서버명;
        private String 메세지;
        
        
        public format(Map<String,String> msgMap) {
            this.msgMap = msgMap;
        }
        
        public format 이벤트등급KeyNm(String val) {
            이벤트등급KeyNm = val;
            return this;
        }
        
        public format 이벤트등급(Severity val) {
            이벤트등급 = val;
            return this;
        }
        
        public format 메세지그룹(String formatStr) {
            메세지그룹 = formatStr;
            return this;
        }
        
        public format 업무코드(String formatStr) {
            업무코드 = formatStr;
            return this;
        }
        
        public format 모니터링대상(String formatStr) {
            모니터링대상 = formatStr;
            return this;
        }

        
        public format 서버명(String formatStr) {
            서버명 = formatStr;
            return this;
        }

        
        public format 메세지(String formatStr) {
            메세지 = formatStr;
            return this;
        }
        
        public CmdArgument builder() {
            return new CmdArgument(this);
        }
        
        
    }
    
    private Severity changeSeverity(String str) {
        
        for(Severity s : Severity.values()) {
            
            if ( StringUtils.equals(str, s.getValue()) || StringUtils.equalsIgnoreCase(str, s.getCode()) ) {
                return s;
            }
        }
        return Severity.Normal;
    }
    
    private CmdArgument(format format) {
        
        msgMap = format.msgMap;
        StringSubstitutor ss = new StringSubstitutor(msgMap);
        ss.setEscapeChar('^');
        이벤트등급 = (format.이벤트등급 == null) ? changeSeverity( msgMap.get(format.이벤트등급KeyNm) ) : format.이벤트등급;
        메세지그룹 = ss.replace(format.메세지그룹);
        업무코드 = ss.replace(format.업무코드);
        모니터링대상 = ss.replace(format.모니터링대상);
        서버명 = ss.replace(format.서버명);
        메세지 = ss.replace(format.메세지);
        
    }
    
}