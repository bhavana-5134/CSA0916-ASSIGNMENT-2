import java.util.*;

public class StringBenchmark {
    static volatile int sink;
    static String withPlus(int fields) {
        String s = "";
        for (int i = 0; i < fields; i++) s += "FIELD" + i + "=VALUE" + i + "\n";
        return s;
    }
    static String withBuilder(int fields) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < fields; i++) b.append("FIELD").append(i).append("=VALUE").append(i).append('\n');
        return b.toString();
    }
    static String withBuffer(int fields) {
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < fields; i++) b.append("FIELD").append(i).append("=VALUE").append(i).append('\n');
        return b.toString();
    }
    static long measure(java.util.function.IntFunction<String> fn, int fields, int repeats) {
        long start = System.nanoTime();
        for (int i=0;i<repeats;i++) sink += fn.apply(fields).length();
        return (System.nanoTime()-start)/repeats;
    }
    public static void main(String[] args) {
        int[][] workloads = {{6,1},{50,10000},{100,1000}};
        System.out.println("Workload,FieldsPerTicket,Tickets,Plus_ns,Builder_ns,Buffer_ns");
        for (int[] w : workloads) {
            int fields=w[0], tickets=w[1];
            int repeats=Math.max(5, Math.min(tickets, 1000));
            long p=measure(StringBenchmark::withPlus,fields,repeats);
            long b=measure(StringBenchmark::withBuilder,fields,repeats);
            long sb=measure(StringBenchmark::withBuffer,fields,repeats);
            System.out.printf("%s,%d,%d,%d,%d,%d%n",
                fields==6?"Short ticket":fields==50?"Large booking batch":"Custom workload",
                fields,tickets,p,b,sb);
        }
        System.out.println("Note: timings vary by machine/JVM; run more than once. String + creates intermediate immutable strings; Builder is mutable and not synchronized; Buffer is synchronized.");
        System.out.println("Recommendation: StringBuilder for each private ticket; StringBuffer only when a mutable buffer itself is shared between threads. Use a thread-safe logger for shared logs.");
    }
}
