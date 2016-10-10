import java.text.MessageFormat;
import java.util.*;

public class AtsdSendExample {

    /**
     * java AtsdSendExample
     *
     * @param args ATSD hostname, ATSD network API port (8081)
     * @return null.
     */
    public static void main(String[] args) throws Exception {

        if (args == null || args.length < 2) {
            throw new IllegalArgumentException("Program arguments are missing or incomplete: {atsd_hostname} {atsd_network_api_port}");
        }

        log("start: {0}:{1}", args);

        //initialize TCP client, open connection
        AtsdTcpClient client = new AtsdTcpClient(args[0], Integer.parseInt(args[1]));
        client.init();

        //send a numeric sample
        client.sendSeries(new Date(), "sensor-01", "temperature", 24.4d);

        //send a text message
        {
            Map<String, String> tags = new LinkedHashMap<String, String>();
            tags.put("type", "equipment");
            tags.put("source", "plant");
            client.sendMessage(new Date(), "sensor-01", tags, "Shutting down");
        }

        //send a property
        {
            Map<String, String> tags = new LinkedHashMap<String, String>();
            tags.put("precision", "analog");
            client.sendProperty(new Date(), "sensor-01", "equipment", null, tags);
        }

        //send a numeric sample
        client.sendValue(new Date(), "sensor-02", "humidity", 78.9d, null);

        //send a text message
        {
            Map<String, String> tags = new LinkedHashMap<String, String>();
            tags.put("source", "plant");
            client.sendValue(new Date(), "sensor-02", "equipment", "", tags);
        }

        //send a numeric sample
        client.sendPiTag(new Date(), "precipitation", "sensor-03", "", 79.0d, -1, 1, true, true, false, "Any product of the condensation of atmospheric water vapor",  null);

        //send a text message
        {
            Map<String, String> tags = new LinkedHashMap<String, String>();
            tags.put("source", "plant");
            client.sendPiTag(new Date(), "precipitation", "sensor-03", "equipment", null, 0, 0, false, true, false, "Any product of the condensation of atmospheric water vapor",  tags);
        }

        //close the connection
        client.shutdown();
    }


    private static void log(String message, Object... arguments) {
        System.out.println(MessageFormat.format(message, arguments));
    }

}