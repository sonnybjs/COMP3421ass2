package ass2.spec;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;


//import sailing.objects.Island;

/**
 * COMMENT: Comment HeightMap 
 *
 * @author malcolmr
 */
public class Terrain {

    private Dimension mySize;
    private double[][] myAltitude;
    private List<Tree> myTrees;
    private List<Road> myRoads;
    private float[] mySunlight;
    final public boolean debug = true;
    boolean drawTriangle = false;
    
    /**
     * 从地面到树到路都要画出来
     * @param gl
     */
    public void draw(GL2 gl, Texture groundTexture, Texture treeTexture, Texture roadTexture){
    	//gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);  
        //重置模型观察矩阵  
    	
    	if(debug) {
			System.out.println("HEIGHT is "+mySize.getHeight() +" width is "+ mySize.getWidth());
		}
		
		
    	//画地面
    	float textureTop, textureBottom, textureLeft, textureRight;
    	TextureCoords textureCoords = groundTexture.getImageTexCoords();
        textureTop = textureCoords.top();
        textureBottom = textureCoords.bottom();
        textureLeft = textureCoords.left();
        textureRight = textureCoords.right();
        
        // Enables this texture's target in the current GL context's state.
        groundTexture.enable(gl);  // same as gl.glEnable(texture.getTarget());
        // gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
        // Binds this texture to the current GL context.
        groundTexture.bind(gl);  // same as gl.glBindTexture(texture.getTarget(), texture.getTextureObject());
    	
    	for(int i = 0; i< mySize.getHeight()-1 ; i++) { //loop from 1 to 9 
    		for(int y = 0; y< mySize.getWidth()-1; y++) {
    			if(debug) {
    				System.out.println("i:"+i+" y:"+y+" altitute:"+this.getGridAltitude(i, y));
    			}
    			//方向必须是逆时针
    			gl.glColor3d(102/255d, 102/255d, 0.0);
    			/** 
    	         * 如果您在您的场景中使用多个纹理，您应该使用来 
    	         *  glBindTexture(GL_TEXTURE_2D, texture[ 所使用纹理对应的数字 ]) 
    	         * 选择要绑定的纹理。当您想改变纹理时，应该绑定新的纹理。 
    	         * 有一点值得指出的是，您不能在 glBegin() 和 glEnd() 之间绑定纹理， 
    	         * 必须在 glBegin() 之前或 glEnd() 之后绑定。 
    	         */  
    	       // gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);  
	    	        if(!drawTriangle){
	    			gl.glBegin(gl.GL_QUADS);
	    			/*double[] a = {i,this.getGridAltitude(i, y),y};
	    			double[] b = {i,this.getGridAltitude(i, y+1),y+1};
	    			double[] c = {i+1,this.getGridAltitude(i+1, y+1),y+1};
	    			double[] n = MathUtil.normal(a, b, c);
	    			*/
	    			//gl.glNormal3d(0, 1, 0);
	    			gl.glTexCoord2f(textureLeft, textureBottom);//左下
	    			gl.glVertex3d(i,this.getGridAltitude(i, y),y);
	    			gl.glTexCoord2f(textureRight, textureBottom); //右下
	    			gl.glVertex3d(i,this.getGridAltitude(i, y+1),y+1);
	    			gl.glTexCoord2f(textureRight, textureTop);//右上
	    			gl.glVertex3d(i+1,this.getGridAltitude(i+1, y+1),y+1);
	    			gl.glTexCoord2f(textureLeft, textureTop);//左上
	    			gl.glVertex3d(i+1,this.getGridAltitude(i+1,y),y);
	    			gl.glEnd();
    	        } else {
	    			gl.glBegin(gl.GL_TRIANGLES);
	    			gl.glTexCoord2f(textureLeft, textureBottom);//左下
	    			gl.glVertex3d(i,this.getGridAltitude(i, y),y);
	    			gl.glTexCoord2f(textureRight, textureBottom); //右下
	    			gl.glVertex3d(i,this.getGridAltitude(i, y+1),y+1);
	    			gl.glTexCoord2f(textureLeft, textureTop);//左上
	    			gl.glVertex3d(i+1,this.getGridAltitude(i+1,y),y);
	    			gl.glEnd();
	    			
	    			gl.glBegin(gl.GL_TRIANGLES);
	    			gl.glTexCoord2f(textureRight, textureBottom); //右下
	    			gl.glVertex3d(i,this.getGridAltitude(i, y+1),y+1);
	    			gl.glTexCoord2f(textureRight, textureTop);//右上
	    			gl.glVertex3d(i+1,this.getGridAltitude(i+1, y+1),y+1);
	    			gl.glTexCoord2f(textureLeft, textureTop);//左上
	    			gl.glVertex3d(i+1,this.getGridAltitude(i+1,y),y);
	    			gl.glEnd();
    	        }
    			
    			if(!drawTriangle){
	    			//这是网格线,polygonoffset好像不对?
	    			gl.glColor3d(0.0, 0.0, 0.0);
	    			//方向必须是逆时针
	    			gl.glBegin(gl.GL_LINE_LOOP);
	    			gl.glVertex3d(i,this.getGridAltitude(i, y)+0.01,y);
	    			gl.glVertex3d(i,this.getGridAltitude(i, y+1)+0.01,y+1);
	    			gl.glVertex3d(i+1,this.getGridAltitude(i+1, y+1)+0.01,y+1);
	    			gl.glVertex3d(i+1,this.getGridAltitude(i+1,y)+0.01,y);
	    			gl.glEnd();
	    			gl.glDisable( GL.GL_POLYGON_OFFSET_FILL ); 
    			} else {
    				gl.glColor3d(0.0, 0.0, 0.0);
    				gl.glBegin(gl.GL_LINE_LOOP);
	    			gl.glVertex3d(i,this.getGridAltitude(i, y),y);
	    			gl.glVertex3d(i,this.getGridAltitude(i, y+1),y+1);
	    			gl.glVertex3d(i+1,this.getGridAltitude(i+1,y),y);
	    			gl.glEnd();
	    			
	    			gl.glBegin(gl.GL_LINE_LOOP);
	    			gl.glVertex3d(i,this.getGridAltitude(i, y+1)+0.01,y+1);
	    			gl.glVertex3d(i+1,this.getGridAltitude(i+1, y+1)+0.01,y+1);
	    			gl.glVertex3d(i+1,this.getGridAltitude(i+1,y)+0.01,y);
	    			gl.glEnd();
    			}
    		}
    	}
    	drawAvatar(gl);
    	drawTrees(gl,treeTexture);
    	//每个drawtree都有gl.glPopMatrix();所以现在回到了地图原点
    	//gl.glPopMatrix();
    	drawRoad(gl, roadTexture);
    }
    
