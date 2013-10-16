package ass2.spec;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
//import javax.vecmath.Matrix3d;
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

	public double altitude(double x, double z){
		return myTerrain.altitude(x, z);
	}

	public void draw(GL2 gl, Texture texture){

		// 1 重复求t=0~1的每个点的x , z -> points[2]


		// 2 通过 x, z 求得altitude -> y
		// 3 算出t=0~0.1之间的切线向量 表示为tangent[3],这个是t=0的切线,与t=0.1的无关
		// 4 将该tangent旋转90度(乘rotate Matrix)
		// 5 将该tangent scale 长度的1/2 (乘scale matrix)
		// 6 该vector+ (x,y,z)既得路沿的坐标

		// 7 重复计算直到算出t=0.9的切线向量,取t=0.9~1的切线,最后t=1.0的切线等于t=0.9的切线
		double[] scaleRotateTangent = new double[4];
		gl.glColor3d(110/255d, 110/255d, 110/255d);

		float textureTop, textureBottom, textureLeft, textureRight;
		TextureCoords textureCoords = texture.getImageTexCoords();
		textureTop = textureCoords.top();
		textureBottom = textureCoords.bottom();
		textureLeft = textureCoords.left();
		textureRight = textureCoords.right();

		// Enables this texture's target in the current GL context's state.
		texture.enable(gl);  // same as gl.glEnable(texture.getTarget());
		// gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
		// Binds this texture to the current GL context.
		texture.bind(gl);  // same as gl.glBindTexture(texture.getTarget(), texture.getTextureObject());
		gl.glBegin(gl.GL_QUAD_STRIP);
		double offset = 0.001;
		for(double t = 0.0 ; t <= 0.9; t += 0.1){ //t=0.0和t=1.0的点很奇怪
			double[] point0 = point(t);
			double x0 = point0[0];
			double z0 = point0[1];
			double y0 = altitude(x0, z0) + offset;
			double[] midpoint = {x0,y0,z0};

			double t1 = t+0.1;
			double[] point1 = point(t1);
			double x1 = point1[0];
			double z1 = point1[1];
			double y1 = altitude(x1,z1) + offset;

			double[] tangent = {x1-x0, y1-y0, z1-z0,1}; //for matrix calculation
			if(debug){
				System.out.println("In Road: t is "+t+" point is "+ x0+" "+ y0+" "+ z0+" ");
				System.out.println(" Tangent is "+ tangent[0]+" "+ tangent[1]+" "+ tangent[2]+" ");
			}

			double[] rotateTangent = MathUtil.multiply(MathUtil.rotationMatrix(90,false,true,false), tangent); 

			scaleRotateTangent = MathUtil.multiply(MathUtil.scaleMatrix(myWidth/2), rotateTangent);
			double[] result0 = {scaleRotateTangent[0]+midpoint[0],scaleRotateTangent[1]+midpoint[1],scaleRotateTangent[2]+midpoint[2]};
			double[] result1 = {-scaleRotateTangent[0]+midpoint[0],-scaleRotateTangent[1]+midpoint[1],-scaleRotateTangent[2]+midpoint[2]};
			gl.glTexCoord2f(textureLeft, textureBottom);
			gl.glVertex3d(result0[0], result0[1], result0[2]);
			gl.glTexCoord2f(textureRight, textureBottom);
			gl.glVertex3d(result1[0], result1[1], result1[2]); 	    		
			gl.glTexCoord2f(textureRight, textureTop);
			gl.glVertex3d(scaleRotateTangent[0]+x1, scaleRotateTangent[1]+y1, scaleRotateTangent[2]+z1);
			gl.glTexCoord2f(textureLeft, textureTop);
			gl.glVertex3d(-scaleRotateTangent[0]+x1, -scaleRotateTangent[1]+y1, -scaleRotateTangent[2]+z1);
			if(debug){
				System.out.println("In Road: +side "+ result0[0]+" "+ result0[1]+" "+ result0[2]+" ");

				System.out.println("In Road: -side "+ result1[0]+" "+ result1[1]+" "+ result1[2]+" ");
			}
		}
		gl.glEnd();
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
		/*
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
		if(debug){
			System.out.println("--------------------------p is "+ p[0]+" "+p[1]);
			
		}
		return p;
		*/
		return perfectBezier(t);
	}

	public double[] perfectBezier(double t){
		double[] p = new double[2];
		
		int l = myPoints.size()/2-1;
		for(int k = 0; k < myPoints.size()/2; k++){ //注意mypoint是两个一组,k是指当前项的序数
			//k=0,1,2,3时. point有8个0~7
			p[0] += bernstein(l,k,t)* myPoints.get(2*k); //x=0,2,4,6
			
			p[1] += bernstein(l,k,t)* myPoints.get(2*k+1); //y=1,3,5,7
			if(debug){
				
				System.out.println("bernstein: "+bernstein(l,k,t)+" point-x: "+myPoints.get( 2*k)+" point-y: "+myPoints.get(2*k+1));
				System.out.println("result: x "+p[0]+" y "+p[1]);
				
			}
			
		}
		if(debug){
			System.out.println("--------------------------p is "+ p[0]+" "+p[1]);
			
		}
		
		return p;
	}

	/**
	 * Calculate the Bezier coefficients
	 * 
	 * @param i
	 * @param t
	 * @return
	 */
	public double b(int i, double t) {

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



	/**
	 * coeffienct for Bezier with unlimited L and K
	 * @param i
	 * @param t
	 * @return
	 */
	private double coefficient(double l, double k){
		if(l==k || k ==0){
			return 1;
		}
		return MathUtil.factorial(l)/(MathUtil.factorial(k)*MathUtil.factorial(l-k));
	}

	public double bernstein(int l, int k, double t){
		
		return coefficient(l,k) * Math.pow(1-t, l-k) * Math.pow(t, k);
	}

}
