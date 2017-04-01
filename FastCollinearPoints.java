import java.util.LinkedList;
import java.util.Arrays;

public class FastCollinearPoints {

    private int count_c = 0;
    private int num = 0;
    private double temp1 = 0;
    private double temp2 = 0;
    private int num_same;
    private Point[] original, Points;
    private int min;
    private int max;
    
    private LinkedList<LineSegment> start = new LinkedList<LineSegment>();
    public FastCollinearPoints(Point[] points) {
        System.arraycopy(Points,0,points,0,points.length);         
        if ( Points == null)throw new NullPointerException();
        int length = Points.length;        
        temp1 = Points[0].slopeTo(Points[0]);
        original = new Point[Points.length]; 
        System.arraycopy(Points,0,original,0,length);  
        
        Arrays.sort(original);        

        for ( int i = 0; i < length; i++){
            if ( original[i] == null)throw new NullPointerException();  
            System.arraycopy(original,0,Points,0,length);
    
            Arrays.sort(Points,original[i].slopeOrder());

            count_c = 0;
            temp2 = 0;
            num_same = 0; 
            temp1 = original[i].slopeTo(Points[0]); 
            for ( int j = 1; j < length;j++){
                temp2 = original[i].slopeTo(Points[j]); 
                if ( temp2 == Double.NEGATIVE_INFINITY){
                    num_same++;
                    if ( num_same ==1)
                        throw new IllegalArgumentException();
                }
                else if ( temp2 == temp1){
                    count_c++;                    
                    if ( Points[max].compareTo(Points[j]) < 0 )
                        max = j;
                    if ( Points[min].compareTo(Points[j]) > 0 )
                        min = j;                    
                }
                else{
                         if ( count_c > 1 && original[i].compareTo(Points[max]) > 0){

                            start.add(new LineSegment(Points[min],original[i]));
                            num++;
                            count_c = 0;
                            if ( original[i].compareTo(Points[j]) < 0 ){
                                max = j;
                                min = i;
                            }

                         }
                         count_c = 0;
                         min = j;
                         max = j;
                        temp1 = temp2;                    
                }

            
            
        }
            if ( count_c > 1 && original[i].compareTo(Points[max]) > 0){
                start.add(new LineSegment(Points[min],original[i]));
                num++;
                count_c = 0;
                min = 0;
                max = 0;
            }

        }}// finds all line segments containing 4 or more Points
    public           int numberOfSegments() {
        return num;
    }// the number of line segments
    public LineSegment[] segments() {
        LineSegment[] res = start.toArray(new LineSegment[start.size()]);
        return res;
    }// the line segments

}