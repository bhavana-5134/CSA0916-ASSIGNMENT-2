import java.util.*;

public class RedesignComparison {
    static volatile int sink;
    static long arrayListQueue(int n) {
        ArrayList<Integer> q=new ArrayList<>();
        for(int i=0;i<n;i++) q.add(i);
        long t=System.nanoTime();
        while(!q.isEmpty()) sink=q.remove(0);
        return System.nanoTime()-t;
    }
    static long arrayDequeQueue(int n) {
        ArrayDeque<Integer> q=new ArrayDeque<>();
        for(int i=0;i<n;i++) q.addLast(i);
        long t=System.nanoTime();
        while(!q.isEmpty()) sink=q.removeFirst();
        return System.nanoTime()-t;
    }
    public static void main(String[] args){
        int[] sizes={1000,5000,10000,20000};
        System.out.println("QueueSize,ArrayList_frontRemoval_ns,ArrayDeque_frontRemoval_ns");
        for(int n:sizes) System.out.printf("%d,%d,%d%n",n,arrayListQueue(n),arrayDequeQueue(n));
        System.out.println("Diagnosis: ArrayList remove(0) shifts remaining elements; total queue drain is O(n^2).");
        System.out.println("Redesign: ArrayDeque addLast/removeFirst; total drain is O(n), with O(1) amortized end operations.");
        System.out.println("Trade-off: ArrayList remains preferable when frequent indexed/random access is required.");
    }
}
