
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
   private int[] opensite;
   private int siz;
   private int num=0;
   private boolean[] bot;
   private WeightedQuickUnionUF UN;
   public Percolation(int n){
       if (n <= 0) throw new IllegalArgumentException("wrong argument");
       siz = n;
       int N = n * n + 2;
       bot = new boolean[n];
       opensite = new int[N];
       UN = new WeightedQuickUnionUF(N);
       for(int i = 0; i < N ; i++)
           opensite[i] = 0;   
       
       for(int i = 0; i < siz ; i++){
           UN.union(i,siz*siz);
           UN.union(i + siz * siz - siz, siz*siz+1);
           
       }
   }                // create n-by-n grid, with all sites blocked    public    void open(int row, int col)    // open site (row, col) if it is not open already
   public void open(int row, int col) {
       int n = transf(row, col);
        if (opensite[n] == 0) {
           opensite[n] = 1;
           num+=1;
       }
        if (vali(row + 1,col))
           ad_union(n + siz,n);
       if (vali(row - 1,col))
           ad_union(n - siz,n);
       if (vali(row,col + 1))
           ad_union(n + 1,n);
       if (vali(row,col - 1))
           ad_union(n - 1,n);
   }
   public boolean isOpen(int row, int col){
       int n=transf(row, col);
       if (opensite[n]==1)
           return true;
       else
           return false;
  
   }  // is site (row, col) open?
   
   public boolean isFull(int row, int col){
       int n=transf(row, col);
       if (opensite[n]==1 && UN.connected(n,siz*siz))
           return true;
       else
           return false;  
   }  // is site (row, col) full?
   public int numberOfOpenSites(){
       return num;
   }       // number of open sites
   public boolean percolates(){
       return UN.connected(siz*siz,siz*siz + 1);
   }              // does the system percolate?
   private void ad_union(int p, int q){                  
       if (opensite[p] == 1 && opensite[q] == 1)
           UN.union(p,q);  
       
   }  
   private boolean vali(int row, int col){
       if (row <= 0 || row > siz || col <= 0 || col > siz)
            return false;
        else
           return true;
   }
   private int transf(int row, int col){
        int n = (row-1) * siz +col - 1;      
        if (n < 0 || n >= siz*siz || row <= 0 || row > siz || col <= 0 || col > siz) throw new IndexOutOfBoundsException(" index i out of bounds");
        return n;
   }
}