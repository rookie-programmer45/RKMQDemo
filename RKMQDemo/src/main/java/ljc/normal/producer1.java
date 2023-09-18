package ljc.normal;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class producer1 {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/send/{msg}")
    public void send(@PathVariable String msg) {
//        rocketMQTemplate.convertAndSend("topic1", msg);
        rocketMQTemplate.asyncSend("topic1", msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("msg consume success");
            }

            @Override
            public void onException(Throwable e) {
                System.out.println("msg consume fail");
            }
        });
    }
}
