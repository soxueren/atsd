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
            client.sendValue(new Date(), "sensor-02", "equipment", "Suspending", tags);
        }

        //send a numeric sample
        {
            Map<String, String> tags = new LinkedHashMap<String, String>();
            tags.put("precision", "digital");
            client.sendPiComp2("precipitation", new Date(), 2, 79.0d, -1, true, true, false,
                    "Any product of the condensation of atmospheric water vapor", "sensor-03", tags);
        }
        //send a text message
        {
            Map<String, String> tags = new LinkedHashMap<String, String>();
            tags.put("source", "plant");
            client.sendPiComp2("precipitation", new Date(), 3, "Suspending", 0, true, false, true,
                    "Any product of the condensation of atmospheric water vapor", "sensor-03", tags);
        }

        //close the connection
        client.shutdown();
    }


    private static void log(String message, Object... arguments) {
        System.out.println(MessageFormat.format(message, arguments));
    }

}