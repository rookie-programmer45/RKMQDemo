package ljc.transaction;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "tx_topic1", consumerGroup = "tx_consumerG1")
public class TXConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        // 当下游服务收到事务消息时，进行事务处理
        System.out.println("received tx msg: " + message);
    }
}
