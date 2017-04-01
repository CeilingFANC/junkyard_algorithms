import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
   private int n;
   private Node first;
   private Node last;
   
   private class Node{
    private Item item;
    private Node next;
    private Node previous;
   }
 
 public Deque(){
    first = null;
    last = null;
    n =0;
    // construct an empty deque
   }
   public boolean isEmpty(){
    return first == null;
    // is the deque empty?
   }
   public int size(){
    return n;
    // return the number of items on the deque
   }
   public void addFirst(Item item){
    if ( item == null) throw new NullPointerException();
    Node oldfirst = first;
    first = new Node();
    first.item = item;
    first.next = oldfirst;
    first.previous = null;
    n++;
    if( n==1 ) last = first;
    else oldfirst.previous = first;
    // add the item to the front
   }
   public void addLast(Item item){
    if ( item == null) throw new NullPointerException();   
    Node oldlast = last;
    last = new Node();
    last.item = item;
    last.next = null;
    last.previous = oldlast;
    n++;
    if( n==1 ) first = last;
    else oldlast.next = last;
    // add the item to the end
   }
   public Item removeFirst(){
       if (isEmpty()) throw new NoSuchElementException("Stack underflow");
       Item item = first.item;
    first = first.next;       
       if (n == 1) last = null;
    else first.previous = null;    
    n--;
    return item;
    // bug!!!!!!!!!!!!!!
    
    // remove and return the item from the front
   }
   public Item removeLast(){
    if (isEmpty()) throw new NoSuchElementException("Stack underflow");
    Item item = last.item;
    last = last.previous;
    if ( n == 1 ) first = null;
    else last.next = null;
    n--;
    return item;
    // remove and return the item from the end
   }
   public Iterator<Item> iterator(){
    return new DequeIterator();
    // return an iterator over items in order from front to end
   }
   private class DequeIterator implements Iterator<Item>{
    private Node current = first;
    public boolean hasNext(){
     return current != null;
    }
    public void remove(){
     throw new UnsupportedOperationException();
    }
    public Item next(){
           if (!hasNext()) throw new NoSuchElementException();
           Item item = current.item;
           current = current.next; 
           return item;
    }
   }
   public static void main(String[] args){

    // unit testing (optional)
   }
}