package com.rain.httpfileserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpFileServer {
	static final int PORT = 8080;  
    public static void main(String[] args) throws Exception {  
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);  
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
        try {  
            ServerBootstrap b = new ServerBootstrap();  
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO))  
                    .childHandler(new ChannelInitializer<SocketChannel>() {// create channel when socket connect request  
                                @Override 
                                protected void initChannel(SocketChannel ch) throws Exception {  
                                    ChannelPipeline pipeline = ch.pipeline();  
                                    pipeline.addLast(new HttpServerCodec());  
                                    pipeline.addLast(new HttpObjectAggregator(65536));  
                                    pipeline.addLast(new ChunkedWriteHandler());  
                                    pipeline.addLast(new FileServerHandler());  
                                }  
                            });  
   
            Channel ch = b.bind(PORT).sync().channel();  
            System.err.println("Brown Address:" + ("http") + "://127.0.0.1:" + PORT + '/');  
            ch.closeFuture().sync();  
        } finally {  
            bossGroup.shutdownGracefully();  
            workerGroup.shutdownGracefully();  
        }  
    }  

}
