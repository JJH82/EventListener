package com.jjh.eventlistener.core.controller;

import static org.apache.commons.lang3.StringUtils.splitByWholeSeparatorPreserveAllTokens;
import static org.apache.commons.lang3.StringUtils.strip;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.util.PatternMatchUtils;

import com.jjh.eventlistener.core.annotation.KeywordMapping;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class SplitStrRequest {
    
    private final String msg;
    private final ApplicationContext ctx;
    private final String remoteAddr;
    
    private String[] splitMsg;
    private Map<String,String> msgMap;
    private KeywordMapping keywordMapping;
    
    private final Class<? extends Annotation> targetClass = Controller.class;
    private final Class<? extends Annotation> targetMethod = KeywordMapping.class;
    
    public SplitStrRequest(ApplicationContext ctx, String msg, String remoteAddr) {
        this.ctx = ctx;
        this.msg = msg;
        this.remoteAddr = remoteAddr;
    }
    
    public boolean isExecutable(Annotation keywordMapping) throws Exception {
        this.keywordMapping = (KeywordMapping)keywordMapping;
        
        if(!containKeyword()) return containKeyword();
        
        initMsg();
        
        allocateMsgMap();
        
        if(log.isInfoEnabled()) logMsgMap();
        
        msgMap = Collections.unmodifiableMap(msgMap);
        
        return containKeyword();
            
        
    }
    
    private void logMsgMap(){
        for(String key : keywordMapping.msgMapKeys()) log.info("{} = {}", key, msgMap.get(key));
    }
    
    private void initMsg() throws Exception {
        splitMsg = splitByWholeSeparatorPreserveAllTokens(msg, keywordMapping.delimiter());
        msgMap = new HashMap<String,String>();
        
        if(this.keywordMapping.msgMapKeys().length > splitMsg.length) {
            throw new Exception
                (
                String.format("@MapFormat갯수(%1$d)와 splitMsg배열(%2$d) 배열 같은 크기로 데이터를 보내주세요"
                              , keywordMapping.msgMapKeys().length, splitMsg.length
                             )
            );
        }            
    }
    
    private void allocateMsgMap() {
        int i = 0;
        for(String msgMapkey : keywordMapping.msgMapKeys()) {
            msgMap.put(msgMapkey, splitMsg[i]);
            i++;
        }
        
        putLastMsgMapKeyValue();
    }
    
    private void putLastMsgMapKeyValue(){
        StringBuilder sb = new StringBuilder();
        for(int j = lastMsgMapKeyIdx(); j < splitMsg.length; j++) {
            sb.append(splitMsg[j]);
            sb.append(keywordMapping.delimiter());
        }
        
        msgMap.put(lastMsgMapKey(), strip(sb.toString(), this.keywordMapping.delimiter()));
    }
    
    private int lastMsgMapKeyIdx() {
        return keywordMapping.msgMapKeys().length-1;
    }
    
    private String lastMsgMapKey() {
        return keywordMapping.msgMapKeys()[lastMsgMapKeyIdx()];
    }
    
    private boolean containKeyword() throws Exception {
        boolean result = false;
        
        switch(keywordMapping.method()) {
            case startWith :
                result = msg.startsWith(keywordMapping.value());
            break;
            case simpleMatch :
                result = PatternMatchUtils.simpleMatch(keywordMapping.value(), msg);
            break;
        }
        
        return result;
    }
    
}