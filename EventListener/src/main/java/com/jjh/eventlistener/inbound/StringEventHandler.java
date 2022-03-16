package com.jjh.eventlistener.inbound;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import com.jjh.eventlistener.core.controller.SplitStrRequest;
import com.jjh.eventlistener.core.util.ControllerUtils;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringEventHandler extends SimpleChannelInboundHandler<String> {
    
    private ApplicationContext ctx;
        
    public StringEventHandler(ApplicationContext ctx) {
        this.ctx = ctx;
    }
    
    protected void channelRead0(ChannelHandlerContext ch, String msg) throws Exception {
        
        String remoteAdd = StringUtils.replaceOnce(ch.channel().remoteAddress().toString(),"/","");
        remoteAdd = StringUtils.split(remoteAdd,":")[0];
        
        log.info("{} :{}", ch.channel().remoteAddress(), msg);
        
        SplitStrRequest req = new SplitStrRequest(ctx, msg, remoteAdd);
        ControllerUtils.NotifyAnnotation(req);
        
        
    }
}

