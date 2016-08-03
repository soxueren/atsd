import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import javax.xml.bind.DatatypeConverter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class DataApiExample {

    public static void main(String[] args) throws Exception {

        String databaseUrl = "http://10.102.0.6:8088";
        String userName = "axibase";
        String password = "********";
        String seriesQuery = "[{ \"startDate\": \"current_minute\", \"endDate\": \"now\", \"entity\": \"*\", \"metric\": \"mpstat.cpu_busy\", \"limit\": 100 }]";

        System.out.println("Execute seriesQuery:\r\n" + seriesQuery);

        URL url = new URL(databaseUrl + "/api/v1/series/query");
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
        byte[] payload = seriesQuery.getBytes();

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
                JSONObject result = (JSONObject)resultArray.get(i);
                JSONArray data = (JSONArray)result.get("data");

                System.out.println("series: entity= " + result.get("entity") + " : tags=" + result.get("tags"));

                for (int y = 0; y < data.size(); y++) {
                    JSONObject sample = (JSONObject)data.get(y);
                    System.out.println("\t" + sample.get("d") + " = " + sample.get("v"));
                }
            }

        } else {
            System.out.println(conn.getResponseMessage());
        }

    }


}
