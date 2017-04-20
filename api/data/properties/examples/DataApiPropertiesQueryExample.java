import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.xml.bind.DatatypeConverter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class DataApiPropertiesQueryExample {

    public static void main(String[] args) throws Exception {

        String databaseUrl = "http://10.102.0.6:8088";
        String userName = "axibase";
        String password = "********";
        String query = "[{\"type\": \"java_method\",\"entity\": \"atsd\",\"startDate\": \"current_hour\",\"endDate\":\"now\",\"limit\":3}]";

        System.out.println("Execute properties query:\r\n" + query);

        URL url = new URL(databaseUrl + "/api/v1/properties/query");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        System.out.println("Connection established to " + databaseUrl);

        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("Accept-Encoding", "gzip");
        conn.setRequestProperty( "Content-Type", "application/json");

        String authString = userName + ":" + password;
        String authEncoded = DatatypeConverter.printBase64Binary(authString.getBytes());
        conn.setRequestProperty("Authorization", "Basic " + authEncoded);
        byte[] payload = query.getBytes();

        conn.setRequestProperty("Content-Length", Integer.toString(payload.length));
        conn.setUseCaches(false);

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.write(payload);

        System.out.println("Request sent");

        int code = conn.getResponseCode();

        System.out.println("Response code: " + code);

        if (code == HttpURLConnection.HTTP_OK) {
            InputStreamReader reader;
            if ("gzip".equals(conn.getContentEncoding())) {
                reader = new InputStreamReader(new GZIPInputStream(conn.getInputStream()));
            } else {
                reader = new InputStreamReader(conn.getInputStream(), "utf-8");
            }
            JSONParser parser = new JSONParser();
            JSONArray resultArray = (JSONArray)parser.parse(reader);

            for (int i = 0; i < resultArray.size(); i++){
                JSONObject prop = (JSONObject)resultArray.get(i);

                System.out.println("property: entity= " + prop.get("entity") + " : type= " + prop.get("type") + " : date= " + prop.get("date"));

                JSONObject key = (JSONObject)prop.get("key");
                JSONObject tags = (JSONObject)prop.get("tags");
                System.out.println("    key = " + key);
                System.out.println("    tags= " + tags);
            }

        } else {
            System.out.println(conn.getResponseMessage());
        }

    }


}
