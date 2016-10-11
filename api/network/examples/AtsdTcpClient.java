import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

public class AtsdTcpClient {

    private String host;
    private int port;
    private Socket socket;
    private PrintWriter writer;
    private int commandCounter;

    public AtsdTcpClient(String _host, int _port) {
        host = _host;
        port = _port;
    }

    public void init() throws IOException {
        log("Connecting to {0}:{1,number,#}", host, port);
        socket = new Socket(host, port);
        writer = new PrintWriter(socket.getOutputStream(), true);
        log("Connection established to {0}:{1,number,#}", host, port);
        String version = getServerVersion();
        log("Server version: {0}", version);
        //check the version, it should be a numeric revision number such as 13743
        if (version == null) {
            throw new UnknownServiceException("Server failed to respond to 'version' command. Check that the client is connecting to TCP port (default 8081). Current port: " + port);
        } else {
            try {
                Integer.parseInt(version);
            } catch (NumberFormatException ne) {
                throw new UnknownServiceException("Invalid server version: " + version);
            }
        }
    }

    private String getServerVersion() throws IOException {
        writeCommand("version");
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line = reader.readLine();
        reader.close();
        return line;
    }

    public void shutdown() {
        log("Shutting down connection to {0}:{1,number,#} > commands sent={2}", host, port, commandCounter);
        try {
            writer.flush();
            writer.close();
        } catch (Exception ioe) {
        }
        try {
            socket.close();
        } catch (Exception ioe) {
        }
    }

    public synchronized void writeCommand(String command) {
        writer.println(command);
        writer.flush();
        commandCounter++;
        //print first 10 commands to stdout
        if (commandCounter <= 10) {
            log("Command [{0,number,#}] sent: {1}", commandCounter, command);
        }
    }

    public void sendSeries(Date date, String entity, String metric, double value) throws IOException {
        if (entity.contains(" ")) {
            throw new IllegalArgumentException("Entity name can include only printable characters");
        }
        if (metric.contains(" ")) {
            throw new IllegalArgumentException("Metric name can include only printable characters");
        }
        String command = "series";
        command += " ms:" + date.getTime();
        command += " e:" + escape(entity);
        command += " m:" + escape(metric) + "=" + value;
        writeCommand(command);
    }

    public void sendSeries(Date date, String entity, String metric, double value, Map<String, String> tags) throws IOException {
        if (entity.contains(" ")) {
            throw new IllegalArgumentException("Entity name can include only printable characters");
        }
        if (metric.contains(" ")) {
            throw new IllegalArgumentException("Metric name can include only printable characters");
        }
        String command = "series";
        command += " ms:" + date.getTime();
        command += " e:" + escape(entity);
        command += " m:" + escape(metric) + "=" + value;
        if (tags != null) {
            for (Map.Entry<String, String> entry : tags.entrySet()) {
                if (entry.getKey().contains(" ")) {
                    throw new IllegalArgumentException("Series tag name can include only printable characters");
                }
                if (entry.getValue() == null) {
                    throw new IllegalArgumentException("Series tag value cannot be null");
                }
                String val = escape(entry.getValue()).trim();
                if (val.isEmpty()) {
                    throw new IllegalArgumentException("Series tag value cannot be empty");
                }
                command += " t:" + escape(entry.getKey()) + "=" + val;
            }
        }
        writeCommand(command);
    }

    public void sendSeries(Date date, String entity, Map<String, Double> metrics, Map<String, String> tags) throws IOException {
        if (entity.contains(" ")) {
            throw new IllegalArgumentException("Entity name can include only printable characters");
        }
        String command = "series";
        command += " ms:" + date.getTime();
        command += " e:" + escape(entity);
        for (Map.Entry<String, Double> entry : metrics.entrySet()) {
            if (entry.getKey().contains(" ")) {
                throw new IllegalArgumentException("Metric name can include only printable characters");
            }
            command += " m:" + escape(entry.getKey()) + "=" + entry.getValue();
        }
        if (tags != null) {
            for (Map.Entry<String, String> entry : tags.entrySet()) {
                if (entry.getKey().contains(" ")) {
                    throw new IllegalArgumentException("Series tag name can include only printable characters");
                }
                if (entry.getValue() == null) {
                    throw new IllegalArgumentException("Series tag value cannot be null");
                }
                String val = escape(entry.getValue()).trim();
                if (val.isEmpty()) {
                    throw new IllegalArgumentException("Series tag value cannot be empty");
                }
                command += " t:" + escape(entry.getKey()) + "=" + val;
            }
        }
        writeCommand(command);
    }

