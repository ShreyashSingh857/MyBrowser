
import java.net.InetAddress;
import java.net.UnknownHostException;

class URL{
    String scheme;
    String host;
    String url;
    boolean isHTTP;
    String path;
    public URL(String url){
        String[] splittedURL = url.split("://" , 2);
        this.scheme = splittedURL[0];
        this.url = splittedURL[1];
        this.isHTTP = scheme.equals("http");
        if(!this.url.contains("/")){
            this.url = this.url + "/";
        }
        String[] splittedURLHost = this.url.split("/", 2);
        this.host = splittedURLHost[0];
        this.path = "/" + splittedURLHost[1];
    }

    // public void request(){
        
        

    // }
    public static void main(String[] args) throws UnknownHostException{
        // String url = "https://gemini.google.com/app/c7d9aebfb849ed2b?utm_source=app_launcher&utm_medium=owned&utm_campaign=base_all";
        // URL u = new URL(url);
        // System.out.println(u.scheme);
        // System.out.println(u.host);
        // System.out.println(u.path);
        InetAddress Address = InetAddress.getLocalHost();
        System.out.println(Address);
        Address = InetAddress.getByName("www.google.com");
        System.out.println(Address);
        InetAddress addresses[] = InetAddress.getAllByName("www.nba.com");
        for(int i = 0; i<addresses.length; i++){
            System.out.println(addresses[i]);
        }
    }
}