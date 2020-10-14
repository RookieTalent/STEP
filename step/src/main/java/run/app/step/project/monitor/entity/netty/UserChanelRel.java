package run.app.step.project.monitor.entity.netty;

import io.netty.channel.Channel;

import java.util.HashMap;

/**
 * 用户ID和channel关联关系处理类
 *
 * @author lingSong
 * @date 2020/10/12 15:12
 */
public class UserChanelRel {
    private static HashMap<String, Channel> manage = new HashMap<>();

    public static void put(String senderId, Channel channel){
        manage.put(senderId, channel);
    }

    public static Channel get(String senderId){
        return manage.get(senderId);
    }


}
