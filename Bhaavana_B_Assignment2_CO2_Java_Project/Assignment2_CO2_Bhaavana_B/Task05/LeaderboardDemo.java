import java.util.*;

class Team {
    final String name; final int points; final int goalDiff;
    Team(String name,int points,int goalDiff){this.name=name;this.points=points;this.goalDiff=goalDiff;}
    public String toString(){return name+"("+points+","+goalDiff+")";}
}
class Leaderboard<T> {
    private final List<T> items=new ArrayList<>();
    private final Comparator<? super T> order;
    Leaderboard(Comparator<? super T> order){this.order=order;}
    void add(T item){items.add(item);}
    // TODO 1 completed: sort a copy so internal insertion order is unchanged.
    List<T> ranking(){List<T> copy=new ArrayList<>(items); copy.sort(order); return copy;}
    // PECS: accepts a list of T or subtype of T.
    void addAll(List<? extends T> values){items.addAll(values);}
}
public class LeaderboardDemo {
    public static void main(String[] args){
        Comparator<Team> order=Comparator.comparingInt((Team t)->t.points).reversed()
            .thenComparing(Comparator.comparingInt((Team t)->t.goalDiff).reversed())
            .thenComparing(t->t.name);
        Leaderboard<Team> board=new Leaderboard<>(order);
        board.addAll(Arrays.asList(new Team("Falcons",12,5),new Team("Tigers",12,8),
            new Team("Wolves",9,7),new Team("Bears",12,8)));
        System.out.println("Leaderboard:");
        for(Team t:board.ranking()) System.out.println(t);
        System.out.println("Original insertion order remains unchanged internally.");
        System.out.println("Comparator tie-breaks: points desc, goal difference desc, name asc.");
    }
}
