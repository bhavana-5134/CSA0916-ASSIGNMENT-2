import java.util.*;

class Student {
    private final String regNo, name;
    Student(String regNo,String name){this.regNo=regNo;this.name=name;}
    public boolean equals(Object o){
        if(!(o instanceof Student)) return false;
        return regNo.equals(((Student)o).regNo);
    }
    // TODO 1 completed: equal students must produce the same hash code.
    public int hashCode(){ return Objects.hash(regNo); }
    public String toString(){return regNo+"-"+name;}
}
public class StudentHashSetDemo {
    public static void main(String[] args) {
        Set<Student> students=new HashSet<>();
        students.add(new Student("192521153","Bhaavana"));
        students.add(new Student("192521153","Bhaavana B"));
        students.add(new Student("192521154","Student Two"));
        System.out.println("Unique students: "+students.size());
        System.out.println("Contains registration 192521153: "+students.contains(new Student("192521153","Any name")));
        System.out.println("Expected corrected set size: 2");
        System.out.println("Reason: equals() and hashCode() both use immutable regNo.");
    }
}
