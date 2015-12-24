package rabbitMQ_03_PublishSubscribe;

/**
 * Created by huaaijia on 2015/12/17.
 */

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        for(int i=0; i<5; i++){
            String message = "The "+(i+1)+" message"+getMessage2();

            channel.basicPublish(EXCHANGE_NAME,
                                 "",
                                 null,
                                 message.getBytes("UTF-8"));

            System.out.println(" [x] Sent '" + message + "'");
        }

        channel.close();
        connection.close();
    }

    private static String getMessage(String[] strings){
        if (strings.length < 1)
            return "info: Hello World!";
        return joinStrings(strings, " ");
    }

    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0) return "";
        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }

    private static String getMessage2() {
        String spendTime = ".";
        int n = (int) (Math.random() * 10);
        for(int i=0; i<n; i++){
            spendTime+=".";
        }
        return spendTime;
    }
}
