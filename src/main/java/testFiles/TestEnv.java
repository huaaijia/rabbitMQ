package testFiles;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by huaaijia on 16/1/21.
 */
public class TestEnv {
    public static void main(String[] args) {
        System.out.println("current system environment variable:");
        Map<String,String> map = System.getenv();
        Set<Map.Entry<String,String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("current system properties:");
        Properties properties = System.getProperties();
        Set<Map.Entry<Object, Object>> set = properties.entrySet();
        for (Map.Entry<Object, Object> objectObjectEntry : set) {
            System.out.println(objectObjectEntry.getKey() + ":" + objectObjectEntry.getValue());
        }

        System.out.println("=======");
        System.out.println("System.getenv(\"DM_MESSAGE_QUEUE\")="+System.getenv("DM_MESSAGE_QUEUE"));

//        Date date = new Date(1474271327007L);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.print(sdf.format(date));

    }
}
