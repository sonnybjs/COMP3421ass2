package ass2.spec;

/**
 * A collection of useful math methods 
 *
 * TODO: The methods you need to complete are at the bottom of the class
 *
 * @author malcolmr
 */
public class MathUtil {

	final public boolean debug = true;
	
    /**
     * Normalise an angle to the range (-180, 180]
     * 
     * @param angle 
     * @return
     */
    static public double normaliseAngle(double angle) {
        return ((angle + 180.0) % 360.0 + 360.0) % 360.0 - 180.0;
    }

    /**
     * Clamp a value to the given range
     * 
     * @param value
     * @param min
     * @param max
     * @return
     */

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
    
    /**
     * Multiply two matrices
     * 
     * @param p A 3x3 matrix
     * @param q A 3x3 matrix
     * @return
     */
    public static double[][] multiply(double[][] p, double[][] q) {

        double[][] m = new double[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                m[i][j] = 0;
                for (int k = 0; k < 4; k++) {
                   m[i][j] += p[i][k] * q[k][j]; 
                }
            }
        }

        return m;
    }

    /**
     * Multiply a vector by a matrix
     * 
     * @param m A 3x3 matrix
     * @param v A 3x1 vector
     * @return
     */
    public static double[] multiply(double[][] m, double[] v) {

        double[] u = new double[4];

        for (int i = 0; i < 4; i++) {
            u[i] = 0;
            for (int j = 0; j < 4; j++) {
            	//System.out.println("FLAG:"+m[i][j]);
            	//System.out.println(" "+v[j]);
            	
                u[i] += m[i][j] * v[j];
                //System.out.println("FLAG!!:"+i+" "+j);
                //System.out.println("FLAG:"+m[i][j]+" "+v[j]);
            }
        }

        return u;
    }



    // ===========================================
    // COMPLETE THE METHODS BELOW
    // ===========================================
    

    /**
     * TODO: A 2D translation matrix for the given offset vector
     * 
     * @param pos
     * @return
     */
    public static double[][] translationMatrix(double[] v) {
    	
    	double[][] r = new double[4][4];
    	
    	for(int i = 0; i < 4; i++) {  		
    		for(int j = 0; j < 4; j++){
    			if(j == i){ 
    				r[i][j] = 1;
    			} else if( j == 3 ){
    				r[i][j] = v[i];
    			} else {
    				r[i][j] = 0;
    			}
    		}	    		
    	}
    	
        return r;
    }

    /**
     * TODO: A 2D rotation matrix for the given angle
     * 
     * @param angle
     * @return
     */
    public static double[][] rotationMatrix(double angle,boolean x, boolean y, boolean z) {
    	double radius = angle*Math.PI/180;
    	double[][] r = new double[4][4];
    	
    	if(x == true){
    		for(int i = 0; i < 4; i++) {   		
        		for(int j = 0; j < 4; j++){
        			if ((i == 0 && j == 0 )|| ( i==3 && j==3)){
        				r[i][j] = 1;
        			} else if (i == 1 && j == 1){
        				r[i][j] = Math.cos(radius);
        			} else if (i == 1 && j == 2){
        				r[i][j] = Math.sin(radius)*(-1);
        			} else if (i == 2 && j == 1){
        				r[i][j] = Math.sin(radius);
        			} else if (i == 2 && j == 2){
        				r[i][j] = Math.cos(radius);
        			} else {
        				r[i][j] = 0;
        			}
        		}	    		
        	}
    	} else if(y == true){
    		for(int i = 0; i < 4; i++) {   		
        		for(int j = 0; j < 4; j++){
        			if ((i == 1 && j == 1 )|| ( i==3 && j==3)){
        				r[i][j] = 1;
        			} else if (i == 0 && j == 0){
        				r[i][j] = Math.cos(radius);
        			} else if (i == 0 && j == 2){
        				r[i][j] = Math.sin(radius);
        			} else if (i == 2 && j == 0){
        				r[i][j] = Math.sin(radius)*(-1);
        			} else if (i == 2 && j == 2){
        				r[i][j] = Math.cos(radius);
        			} else {
        				r[i][j] = 0;
        			}
        		}	    		
        	} 		
    	} else if (z == true){
    		for(int i = 0; i < 4; i++) {   		
        		for(int j = 0; j < 4; j++){
        			if ((i == 2 && j == 2 )|| ( i==3 && j==3)){
        				r[i][j] = 1;
        			} else if (i == 0 && j == 0){
        				r[i][j] = Math.cos(radius);
        			} else if (i == 0 && j == 1){
        				r[i][j] = Math.sin(radius)*(-1);
        			} else if (i == 1 && j == 0){
        				r[i][j] = Math.sin(radius);
        			} else if (i == 1 && j == 1){
        				r[i][j] = Math.cos(radius);
        			} else {
        				r[i][j] = 0;
        			}
        		}	    		
        	} 
    	} else {
    		//this shouldn't happen
    	}
    	
    	
    	/*
    	System.out.print(Math.cos(90)+"|||");
    	for(int i = 0; i<3 ; i++){
    		for(int j = 0 ; j<3 ; j++){
    			System.out.print(r[i][j]+" ");
    		}
    		System.out.println();
    	}
    	System.out.println("---------------");
    	*/
        return r;
    }

    /**
     * TODO: A 2D scale matrix that scales both axes by the same factor
     * 
     * @param scale
     * @return
     */
    public static double[][] scaleMatrix(double scale) {
    	double[][] r = new double[4][4];
    	for(int i = 0; i < 4; i++) {    		
    		for(int j = 0; j < 4; j++){
    			if (i == j && i == 0){
    				r[i][j] = scale;
    			} else if (i == j && i == 1){
    				r[i][j] = scale;
    			} else if (i == j && i == 2){
    				r[i][j] = scale;
    			} else if (i == j && i == 3){
    				r[i][j] = 1;
    			} else {
    				r[i][j] = 0;
    			}
    		}	    		
    	}
        return r;
    }

    
}
