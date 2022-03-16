package com.jjh.eventlistener.controller;

import com.jjh.eventlistener.core.controller.Controller;
import com.jjh.eventlistener.core.annotation.KeywordMapping;
import com.jjh.eventlistener.core.controller.SplitStrRequest;
import com.jjh.eventlistener.cmd.CmdArgument;
import com.jjh.eventlistener.cmd.CmdExecutor;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class EventController {
    
    @Autowired
    private CmdExecutor cmdExecutor;
    
    @KeywordMapping(
        value = "PEM-NMS"
        ,msgMapKeys = {
            "키워드"
          ,"이벤트발생일시"
          ,"업무그분코드"
          ,"이벤트등급"
          ,"호스트명"
          ,"부점명"
          ,"장비명"
          ,"부점코드"
          ,"메세지"
        }
    )
    public void PEM_NMS(SplitStrRequest req) throws Exception {
        
        CmdArgument cmdArg = new CmdArgument
                                .format(req.getMsgMap())
                                .이벤트등급KeyNm("이벤트등급")
                                .메세지그룹("NMS")
                                .업무코드("${부점명} ${장비명}")
                                .모니터링대상("${호스트명}")
                                .서버명("${호스트명}")
                                .메세지("${이벤트발생일시}||${호스트명}||${메세지}")
                                .builder();
        
        cmdExecutor.execute(cmdArg);
    }
    
    
        @KeywordMapping(
        value = "OBM"
        ,msgMapKeys = {
            "키워드"
          ,"메세지그룹"
          ,"이벤트등급"
          ,"업무코드"
          ,"모니터링대상"
          ,"서버명"
          ,"메세지"
        }
    )
    public void OBM(SplitStrRequest req) throws Exception {
        
        CmdArgument cmdArg = new CmdArgument
                                .format(req.getMsgMap())                
                                .메세지그룹("${메세지그룹}")
                                .업무코드("${업무코드}")
                                .모니터링대상("${모니터링대상}")
                                .서버명("${서버명}")
                                .메세지("${메세지}")
                                .builder();
        
        cmdExecutor.execute(cmdArg);
    }
    
}