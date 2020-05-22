package com.sanshengshui.netty.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.util.Date;
import java.util.logging.Logger;

import com.sanshengshui.netty.utils.DigitalUtils;



@Sharable
public class ServerHandler extends SimpleChannelInboundHandler<byte[]> {
	
	
//	private static final Logger Logger = Lo
	
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 为新连接发送庆祝
        ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n");
        ctx.write("It is " + new Date() + " now.\r\n");
        ctx.flush();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, byte[] req) throws Exception {
        // Generate and write a response.
//        String response;
        boolean close = false;
        String  request = DigitalUtils.byte2hex(req);
//        if (request.isEmpty()) {
//            response = "Please type something.\r\n";
//        } else if ("bye".equals(request.toLowerCase())) {
//            response = "Have a good day!\r\n";
//            close = true;
//        } else {
//            response = "Did you say '" + request + "'?\r\n";
//        }
        
        
        System.out.println(request);
        

        ChannelFuture future = ctx.write(DigitalUtils.hex2byte(request));

        if (close) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
