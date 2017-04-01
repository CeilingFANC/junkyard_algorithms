import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;
public class RandomizedQueue<Item> implements Iterable<Item> {
   private Item[] a;
   private int n;
   private int size;
   private int N;
 public RandomizedQueue(){
    a = (Item[]) new Object[2];
    n = 0;
    N = 0;
    size = 2;
    // construct an empty randomized queue
   }
   public boolean isEmpty(){
    return n == 0;
    // is the queue empty?
   }
   public int size(){
    return n;
    // return the number of items on the queue
   }
   public void enqueue(Item item){
    if ( item == null) throw new NullPointerException();  
    if ( N == size) resize(2*size);
    a[N] = item;
    N++;
    n++;
    // add the item
   }
   public Item dequeue() {
    if (isEmpty()) throw new NoSuchElementException();
        if (n >0 && n == size/4)resize(size/2);
    int inde;
    inde = StdRandom.uniform(N);
    while(a[inde]==null){
     inde = StdRandom.uniform(N);
    }
    Item item = a[inde];
    a[inde] = null;
    n--;

    return item;
    // remove and return a random item
   }
   private void resize(int capacity) {
    Item[] temp = (Item[]) new Object[capacity];
    int j=0;
    Item tempo;
    for (int i = 0; i < N; i++) {
     tempo = a[i];
     if (tempo!=null)
      temp[j++] = tempo;
    }
    N=j;
    a = temp;
    size = capacity;
   }
   
   public Item sample(){
    if (isEmpty()) throw new NoSuchElementException();   
    int inde;
    inde = StdRandom.uniform(N);
    while(a[inde]==null){
     inde = StdRandom.uniform(N);
    }
    return a[inde];
    // return (but do not remove) a random item
   }
   public Iterator<Item> iterator() {
    return new RandomizedQueueIterator();
    // return an independent iterator over items in random order
   }
   private class RandomizedQueueIterator implements Iterator<Item> {
    private int i;
    private Item[] itali;
    public RandomizedQueueIterator() {
        itali = (Item[]) new Object[n];
        int j=0;
        Item tempo;
        for (int i = 0; i < N; i++) {
            tempo = a[i];
            if (tempo!=null){
                itali[j] = tempo;
                j++;
            }
        }



     StdRandom.shuffle(itali);
 
     i = 0;
    }
    public boolean hasNext(){
     
     if ( i < n  ) {
      
      return true;
     }
      
     else return false;
    }
    public void remove() {
     throw new UnsupportedOperationException();
    }
    public Item next() {
     if(!hasNext()) throw new NoSuchElementException();
     return itali[i++];
    }
   }
   public static void main(String[] args) {

    // unit testing (optional)
   }
}