    public Avatar getAvatar(){
    	return avatar;
    }
    
    private Avatar avatar = new Avatar(this);
    public void drawAvatar(GL2 gl){    	
    	avatar.draw(gl);
    }
    
    public void drawTrees(GL2 gl,Texture TreeTexture){
    	for(Tree tree : myTrees){
    		tree.draw(gl,TreeTexture);
    	}
    }
    
    public void drawRoad(GL2 gl,Texture texture){
    	for(Road road: myRoads){
    		road.draw(gl, texture);
    	}
    }
    
    
    /**
     * Create a new terrain
     *
     * @param width The number of vertices in the x-direction
     * @param depth The number of vertices in the z-direction
     */
    public Terrain(int width, int depth) {
        mySize = new Dimension(width, depth);
        myAltitude = new double[width][depth];
        myTrees = new ArrayList<Tree>();
        myRoads = new ArrayList<Road>();
        mySunlight = new float[3];
    }
    
    public Terrain(Dimension size) {
        this(size.width, size.height);
    }

    public Dimension size() {
        return mySize;
    }

    public List<Tree> trees() {
        return myTrees;
    }

    public List<Road> roads() {
        return myRoads;
    }

    public float[] getSunlight() {
        return mySunlight;
    }

    /**
     * Set the sunlight direction. 
     * 
     * Note: the sun should be treated as a directional light, without a position
     * 
     * @param dx
     * @param dy
     * @param dz
     */
    public void setSunlightDir(float dx, float dy, float dz) {
        mySunlight[0] = dx;
        mySunlight[1] = dy;
        mySunlight[2] = dz;        
    }
    
    /**
     * Resize the terrain, copying any old altitudes. 
     * 
     * @param width
     * @param height
     */
    public void setSize(int width, int height) {
        mySize = new Dimension(width, height);
        double[][] oldAlt = myAltitude;
        myAltitude = new double[width][height];
        
        for (int i = 0; i < width && i < oldAlt.length; i++) {
            for (int j = 0; j < height && j < oldAlt[i].length; j++) {
                myAltitude[i][j] = oldAlt[i][j];
            }
        }
    }

    /**
     * Get the altitude at a grid point
     * 
     * @param x
     * @param z
     * @return
     */
    public double getGridAltitude(int x, int z) {
        return myAltitude[x][z];
    }

    /**
     * Set the altitude at a grid point
     * 
     * @param x
     * @param z
     * @return
     */
    public void setGridAltitude(int x, int z, double h) {
        myAltitude[x][z] = h;
    }

