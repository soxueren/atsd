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
        log("connecting to {0}:{1,number,#}", host, port);
        socket = new Socket(host, port);
        writer = new PrintWriter(socket.getOutputStream(), true);
        log("connection established to {0}:{1,number,#}", host, port);
    }

    public void shutdown() {
        log("shutting down connection to {0}:{1,number,#} > commands sent={2}", host, port, commandCounter);
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
        if (commandCounter <= 10){
            log("command [{0,number,#}] sent: {1}", commandCounter, command);
        }
    }

    public void sendSeries(Date date, String entity, String metric, double value) throws IOException {
        String command = "series";
        command += " ms:" + date.getTime();
        command += " e:" + escape(entity);
        command += " m:" + escape(metric)+"=" + value;
        writeCommand(command);
    }

    public void sendSeries(Date date, String entity, String metric, double value, Map<String, String> tags) throws IOException {
        String command = "series";
        command += " ms:" + date.getTime();
        command += " e:" + escape(entity);
        command += " m:" + escape(metric)+"=" + value;
        if (tags != null) {
            for (Map.Entry<String, String> entry : tags.entrySet()) {
                command += " t:" + escape(entry.getKey()) +"=" + escape(entry.getValue());
            }
        }
        writeCommand(command);
    }

    public void sendSeries(Date date, String entity, Map<String, Double> metrics, Map<String, String> tags) throws IOException {
        String command = "series";
        command += " ms:" + date.getTime();
        command += " e:" + escape(entity);
        for (Map.Entry<String, Double> entry : metrics.entrySet()) {
            command += " m:" + escape(entry.getKey()) +"=" + entry.getValue();
        }
        if (tags != null) {
            for (Map.Entry<String, String> entry : tags.entrySet()) {
                command += " t:" + escape(entry.getKey()) +"=" + escape(entry.getValue());
            }
        }
        writeCommand(command);
    }

    public void sendMessage(Date date, String entity, Map<String, String> tags, String message) throws IOException {
        String command = "message";
        command += " ms:" + date.getTime();
        command += " e:" + escape(entity);
        if (tags != null) {
            for (Map.Entry<String, String> entry : tags.entrySet()) {
                command += " t:" + escape(entry.getKey()) +"=" + escape(entry.getValue());
            }
        }
        command += " m:" + escape(message);
        writeCommand(command);
    }

    public void sendProperty(Date date, String entity, String type, Map<String, String> keys, Map<String, String> tags) throws IOException {
        String command = "property";
        command += " ms:" + date.getTime();
        command += " e:" + escape(entity);
        command += " t:" + escape(type);
        if (keys != null) {
            for (Map.Entry<String, String> entry : keys.entrySet()) {
                command += " k:" + escape(entry.getKey()) +"=" + escape(entry.getValue());
            }
        }
        if (tags != null) {
            for (Map.Entry<String, String> entry : tags.entrySet()) {
                command += " v:" + escape(entry.getKey()) +"=" + escape(entry.getValue());
            }
        }
        writeCommand(command);
    }

    private String escape(String s){
        if (s.indexOf("\"") >= 0){
            s = s.replaceAll("\"", "\"\"");
        }
        char[] escapeChars = {'=','"', ' ','\r','\n','\t'};
        checkQuote: for (char c : escapeChars) {
            if (s.indexOf(c)>=0){
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
