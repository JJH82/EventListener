package com.jjh.eventlistener.service;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjh.eventlistener.repository.HSqlRepository;
import com.jjh.eventlistener.type.RequestType;


@Service
public class AsIsSmsService {
    
    @Autowired
    private HSqlRepository hsqlRepository;
    
    
    @Transactional(rollbackFor = Exception.class)
    public void sendTelNo(Map<String,String> paramMap) throws Exception {
        
        paramMap.put("어플리케이션코드", "SQE");
        paramMap.put("수신대상자데이터타입", RequestType.전화번호.getValue());
        paramMap.put("프로그램명","PEM_INFO");
        
        hsqlRepository.update("sendTelNo",paramMap);
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void sendEmpId(Map<String,String> paramMap) throws Exception {
        
        paramMap.put("어플리케이션", "SQE");
        paramMap.put("수신대상자데이터타입", RequestType.직원번호.getValue());
        paramMap.put("프로그램명","PEM_INFO");
        
        hsqlRepository.update("sendEmpId",paramMap);
        
    }
    @Transactional(rollbackFor = Exception.class)    
    public void sendAppCode(Map<String,String> paramMap) throws Exception {
        
        paramMap.put("어플리케이션", "SQE");
        paramMap.put("수신대상자데이터타입", RequestType.직원번호.getValue());
        paramMap.put("프로그램명","PEM_INFO");
        
        hsqlRepository.update("sendAppCode",paramMap);
    }
    
    @Transactional(readOnly = true)
    public String teamLeaderNo(Map<String,String> paramMap) throws Exception {
        
        return hsqlRepository.queryForObject("teamLeaderNo", paramMap, String.class);
    }
}