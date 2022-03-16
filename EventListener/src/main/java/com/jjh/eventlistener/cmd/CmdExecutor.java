package com.jjh.eventlistener.cmd;

import java.nio.charset.Charset;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component 
public class CmdExecutor {
    
    @Value("${main.cmd}")
    private String cmd;
    
    public int execute(CmdArgument arg) {
        
        CommandLine cmdLine = new CommandLine(cmd);
        cmdLine.addArgument( formatEqualArg("severity", arg.get이벤트등급().getCode()), false  );
        cmdLine.addArgument( formatEqualArg("msg_grp", arg.get메세지그룹()), false  );
        cmdLine.addArgument( formatEqualArg("application", arg.get업무코드()), false  );
        cmdLine.addArgument( formatEqualArg("object", arg.get모니터링대상()), false  );
        cmdLine.addArgument( formatEqualArg("node", arg.get서버명()), false  );
        cmdLine.addArgument( formatEqualArg("msg_text", arg.get메세지()), false  );    
        
        return CommandLineExecute(cmdLine);
    }
    
    private String formatEqualArg(String key, String value) {
        return String.format("%1$s=%2$s", key, StringUtils.toEncodedString(value.getBytes(),Charset.forName("UTF-8")));
    }
    
    private int CommandLineExecute(CommandLine cmdLine) {
        
        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
        ExecuteWatchdog watchDog = new ExecuteWatchdog(60*1000);
        Executor executor = new DefaultExecutor();
        executor.setExitValue(0);
        executor.setWatchdog(watchDog);
        
        try {
            
            log.info("{}",cmdLine.toString());
            executor.execute(cmdLine, resultHandler);
            resultHandler.waitFor();
        } catch(Exception e) {
            log.error("{}\n" + resultHandler.getException(), cmdLine.toString());
        }
        
        if(resultHandler.getExitValue() != 0) log.error("{}\n" + resultHandler.getException(), cmdLine.toString());
        
        return resultHandler.getExitValue();
    }
    
}