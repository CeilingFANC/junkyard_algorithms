import java.util.*;
public class KD_Tree {
	public int num_dim=3;
	public int use_dim=3;
	public  double[] total_min={0.0,0.0,0.0,0.0};
	public  double[] total_max={1.0,1.0,1.0,1.0};
	private node root;
    private static double distance= Double.POSITIVE_INFINITY;
    private static node beast;
    private static double[] bat;
	private static boolean stop_sign=true;
    
	
	private static boolean state=true;
	
    private int siz=0;
    
    private class resu{
		node pi=null;
		double dist=Double.POSITIVE_INFINITY;
		
	}
	private class que implements Comparable<que>{
		node refrenece;
		double[] q_min;
		double[] q_max;
		double dista;
		private que(node a, double b){
			this.refrenece=a;
			this.dista=b;
		}
		public int compareTo(que other) {
		    return Double.compare(this.dista, other.dista);
		}
		
	}
	private class que_2 implements Comparable<que_2>{
		node refrenece;
		double[] poi;
		double[] q_min;
		double[] q_max;
		double dista;
		private que_2(node a, double b){
			this.refrenece=a;
			this.dista=b;
		}
		public int compareTo(que_2 other) {
		    return Double.compare(this.dista, other.dista);
		}
		
	}	
	private class node{
		
		double[] point;
		private int dim;
		private node left;
		private node right;
		
		private node(double[] point, int split_dim){
			this.point=new double[point.length];
			for (int i =0; i<point.length;i++){
				this.point[i]=point[i];
				
			}
			
			dim=split_dim;
		}		
	}

	public KD_Tree(int n_d,int u_d){
		num_dim=n_d;
		use_dim=u_d;		
		
	}
	public KD_Tree(int n_d,int u_d,double[] point){
		num_dim=n_d;
		use_dim=u_d;
		root=new node(point,0);
		siz++;
		
	}
	public int size(){
		return siz;
	}
	public void insert(double[] p ){
		insert(root,p);
		siz++;
	}
	
