package run.app.step.project.monitor.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import run.app.step.common.enums.code.msg.MsgActionEnum;
import run.app.step.common.utils.converter.ConvertUtils;
import run.app.step.project.monitor.entity.netty.DataContent;
import run.app.step.project.monitor.entity.netty.UserChanelRel;

/**
 * @author lingSong
 * @date 2020/10/12 14:46
 */
@Slf4j
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    // 用于记录和管理所有客户端的channel
    public static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        Channel channel = ctx.channel();
        // 获取客户端所传输的消息
        String content = msg.text();
        System.out.println("content = " + content);
        //类型转换
        DataContent dataContent = ConvertUtils.jsonToPojo(content, DataContent.class);
        // 根据不同消息类型来处理不同的业务
        Integer action = dataContent.getAction();

        // 2.1 当websocket 第一次open的时候， 初始化channel 与userID关联
        if(action == MsgActionEnum.CONNECT.type){
            String senderId = dataContent.getChatMsg().getSenderId();
            UserChanelRel.put(senderId, channel);
        }else if(action == MsgActionEnum.CHAT.type) {
            // 2.2 聊天类型的消息， 把聊天记录保存到数据库  同时标记消息的签收状态
            //TODO 建议方法封装

        }else if(action == MsgActionEnum.SIGNED.type){
            // 2.3 签收消息类型 针对具体的消息进行签收 修改数据库中对应消息的签收状态


        }else if(action == MsgActionEnum.KEEPALIVE.type){
            //2.4 保存心跳类型
        }

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        users.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        String shortChannelId = ctx.channel().id().asShortText();
        log.info("客户端被移除, channel idWie：[{}]", shortChannelId);

        users.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 发生了异常
        ctx.channel().close();
        users.remove(ctx.channel());
    }

}
