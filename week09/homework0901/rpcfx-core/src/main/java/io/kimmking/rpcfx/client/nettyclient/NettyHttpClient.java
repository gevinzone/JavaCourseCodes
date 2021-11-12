package io.kimmking.rpcfx.client.nettyclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URI;
import java.nio.charset.StandardCharsets;

public class NettyHttpClient {
    public String post(URI uri, String requestJson) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        NettyInboundHandler inboundHandler = new NettyInboundHandler();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                    ch.pipeline().addLast(new HttpResponseDecoder());
                    // 客户端发送的是httpRequest，所以要使用HttpRequestEncoder进行编码
                    ch.pipeline().addLast(new HttpRequestEncoder());
                    ch.pipeline().addLast(inboundHandler);
                }
            });

            // Start the client.
            b.connect(uri.getHost(), uri.getPort()).sync();

            DefaultFullHttpRequest request = new DefaultFullHttpRequest(
                    HttpVersion.HTTP_1_1, HttpMethod.POST, uri.toASCIIString());

            // 构建http请求
            request.headers().set(HttpHeaderNames.HOST, uri.getHost());
            request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            request.headers().add(HttpHeaderNames.CONTENT_TYPE, "application/json");
            ByteBuf byteBuf = Unpooled.copiedBuffer(requestJson, StandardCharsets.UTF_8);
//            request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, bbuf.readableBytes());
            request.content().clear().writeBytes(byteBuf);
            request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());



            ChannelPromise promise = inboundHandler.sendMessage(request);
            promise.await();
            return inboundHandler.getData();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

//    public ChannelFuture post(String host, int port) throws Exception {
//        Bootstrap b = new Bootstrap();
//        b.group(workerGroup);
//        b.channel(NioSocketChannel.class);
//        b.option(ChannelOption.SO_KEEPALIVE, true);
//        b.handler(new ChannelInitializer<SocketChannel>() {
//            @Override
//            public void initChannel(SocketChannel ch) throws Exception {
//                // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
//                ch.pipeline().addLast(new HttpResponseDecoder());
//                // 客户端发送的是httpRequest，所以要使用HttpRequestEncoder进行编码
//                ch.pipeline().addLast(new HttpRequestEncoder());
//                ch.pipeline().addLast(new NettyInboundHandler());
//            }
//        });
//
//        // Start the client.
//        return b.connect(host, port).sync();
//    }
//
//    public void sendRequest(ChannelFuture f, URI uri) throws InterruptedException {
//        DefaultFullHttpRequest request = new DefaultFullHttpRequest(
//                HttpVersion.HTTP_1_1, HttpMethod.POST, uri.toASCIIString());
//
//        // 构建http请求
//        request.headers().set(HttpHeaderNames.HOST, host);
//        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
//        request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
//
//
//        f.channel().write(request);
//        f.channel().flush();
//        f.channel().closeFuture().sync();
//    }
//
//    public void closeConnection() {
//        workerGroup.shutdownGracefully();
//    }

}
