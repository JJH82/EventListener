package com.jjh.eventlistener.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

public class SendMessage {
	
	final Charset EUC_KR = Charset.forName("EUC-KR");
	final String Host = "";
	final int PORT = 3264;
	
	private void send(final String msg) {
		
		SocketChannel socketChannel = null;
		
		try {
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(true);
			socketChannel.connect(new InetSocketAddress(Host, PORT));
			
			if(socketChannel.isConnected()) {
				ByteBuffer byteBuffer = EUC_KR.encode(msg);
				socketChannel.write(byteBuffer);
				System.out.println(new String(byteBuffer.array()));
			}
		} catch (IOException e) {
			
			System.err.println(e);
			if(socketChannel != null&& socketChannel.isOpen()) {
				try {
					socketChannel.close();
				} catch(IOException e1) {
					System.err.println(e1);
				}
			}
		}finally {
			if(socketChannel != null&& socketChannel.isOpen()) {
				try {
					socketChannel.close();
				} catch(IOException e1) {
					System.err.println(e1);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		
		StringBuilder sb = new StringBuilder();
		List<String> msgList = new LinkedList<String>();
		SendMessage sender =new SendMessage(); 
		
		msgList.add("INFO 1 emp2 20210316140000 emp1 P TQQ TCP전송 NA 직원번호 테스트");
		msgList.add("INFO 2 01044531242 20210316140000 emp1 P TQQ TCP전송 NA 휴대폰번호 테스트");
		msgList.add("INFO 3 TTA 20210316140000 emp1 P TQQ TCP전송 NA 어플리케이션 테스트");
		

		msgList.forEach( sender::send);
		
	}

}
