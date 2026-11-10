import java.util.*;

class Patient {
    final String name; final int severity; final long arrival;
    Patient(String name,int severity,long arrival){this.name=name;this.severity=severity;this.arrival=arrival;}
    public String toString(){return name+"(S"+severity+")";}
}
public class Triage {
    private long counter=0;
    // TODO 1 completed: lower severity number first; FIFO for equal severity.
    private final PriorityQueue<Patient> queue=new PriorityQueue<>(
        Comparator.comparingInt((Patient p)->p.severity).thenComparingLong(p->p.arrival));
    void admit(String name,int severity){
        if(severity<1||severity>5) throw new IllegalArgumentException("Severity must be 1 to 5");
        queue.add(new Patient(name,severity,counter++));
    }
    Patient next(){return queue.poll();}
    public static void main(String[] args){
        Triage t=new Triage();
        t.admit("Arun",3);t.admit("Bala",1);t.admit("Chitra",2);t.admit("Deepa",1);
        t.admit("Ezhil",5);t.admit("Farah",2);t.admit("Gokul",4);t.admit("Hari",3);
        System.out.println("Treatment order (verified by polling):");
        Patient p; while((p=t.next())!=null) System.out.println(p);
        System.out.println("PriorityQueue add/poll: O(log n); peek: O(1).");
        System.out.println("Fairness note: use waiting-time escalation/aging to reduce starvation.");
    }
}
