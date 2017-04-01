import java.util.*;
import java.util.Stack;
import java.lang.*;
import edu.princeton.cs.algs4.*;
public class Board {
    private final int n;
    private final char[] board;
    private int M = -1;
    private  int H = -1;
    public Board(int[][] blocks) {
        
        n = blocks.length;
        board = new char[n*n];
        for (int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
            board[i*n + j] = (char) blocks[i][j];
        
    }// construct a board from an n-by-n array of blocks
                                           // (where blocks[i][j] = block in row i, column j)
    public int dimension() {
        return n;
    }// board dimension n
    public int hamming() {
        if ( H > 0)
            return H;
        int count = 1;
        int result = 0;
        for(char s : board){
            if ( s == 0) {
                count++;
                continue;
            }
            if ( s != count){
             result++;   
            }
            count++;
          
        }
        H = result;
        return result;
    }// number of blocks out of place
    public int manhattan() {

        int count = 0;
        int result = 0;

        for ( char s : board){
            if (s == 0) {
                count++;
                continue;
            }

            result += Math.abs(count/n - (s - 1)/n)+Math.abs(count%n - (s-1)%n);
            count++;            
        }
        M = result;
        return result;
    }// sum of Manhattan distances between blocks and goal
    public boolean isGoal() {
        int count = 1;

        for ( char s : board){
            if ( s == 0 ){
             count++;
             continue;
            }
                
                
            else if ( s != count)
                return false;
            count++;

        }
        if ( board[n*n-1] != 0)
            return false;
        return true;
    }// is this board the goal board?
    public Board twin() {
        int[][] feed = new int[n][n];
        int count = 0;
        int position = 0;
        
        for ( char s : board) {
            feed[count/n][count%n] = (int) s;
            if ( s == 0)
                position = count;
            count++;
        }
        
        if ( position < n*n-2){
            int cvs;
            cvs = feed[n-1][n-1];
            feed[n-1][n-1] = feed[n-1][n-2];
            feed[n-1][n-2] = cvs;
        }
        else {
          int cvs;
            cvs = feed[n-2][n-1];
            feed[n-2][n-1] = feed[n-2][n-2];
            feed[n-2][n-2] = cvs;       
        }
        return new Board(feed);
        
    }// a board that is obtained by exchanging any pair of blocks
    public boolean equals(Object y) {
        if ( this.getClass().isInstance(y)){
            Board that = (Board) y;
        if ( this.n != that.n)
            return false;
        int N = n*n;
        for ( int i = 0; i < N; i++) {
            if ( this.board[i] != that.board[i])
                return false;
        }
        return true;
        }
        else 
            return false;
    }// does this board equal y?
    public Iterable<Board> neighbors() {
        Stack<Board> res = new Stack<Board>();
        int[][] feed = new int[n][n];     
        int count = 0;
        int position = 0;
        
        for ( char s : board) {
            feed[count/n][count%n] = (int) s;
            if ( s == 0)
                position = count;
            count++;
        } 
        int x = position/n;
        int y = position%n;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
             if ( i ==0 && j == 0)
                 continue;
             if ( i*j != 0)
                 continue;
             if ( x + i < 0 || x + i == n || y + j < 0 || y + j == n)
                 continue;
             feed[x][y] = feed[x+i][y+j];
             feed[x+i][y+j] = 0;
             res.push(new Board(feed));
             feed[x+i][y+j] = feed[x][y];
             feed[x][y] = 0;             
            }        
        }
        return res;
    }// all neighboring boards
    
    
    public String toString() {
        int[][] feed = new int[n][n];
        int count = 0;
        
        for ( char s : board) {
            feed[count/n][count%n] = (int) s;
           
            count++;
        }       
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", feed[i][j]));
            }
            s.append("\n");
        }
         return s.toString();    
    }// string representation of this board (in the output format specified below)

    public static void main(String[] args){
    }// unit tests (not graded)
}