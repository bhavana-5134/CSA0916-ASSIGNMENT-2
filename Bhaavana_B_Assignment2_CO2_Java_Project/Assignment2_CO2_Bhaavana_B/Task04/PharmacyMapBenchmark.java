import java.util.*;

public class PharmacyMapBenchmark {
    static volatile int sink;
    static void run(int n) {
        HashMap<String,Integer> hash=new HashMap<>();
        TreeMap<String,Integer> tree=new TreeMap<>();
        LinkedHashMap<String,Integer> linked=new LinkedHashMap<>();
        for(int i=0;i<n;i++){String k=String.format("MED%06d",i); hash.put(k,i);tree.put(k,i);linked.put(k,i);}
        String[] keys=hash.keySet().toArray(new String[0]);
        long t=System.nanoTime(); int s=0;
        for(int i=0;i<100000;i++) s+=hash.get(keys[i%n]);
        long hashLookup=System.nanoTime()-t; sink=s;
        t=System.nanoTime(); s=0;
        for(int i=0;i<100000;i++) s+=tree.get(keys[i%n]);
        long treeLookup=System.nanoTime()-t; sink=s;
        t=System.nanoTime(); String first=tree.keySet().iterator().next(); String last=((TreeMap<String,Integer>)tree).lastKey();
        long treeReport=System.nanoTime()-t;
        t=System.nanoTime(); first=linked.keySet().iterator().next(); last=null; for(String k:linked.keySet()) last=k;
        long insertionReport=System.nanoTime()-t;
        System.out.printf("%d,%d,%d,%d,%d%n",n,hashLookup,treeLookup,treeReport,insertionReport);
    }
    public static void main(String[] args){
        System.out.println("Medicines,HashMap_100kLookups_ns,TreeMap_100kLookups_ns,TreeMap_orderReport_ns,LinkedHashMap_insertionOrderReport_ns");
        run(100); run(1000); run(10000);
        System.out.println("HashMap: average O(1) lookup, no order guarantee. TreeMap: O(log n) lookup, sorted keys. LinkedHashMap: average O(1), insertion order.");
        System.out.println("Hashtable is legacy synchronized; prefer HashMap plus explicit concurrency control if required.");
    }
}
