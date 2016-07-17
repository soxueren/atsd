import java.io.*;
import java.text.*;
import java.util.*;

public class AtsdParseExample {

    /**
     * java AtsdParseExample atsd_server 8081 AX-1000 /home/axibase/in.csv
     *
     * @param args ATSD hostname, ATSD network API port (8081), default entity, absolute path to csv file.
     * @return null.
     */
    public static void main(String[] args) throws Exception {

        if (args == null || args.length < 4) {
            throw new IllegalArgumentException("Program arguments are missing or incomplete: {atsd_hostname} {atsd_network_api_port} {entity} {csv_file_path}");
        }

        log("start: {0}:{1} : entity={2} : file={3}", args);

        String entity = args[2];
        File file = new File(args[3]);

        //initialize TCP client, open connection
        AtsdTcpClient client = new AtsdTcpClient(args[0], Integer.parseInt(args[1]));
        client.init();

        //parse CSV file line by line, build commands from line fields, send commands via the client
        processFile(entity, file, client);

        //close the connection
        client.shutdown();
    }

    public static void processFile(String entity, File file, AtsdTcpClient dataService) throws IOException, ParseException {

        log("processing file={0} : size={1}", file.getAbsolutePath(), file.length());

        //7/8/2016 4:45:31 AM
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("PDT"));

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            int lineCount = 0;
            int seriesCount = 0;
            int messageCount = 0;
            int propertyCount = 0;
            long startTime = System.currentTimeMillis();
            String[] header = null;
            String line = reader.readLine();
            while (line != null) {
                lineCount++;
                line = line.trim();
                String[] fields = line.split(",");
                if (header == null){
                    header = fields;
                    log("header= {0}", line);
                    line = reader.readLine();
                    continue;
                }

                //tag,time,_index,value,status,questionable,substituted,annotated,annotations
                //AXI.NN-1000-80/OD-PV.CV,7/8/2016 4:45:31 AM,1,2.334466,0,False,False,False,

                //set pitag as metric
                String metric = fields[0];

                //alternatively, set metric as entity and entity as metric
                //possibly more efficient, depends on read queries

                Date date = dateFormat.parse(fields[1]);
                String valueStr = fields[3];

                try {
                    //if value is numeric, store it as series
                    double valueDouble = Double.parseDouble(valueStr);
                    dataService.sendSeries(date, entity, metric, valueDouble);
                    seriesCount++;
                } catch (NumberFormatException nfe) {
                    //store pitag as message
                    Map<String, String> tags = new LinkedHashMap<String, String>();
                    tags.put("type", "pi");
                    tags.put("source", metric);
                    dataService.sendMessage(date, entity, tags, valueStr);
                    messageCount++;
                }

                line = reader.readLine();
            }

            long endTime = System.currentTimeMillis();
            log("processed file={0} : time={1} ms : line.count= {2} : lines/sec= {3,number,#,###} : series={4} : messages={5} : properties={6}", file.getAbsolutePath(), (endTime - startTime), lineCount, (lineCount*1000d)/(endTime - startTime), seriesCount, messageCount, propertyCount);

        } finally {
            try {
                fis.close();
            } catch (Exception ie) {}
        }
    }

    private static void log(String message, Object... arguments) {
        System.out.println(MessageFormat.format(message, arguments));
    }

}