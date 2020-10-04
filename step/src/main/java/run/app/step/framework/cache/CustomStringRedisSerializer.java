package run.app.step.framework.cache;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author lingSong
 * @date 2020/8/15 17:31
 */
public class CustomStringRedisSerializer implements RedisSerializer<Object> {

    private final Charset charset;

    public CustomStringRedisSerializer(){
        this(StandardCharsets.UTF_8);
    }

    public CustomStringRedisSerializer(Charset charset) {
        Assert.notNull(charset, "charset must not be null");
        this.charset = charset;
    }

    @Override
    public byte[] serialize(Object object) throws SerializationException {
        if(object == null){
            return new byte[0];
        }
        if(object instanceof String){
            return object.toString().getBytes(charset);
        }else{
            String string = JSON.toJSONString(object);
            return string.getBytes(charset);
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return (bytes == null ? null : new String(bytes, charset));
    }
}
