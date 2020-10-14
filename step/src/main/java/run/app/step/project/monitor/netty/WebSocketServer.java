package run.app.step.project.monitor.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author lingSong
 * @date 2020/10/12 15:40
 */
@Slf4j
@Component
public class WebSocketServer {

    private static class SingletionWSServer{
        static final WebSocketServer instance = new WebSocketServer();
    }

    public static WebSocketServer getInstance(){
        return SingletionWSServer.instance;
    }

    private EventLoopGroup mainGroup;
    private EventLoopGroup subGroup;
    private ServerBootstrap server;
    private ChannelFuture future;

    public WebSocketServer() {
        mainGroup = new NioEventLoopGroup();
        subGroup = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(mainGroup, subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WSServerInitialzer());
    }

    public void start(){
        //TODO 注意端口是否发送错误
        this.future = server.bind(8082);
        if(future.isSuccess()){
            log.debug("启动 Netty 成功");
            System.out.println("启动 Netty 成功");
        }
    }


}
