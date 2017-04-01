import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.*;


public class KdTree{
    private static double distance= Double.POSITIVE_INFINITY;
    private Node root;
    private int size=0;
    private KdTree(Point2D point){
        root = new Node(point);
        root.odd_even=true;
        size+=1;
        
    }
    public KdTree(){
        root = null;
        
    }    
    private class Node{
        private Node left,right;
        private boolean odd_even;
        private Point2D point;
        private RectHV rect;
        private Node(Point2D point){
            this.point=point;     
        }   
        private Node(RectHV rect){
            this.rect=rect;
        }

    }

    public           boolean isEmpty() {
      return size==0;   
    }// is the set empty? 
    public               int size() {
        return size;
    }// number of points in the set 
    public              void insert(Point2D p) {
        if (root==null){
            root=new Node(p);
            root.odd_even=true;
            root.left=new Node(new RectHV(0.0,0.0,root.point.x(),1.0));
            root.right=new Node(new RectHV(root.point.x(),0.0,1.0,1.0));
            root.left.odd_even=false;
            root.right.odd_even=false;            
            size=1;
            return;
        }
        Node current = root;
        boolean one=true;
        double[] boundary=new double[4];

        boundary[0]=0.0;
        boundary[1]=0.0;
        boundary[2]=1.0; 
        boundary[3]=1.0;        
        while(one){

            if ( current.odd_even){
                if ( current.point==null){
                    current.point=p;
                    current.left=new Node(new RectHV(boundary[0],boundary[1],boundary[2],current.point.y()));
                    current.right=new Node(new RectHV(boundary[0],current.point.y(),boundary[2],boundary[3]));
                    current.left.odd_even=current.odd_even;
                    current.right.odd_even=current.odd_even;                        
                    one=false;                    
                }else{
                    if ( p.distanceSquaredTo(current.point)==0){
                        return;
                    }                    
                    if (p.x()<current.point.x()){

                        boundary[2]=current.point.x();
                        current=current.left;                        
                    }else{
                        boundary[0]=current.point.x();                          
                        current=current.right;
                      
                    }                    
                }
                
                
            }else{
                if ( current.point==null){
                    current.point=p;
                    current.left=new Node(new RectHV(boundary[0],boundary[1],boundary[2],current.point.y()));
                    current.right=new Node(new RectHV(boundary[0],current.point.y(),boundary[2],boundary[3]));
                    current.left.odd_even=current.odd_even;
                    current.right.odd_even=current.odd_even;                        
                    one=false;                    
                }else{
                    if ( p.distanceSquaredTo(current.point)==0){
                        return;
                    }                        
                    if (p.y()<current.point.y()){
                        boundary[3]=current.point.y();
                        current=current.left;                          

                    }else{
                        boundary[1]=current.point.y();
                        current=current.right;                          
                     
                    }                    
                }                
                
            }
            
            
            


        }
        
        size=size+1;       
    }// add the point to the set (if it is not already in the set)
    public           boolean contains(Point2D p) {

        if(root==null){
            return false;
        }
        Node current=root;        
        boolean one=true;
        boolean result=false;
        while(one){
            if (p.distanceSquaredTo(current.point)==0){
             return true;   
            }

            
            if (current.odd_even){
                if (p.x()<current.point.x()){
                    if (current.left.point == null){
                        one=false;
                        result=false;
                        return false;
                    }else{
                        current=current.left;
                    }
                }else{
                    if (current.right.point == null){
                        one=false;
                        result=false;
                        return false;                        
                    }else{
                        current=current.right;
                    }
                }
            }else{
                if (p.y()<current.point.y()){
                    if (current.left.point == null){
                        one=false;
                        result=false;
                    }else{
                        current=current.left;
                    }
                }else{
                    if (current.right.point == null){
                        one=false;
                        result=false;
                    }else{
                        current=current.right;
                    }
                }
            }
        }
        
        return false;

    }// does the set contain point p? 
    public              void draw(){
        
    }// draw all points to standard draw 
    private LinkedList<Point2D> range(LinkedList<Point2D> result,Node current,RectHV rect){

        Iterable<Point2D> temp=new LinkedList<Point2D>();        
        if (rect.contains(current.point)==true){
            result.add(current.point);   
            if(current.left.point!=null){
                result=range(result,current.left,rect);          
            }            
            if(current.right.point!=null){
                result=range(result,current.right,rect);

            }             
        }else{
            if(current.left.point!=null){
                if(current.left.rect.intersects(rect)){
                    result=range(result,current.left,rect);
                }      
            }
            if(current.right.point!=null){
                if(current.right.rect.intersects(rect)){
                    result=range(result,current.right,rect);

                }      
            }            
        }
        

        return result;
        
    }
    public Iterable<Point2D> range(RectHV rect) {
        LinkedList<Point2D> result=new LinkedList<Point2D>();
        Node current=root;
        return range(result,current,rect);
    }// all points that are inside the rectangle 
    public           Point2D nearest(Point2D p) {
        if( root==null){
            return null;
        }
        distance=Double.POSITIVE_INFINITY;
        return nearest(root,p,root.point);



    }// a nearest neighbor in the set to point p; null if the set is empty 
    private Point2D nearest(Node current,Point2D p,Point2D best){
        if(current.point==null){
            return best;
        }
        distance=p.distanceSquaredTo(best);
        double temp=p.distanceSquaredTo(current.point);
        if(distance>temp){
            best=current.point;
            distance=temp;
        }
        if (current.odd_even){
            if(p.x()<current.point.x()){
                if(current.left.point!=null){
                    if(current.left.rect.distanceSquaredTo(p)<distance){
                        best=nearest(current.left,p,best);
                    }
                }
                if(current.right.point!=null){                
                    if(current.right.rect.distanceSquaredTo(p)<distance){
                        best=nearest(current.right,p,best);
                    }  
                }
                
            }else{
                if(current.right.point!=null){
                    if(current.right.rect.distanceSquaredTo(p)<distance){
                        best=nearest(current.right,p,best);
                    }
                }
                if(current.left.point!=null){
                    if(current.left.rect.distanceSquaredTo(p)<distance){
                        best=nearest(current.left,p,best);
                    }
                }                   
            }
        }else{
            if(p.y()<current.point.y()){  
                if(current.left.point!=null){
                    if(current.left.rect.distanceSquaredTo(p)<distance){
                        best=nearest(current.left,p,best);
                    }
                }
                if(current.right.point!=null){                
                    if(current.right.rect.distanceSquaredTo(p)<distance){
                        best=nearest(current.right,p,best);
                    }                    
                }
            }else{
                if(current.right.point!=null){
                    if(current.right.rect.distanceSquaredTo(p)<distance){
                        best=nearest(current.right,p,best);
                    }
                }
                if(current.left.point!=null){
                    if(current.left.rect.distanceSquaredTo(p)<distance){
                        best=nearest(current.left,p,best);                    
                    }                    
                }
            
            }
        }        
        return best;
    }

    
    
}