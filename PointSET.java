import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;



public class PointSET {
    private SET<Point2D> set;
    public PointSET(){
        set = new SET<Point2D>();
        
    }// construct an empty set of points 
    public           boolean isEmpty() {
        return set.isEmpty();
    }// is the set empty? 
    public               int size() {
        return set.size();
    }// number of points in the set 
    public              void insert(Point2D p) {
        set.add(p);   
    }// add the point to the set (if it is not already in the set)
    public           boolean contains(Point2D p) {
        return set.contains(p);   
    }// does the set contain point p? 
    public              void draw() {
        for (Point2D p : set){
            p.draw();
        }
    }// draw all points to standard draw 
    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> res = new SET<Point2D>();
         for (Point2D p : set){
             
             if(rect.contains(p)) {    
                 res.add(p);
             }
            
         }
         return res;
    }// all points that are inside the rectangle 
    public           Point2D nearest(Point2D p) {
        double a= 100000000;
        double b=0;
        Point2D res=null;
        for (Point2D poi :set){
            b=p.distanceSquaredTo(poi);
            
            if ( a>b){
                a=b;
                res=poi;
            }
        }
        return res;
    }// a nearest neighbor in the set to point p; null if the set is empty 

    public static void main(String[] args) {
        
    }// unit testing of the methods (optional) 
}