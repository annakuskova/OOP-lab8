
import java.net.*;
public class UrlDepthPair {
    private int depth;
    private String url = "";

    UrlDepthPair(String u, int d){
        url = u;
        depth = d;
    }
    UrlDepthPair(String u){
        url = u;
        depth = 0;
    }

    public String getURL() {
        return url;
    }
    public int getDepth() {
        return depth;
    }
    public String toString() {
        return url + " : " + depth;
    }

    public String getDocPath() {
        try {
            URL tempURL = new URL(url);
            return tempURL.getPath();
        }
        catch (MalformedURLException malformedURLException) {
            System.err.println("MalformedURLException in getDocPath(): " + malformedURLException.getMessage());
            return null;
        }
    }

    public String getWebHost() {
        try {
            URL tempURL = new URL(url);
            return tempURL.getHost();
        }
        catch (MalformedURLException malformedURLException) {
            System.err.println("MalformedURLException in getWebHost: " + malformedURLException.getMessage());
            return null;
        }
    }
}