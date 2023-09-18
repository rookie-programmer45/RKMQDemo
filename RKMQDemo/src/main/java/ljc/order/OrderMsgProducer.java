package ljc.order;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * 顺序消息类型的主题创建命令: ./mqadmin updateTopic -n localhost:9876 -t fifo_topic1 -c DefaultCluster -a +message.type=FIFO
 * 顺序消息生产者
 */
@RestController
public class OrderMsgProducer {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/sendfifo/{msgs}")
    public void send(@PathVariable String msgs) {
        String[] msgsArray = msgs.split("-");
        Arrays.stream(msgsArray).forEach(this::sendSyncOrderMsg);
    }

    private void sendSyncOrderMsg(String msgPayload) {
        SendResult sendResult = rocketMQTemplate.syncSendOrderly("fifo_topic1", msgPayload, "orderMsgKey1");
        System.out.println("顺序消息{" + msgPayload + "}发送结果: " + sendResult.getSendStatus());
    }
}
