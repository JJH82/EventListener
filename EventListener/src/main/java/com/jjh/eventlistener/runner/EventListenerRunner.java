package com.jjh.eventlistener.runner;


import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.jjh.eventlistener.inbound.GatherStringHandler;
import com.jjh.eventlistener.inbound.StringEventHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;



@Component
public class EventListenerRunner implements CommandLineRunner {
    
    
    @Value("${event-listener.port}")
    private int port;
    
    @Autowired
    private ApplicationContext ctx;
    
    @Override
	public void run(String... args) throws Exception {
        
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(32,1024,3072))
            .childHandler(new ChannelInitializer<SocketChannel>() {
                
                @Override
				public void initChannel(SocketChannel sh) throws Exception {
                    ChannelPipeline cp = sh.pipeline();
//                    cp.addLast(new GatherStringHandler());
                    cp.addLast(new StringDecoder(Charset.forName("EUC-KR")));
                    cp.addLast(new StringEventHandler(ctx));
                    
                }
            });
            
            ChannelFuture f = b.bind(port).sync();
            
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
    
}