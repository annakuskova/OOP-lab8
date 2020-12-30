
import java.util.*;

public class CrawlerTask implements Runnable {
    public UrlDepthPair depthPair;

    public UrlPool myPool;

    public CrawlerTask(UrlPool pool) {
        myPool = pool;
    }

    public void run() {
        depthPair = myPool.get();

        int myDepth = depthPair.getDepth();

        LinkedList<String> linksList = new LinkedList<String>();
        linksList = Crawler.getAllLinks(depthPair);

        for (String newURL : linksList) {
            UrlDepthPair newDepthPair = new UrlDepthPair(newURL, myDepth + 1);
            myPool.put(newDepthPair);
        }
    }
}