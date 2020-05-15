package com.sanshengshui.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {
//    private static final StringDecoder DECODER = new StringDecoder();
//    private static final StringEncoder ENCODER = new StringEncoder();
    
    private final SslContext context;
    
    public ServerInitializer(SslContext context) {
		// TODO Auto-generated constructor stub
    	this.context = context;
	}

    private static final ServerHandler SERVER_HANDLER = new ServerHandler();


    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        
     // 添加SSL安装验证
        pipeline.addLast(context.newHandler(ch.alloc()));

        // Add the text line codec combination first,
//        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        // the encoder and decoder are static as these are sharable
//        pipeline.addLast(DECODER);
//        pipeline.addLast(ENCODER);

        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,22,2,1,0));
        //
        pipeline.addLast(new ByteArrayDecoder());
        //
        pipeline.addLast(new ByteArrayEncoder());
        
        // and then business logic.
        pipeline.addLast(SERVER_HANDLER);
    }
}
