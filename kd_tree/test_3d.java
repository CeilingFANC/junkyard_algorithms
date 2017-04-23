import java.util.Arrays;
import java.util.Scanner;

public class test_3d {

	private static double[] wait;

	public static void main(String[] args) {
    	KD_Tree million = new KD_Tree(3,3,new double[] {0.5,0.5,0.5});
    	wait = new double[3];
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
    	
       System.out.println("Hello World!"+count); // Display the string.
       System.out.println("Totally 999*999*999 points, that is"+million.size()); // Display the string.
       System.out.println("x,y is range from 0.000 to 0.999, every 0.001 there will be a point, a grid."); // Display the string.

       System.out.println("The nearest neighbor for (0.2011,0.6019)"+Arrays.toString(million.nearest(new double[] {0.2011,0.2019,0.1111}))); // Display the string.
       System.out.println("The nearest neighbor for (0.6011,0.2011)"+Arrays.toString(million.nearest(new double[] {0.6011,0.2011,0.1111}))); // Display the string.
       System.out.println("The nearest neighbor for (0.1118,0.6011)"+Arrays.toString(million.nearest(new double[] {0.1118,0.6011,0.1111}))); // Display the string.
       System.out.println("The nearest neighbor for (0.1118,0.6011)"+Arrays.toString(million.nearest(new double[] {0.1121,0.6011,0.1111}))); // Display the string.
      // create a scanner so we can read the command-line input
       System.out.println("try me out");

       System.out.println("The nearest neighbor for (0.2011,0.6019)"+Arrays.toString(million.nns_3(new double[] {0.2011,0.2019,0.1111}))); // Display the string.
       System.out.println("The nearest neighbor for (0.6011,0.2011)"+Arrays.toString(million.nns_3(new double[] {0.6011,0.2011,0.1111}))); // Display the string.
       System.out.println("The nearest neighbor for (0.1118,0.6011)"+Arrays.toString(million.nns_3(new double[] {0.1118,0.6011,0.1111}))); // Display the string.
       System.out.println("The nearest neighbor for (0.1118,0.6011)"+Arrays.toString(million.nns_3(new double[] {0.1121,0.6011,0.1111}))); // Display the string.
            
       System.out.println("California rasins");
    
       System.out.println("The nearest neighbor for (0.2011,0.6019)"+Arrays.toString(million.nns_2(new double[] {0.2011,0.2019,0.1111}))); // Display the string.
       System.out.println("The nearest neighbor for (0.6011,0.2011)"+Arrays.toString(million.nns_2(new double[] {0.6011,0.2011,0.1111}))); // Display the string.
       System.out.println("The nearest neighbor for (0.1118,0.6011)"+Arrays.toString(million.nns_2(new double[] {0.1118,0.6011,0.1111}))); // Display the string.
       System.out.println("The nearest neighbor for (0.1118,0.6011)"+Arrays.toString(million.nns_2(new double[] {0.1121,0.6011,0.1111}))); // Display the string.
            
              
       Scanner scanner = new Scanner(System.in);
       double x;
       double y;

       while (true) {
    	   
    	   System.out.println("Start input double for x and y");
    	   System.out.println("now for x:");
   	   
    	   
    	   // if the next is a double, print found and the double
    	   if (scanner.hasNextDouble()) {
    		   x=scanner.nextDouble();
        	   System.out.println("now for y:");
    		   
        	   if (scanner.hasNextDouble()) {
        		   
        		   y=scanner.nextDouble();
        		   System.out.print("The nearest neighbor:");
            	   System.out.println(Arrays.toString(million.nns(new double[] {x,y})));

        	   }    	  
    	   }
       }

       
       


    }
}