package ljc.normal;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "topic1", consumerGroup = "group1")
public class consumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String msg) {
        System.out.println("received msg: " + msg);
    }
}
