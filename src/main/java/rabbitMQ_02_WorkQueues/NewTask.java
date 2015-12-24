package rabbitMQ_02_WorkQueues;

/**
 * Created by huaaijia on 2015/12/17.
 */
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //1要求队列是持久化的
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

        for(int i=0; i<5; i++){
            String message = "The "+(i+1)+" message"+getMessage2();

            //2要求队列中的数据是持久化的
            channel.basicPublish("",
                                 TASK_QUEUE_NAME,
                                 MessageProperties.PERSISTENT_TEXT_PLAIN,
                                 message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        }


        channel.close();
        connection.close();
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 1)
            return "Hello World!";
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
