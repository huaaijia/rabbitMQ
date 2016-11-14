package rabbitMQ_05_Topics;

/**
 * Created by huaaijia on 2015/12/17.
 */

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogTopic {

    private static final String EXCHANGE_NAME = "topic_logs";

    //    * (star) can substitute for exactly one word.
    //    # (hash) can substitute for zero or more words.
    private static String[] routingKey = new String[]{"gray.smart.mouse","red.smart.fox","blue.fool.fish"};


    public static void main(String[] argv) {
        Connection connection = null;
        Channel channel = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");

            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            for(int i=0; i<10; i++){
                String routingKey = getRouting2();
                String message = "The "+(i+1)+" message"+getMessage2();

                channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
            }
        }
        catch  (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (Exception ignore) {}
            }
        }
    }

    private static String getRouting(String[] strings){
        if (strings.length < 1)
            return "anonymous.info";
        return strings[0];
    }

    private static String getMessage(String[] strings){
        if (strings.length < 2)
            return "Hello World!";
        return joinStrings(strings, " ", 1);
    }

    private static String joinStrings(String[] strings, String delimiter, int startIndex) {
        int length = strings.length;
        if (length == 0 ) return "";
        if (length < startIndex ) return "";
        StringBuilder words = new StringBuilder(strings[startIndex]);
        for (int i = startIndex + 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }

    private static String getRouting2(){
        int n = (int) (Math.random() * 3);
        return routingKey[n];
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
