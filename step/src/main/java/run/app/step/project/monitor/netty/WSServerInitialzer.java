package run.app.step.project.monitor.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author lingSong
 * @date 2020/10/12 15:46
 */
public class WSServerInitialzer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        // 获取管道
        ChannelPipeline pipeline = channel.pipeline();
        // websocket 基于http协议 所以需要编码器
        pipeline.addLast(new HttpServerCodec());
        // 在http上有一些数据流产生
        pipeline.addLast(new ChunkedWriteHandler());
        // 对httpMessage 进行聚合处理?
        pipeline.addLast(new HttpObjectAggregator(1024*64));

        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        // 自定义handler
        pipeline.addLast(new ChatHandler());
    }

}
