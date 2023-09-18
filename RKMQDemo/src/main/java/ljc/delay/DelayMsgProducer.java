package ljc.delay;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 定时消息类型的主题创建命令: ./mqadmin updateTopic -n localhost:9876 -t delay_topic1 -c DefaultCluster -a +message.type=DELAY
 * 定时消息生产者
 */
@RestController
public class DelayMsgProducer {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/senddelay/{msg}")
    public void send(@PathVariable String msg) {
        sendSyncDelayMsg(msg);
    }

    private void sendSyncDelayMsg(String msgPayload) {
        // 延迟30s后才能消费
//        SendResult sendResult = rocketMQTemplate.syncSendDelayTimeMills("delay_topic1", msgPayload, 30 * 1000);

        // 也可以直接指定具体延迟到哪个时间点才能消费，这里要注意，这个时间点是broker服务器的时间点，若生产者服务器时间

        /*
        * 也可以直接指定具体延迟到哪个时间点才能消费
        * 这里要注意，这里指定的DeliverTime时间点是broker服务器的时间点，若生产者机器时间与broker机器的时间有误差，
        * 则这个延迟时间会与生产者预期的定时时间有出入
        * */
        SendResult sendResult = rocketMQTemplate.syncSendDeliverTimeMills("delay_topic1", msgPayload,
                System.currentTimeMillis() + 5 * 1000);
        System.out.println("定时消息{" + msgPayload + "}发送结果: " + sendResult.getSendStatus());
        System.out.println("生产消息时间: " + new Date());
    }
}
