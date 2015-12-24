package rabbitMQ_04_Routing;

/**
 * Created by huaaijia on 2015/12/17.
 */
import com.rabbitmq.client.*;

import java.io.IOException;

public class ReceiveLogsDirect {

    private static final String EXCHANGE_NAME = "direct_logs";

    private static String[] al_severity = new String[]{"error","info","debug"};

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //channel.basicQos(1);

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        String queueName = channel.queueDeclare().getQueue();

        String[] severities = setSeverity();
        String strSeverities = "";

        for(String severity : severities){
            channel.queueBind(queueName, EXCHANGE_NAME, severity);
            strSeverities += severity+" ";
        }
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        System.out.println(" [*] Severities : "+strSeverities);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
                doWork(message);
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }

    private static String[] setSeverity(){
        int n = (int) (Math.random() * 3);
        String[] severites = new String[n];
        for(int i=0; i<n; i++){
            int k = (int) (Math.random() * 3);
            severites[i] = al_severity[k];
        }
        return severites;
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
