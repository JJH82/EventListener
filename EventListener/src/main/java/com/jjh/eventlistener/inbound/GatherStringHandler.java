package com.jjh.eventlistener.inbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;



public class GatherStringHandler extends ChannelInboundHandlerAdapter {
    
    private ByteBuf reciveMsgBuf;
    
    @Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
        reciveMsgBuf = ctx.alloc().buffer();
        super.channelActive(ctx);
    }
    
    @Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        
        ByteBuf msgBuf = (ByteBuf)msg;
        try {
            reciveMsgBuf.writeBytes(msgBuf);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
    
    @Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        
        ctx.deregister();
        ReferenceCountUtil.release(reciveMsgBuf);
        ctx.fireChannelRead(reciveMsgBuf);
            
    }
    
    
}