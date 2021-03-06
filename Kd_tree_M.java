
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;



public class Kd_tree_M {
	public int num_dim=3;
	public int use_dim=3;
	private int limit;
	public  double[] total_min;
	public  double[] total_max;
	private int root;
	
    private static double distance= Double.POSITIVE_INFINITY;
    private static int bat;
    
	
	ArrayList<double[]> points=new ArrayList<double[]>();
	private boolean ready=false;
	private double[][] pointlist;
	
	
	private int[] rotate;
	//array that saves the tree
	int[] link_point;
	int[] left,right;
	int[] split_dimen;
	double[] split_value;

	// for constructing tree
	private double[] means;
	private double[] variances;
	
	
	private static boolean state=true;
	private static boolean stop_sign=true;	
    private int siz=0;
    
	private int remember_last=1;
    
    
    private class tree{
    	
    	
    }
    

    private class que implements Comparable<que>{
    	int index_node;
    	double[] q_min;
    	double[] q_max;
    	double dista;
		private que(int a, double dis,double[] q_min,double[] q_max){
			this.index_node=a;
			this.dista=dis;
			this.q_min=q_min;
			this.q_max=q_max;
		}
		public int compareTo(que other) {
		    return Double.compare(this.dista, other.dista);
		}    	
    }
    
 
 	

	public Kd_tree_M(int n_d,int u_d,int limit){
		this.num_dim=n_d;
		this.use_dim=u_d;		
		this.limit=limit;
		means=new double[num_dim];
		variances=new double[use_dim];
		total_min=new double[use_dim];
		total_max=new double[use_dim];
		for (int i=0;i<use_dim;i++){
			total_min[i]=Double.NEGATIVE_INFINITY;
			total_max[i]=Double.POSITIVE_INFINITY;
		
		}
		
	}
/*
 * return size;
 */
	public int size(){
		return siz;
	}
	
	
	/*
	 * put in point first, being processed and construct real treewhen the input is over
	 */
	public void insert(double[] p ){
		points.add(p.clone());
		siz++;
	}
	public void build(){
		pointlist=new double[points.size()][num_dim];
		pointlist=points.toArray(pointlist);
		left=new int[pointlist.length];
		right=new int[pointlist.length];
		link_point=new int[pointlist.length];
		split_dimen=new int[pointlist.length];
	    split_value=new double[pointlist.length];
	    
	    rotate=new int[use_dim];
	    for (int i=0;i<pointlist.length;i++){
	    	left[i]=-1;
	    	right[i]=-1;
	    	link_point[i]=-1;
	    }
		
		mean();
		variance();
		ArrayList<Integer> sorted= new ArrayList<Integer>();
		int[] index_list=new int[num_dim];
		for (int i=0;i<num_dim;i++){
			sorted.add(i);
			index_list[i]=i;
		}
		Collections.sort(sorted,new Comparator<Integer>(){
            public int compare(Integer o1,Integer o2){
            	return Double.compare(variances[o1], variances[o1]);
                  // Write your logic here.
            }});
		for (int i=0;i<use_dim;i++){
			rotate[i]=sorted.get(i);
		}
		
		int[] inse=new int[pointlist.length];
		for (int i=0;i<pointlist.length;i++){
			inse[i]=i;
		}
		System.out.println(pointlist.length);

		shuffleArray(inse);

		System.out.println("finish the shuffle");
		System.out.println(Arrays.toString(rotate));
		//System.out.println(fuck(inse,inse.length/2));
		System.out.println("what?");
		

		System.out.println("mint chocolate");

		int split_p=selectKth(inse,0,0,inse.length-1);
		root=0;
		System.out.println("mit");
		link_point[0]=split_p;
		split_value[0]=pointlist[split_p][rotate[0]];
		split_dimen[0]=rotate[0];
		order(inse,1,0,split_p-1);
		order(inse,1,split_p+1,inse.length-1);

				
		
		
	}
	private static int cooo=0;
	private void order(int[] inse, int dim,int from, int to){
		//System.out.println(cooo);
		cooo++;
		int split_1=selectKth(inse,0,from,to);
		if(to!=-1){
		System.out.println(split_1+"which one!!!"+from+"--"+to);
		}
		insert(split_1,root,0);
		//System.out.println(split_1);
		if(split_1-1==from){
			insert(from,root,0);
			
		}else{
			order(inse,(dim+1)%use_dim,from,split_1-1);
		}
		if(split_1+1==to){
			insert(to,root,0);
		}else{
			order(inse,(dim+1)%use_dim,split_1+1,to);
		}		
	}
	

