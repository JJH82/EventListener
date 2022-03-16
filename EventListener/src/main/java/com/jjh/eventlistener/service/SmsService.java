package com.jjh.eventlistener.service;


import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjh.eventlistener.repository.HSqlRepository;
import com.jjh.eventlistener.type.RequestType;



@Service
public class SmsService {
    
    
    @Autowired
    private HSqlRepository hsqlRepository;
    
    
    @Transactional(rollbackFor = Exception.class)
    public void sendTelNo(Map<String,String> paramMap) throws Exception {

        paramMap.put("수신대상자데이터타입", RequestType.전화번호.getValue());
        
        hsqlRepository.update("sendTelNo", paramMap);
        
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void sendEmpId(Map<String,String> paramMap) throws Exception {

        paramMap.put("수신대상자데이터타입", RequestType.직원번호.getValue());
        
        hsqlRepository.update("sendEmpId", paramMap);

        
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void sendAppCode(Map<String,String> paramMap) throws Exception {
        
        paramMap.put("수신대상자데이터타입", RequestType.직무코드어플리케이션코드.getValue());
        
        hsqlRepository.update("sendAppCode", paramMap);

    }
    

    
    @Transactional(rollbackFor = Exception.class)
    public void sendHandmateGroup(Map<String,String> paramMap) throws Exception {

         paramMap.put("수신대상자데이터타입", RequestType.전파그룹.getValue());
        
        hsqlRepository.update("sendHandmateGroup", paramMap);
        
    }
    
    
}