    public void sendMessage(Date date, String entity, Map<String, String> tags, String message) throws IOException {
        if (entity.contains(" ")) {
            throw new IllegalArgumentException("Entity name can include only printable characters");
        }
        String command = "message";
        command += " ms:" + date.getTime();
        command += " e:" + escape(entity);
        if (tags != null) {
            for (Map.Entry<String, String> entry : tags.entrySet()) {
                if (entry.getKey().contains(" ")) {
                    throw new IllegalArgumentException("Message tag name can include only printable characters");
                }
                if (entry.getValue() == null) {
                    throw new IllegalArgumentException("Message tag value cannot be null");
                }
                String val = escape(entry.getValue()).trim();
                if (val.isEmpty()) {
                    throw new IllegalArgumentException("Message tag value cannot be empty");
                }
                command += " t:" + escape(entry.getKey()) + "=" + val;
            }
        }
        command += " m:" + escape(message);
        writeCommand(command);
    }

    public void sendProperty(Date date, String entity, String type, Map<String, String> keys, Map<String, String> tags) throws IOException {
        if (entity.contains(" ")) {
            throw new IllegalArgumentException("Entity name can include only printable characters");
        }
        if (type.contains(" ")) {
            throw new IllegalArgumentException("Property type can include only printable characters");
        }
        String command = "property";
        command += " ms:" + date.getTime();
        command += " e:" + escape(entity);
        command += " t:" + escape(type);
        if (keys != null) {
            for (Map.Entry<String, String> entry : keys.entrySet()) {
                if (entry.getKey().contains(" ")) {
                    throw new IllegalArgumentException("Property key name can include only printable characters");
                }
                if (entry.getValue() == null) {
                    throw new IllegalArgumentException("Property key value cannot be null");
                }
                String val = escape(entry.getValue()).trim();
                if (val.isEmpty()) {
                    throw new IllegalArgumentException("Property key value cannot be empty");
                }
                command += " k:" + escape(entry.getKey()) + "=" + val;
            }
        }
        if (tags != null) {
            for (Map.Entry<String, String> entry : tags.entrySet()) {
                if (entry.getKey().contains(" ")) {
                    throw new IllegalArgumentException("Property tag name can include only printable characters");
                }
                if (entry.getValue() == null) {
                    throw new IllegalArgumentException("Property tag value cannot be null");
                }
                String val = escape(entry.getValue()).trim();
                if (val.isEmpty()) {
                    throw new IllegalArgumentException("Property tag value cannot be empty");
                }
                command += " v:" + escape(entry.getKey()) + "=" + val;
            }
        }
        writeCommand(command);
    }

    public void sendValue(Date date, String entity, String name, Object value, Map<String, String> tags) throws IOException {
        if (entity.contains(" ")) {
            throw new IllegalArgumentException("Entity name can include only printable characters");
        }
        if (tags == null) {
            tags = new HashMap<>();
        }
        String command;
        if (value == null || value instanceof String) {
            command = "message";
            command += " ms:" + date.getTime();
            command += " e:" + escape(entity);
            tags.put("type", name);
            for (Map.Entry<String, String> entry : tags.entrySet()) {
                if (entry.getKey().contains(" ")) {
                    throw new IllegalArgumentException("Message tag name can include only printable characters");
                }
                if (entry.getValue() == null) {
                    throw new IllegalArgumentException("Message tag value cannot be null");
                }
                String val = escape(entry.getValue()).trim();
                if (val.isEmpty()) {
                    throw new IllegalArgumentException("Message tag value cannot be empty");
                }
                command += " t:" + escape(entry.getKey()) + "=" + val;
            }
            String message = value == null ? "" : String.valueOf(value);
            command += " m:" + escape(message);
        } else {
            if (name.contains(" ")) {
                throw new IllegalArgumentException("Metric name can include only printable characters");
            }
            command = "series";
            command += " ms:" + date.getTime();
            command += " e:" + escape(entity);
            command += " m:" + escape(name) + "=" + String.valueOf(value);
            for (Map.Entry<String, String> entry : tags.entrySet()) {
                if (entry.getKey().contains(" ")) {
                    throw new IllegalArgumentException("Series tag name can include only printable characters");
                }
                if (entry.getValue() == null) {
                    throw new IllegalArgumentException("Series tag value cannot be null");
                }
                String val = escape(entry.getValue()).trim();
                if (val.isEmpty()) {
                    throw new IllegalArgumentException("Series tag value cannot be empty");
                }
                command += " t:" + escape(entry.getKey()) + "=" + val;
            }
        }
        writeCommand(command);
    }

