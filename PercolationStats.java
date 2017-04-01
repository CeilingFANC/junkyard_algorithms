import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
public class PercolationStats {
    private int T;
    private double[] res;
    public PercolationStats(int n, int trials){
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("wrong argument");
        res=new double[trials];
        T=trials;
        for(int i = 0;i<trials;i++){
            Percolation a =new Percolation(n);
            while(!a.percolates()){
                
                int row=StdRandom.uniform(n)+1;
                int col=StdRandom.uniform(n)+1;
                a.open(row,col);   
            }
            
            res[i] = (double)a.numberOfOpenSites()/(n*n);


            
        }
    }// perform trials independent experiments on an n-by-n grid
    public double mean(){
        return StdStats.mean(res);
    }// sample mean of percolation threshold
    public double stddev(){
        return StdStats.stddev(res);
    }// sample standard deviation of percolation threshold
    public double confidenceLo(){
        return mean()-1.96*stddev()/(Math.sqrt(T));
        
    }// low  endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean()+1.96*stddev()/(Math.sqrt(T));
    }// high endpoint of 95% confidence interval

    public static void main(String[] args){
        String A= args[0];
        String B= args[1];
        int a =Integer.parseInt(A);
        int b =Integer.parseInt(B);        
        PercolationStats tt=new PercolationStats(a,b);
        StdOut.printf("mean                    = %f\n",tt.mean());
        StdOut.printf("stddev                  = %f\n",tt.stddev());
        StdOut.printf("95%% confidence interval = %f, %f\n",tt.confidenceLo(),tt.confidenceHi());
    }// test client (described below)
}