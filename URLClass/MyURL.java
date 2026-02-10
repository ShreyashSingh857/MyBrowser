package URLClass;

import CustomExceptions.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class MyURL {

    String scheme;
    String host;
    String url;
    Map<String, List<String>> header = new HashMap<>();
    String path;
    String StatusLine;
    String version, status, explaination;
    String content = "";
    int port;

    public MyURL(String url) {
        String[] splittedURL = url.split("://", 2);
        this.scheme = splittedURL[0];
        this.url = splittedURL[1];
        if ((!scheme.equals("http")) && (!scheme.equals("https"))) {
            throw new NotHTTPException("This browser only supports HTTP and HTTPS");
        };
        if (!this.url.contains("/")) {
            this.url = this.url + "/";
        }
        String[] splittedURLHost = this.url.split("/", 2);
        this.host = splittedURLHost[0];
        this.path = "/" + splittedURLHost[1];
    }

    public String request() throws Exception {
        Socket s = new Socket();
        this.port = scheme.equals("http") ? 80 : 443;
        InetSocketAddress address = new InetSocketAddress(this.host, this.port);
        s.connect(address);
        if (scheme.equals("https")) {
            this.port = 443;
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            s = (SSLSocket) factory.createSocket(
                    s,
                    this.host,
                    this.port,
                    true
            );
            ((SSLSocket) s).startHandshake();
        }
        System.out.println("Connected to " + host + " at port " + this.port + "!");
        String requestBody = "GET " + this.path + " HTTP/1.0\r\n";
        requestBody += "HOST: " + this.host + "\r\n";
        requestBody += "\r\n";

        OutputStream out = s.getOutputStream();
        out.write(requestBody.getBytes(StandardCharsets.UTF_8));
        out.flush();

        InputStream in = s.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        String line;

        String[] headerValues;
        boolean headers = true;
        int NumberOfLine = 0;
        while ((line = reader.readLine()) != null) {
            if (NumberOfLine == 0) {
                StatusLine = line;
                if (StatusLine == null) {
                    throw new IOException("Server closed the connection without a response.");
                }
            }
            if (NumberOfLine > 0 && headers == true) {
                if (line.isEmpty()) {
                    headers = false;
                    continue;
                }
                headerValues = line.split(":", 2);
                if (headerValues.length == 2) {
                    String key = headerValues[0].trim();
                    String values = headerValues[1].trim();
                    String[] IndividualValue = values.split(",");
                    for (String v : IndividualValue) {
                        header.computeIfAbsent(key, k -> new ArrayList<>()).add(v.trim());
                    }
                }
            } else if (!headers) {
                content = content + line;
                // System.out.println(line);
            }
            NumberOfLine++;
        }
        if (header.containsKey("tranfer-encoding")) {
            throw new TransferEncodingHeaderFoundException("transfer-encoding header found, which is not supported");
        }
        if (header.containsKey("content-encoding")) {
            throw new ContentEncodingHeaderFoundException("content-encoding header found, which is not supported");
        }
        String[] SplittedStatusLine = StatusLine.split(" ", 3);
        version = SplittedStatusLine[0];
        status = SplittedStatusLine[1];
        explaination = SplittedStatusLine[2];

        System.out.println(version);
        System.out.println(status);
        System.out.println(explaination);

        header.forEach((key, value) -> {
            value.forEach(values -> System.out.println(key + ": " + values));
        });
        // System.out.println(content);
        return content;
    }

    // public void request(){
    // }
}
