import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
public class Permutation {
   public static void main(String[] args) {
    String A = args[0];
    int a = Integer.parseInt(A);
    RandomizedQueue<String> tt = new RandomizedQueue<String>();
    String in;
    int count = 0;
    int prob = 0;
    String garb;
    while (!StdIn.isEmpty()){
        in = StdIn.readString();
        count++;
     if ( count <= a) {
      tt.enqueue(in);     
     }
     else{
      prob=StdRandom.uniform(count);
      if (prob < a){
       garb = tt.dequeue();
       tt.enqueue(in);
      }
     }

    }
    for (String s:tt){
     StdOut.println(s);     
    }

   }
}