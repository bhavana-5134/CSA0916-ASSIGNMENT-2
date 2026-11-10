import java.util.*;

public class PlaylistBenchmark {
    static volatile Object sink;
    static long timeAddArray(int n) {
        ArrayList<Integer> a=new ArrayList<>();
        long t=System.nanoTime(); for(int i=0;i<n;i++) a.add(i);
        sink=a; return System.nanoTime()-t;
    }
    static long timeAddLinked(int n) {
        LinkedList<Integer> a=new LinkedList<>();
        long t=System.nanoTime(); for(int i=0;i<n;i++) a.add(i);
        sink=a; return System.nanoTime()-t;
    }
    static long timeMiddleArray(int n) {
        ArrayList<Integer> a=new ArrayList<>(); for(int i=0;i<n;i++) a.add(i);
        long t=System.nanoTime(); for(int i=0;i<Math.min(n,1000);i++) a.add(a.size()/2,-i);
        sink=a; return System.nanoTime()-t;
    }
    static long timeMiddleLinked(int n) {
        LinkedList<Integer> a=new LinkedList<>(); for(int i=0;i<n;i++) a.add(i);
        long t=System.nanoTime(); for(int i=0;i<Math.min(n,1000);i++) a.add(a.size()/2,-i);
        sink=a; return System.nanoTime()-t;
    }
    static long timeGetArray(int n) {
        ArrayList<Integer> a=new ArrayList<>(); for(int i=0;i<n;i++) a.add(i);
        long t=System.nanoTime(); int s=0; for(int i=0;i<10000;i++) s+=a.get((i*31)%n);
        sink=s; return System.nanoTime()-t;
    }
    static long timeGetLinked(int n) {
        LinkedList<Integer> a=new LinkedList<>(); for(int i=0;i<n;i++) a.add(i);
        long t=System.nanoTime(); int s=0; for(int i=0;i<10000;i++) s+=a.get((i*31)%n);
        sink=s; return System.nanoTime()-t;
    }
    public static void main(String[] args) {
        int[] sizes={1000,5000,10000};
        System.out.println("Size,ArrayList_append_ns,LinkedList_append_ns,ArrayList_middleInsert1000_ns,LinkedList_middleInsert1000_ns,ArrayList_randomGet10000_ns,LinkedList_randomGet10000_ns");
        for(int n:sizes) System.out.printf("%d,%d,%d,%d,%d,%d,%d%n",n,
            timeAddArray(n),timeAddLinked(n),timeMiddleArray(n),timeMiddleLinked(n),timeGetArray(n),timeGetLinked(n));
        ArrayDeque<String> upNext=new ArrayDeque<>();
        upNext.addFirst("Urgent song"); upNext.addLast("Normal song");
        System.out.println("Up Next (deque): "+upNext);
        System.out.println("Recommendation: ArrayList for random access/read-heavy playlists; ArrayDeque for frequent add/remove at both ends.");
    }
}