    /**
     * Get the altitude at an arbitrary point. 
     * Non-integer points should be interpolated from neighbouring grid points
     * 
     * TO BE COMPLETED
     * 
     * @param x
     * @param z
     * @return
     */
    public double altitude(double x, double z) {
        double altitude = 0;
        int xu = (int) Math.ceil(x);
    	int xd = (int) Math.floor(x);
    	int zu = (int) Math.ceil(z);
    	int zd = (int) Math.floor(z);
    	if(zu!=z && x!=xu){
	    	double r1 = Math.abs((xu-x)*getGridAltitude(xd, zd)) + Math.abs((x-xd)*getGridAltitude(xu,zd));
	    	double r2 = Math.abs((xu-x)*getGridAltitude(xd, zu)) +  Math.abs((x-xd)*getGridAltitude(xu,zu));
	        altitude = (zu-z)*r1+(z-zd)*r2;
	        if(debug) {
	    		System.out.println("r1: "+r1);
	    	}
    	} else if(zu == z && xu != x){ //当z是整数时
    		double r1 = Math.abs((xu-x)*getGridAltitude(xd, zd)) + Math.abs((x-xd)*getGridAltitude(xu,zd));
    		return r1;
    	} else if(xu == x && zu != z){ //当x是整数时
    		double r1 = Math.abs((zu-z)*getGridAltitude(xd, zd)) + Math.abs((z-zd)*getGridAltitude(xd,zu));
    		return r1;
    	} else if(xu == x && zu == z){ //当x z都是整数时
    		return getGridAltitude((int)x,(int)z);
    	}
        return altitude;
    }

    /**
     * Add a tree at the specified (x,z) point. 
     * The tree's y coordinate is calculated from the altitude of the terrain at that point.
     * 
     * @param x
     * @param z
     */
    public void addTree(double x, double z) {
        double y = altitude(x, z);
        Tree tree = new Tree(x, y, z,this);
        myTrees.add(tree);
    }

    

    /**
     * Add a road. 
     * 
     * @param x
     * @param z
     */
    public void addRoad(double width, double[] spine) {
        Road road = new Road(width, spine,this);
        myRoads.add(road);        
    }

    
    
    
    
    
    
    
    
    

    
    public void triangleTest(GL2 gl){
    	//所有的面都要按照逆时针绘制！！//清理屏幕和深度缓存  
        //gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);  
        //重置模型观察矩阵  
        gl.glLoadIdentity();  
        //将绘制中心左移1.5个单位，向屏幕里移入6个单位  
        gl.glTranslatef(-1.0f, 0.0f, -6.0f);  
        //设置旋转轴，以Y轴为旋转轴旋转rtri度  
        gl.glRotatef(30, 45, 1.0f, 0.0f);  
         /** 
         * 下面的代码创建一个绕者其中心轴旋转的金字塔。 
         * 金字塔的上顶点高出原点一个单位，底面中心低于原点一个单位。 
         * 上顶点在底面的投影位于底面的中心. 
         * 注意所有的面－三角形都是逆时针次序绘制的。 
         * 这点十分重要，在以后的课程中我会作出解释。 
         * 现在，您只需明白要么都逆时针，要么都顺时针， 
         * 但永远不要将两种次序混在一起，除非您有足够的理由必须这么做。 
         * 下面我们开始绘制金字塔的各个面 
         */  
        gl.glBegin(GL2.GL_TRIANGLES);           // Drawing Using Triangles  
        /** 
         * 开始绘制金字塔的的前侧面 
         */  
        //设置当前的颜色为红色，设置前侧面的上顶点  
        gl.glColor3f(1.0f, 0.0f, 0.0f);           
        gl.glVertex3f(0.0f, 1.0f, 0.0f);              
       //设置当前的颜色为绿色，设置这个前侧面的左顶点  
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);  
        //设置当前的颜色为蓝色,设置前侧面的右顶点    
        gl.glVertex3f(1.0f, -1.0f, 1.0f);  
          
        /** 
         * 绘制金字塔的右侧面 
         * 设置渲染颜色为红色，当前的点为右侧面的上顶点 
         */  
        gl.glColor3f(0.0f, 1.0f, 0.0f);           
        gl.glVertex3f(0.0f, 1.0f, 0.0f);  
        //设置当前的颜色为蓝色，当前的点为右侧面的左顶点     
        gl.glVertex3f(1.0f, -1.0f, 1.0f);  
        //设置当前的颜色为绿色，当前的点位右侧面右顶点  
        gl.glVertex3f(1.0f, -1.0f, -1.0f);  
  
        /** 
         * 绘制金字塔的背面 
         * 设置当前渲染颜色为红色，当前的点位背面的上顶点 
         */  
        gl.glColor3f(0.0f, 0.0f, 1.0f);           
        gl.glVertex3f(0.0f, 1.0f, 0.0f);  
         //设置当前的颜色为绿色，当前的点为背面的左顶点   
        gl.glVertex3f(1.0f, -1.0f, -1.0f);  
        //设置当前的颜色为蓝色，当前的点为背面的右顶点  
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);  
  
        /** 
         * 绘制金字塔的左侧面 
         * 设置颜色为红色，设置左侧面的上顶点 
         */  
        gl.glColor3f(1.0f, 1.0f, 0.0f);           
        gl.glVertex3f(0.0f, 1.0f, 0.0f);  
        //设置颜色为蓝色，设置左侧面的左顶点       
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);  
        //设置颜色为绿色，设置左侧面的右顶点       
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);  
        //完成金字塔的绘制  
        gl.glEnd();  
        //重置模型观察矩阵  
        gl.glLoadIdentity();  

    }
    

}
