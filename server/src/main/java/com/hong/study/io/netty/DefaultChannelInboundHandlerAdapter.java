package com.hong.study.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.nio.charset.StandardCharsets;

public class DefaultChannelInboundHandlerAdapter extends ChannelInboundHandlerAdapter {
    protected static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端上线：" + ctx.channel().remoteAddress());
        channels.add(ctx.channel());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端下线：" + ctx.channel().remoteAddress());
        channels.remove(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        String rtn = "客户端：" + ctx.channel().remoteAddress() + ":" + byteBuf.toString(CharsetUtil.UTF_8);
        channels.forEach(channel -> channel.writeAndFlush(Unpooled.wrappedBuffer(rtn.getBytes(StandardCharsets.UTF_8))));
    }
}
