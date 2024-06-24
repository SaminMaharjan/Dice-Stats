import java.util.ArrayList;
import java.util.Arrays;
public class Test{
  public static void main(String[] args){
    Main test = new Main();
    //Test One : throwDice Method
    final int TRIALS = 10000;
    int[] trials = new int[TRIALS];
    int[] outcomes = new int[6];
    for (int trial: trials){
      int dice = test.throwDice();
      outcomes[dice-1]++;
    }
    //They should all be almost equal to 1/6 = 0.1667
    double total = 0;
    for (int outcome : outcomes){
      System.out.println(outcome/(double)TRIALS);
      total += outcome/(double)TRIALS;   
    }
    //total should be close to one
    System.out.println(total);

    //Testing longestConsecutive MEthod
    Integer [] eventsArr = {2, 3, 2, 2, 4, 4, 6, 6, 6, 6};
    ArrayList<Integer> events = new ArrayList<Integer>(Arrays.asList(eventsArr));
    //It should print 4 since 6 has come 4 times
    System.out.println(test.longestConsecutive(events, 3));

    
  }
}