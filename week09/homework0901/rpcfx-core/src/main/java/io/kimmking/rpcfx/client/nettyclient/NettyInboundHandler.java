package io.kimmking.rpcfx.client.nettyclient;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

public class NettyInboundHandler extends ChannelInboundHandlerAdapter {
    private ChannelHandlerContext ctx;
    private ChannelPromise promise;
    private String data = "";
    private long readByte;
    private long contentLength;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.ctx = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpResponse) {
            processHttpResponse((HttpResponse) msg);
        }
        if (msg instanceof HttpContent) {
            processHttpContent((HttpContent) msg);
        }

    }

    private void processHttpResponse(HttpResponse response) {
        if (response.headers().get(HttpHeaderNames.CONTENT_LENGTH) == null) {
            return;
        }
        contentLength = Long.parseLong(response.headers().get(HttpHeaderNames.CONTENT_LENGTH));
        readByte = 0;
    }

    private void processHttpContent(HttpContent content) {
        ByteBuf buf = content.content();

        readByte += buf.readableBytes();
        data += buf.toString(CharsetUtil.UTF_8);
        if (readByte >= contentLength) {
            promise.setSuccess();
        }
        buf.release();
    }

    public ChannelPromise sendMessage(Object message) {
        if (ctx == null)
            throw new IllegalStateException();
        promise = ctx.writeAndFlush(message).channel().newPromise();
        return promise;
    }

    public String getData() {
        return data;
    }
}
