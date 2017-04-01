import java.util.LinkedList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
public class BruteCollinearPoints {
    private int num = 0;
    private LinkedList<LineSegment> start = new LinkedList<LineSegment>();
    public BruteCollinearPoints(Point[] Points) {
        if ( Points == null)throw new NullPointerException();
        for (int i = 0; i < Points.length; i++)
            for (int j = 0; j<Points.length;j++){
            if ( Points[j] == null)throw new NullPointerException();            
            if ( i != j && Points[i].compareTo(Points[j]) == 0)
                throw new IllegalArgumentException();
        }
        double a,b,c;
       
        for( int i = 0; i < Points.length; i++)
            for ( int j = 0; j < Points.length; j++)        
            for ( int k = 0; k < Points.length; k++){
                            a = Points[i].slopeTo(Points[j]);
                            b = Points[i].slopeTo(Points[k]); 
                            if ( a != b)
                                continue;
                       for ( int m = 0; m <Points.length; m++){
                          c = Points[i].slopeTo(Points[m]);
                          if ( a == c && Points[i].compareTo(Points[j]) < 0 && Points[j].compareTo(Points[k]) < 0&& Points[k].compareTo(Points[m]) <0){
                              start.add(new LineSegment(Points[i],Points[m]));
                              num++;      
                          }}}
            
        
    }// finds all line segments containing 4 Points
    public           int numberOfSegments() {
        return num;
    }// the number of line segments
    public LineSegment[] segments() {
        LineSegment[] res = start.toArray(new LineSegment[start.size()]);
        return res;
    }// the line segments
    public static void main(String[] args) {

    // read the n Points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] Points = new Point[n];
    for (int i = 0; i < n; i++) {
        int x = in.readInt();
        int y = in.readInt();
        Points[i] = new Point(x, y);
    }

    // draw the Points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : Points) {
        p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(Points);
    
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
}
}