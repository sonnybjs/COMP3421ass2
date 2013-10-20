package ass2.spec;

import org.junit.Test;


public class MathTest {
	 //private static final double EPSILON = 0.001;
	 
	 @Test
	 public void testNormal(){
		 //0 0 0, 0 0 1, 1 0 0
		 double[] a = {0,0,0,1};
		 double[] c = {0,1,0,1};
		 double[] b = {0,1,1,1};
		 System.out.println("--------testNormal-----------");
		 double[] r = MathUtil.normal(a, b, c);
		 System.out.println("result: "+r[0]+" "+r[1]+" "+r[2]);
	 }
	 
	 
	@Test
	public void testRotation() {
		System.out.println("------------test rotation--------");
		double theta = 90/Math.PI;
        double[][] m = MathUtil.rotationMatrix(theta,false,true,false);
        for(int i = 0; i< 4; i++){
        	for(int j = 0; j<4; j++){
        		System.out.print(m[i][j]+" ");
        	}
        	System.out.println();
        }
        
	}
	
	@Test
	public void testRotationMultiply() {
		System.out.println("------------test rotation multiply------");
		double theta = 90/Math.PI;
        double[][] m = MathUtil.rotationMatrix(theta,false,true,false);
        double[] v = {1, 2, 3,1};
        double[] r = MathUtil.multiply(m, v);
        for(int i = 0; i< 4; i++){
        	//for(int j = 0; j<4; j++){
        		System.out.print(r[i]+" ");
        	//}
        	System.out.println();
        }
	}
	
	@Test
	public void normaTest(){
		//System.out.println("aaaaaaaaaaa"+MathUtil.normal(a, b, c))
	}

}
