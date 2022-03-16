package com.jjh.eventlistener.controller;

import java.util.Map;

import org.apache.commons.exec.util.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.jjh.eventlistener.core.annotation.KeywordMapping;
import com.jjh.eventlistener.core.controller.Controller;
import com.jjh.eventlistener.core.controller.SplitStrRequest;
import com.jjh.eventlistener.service.AsIsSmsService;
import com.jjh.eventlistener.service.SmsService;
import com.jjh.eventlistener.type.OnlySmsMsg;
import com.jjh.eventlistener.type.RequestType;

@Controller
public class SmsController {
    
    @Autowired
    private AsIsSmsService asIsSmsService;
    
    @Autowired
    private SmsService smsService;
    
    @Value("${event-listener.send-no}")
    private String sendNo;
    
    @Value("${event-listener.sms-head}")
    private String smsHead;
    
    @KeywordMapping(
        value = "PEM_INFO"
        ,msgMapKeys = {
            "키워드"
            ,"요청구분자"
            ,"수신대상자"
            ,"이벤트발생시간"
            ,"요청자"
            ,"메세지"
        })
    public void PEM_INFO(SplitStrRequest req) throws Exception {
        
        Map<String,String> paramMap = MapUtils.copy(req.getMsgMap());
        paramMap.put("메세지",req.getMsgMap().get("요청자") + " " + req.getMsgMap().get("메세지"));
        paramMap.put("송신대상자", sendNo);
        paramMap.put("발송대상타입","P");
        paramMap.put("요청구분키","NA");
        paramMap.put("SMS전송요청IP", req.getRemoteAddr());
        
        if(isSmsTypeMsg(paramMap)) paramMap.put("발송대상타입","S");    
    }
    
    
    
    
    @KeywordMapping(
        value = "INFO"
        ,msgMapKeys = {
            "키워드"
            ,"요청구분자"
            ,"수신대상자"
            ,"이벤트발생시간"
            ,"요청자"
            ,"발송대상타입"
            ,"어플리케이션코드"
            ,"프로그램명"
            ,"요청구분키"                
            ,"메세지"
        })
    public void INFO(SplitStrRequest req) throws Exception {
        
        Map<String,String> paramMap = MapUtils.copy(req.getMsgMap());
        
        
        paramMap.put("송신대상자", sendNo);
        paramMap.put("SMS전송요청IP", req.getRemoteAddr());
        paramMap.put("메세지", this.smsHead + paramMap.get("메세지"));
        
        if( isEmpNo(paramMap) ) smsService.sendEmpId(paramMap);
        if( isTelNo(paramMap) ) smsService.sendTelNo(paramMap);
        if( isAppCode(paramMap) ) smsService.sendAppCode(paramMap);
        if( isHandmateGroup(paramMap) ) smsService.sendHandmateGroup(paramMap);
    }
    
    
    
    private boolean isAppCode(Map<String,String> paramMap) {
        return StringUtils.equals(RequestType.어플리케이션코드.getValue(), paramMap.get("요청구분자"));
    }

    private boolean isTelNo(Map<String,String>  paramMap) {
        return StringUtils.equals(RequestType.전화번호.getValue(), paramMap.get("요청구분자"));
    }

    private boolean isEmpNo(Map<String,String>  paramMap) {
        return StringUtils.equals(RequestType.직원번호.getValue(), paramMap.get("요청구분자"));
    }

    private boolean isHandmateGroup(Map<String,String>  paramMap) {
        return StringUtils.equals(RequestType.전파그룹.getValue(), paramMap.get("요청구분자"));
    }

    private boolean isTelnoOrForceSms(Map<String,String> paramMap) {
        return isTelNo(paramMap) || isSmsTypeMsg(paramMap);
    }

    private boolean isSmsTypeMsg(Map<String,String> paramMap) {
        for(OnlySmsMsg osm : OnlySmsMsg.values()) {
            if(StringUtils.startsWith(paramMap.get("메세지"), osm.getValue())) return true;
        }
        return false;
    }
}