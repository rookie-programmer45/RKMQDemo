package ljc.delay;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RocketMQMessageListener(topic = "delay_topic1", consumerGroup = "delay_consumerG1")
public class DelayMsgConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        // 处理定时消息
        System.out.println("received delay msg: " + message + ", received time: " + new Date());
    }
}
