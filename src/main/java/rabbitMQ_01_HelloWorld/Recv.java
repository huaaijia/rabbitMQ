package rabbitMQ_01_HelloWorld;

/**
 * Created by huaaijia on 2015/12/17.
 */
import com.rabbitmq.client.*;

import java.io.IOException;

public class Recv {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //-----queueDeclare(String queue,
        //                  boolean durable,
        //                  boolean exclusive,
        //                  boolean autoDelete,
        //                  Map<String, Object> arguments)
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        //------basicConsume(String queue,
        //                   boolean autoAck,
        //                   Consumer callback)
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
