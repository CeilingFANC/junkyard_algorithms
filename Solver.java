import java.util.*;
import java.util.Stack;
import java.lang.*;
import edu.princeton.cs.algs4.*;
public class Solver {
    private search_node temp_un;
    private search_node temp;
    private boolean ready = true;
    private Stack<Board> res = new Stack<Board>();
    private             Stack<Board> result = new Stack<Board>();
    private int trace;
    private boolean solution = false;
    private Board ini;
    private final int cat;
    private final int dog;    
    
    public Solver(Board initial) {
        this.ini = initial;
        search_node fir= new search_node(null,initial);
        MinPQ<search_node> finding = new MinPQ<search_node>();    
        finding.insert(fir);
        cat = initial.manhattan()-1;
        dog = initial.twin().manhattan()-1;
        MinPQ<search_node> finding_un = new MinPQ<search_node>();
        finding_un.insert(new search_node(null,initial.twin()));
        boolean stop = true;       
        while ( ready){

            temp = finding.delMin();


            for ( Board a : temp.cur.neighbors()){
                if (temp.move == 0)
                finding.insert(new search_node(temp,a));                    
                else if ( a.equals(temp.pre.cur))
                    ;
                else
                finding.insert(new search_node(temp,a));
                
            }
            if(stop){
                            temp_un = finding_un.delMin();
            for ( Board b : temp_un.cur.neighbors()){
                if (temp_un.move==0)
                finding_un.insert(new search_node(temp_un,b));     
                else if ( b.equals(temp_un.pre.cur))
                    ;
                else
                finding_un.insert(new search_node(temp_un,b));
                
            }
            }
            
            
            if ( temp.move < cat && temp_un.move < dog)
                continue;
            
            if ( temp.M == 0 ){
                ready = false;
                     trace = temp.move;
        Board abs =temp.cur;                                        
        search_node fool;                                
        res.push(abs);
        fool =temp;
        for ( int k = 0;k < trace;k++){
            fool = fool.pre;
            res.push(fool.cur);
        }
            solution = true;  
                    while ( !res.empty()){
            result.push(res.pop());
        }
            }
            if (temp_un.M == 0){
                ready = false;
                         result = null;
            trace = -1;
            solution = false;   
            }
            if ( temp_un.P > 8*dog )
               stop = false;
            if ( temp.move > 40){
                ready = false;
                solution = false;
                result = null;
            }
        }
       
    }// find a solution to the initial board (using the A* algorithm)
    
    private class search_node implements Comparable<Solver.search_node> {
        private final search_node pre;
        private final Board cur;
        private int move = 0; 
        private final int M ;
        private final int P;
        private final int H;
        private search_node(search_node pre, Board cur) {
            this.pre = pre;

            this.cur = cur;
            M = this.cur.manhattan();
            if ( pre == null)
                this.move = 0;
            else
                this.move = this.pre.move + 1;
            P = M + this.move;
            H = this.cur.hamming() + this.move;
        }
        public int compareTo(search_node b){

            
            if ( this.P< b.P)
                return -1;
            else if ( this.P == b.P){
                if ( this.M < b.M)
                    return -1;
                else if ( this.M > b.M)
                    return 1;
                else 
                    {
                if ( this.H < b.H)
                    return -1;
                else if ( this.H > b.H)
                    return 1;
                else 
                    return 0;
                    
                    
            }
                    
            }
 
            else 
                return 1;
        }
       
        
    }
    
    
    
    
    public boolean isSolvable() {
  
            return solution;
        
    }// is the initial board solvable?
    public int moves() {
        if ( solution)
        return trace;
        else 
            return -1;
    }// min number of moves to solve initial board; -1 if unsolvable
    public Iterable<Board> solution() {
    
        return result;
    }// sequence of boards in a shortest solution; null if unsolvable
    public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
}
}