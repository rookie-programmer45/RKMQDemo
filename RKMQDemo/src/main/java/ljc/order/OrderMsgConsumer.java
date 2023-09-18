package ljc.order;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "fifo_topic1", consumerGroup = "fifo_consumerG1", consumeMode = ConsumeMode.ORDERLY)
public class OrderMsgConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        // 处理顺序消息
        System.out.println("received order msg: " + message);
    }
}
