package run.app.step.framework.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import run.app.step.project.monitor.netty.WebSocketServer;

/**
 * @author lingSong
 * @date 2020/10/12 15:50
 */
@Component
public class NettyBooterListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null){
            try {
                WebSocketServer.getInstance().start();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
