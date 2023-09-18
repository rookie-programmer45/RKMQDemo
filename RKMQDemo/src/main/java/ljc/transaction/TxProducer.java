package ljc.transaction;

import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 创建事务消息类型的主题命令:
 * ./mqadmin updateTopic -n localhost:9876 -t tx_topic1 -c DefaultCluster -a +message.type=TRANSACTION
 */
@RestController
public class TxProducer {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/sendtx/{msg}")
    public void send(@PathVariable String msg) {
        sendHalfMsg(msg);
    }

    /**
     * 发送半事务消息给broker
     * @param msgPayload 消息体
     */
    private void sendHalfMsg(String msgPayload) {
        Message<String> mqMsg = MessageBuilder.withPayload(msgPayload).setHeader(RocketMQHeaders.TRANSACTION_ID, "tx01").build();
        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction("tx_topic1", mqMsg, "发送给本地事务处理方法的参数");
        System.out.println("半事务消息发送结果: " + transactionSendResult.getSendStatus());
    }
}
