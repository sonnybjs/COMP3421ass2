package ass2.spec;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

/**
 * COMMENT: Comment Road 
 *
 * @author malcolmr
 */
public class Road {

    private List<Double> myPoints;
    private double myWidth;
    public final boolean debug = true;
    private Terrain myTerrain;
    
    public void draw(GL2 gl){
    	double[] pPre =  new double[2];
    	pPre[0] = myPoints.get(0);
    	pPre[1] = myPoints.get(1);
    	double pPreAl = myTerrain.altitude(pPre[0],pPre[1]);    	
    	if(debug){
    		System.out.println("pPre :"+pPre[0]+" "+pPre[1]+" "+pPreAl);
    	}
    	gl.glColor3d(1.0, 1.0, 1.0);
    	for(double i = 0.0; i<=1.0; i += 0.1){
    		double[] p = point(i);
    		
    		//此时满足条件:point在的点是flat,即xu,xd,zu,zd都相等,则point的y是该平面的altitude
    		//或者,直接计算road所在的每个点的位置,用插值算法
    		//由于路是有宽度的,所以用一堆quad表示??如何表示??
    		double pAl = myTerrain.altitude(p[0], p[1]);
    		if(debug){
        		System.out.println("point is "+ point(i)[0]+ " "+ point(i)[1]+" "+pAl);
        	}
    		gl.glBegin(gl.GL_LINE_LOOP);
    		/**
    		gl.glVertex3d(pPre[0]-myWidth/2, pPreAl, pPre[1]);
    		gl.glVertex3d(pPre[0]+myWidth/2, pPreAl, pPre[1]);
    		gl.glVertex3d(p[0]+myWidth/2, pAl, p[1]);
    		gl.glVertex3d(p[0]-myWidth/2, pAl, p[1]);
    		*/
    		gl.glVertex3d(pPre[0], pPreAl, pPre[1]);
    		gl.glVertex3d(pPre[0], pPreAl, pPre[1]);
    		gl.glVertex3d(p[0], pAl, p[1]);
    		gl.glVertex3d(p[0], pAl, p[1]);
    		
    		pPre[0] = p[0];
    		pPre[1] = p[1];
    		pPreAl = pAl;
    		gl.glEnd();
    		gl.glFlush();
    		if(debug){
        		System.out.println("now pPre is "+ pPre[0]+ " "+ pPre[1]+" "+pPreAl);
        	}
    	}
    	
    }
    
    /** 
     * Create a new road starting at the specified point
     */
    public Road(double width, double x0, double y0) {
        myWidth = width;
        myPoints = new ArrayList<Double>();
        myPoints.add(x0);
        myPoints.add(y0);
    }

    /**
     * Create a new road with the specified spine 
     *
     * @param width
     * @param spine
     */
    public Road(double width, double[] spine, Terrain terrain) {
    	myTerrain = terrain;
        myWidth = width;
        myPoints = new ArrayList<Double>();
        for (int i = 0; i < spine.length; i++) {
            myPoints.add(spine[i]);
        }
    }

    /**
     * The width of the road.
     * 
     * @return
     */
    public double width() {
        return myWidth;
    }

    /**
     * Add a new segment of road, beginning at the last point added and ending at (x3, y3).
     * (x1, y1) and (x2, y2) are interpolated as bezier control points.
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param x3
     * @param y3
     */
    public void addSegment(double x1, double y1, double x2, double y2, double x3, double y3) {
        myPoints.add(x1);
        myPoints.add(y1);
        myPoints.add(x2);
        myPoints.add(y2);
        myPoints.add(x3);
        myPoints.add(y3);        
    }
    
    /**
     * Get the number of segments in the curve
     * 
     * @return
     */
    public int size() {
        return myPoints.size() / 6;
    }

    /**
     * Get the specified control point.
     * 
     * @param i
     * @return
     */
    public double[] controlPoint(int i) {
        double[] p = new double[2];
        p[0] = myPoints.get(i*2);
        p[1] = myPoints.get(i*2+1);
        return p;
    }
    
    
    /**
     * Get a point on the spine. The parameter t may vary from 0 to size().
     * Points on the kth segment take have parameters in the range (k, k+1).
     * 
     * @param t
     * @return
     */
    public double[] point(double t) {
        int i = (int)Math.floor(t);
        t = t - i;
        
        i *= 6;
        
        double x0 = myPoints.get(i++);
        double y0 = myPoints.get(i++);
        double x1 = myPoints.get(i++);
        double y1 = myPoints.get(i++);
        double x2 = myPoints.get(i++);
        double y2 = myPoints.get(i++);
        double x3 = myPoints.get(i++);
        double y3 = myPoints.get(i++);
        
        double[] p = new double[2];

        p[0] = b(0, t) * x0 + b(1, t) * x1 + b(2, t) * x2 + b(3, t) * x3;
        p[1] = b(0, t) * y0 + b(1, t) * y1 + b(2, t) * y2 + b(3, t) * y3;        
        
        return p;
    }
    
    
    /**
     * Calculate the Bezier coefficients
     * 
     * @param i
     * @param t
     * @return
     */
    private double b(int i, double t) {
        
        switch(i) {
        
        case 0:
            return (1-t) * (1-t) * (1-t);

        case 1:
            return 3 * (1-t) * (1-t) * t;
            
        case 2:
            return 3 * (1-t) * t * t;

        case 3:
            return t * t * t;
        }
        
        // this should never happen
        throw new IllegalArgumentException("" + i);
    }


}