    public void sendPiComp2(String piTag, Date date, int index, Object value, int status, String flags,
                            String entity, Map<String, String> tags) throws IOException {
        if (entity.contains(" ")) {
            throw new IllegalArgumentException("Entity name can include only printable characters");
        }
        tags = filterIgnoredTags(status, flags, tags);
        String command;
        if (value == null || value instanceof String) {
            command = "message";
            command += " s:" + getSeconds(date, index);
            command += " e:" + escape(entity);
            tags.put("type", piTag);
            for (Map.Entry<String, String> entry : tags.entrySet()) {
                if (entry.getKey().contains(" ")) {
                    throw new IllegalArgumentException("Message tag name can include only printable characters");
                }
                if (entry.getValue() == null) {
                    throw new IllegalArgumentException("Message tag value cannot be null");
                }
                String val = escape(entry.getValue()).trim();
                if (val.isEmpty()) {
                    throw new IllegalArgumentException("Message tag value cannot be empty");
                }
                command += " t:" + escape(entry.getKey()) + "=" + val;
            }
            String svalue = value == null ? "" : String.valueOf(value);
            command += " m:" + escape(svalue);
        } else {
            if (piTag.contains(" ")) {
                throw new IllegalArgumentException("Metric name can include only printable characters");
            }
            command = "series";
            command += " s:" + getSeconds(date, index);
            command += " e:" + escape(entity);
            command += " m:" + escape(piTag) + "=" + String.valueOf(value);
            if (tags != null) {
                for (Map.Entry<String, String> entry : tags.entrySet()) {
                    if (entry.getKey().contains(" ")) {
                        throw new IllegalArgumentException("Series tag name can include only printable characters");
                    }
                    if (entry.getValue() == null) {
                        throw new IllegalArgumentException("Series tag value cannot be null");
                    }
                    String val = escape(entry.getValue()).trim();
                    if (val.isEmpty()) {
                        throw new IllegalArgumentException("Series tag value cannot be empty");
                    }
                    command += " t:" + escape(entry.getKey()) + "=" + val;
                }
            }
        }
        writeCommand(command);
    }

    private long getSeconds(Date date, int index) {
        return (date.getTime() / 1000 + (index > 1 ? index - 1 : 0));
    }

    private Map<String, String> filterIgnoredTags(int status, String flags, Map<String, String> tags) {
        flags = flags.toLowerCase();
        if (tags == null) {
            tags = new HashMap<>();
        }
        if (status != 0) {
            tags.put("status", String.valueOf(status));
        }
        if (flags.contains("a")) {
            tags.put("annotated", "true");
        }
        if (flags.contains("s")) {
            tags.put("substituted", "true");
        }
        if (flags.contains("q")) {
            tags.put("questionable", "true");
        }
        return tags;
    }

    private String escape(String s) {
        if (s.indexOf("\"") >= 0) {
            s = s.replaceAll("\"", "\"\"");
        }
        char[] escapeChars = {'=', '"', ' ', '\r', '\n', '\t'};
        checkQuote:
        for (char c : escapeChars) {
            if (s.indexOf(c) >= 0) {
                s = "\"" + s + "\"";
                break checkQuote;
            }
        }
        return s;
    }

    private static void log(String message, Object... arguments) {
        System.out.println(MessageFormat.format(message, arguments));
    }

}