	private void insert(node current,double[] p){
		if (p==null){
			return;
		}
		if (p[current.dim]<current.point[current.dim]){
			if ( current.left==null){
				current.left=new node(p,(current.dim+1)%use_dim);
			}else{
				insert(current.left,p);
			}
		}else{
			if ( current.right==null){
				current.right=new node(p,(current.dim+1)%use_dim);
			}else{
				insert(current.right,p);
			}				
		}	
	}
	private double betweenpoint_2(double[] a, double[] b){
		double re=0;
		for (int i=0;i<a.length;i++){
			re+=Math.pow(a[i]-b[i], 2);
		}
		return re;
	}
	private double pointtoplanet_2(double[] p, double[] min,double[] max){
		double re=0;
		for (int i=0;i<p.length;i++){
			if (p[i]<min[i]){
				re+=Math.pow(min[i]-p[i], 2);
				
			}else if(p[i]>max[i]){
				re+=Math.pow(max[i]-p[i], 2);
			}
		}			
		return re;
	}
	private double pointtoplanet_MINUS(double[] p, double[] min,double[] max,int dim){
		double re=0;
		for (int i=0;i<p.length;i++){
			if (i==dim){
				continue;
			}
			if (p[i]<min[i]){
				re+=Math.pow(min[i]-p[i], 2);
				
			}else if(p[i]>max[i]){
				re+=Math.pow(max[i]-p[i], 2);
			}
		}			
		return re;
	}	
	
	
	
	
	public double[] nearest(double[] target){
		distance=Double.POSITIVE_INFINITY;
		nearest(root,target,total_min,total_max,distance);
		System.out.print(distance);
		return beast.point;
		
		
	}
	private resu nearest(node current,double[] target, double[] min,double[] max,double dist){
		if (current==null){
			return new resu();
		}
		int dims=current.dim;
		node close,far;
		double[] c_min;
		double[] c_max;
		double[] f_min;
		double[] f_max;	
		if (target[dims]<current.point[dims]){
			close = current.left;
			far = current.right;
			c_min=min.clone();
			c_max=max.clone();
			f_min=min.clone();
			f_max=max.clone();
			c_max[current.dim]=current.point[current.dim];
			f_min[current.dim]=current.point[current.dim];		
		}else{
			close = current.right;
			far = current.left;
			c_min=min.clone();
			c_max=max.clone();
			f_min=min.clone();
			f_max=max.clone();
			c_min[current.dim]=current.point[current.dim];
			f_max[current.dim]=current.point[current.dim];				
		}
		nearest(close,target,c_min,c_max,dist);
		if (pointtoplanet_2(target,f_min,f_max)<distance){
			double tempemt=betweenpoint_2(target,current.point);
			if (tempemt<distance){

				distance=tempemt;
				beast=current;
				if (state){
					System.out.println(Arrays.toString(beast.point)+distance);
					
				}
			}
			nearest(far,target,f_min,f_max,dist);
			//System.out.println(distance+Arrays.toString(beast.point));
		}
		return new resu();
		
		
		
	}
	/*
	 * ?????
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public double[] nns_3(double[] target){
		PriorityQueue<que_2> lisa=new PriorityQueue<que_2>();
		que_2 template =new que_2(root, pointtoplanet_2(target,total_min,total_max));
		template.q_min=total_min.clone();
		template.q_max=total_max.clone();	
		template.poi=root.point.clone();
		lisa.add(template);
		distance=Double.POSITIVE_INFINITY;
		bat=null;	
		double wwp;
		int count=1000000000;
		while (lisa.size()>0 && count>0){
			//System.out.print("how come"+lisa.size());
			que_2 tt=lisa.poll();

			wwp=betweenpoint_2(target,tt.poi);
			if(wwp<distance){
				distance=wwp;
				//bat=template.poi.clone();
				beast=tt.refrenece;
			}
			if(tt.dista>distance){
				break;
			}
			nearest_attempt(tt.refrenece,target,tt.q_min,tt.q_max,lisa);
			//System.out.println(Arrays.toString(bat)+"  "+distance);
			
			count--;
			//System.out.println("how come"+count);
		}	
		System.out.println(count+Arrays.toString(beast.point)+"  "+distance);
		return beast.point;		
		
	}
	private resu nearest_attempt(node current,double[] target, double[] min,double[] max,PriorityQueue<que_2> lisa){
		if (current==null){
			return null;
		}
		int dims=current.dim;
		node close,far;
		double[] c_min;
		double[] c_max;
		double[] f_min;
		double[] f_max;	
		if (target[dims]<current.point[dims]){
			close = current.left;
			far = current.right;
			c_min=min.clone();
			c_max=max.clone();
			f_min=min.clone();
			f_max=max.clone();
			c_max[current.dim]=current.point[current.dim];
			f_min[current.dim]=current.point[current.dim];		
		}else{
			close = current.right;
			far = current.left;
			c_min=min.clone();
			c_max=max.clone();
			f_min=min.clone();
			f_max=max.clone();
			c_min[current.dim]=current.point[current.dim];
			f_max[current.dim]=current.point[current.dim];				
		}
		nearest_attempt(close,target,c_min,c_max,lisa);
		double tra=pointtoplanet_2(target,f_min,f_max);
		if (tra<distance){
			double tempemt=betweenpoint_2(target,current.point);
			if (tempemt<distance){
				distance=tempemt;
				beast=current;
			}
			que_2 ins = new que_2(far,tra);
			ins.q_min=f_min;
			ins.q_max=f_max;
			ins.poi=current.point.clone();
			lisa.add(ins);	
			
			//System.out.println(distance+Arrays.toString(beast.point));
		}
		return null;	
	}
	private resu nearest_p_1(double[] target,PriorityQueue<que_2> lisa){


		que_2 template=lisa.poll();
		//System.out.println(template.dista);
		if (template.dista>=distance){
			lisa.clear();
			return null;
		}			
		double[] c_min=template.q_min.clone();
		double[] c_max=template.q_max.clone();
		double[] f_min=template.q_min.clone();
		double[] f_max=template.q_max.clone();
		node close,far,current;
		current=template.refrenece;
		int dims;
		//double tra;
		double wwp;
		wwp=betweenpoint_2(target,template.poi);
		if(wwp<distance){
			distance=wwp;
			bat=template.poi.clone();
		}
			
		while(true){

			f_min=c_min.clone();
			f_max=c_max.clone();		
			dims=current.dim;

			if (target[dims]<current.point[dims]){
				close = current.left;
				far = current.right;
				c_max[current.dim]=current.point[current.dim];
				f_min[current.dim]=current.point[current.dim];		
			}else{
				close = current.right;
				far = current.left;
				c_min[current.dim]=current.point[current.dim];
				f_max[current.dim]=current.point[current.dim];	
			}
			if(far!=null){
				//que_2 passedby = new que_2(far,howfar(target[dims],f_min[dims],f_max[dims])+Math.pow(target[dims]-current.point[dims], 2));
				//possibly wrong, it will not working on some dimension
				double tra=pointtoplanet_2(target,f_min,f_max);
				if (tra<distance){
					que_2 passedby = new que_2(far,pointtoplanet_2(target,f_min,f_max));
					passedby.q_min=f_min.clone();
					passedby.q_max=f_max.clone();
					passedby.poi=current.point.clone();
					lisa.add(passedby);	
				}
			}
			if (close==null){
				wwp=betweenpoint_2(target,current.point);
				if(betweenpoint_2(target,current.point)<distance){
					distance=betweenpoint_2(target,current.point);
					bat=current.point.clone();
					beast=template.refrenece;
					
					//System.out.println(Arrays.toString(bat)+"in the river"+distance);

				}
				break;
			}
			current=close;

		}
		return null;
		
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
	/*
	 * build up the priority queue and keep deal with the highest priority one
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public double[] nns_2(double[] target){
		PriorityQueue<que_2> lisa=new PriorityQueue<que_2>();
		que_2 template =new que_2(root, pointtoplanet_2(target,total_min,total_max));
		template.q_min=total_min.clone();
		template.q_max=total_max.clone();	
		template.poi=root.point.clone();
		lisa.add(template);
		distance=Double.POSITIVE_INFINITY;
		bat=null;		
		int count=1000000000;
		while (lisa.size()>0 && count>0){
			//System.out.print("how come"+lisa.size());
			
			nearest_p_1(target,lisa);
			//System.out.println(Arrays.toString(bat)+"  "+distance);
			
			count--;
			//System.out.println("how come"+count);
		}	
		System.out.println(count+Arrays.toString(bat)+"  "+distance);
		return bat;
		
	}
	public double[] nns(double[] target){
		PriorityQueue<que> lisa=new PriorityQueue<que>();
		que template =new que(root, pointtoplanet_2(target,total_min,total_max));
		template.q_min=total_min.clone();
		template.q_max=total_max.clone();	
		lisa.add(template);
		distance=Double.POSITIVE_INFINITY;
		bat=null;
		int count=1000000000;
		System.out.println(count);
		stop_sign=true;
		while (lisa.size()>0 && count>0 && stop_sign){
			//System.out.print("how come"+lisa.size());
			
			nearest_p(target,lisa);
			count--;
			//System.out.println("how come"+count);
		}
		System.out.println(count+"fuc"+distance);
		resu resss= new resu();
		resss.dist=distance;
		resss.pi=beast;
		return bat;
	}
	private void nearest_p(double[] target,PriorityQueue<que> lisa){
		//PriorityQueue<que> lisa=new PriorityQueue<que>();
		//que template =new que(root, 0);
		//lisa.add(root);
		
		que template=lisa.poll();


		//System.out.print(template==null);
		//System.out.print(Arrays.toString(target)+Arrays.toString(bat));
		//System.out.print(lisa.size());
		if (template.dista>=distance){
			stop_sign=false;
			return;
		}

		//System.out.println(Arrays.toString(template.refrenece.point)+template.dista);
		double wwp=betweenpoint_2(target,template.refrenece.point);
		if(wwp<distance){
			distance=wwp;
			bat=template.refrenece.point.clone();
			beast=template.refrenece;
		}
		node current = template.refrenece;
		double temp_b,temp,temp_0,temp_1=Double.POSITIVE_INFINITY;
		temp=pointtoplanet_MINUS(target,template.q_min,template.q_max,current.dim);
		que inser;
		temp_b=pointtoplanet_2(target,template.q_min,template.q_max)-howfar(target[current.dim],template.q_min[current.dim],template.q_max[current.dim]);
		temp_b=template.dista-howfar(target[current.dim],template.q_min[current.dim],template.q_max[current.dim]);
		temp_0=temp_b+howfar(target[current.dim],template.q_min[current.dim],current.point[current.dim]);
		temp_1=temp_b+howfar(target[current.dim],current.point[current.dim],template.q_max[current.dim]);
		double[] c=template.q_min.clone();
		double[] d=template.q_max.clone();
		d[current.dim]=current.point[current.dim];
		double[] c_2=template.q_min.clone();
		c_2[current.dim]=current.point[current.dim];
		double[] d_2=template.q_max.clone();		
		//temp_0=pointtoplanet_2(target,c,d);
		//temp_1=pointtoplanet_2(target,c_2,d_2);
		
		
		
		if (temp_0<distance&&current.left!=null){
			inser=new que(current.left,temp_0);
			inser.q_min=template.q_min.clone();
			inser.q_max=template.q_max.clone();	
			inser.q_max[current.dim]=current.point[current.dim];
			lisa.add(inser);
		}
		if (temp_1<distance&&current.right!=null){
			inser=new que(current.right,temp_1);
			inser.q_min=template.q_min.clone();
			inser.q_max=template.q_max.clone();	
			inser.q_min[current.dim]=current.point[current.dim];
			lisa.add(inser);
		}			
	}
	
	public static void main(String[] args){
		
	}
	
	
	
	
	
	
	
	
}
