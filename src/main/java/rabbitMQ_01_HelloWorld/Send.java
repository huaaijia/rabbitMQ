package rabbitMQ_01_HelloWorld;

/**
 * Created by huaaijia on 2015/12/17.
 */
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

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
        String message = "Hello World!";
        //------basicPublish(String exchange,
        //                   String routingKey,
        //                   BasicProperties props,
        //                   byte[] body)
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}
