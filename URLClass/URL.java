package URLClass;
import CustomExceptions.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
public class URL {
    String scheme;
    String host;
    String url;
    Map<String, List<String>> header = new HashMap<>();
    String path;
    String StatusLine;
    String version, status, explaination;
    

    public URL(String url) {
        String[] splittedURL = url.split("://", 2);
        this.scheme = splittedURL[0];
        this.url = splittedURL[1];
        if(!scheme.equals("http")){
            throw new NotHTTPException("This browser only supports HTTP");
        };
        if (!this.url.contains("/")) {
            this.url = this.url + "/";
        }
        String[] splittedURLHost = this.url.split("/", 2);
        this.host = splittedURLHost[0];
        this.path = "/" + splittedURLHost[1];
    }

    public void request() throws Exception{
        Socket s = new Socket();
        InetSocketAddress address = new InetSocketAddress(this.host, 80);
        s.connect(address);
        System.out.println("Connected to " + host + " at port 80!");

        String requestBody = "GET "+ this.path + " HTTP/1.0\r\n";
        requestBody += "HOST: "+this.host + "\r\n";
        requestBody +="\r\n";

        OutputStream out = s.getOutputStream();
        out.write(requestBody.getBytes(StandardCharsets.UTF_8));
        out.flush();

        InputStream in = s.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        String line;

        String[] headerValues;
        boolean headers = true;
        int NumberOfLine = 0;
        while((line = reader.readLine() )!= null){
            if(NumberOfLine == 0){
                StatusLine = line;
            }
            if(NumberOfLine >0 && headers == true){
                if(line.isEmpty()) {
                    headers = false;
                    continue;
                }
                headerValues = line.split(":", 2);
                if(headerValues.length== 2){
                    String key = headerValues[0].trim();
                    String values = headerValues[1].trim();
                    String[] IndividualValue = values.split(",");
                    for(String v: IndividualValue){
                        header.computeIfAbsent(key, k -> new ArrayList<>()).add(v.trim());
                    }
                }
            }
            else{
                System.out.println(line);

            }
            NumberOfLine++;
        }
        String[] SplittedStatusLine = StatusLine.split(" ", 3);
        version = SplittedStatusLine[0];
        status = SplittedStatusLine[1];
        explaination = SplittedStatusLine[2];

        System.out.println(version);
        System.out.println(status);
        System.out.println(explaination);

        header.forEach((key, value) ->{
            value.forEach(values -> System.out.println(key + ": " + values));
        });

    }

    // public void request(){
    // }
    public static void main(String[] args) throws UnknownHostException {
        // String url = "https://gemini.google.com/app/c7d9aebfb849ed2b?utm_source=app_launcher&utm_medium=owned&utm_campaign=base_all";
        // URL u = new URL(url);
        // System.out.println(u.scheme);
        // System.out.println(u.host);
        // System.out.println(u.path);
        //     InetAddress Address = InetAddress.getLocalHost();
        //     System.out.println(Address);
        //     Address = InetAddress.getByName("www.google.com");
        //     System.out.println(Address);
        //     InetAddress addresses[] = InetAddress.getAllByName("www.nba.com");
        //     for(int i = 0; i<addresses.length; i++){
        //         System.out.println(addresses[i]);
        //     }
            String url = "http://example.com/";
            URL u = new URL(url);
            try {
                u.request();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

         }

    }
