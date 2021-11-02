package com.gevinzone.gateway.outbound.netty4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.util.CharsetUtil;

public class NettyHttpClientOutboundHandler  extends ChannelInboundHandlerAdapter {
    
    @Override
    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {
        
        
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {

//        System.out.println(msg.toString());
        if (msg instanceof HttpResponse)
        {
            processHttpResponse((HttpResponse) msg);
        }
        if(msg instanceof HttpContent)
        {
            processHttpContent((HttpContent) msg);
        }
        
    }

    private void processHttpResponse(HttpResponse response) {
        System.out.println("CONTENT_TYPE:" + response.headers().get(HttpHeaderNames.CONTENT_TYPE));
    }

    private void processHttpContent(HttpContent content) {
        ByteBuf buf = content.content();
        System.out.println(buf.toString(CharsetUtil.UTF_8));
        buf.release();
    }
}