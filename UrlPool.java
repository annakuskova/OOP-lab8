
import java.util.*;
public class UrlPool {
    public static int max_depth;
    private LinkedList<UrlDepthPair> pendingURLs;
    public LinkedList<UrlDepthPair> processedURLs;
    public ArrayList<String> seenURLs = new ArrayList<String>();

    public int waitingThreads;

    public UrlPool(int d) {
        waitingThreads = 0;
        pendingURLs = new LinkedList<UrlDepthPair>();
        processedURLs = new LinkedList<UrlDepthPair>();
        max_depth = d;
    }

    public synchronized int getWaitThreads() {
        return waitingThreads;
    }

    public synchronized int size() {
        return pendingURLs.size();
    }

    public synchronized void decrimentWaitingThreads() {
        waitingThreads--;
    }

    public synchronized boolean put(UrlDepthPair depthPair) {
        boolean isAdded = false;

        if (depthPair.getDepth() < max_depth && !processedURLs.contains(depthPair.getURL())) {
            pendingURLs.addLast(depthPair);
            isAdded = true;
            this.notify();
        } else {
            addSeenURL(depthPair);
        }

        return isAdded;
    }

    public synchronized UrlDepthPair get() {

        UrlDepthPair depthPair = null;
        if (pendingURLs.size() == 0) {
            waitingThreads++;
            try {
                this.wait();
            }
            catch (InterruptedException interruptedException) {
                System.err.println("InterruptedException: " + interruptedException.getMessage());
                return null;
            }
        }
        if(waitingThreads>0)waitingThreads--;
        depthPair = pendingURLs.pop();
        addSeenURL(depthPair);
        processedURLs.add(depthPair);
        return depthPair;
    }

    private synchronized void addSeenURL(UrlDepthPair dp) {
        if(!seenURLs.contains(dp.toString()))
            seenURLs.add(dp.toString());
    }

    public synchronized ArrayList<String> getSeenList() {
        return seenURLs;
    }
}