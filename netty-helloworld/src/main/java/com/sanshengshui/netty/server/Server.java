package com.sanshengshui.netty.server;

import java.io.File;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslProvider;

/**
 * @author 穆书伟
 * @date 2018年9月18号
 * @description 服务端启动程序
 */
public final class Server {
    public  static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            File client = new File(System.getProperty("user.dir")+"/mycert/client.crt");
            File key = new File(System.getProperty("user.dir")+"/mycert/client.key");

            File rootCA = new File(System.getProperty("user.dir")+"/mycert/ca.crt");
            
            
            SslContext sslContext = SslContextBuilder.forServer(client,key).
            		clientAuth(ClientAuth.REQUIRE).sslProvider(SslProvider.OPENSSL)
            		.trustManager(rootCA)
            		.build();
            
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ServerInitializer(sslContext));
            ChannelFuture f = b.bind(8888);
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
