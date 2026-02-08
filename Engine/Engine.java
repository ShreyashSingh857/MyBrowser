package Engine;
import HTMLParser.TextExtracter;
import URLClass.*;
import java.net.*;
public class Engine{
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
            MyURL u = new MyURL(url);
            try {
                String body = u.request();
                TextExtracter t = new TextExtracter(body);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
         }
}