	private void insert(int index_point,int path_p,int dim){
		if(pointlist[index_point][split_dimen[path_p]]<split_value[path_p]){

			if (left[path_p]==-1){
				//System.out.print(index_point);

				//System.out.println("left "+remember_last);
				left[path_p]=remember_last;
				link_point[remember_last]=index_point;
				split_dimen[remember_last]=rotate[dim];
				split_value[remember_last]=pointlist[index_point][split_dimen[remember_last]];
				remember_last++;
			}else{
				insert(index_point,left[path_p],(dim+1)%use_dim);
			}
		}else{
			if (right[path_p]==-1){
				//System.out.print(index_point);

				//System.out.println("right "+remember_last);
				right[path_p]=remember_last;
				link_point[remember_last]=index_point;
				split_dimen[remember_last]=rotate[dim];
				split_value[remember_last]=pointlist[index_point][split_dimen[remember_last]];
				remember_last++;
			}else{
				insert(index_point,right[path_p],(dim+1)%use_dim);
			}			
		}
		
	}
	
	
	private double betweenpoint_2(int a, double[] b){
		double re=0;
		for (int i=0;i<num_dim;i++){
			re+=Math.pow(pointlist[a][i]-b[i], 2);
		}
		return re;
	}
	private double pointtoplanet_2(double[] p, double[] min,double[] max){
		double re=0;
		for (int i=0;i<use_dim;i++){
			if (p[rotate[i]]<min[i]){
				re+=Math.pow(min[i]-p[rotate[i]], 2);
				
			}else if(p[rotate[i]]>max[i]){
				re+=Math.pow(max[i]-p[rotate[i]], 2);
			}
		}			
		return re;
	}
	private double pointtoplanet_MINUS(double[] p, double[] min,double[] max,int dim){
		double re=0;
		for (int i=0;i<use_dim;i++){
			if (i==dim){
				continue;
			}
			if (p[rotate[i]]<min[i]){
				re+=Math.pow(min[i]-p[rotate[i]], 2);
				
			}else if(p[rotate[i]]>max[i]){
				re+=Math.pow(max[i]-p[rotate[i]], 2);
			}
		}			
		return re;
	}	//
	
	
	
	

	/*
	 * ?????
	 * the one succed 
	 * use requression and priority queue
	 * 
	 * 
	 * 
	 * 
	 */
	public double[] nns(double[] target){
		PriorityQueue<que> lisa=new PriorityQueue<que>();
		que template =new que(root,0,total_min,total_max);
		lisa.add(template);
		distance=Double.POSITIVE_INFINITY;
		bat=-1;	
		double wwp;
		int count=limit;
		while (lisa.size()>0 && count>0){
			//System.out.print("how come"+lisa.size());
			que tt=lisa.poll();
			if(tt.dista>distance){
				break;
			}
			nearest_attempt(root,target,tt.q_min,tt.q_max,lisa);
			//System.out.println(Arrays.toString(bat)+"  "+distance);
			
			count--;
			//System.out.println("how come"+count);
		}	
		//System.out.println(count+Arrays.toString(beast.point)+"  "+distance);
		return pointlist[bat];		
		
	}
	private void nearest_attempt(int current,double[] target, double[] min,double[] max,PriorityQueue<que> lisa){
		if (current==-1){
			return;
		}
		int dims=split_dimen[current];
		int close,far;
		double[] c_min;
		double[] c_max;
		double[] f_min;
		double[] f_max;	
		if (target[dims]<split_value[current]){
			close = left[current];
			far = right[current];
			c_min=min.clone();
			c_max=max.clone();
			f_min=min.clone();
			f_max=max.clone();
			c_max[dims]=split_value[current];	
			f_min[dims]=split_value[current];		
		}else{
			close = right[current];
			far = left[current];
			c_min=min.clone();
			c_max=max.clone();
			f_min=min.clone();
			f_max=max.clone();
			c_min[dims]=split_value[current];	
			f_max[dims]=split_value[current];					
		}
		nearest_attempt(close,target,c_min,c_max,lisa);
		double tra=pointtoplanet_2(target,f_min,f_max);
		if (tra<distance){
			double tempemt=betweenpoint_2(current,target);
			if (tempemt<distance){
				distance=tempemt;
				bat=current;
			}
			que ins = new que(far,distance,f_min,f_max);

			lisa.add(ins);	
			
			//System.out.println(distance+Arrays.toString(beast.point));
		}
		return;	
	}	
	private double howfar(double a, double l,double r){
		if (a<l){
			return Math.pow(a-l, 2);
		}else if(a<r){
			return 0;
		}else{
			return Math.pow(a-r, 2);
		}	
	}

