package ljc.transaction;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;

@RocketMQTransactionListener()
public class TXLocalExecutor implements RocketMQLocalTransactionListener {

    /**
     * 本地事务的执行逻辑
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        // 可以从消息头获取事务id，也可以获取其它参数
        String txId = (String) msg.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        System.out.println("本次处理的事务的id=" + txId);

        /*
        * 事务执行的具体逻辑，包括业务事务执行，以及保存事务执行结果两大步
        * */
        try {
            // 业务逻辑
            // 保存业务逻辑执行结果

            // 模拟事务出现异常
//            int i = 1 / 0;

            // 事务正常执行，返回“事务提交成功”给broker
            System.out.println("事务正常执行，返回“事务提交成功”给broker");
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            // 事务执行异常，返回“事务回滚”给broker
            System.out.println("事务执行异常，返回“事务回滚”给broker");
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 这是兜底的方法。如果生产者一直未把事务执行结果发送给broker的话，broker会回调回来查事务执行结果，该
     * 方法就是查事务执行结果的逻辑
     * @param msg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        // 可以从消息头获取事务id，也可以获取其它参数return RocketMQLocalTransactionState.COMMIT;
        String txId = (String) msg.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        System.out.println("本次回查的事务的id=" + txId);

        // 可用事务id去查事务日志表

        // 根据上一步的结果，若事务执行成功，则返回“事务提交成功”给broker
        return RocketMQLocalTransactionState.COMMIT;

        // 若事务执行失败，则返回“事务回滚”给broker
//        return RocketMQLocalTransactionState.ROLLBACK;
    }
}
