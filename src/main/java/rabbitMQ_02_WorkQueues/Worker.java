package rabbitMQ_02_WorkQueues;

/**
 * Created by huaaijia on 2015/12/17.
 */
import com.rabbitmq.client.*;

import java.io.IOException;

public class Worker {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        //-----queueDeclare(String queue,
        //                  boolean durable,
        //                  boolean exclusive,
        //                  boolean autoDelete,
        //                  Map<String, Object> arguments)
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        //tells RabbitMQ not to give more than one message to a  worker at a time
        //4：公平分发callback的get()
        int prefetchCount = 1 ;
        channel.basicQos(prefetchCount);

        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                System.out.println(" [x] Received '" + message + "'");
                try {
                    doWork(message);
                } finally {
                    // Using this code we can be sure that even if
                    // you kill a worker using CTRL+C while it was processing a message,
                    // nothing will be lost. Soon after the worker dies all
                    // unacknowledged messages will be redelivered.
                    System.out.println(" [x] Done");
                    // 1：手动验证
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        boolean autoAck = false;

//        channel.basicConsume(TASK_QUEUE_NAME, true, consumer);
        //1：手动验证参数2：false autoAck = false，需要手动发送acknowledgement
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, consumer);
    }

    private static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