	//fast calculate, not implement yet
	private void mean(){
		Double sum=0.0;
		for (int i=0;i<num_dim;i++){
			sum=0.0;
			for (int j=0;j<pointlist.length;j++){
				sum+=pointlist[j][i];
			}
			means[i]=sum/pointlist.length;
		}
	}
	private void variance(){
		double sum=0;
		for (int i=0;i<num_dim;i++){
			sum=0;
			double mea=means[i];
			for (int j=0;j<pointlist.length;j++){
				sum+=Math.pow(pointlist[j][i]-mea, 2);
			}
			variances[i]=sum;
		}		
	}
	public int selectKth(int[] arr,int dim,int from,int to) {
		if(from==to){
			 return from;
		}
		int tmp;
		//System.out.print(to);

		int k=(from+to)/2;
		 // if from == to we reached the kth element
		while (from < to) {
			int r = from;
			int w = to;
			double mid = pointlist[arr[(r + w) / 2]][dim];
			//System.out.print(mid);
	 
		  // stop if the reader and writer meets
			while (r < w) {
				//System.out.print(pointlist[arr[r]][dim]);

				if (pointlist[arr[r]][dim] >= mid) { // put the large values at the end
					tmp = arr[w];
					arr[w] = arr[r];
					arr[r] = tmp;
					w--;
					//System.out.println("no"+w);
				} else { // the value is smaller than the pivot, skip
					r++;
					//System.out.println("yes"+r);

				}
			}
			//System.out.println("why");
		  // if we stepped up (r++) we need to step one down
			if (pointlist[arr[r]][dim] > mid){
				r--;
			}
		  // the r pointer is on the end of the first k elements
			if (k <= r) {
				to = r;
	
			} else {
				from = r + 1;
			}
			//System.out.print(r);
			//System.out.print("   ");

			//System.out.print(k);
		  	//System.out.print("  ");

		  	//System.out.print(w);

		  
		 }
		 
		 return k;
	}

	
	public static int fuck(int[] arr, int k) {
		 if (arr == null || arr.length <= k)
		  throw new Error();
		 
		 int from = 0, to = arr.length - 1;
		 
		 // if from == to we reached the kth element
		 while (from < to) {
		  int r = from, w = to;
		  int mid = arr[(r + w) / 2];
		 
		  // stop if the reader and writer meets
		  while (r < w) {
		 
		   if (arr[r] >= mid) { // put the large values at the end
		    int tmp = arr[w];
		    arr[w] = arr[r];
		    arr[r] = tmp;
		    w--;
		   } else { // the value is smaller than the pivot, skip
		    r++;
		   }
		  }
		 
		  // if we stepped up (r++) we need to step one down
		  if (arr[r] > mid)
		   r--;
		 
		  // the r pointer is on the end of the first k elements
		  if (k <= r) {
		   to = r;
		  } else {
		   from = r + 1;
		  }
		 }
		 
		 return arr[k];
		}	
	
	
	
	private static void shuffleArray(int[] array)
	{
	    int index, temp;
	    Random random = new Random();
	    int count=array.length;
	    for (int i = array.length - 1; i > 0; i--)
	    {

	        index = random.nextInt(i + 1);
	        temp = array[index];
	        array[index] = array[i];
	        array[i] = temp;
	    }
	}
    private void selectionSort(int[] arr,int from,int to){  
        for (int i = from; i < to+1; i++)  
        {  
            int index = i;  
            for (int j = i + 1; j < to+1; j++){  
                if (arr[j] < arr[index]){  
                    index = j;//searching for lowest index  
                }  
            }  
            int smallerNumber = arr[index];   
            arr[index] = arr[i];  
            arr[i] = smallerNumber;  
        }  
    }  
	public static void main(String[] args){
    	Kd_tree_M million = new Kd_tree_M(3,3,2000);
    	double[] wait = new double[3];
    	int count=0;
    	for (double i=51; i<100;i++){
    		for (double j =51;j<100;j++){
        		for (double k =51;k<100;k++){    			
    			
    			
    			
    			
	    	    	wait[0]= i/100;
	    	    	wait[1]=j/100; 
	    	    	wait[2]=k/100;
	    			million.insert(wait);
	    			
	    	    	wait[0]= (100-i)/100;
	    	    	wait[1]=j/100; 
	    	    	wait[2]=k/100;
	    			million.insert(wait);
	    	    	wait[0]= i/100;
	    	    	wait[1]=(100-j)/100;
	    	    	wait[2]=k/100;
	    			million.insert(wait);
	    	    	wait[0]= i/100;
	    	    	wait[1]=j/100; 
	    	    	wait[2]=(100-k)/100;
	    			million.insert(wait);
	    			
	    			
	    			
	    	    	wait[0]= i/100;
	    	    	wait[1]=(100-j)/100; 
	    	    	wait[2]=(100-k)/100;
	    			million.insert(wait);
	    	    	wait[0]= (100-i)/100;
	    	    	wait[1]=j/100; 
	    	    	wait[2]=(100-k)/100;
	    			million.insert(wait);
	    	    	wait[0]= (100-i)/100;
	    	    	wait[1]=(100-j)/100; 
	    	    	wait[2]=k/100;
	    			million.insert(wait);
	    			
	    	    	wait[0]= (100-i)/100;
	    	    	wait[1]=(100-j)/100; 
	    	    	wait[2]=(100-k)/100;
	    			million.insert(wait);
	    			count+=8;
        		}
    		}
    	}
    	million.build();
    	
        System.out.println("The nearest neighbor for (0.111,0.111,0.111)"+Arrays.toString(million.nns(new double[] {0.111,0.111,0.111})));    	
        System.out.println("The nearest neighbor for (0.666,0.666,0.666)"+Arrays.toString(million.nns(new double[] {0.666,0.666,0.666})));    	
        System.out.println("The nearest neighbor for (0.111,0.666,0.111)"+Arrays.toString(million.nns(new double[] {0.111,0.666,0.111})));    	
        System.out.println("The nearest neighbor for (0.666,0.111,0.666)"+Arrays.toString(million.nns(new double[] {0.666,0.111,0.666})));    	

        
    	
    	
    	
	}	
}