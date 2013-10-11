package ass2.spec;

import static javax.media.opengl.GL.GL_LINEAR;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.GL.GL_TEXTURE_MAG_FILTER;
import static javax.media.opengl.GL.GL_TEXTURE_MIN_FILTER;

import java.io.IOException;
import java.io.InputStream;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class Camera implements GLEventListener{
	final public boolean debug = true;
	double xtrans = 0.0;
	double ytrans = 0.0;
	double ztrans = 0.0;
	int yrotate = 0;
	Terrain myTerrain;
	GLU glu = new GLU();
	float ambient = 0.5f;
	float diffuse = 1.7f;
	float specular = 0.3f;

    boolean lightEnable = true;
    
    
    
	public Camera(Terrain theTerrain){
		myTerrain = theTerrain;
	}

	@Override
	public void display(GLAutoDrawable arg0) {
		GL2 gl = arg0.getGL().getGL2();
		if(debug) {
			System.out.println("display method working");
		}
		// clear both the colour buffer and the depth buffer
		//设置背景为天蓝色
		gl.glClearColor(0.0f, 245/255f, 1.0f, 0.5f);  
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		
		
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		//如果要改变camera位置，可以直接gl.glrotate
		//或者采用GLU LOOK AT
		/*
		gl.glTranslated(xtrans-2.0, ytrans+1, ztrans-20); 
        gl.glRotatef(30, 1.0f, 0.0f, 0.0f); 
        gl.glRotatef(-30+yrotate, 0.0f, 1.0f, 0.0f); 
        */
		// TODO 这个不对,目的实现了只是
		double eyex = 5.0+xtrans;
		double eyey = 2.0+ytrans;
		double eyez = -15.5+ztrans; //注意这里配合rotate做了更改
		double centerx = 5+xtrans;
		double centery = 2;
		double centerz = 5+ztrans;
		/*
		double[] ec = {eyex-centerx,eyey-centery,eyez-centerz,1};
		double[] r = MathUtil.multiply(MathUtil.rotationMatrix(yrotate, false, true, false), ec);
		double[] ec2 = {centerx-r[0],centery-r[1], centerz - r[2]};
		eyex = ec[0];
		eyey = ec[1];
		eyez = ec[2];
		*/
		glu.gluLookAt(eyex, eyey, eyez, centerx, centery, centerz, 0, 1, 0); //TODO 需要修改
		gl.glRotatef(-30+yrotate, 1.0f, 0.0f, 0.0f); 
		gl.glRotatef(30, 0.0f, 1.0f, 0.0f); 
		gl.glPushMatrix();
		
		if(lightEnable){
			setLighting(gl);
		}
		
        //现在GL在地图应在位置
		drawShape(gl);
	}

	
	private void setLighting(GL2 gl){ //TODO 此处需要调试
		 gl.glShadeModel(GL2.GL_SMOOTH);

	        // rotate the light
	        gl.glMatrixMode(GL2.GL_MODELVIEW);
	        gl.glPushMatrix();
	        gl.glRotated(0, 1, 0, 0);
	        gl.glRotated(0, 0, 1, 0);

	        float[] ambientPos = new float[] { 5.0f, 10.0f, -5.0f, 1.0f };
	        float[] diffusePos = new float[] { 10.0f, 5.0f, 5.0f, 1.0f };
	       // float[] specularPos = new float[] { 5.0f, 2.0f, 4.0f, 1.0f };
	        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, ambientPos, 0);
	        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, diffusePos, 0);
	       // gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_POSITION, specularPos, 0);
	        gl.glPopMatrix();
	        
	        // set the intensities

	        //float ambient = 0.3f;
	       // float diffuse = 0.2f;
	        //float specular = 0.4f;
			if(debug){
				System.out.println("LIghting:ambient "+ambient+" diffuse "+diffuse+" specular "+specular);
			}
	        float[] a = new float[4];
	        a[0] = a[1] = a[2] = ambient;
	        a[3] = 1.0f;
	        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, a, 0); //a是亮度,位置和LIGHT0绑在一起了

	        float[] d = new float[4];
	        d[0] = d[1] = d[2] = diffuse;
	        d[3] = 1.0f;
	        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, d, 0);
	        /*
	        float[] s = new float[4];
	        s[0] = s[1] = s[2] = specular;
	        s[3] = 1.0f;
	        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, s, 0);
	         */	
		
		
	}
	
	
	
	//真正的画图的方法。由于terrian内部含有tree之类的方法，所以只要载入terrian就好了
	private void drawShape(GL2 gl){
		//myTerrain.triangleTest(gl);
		drawCoor(gl);		
		myTerrain.draw(gl,groundTexture,treeTexture,roadTexture);
		
		
	}

	private void drawCoor(GL2 gl){
		gl.glColor3f(0.0f,0.0f,1.0f);// blue
		
		gl.glColor3d(1.0, 0.0, 0.0);		
		gl.glBegin(gl.GL_LINES);
		gl.glVertex3d(0.0, 0.0, 0.0);
		gl.glVertex3d(30.0, 0.0, 0.0);
		gl.glColor3d(0.0, 1.0, 0.0);	
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(0, 30, 0);
		gl.glColor3d(0.0, 0.0, 1.0);	
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(0, 0, 30);
		gl.glEnd();
	}
	
	Texture groundTexture;
	Texture treeTexture;
	Texture leafTexture;
	Texture roadTexture;
	private void setTexture(GL2 gl){
		 // Load texture from image
	      try {
	         // Create a OpenGL Texture object from (URL, mipmap, file suffix)
	         // Use URL so that can read from JAR and disk file.
	    	  groundTexture = TextureIO.newTexture(
	               getClass().getClassLoader().getResource("ground.jpg"), // relative to project root 
	               false, ".jpg");
	    	  treeTexture = TextureIO.newTexture(
		               getClass().getClassLoader().getResource("tree.jpg"), // relative to project root 
		               false, ".jpg");
	    	  roadTexture = TextureIO.newTexture(
		               getClass().getClassLoader().getResource("road.jpg"), // relative to project root 
		               false, ".jpg");

	         // Use linear filter for texture if image is larger than the original texture
	         gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	         // Use linear filter for texture if image is smaller than the original texture
	         gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	      } catch (GLException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	}
	
	
	@Override
	public void init(GLAutoDrawable arg0) {   //获取GL对象  
		GL2 gl = arg0.getGL().getGL2();  
		gl.glLoadIdentity();
		if(lightEnable){
			
		}
		//启用纹理映射  
		gl.glEnable(GL2.GL_TEXTURE_2D);  
        //设置混合  
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL.GL_ONE);  		
		//启用阴影平滑  /启用高斯模糊  
		gl.glShadeModel(GL2.GL_SMOOTH);  
		//设置背景颜色为黑色
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);  
		//设置深度缓存  
		gl.glClearDepth(1.0f);  
		//启用深度测试  
		gl.glEnable(GL.GL_DEPTH_TEST);  
		//所作的深度测试的类型  
		gl.glDepthFunc(GL.GL_LEQUAL);  
		// 告诉系统对透视进行修正  
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);   
		// Really Nice Perspective Calculations  
		setTexture(gl);

	}

	/**
	 * 窗口变换大小
	 */
	public void reshape(GLAutoDrawable glDrawable, int x, int y, int width, int height) {  
		final GLU glu = new GLU();  
		if(debug) {
			System.out.println("reshape method working");
		}

		final GL2 gl = glDrawable.getGL().getGL2();  
		
		 // enable depth testing
        gl.glEnable(GL.GL_DEPTH_TEST);
        if(lightEnable){
	        // enable lighting, turn on light 0
	        gl.glEnable(GL2.GL_LIGHTING);
	        gl.glEnable(GL2.GL_LIGHT0);
	
	        // normalise normals (!)
	        // this is necessary to make lighting work properly
	        gl.glEnable(GL2.GL_NORMALIZE);
        }
		//防止被零除  
		if (height <= 0) // avoid a divide by zero error!  
			height = 1;  
		final float h = (float) width / (float) height;  
		//设置视窗的大小  
		gl.glViewport(0, 0, width, height);  
		//选择投影矩阵 ,投影矩阵负责为我们的场景增加透视。  
		gl.glMatrixMode(GL2.GL_PROJECTION);  
		//重置投影矩阵;  
		gl.glLoadIdentity();  
		//设置视口的大小  
		//glu.gluOrtho2D(1.0, 1.0, 1.0, 1.0);这个是平行的，么有透视
		glu.gluPerspective(45.0f, h, 1.0, 50.0); //--眼睛睁开的角度 ，比例，近，远
		//TODO 如何知道1.0在世界坐标占的大小的？要多少才会超出框呢？
		//启用模型观察矩阵；模型观察矩阵中存放了我们的物体讯息。  
		gl.glMatrixMode(GL2.GL_MODELVIEW);  
		gl.glLoadIdentity();  
	}


	public void rotateRight() {
		if(debug){
			System.out.println("keyboard: Rotata Right");
		}
		yrotate += 10;		
	}
	public void rotateLeft() {
		if(debug){
			System.out.println("keyboard: Rotate LEft");
		}
		yrotate += -10;		
	}

	public void stepLeft() {
		if(debug){
			System.out.println("keyboard: Step Left");
		}
		xtrans += -0.1;
	}

	public void stepRight() {
		if(debug){
			System.out.println("keyboard: Step Right");
		}
		xtrans += 0.1;		
	}
/**
 * The camera should move up and down following the terrain. So if you move it forward up a hill, the camera should move up the hill.
 * 镜头要随着山坡移动(永远在山坡上方一定距离,如果距离山坡小于某个距离,则自动上抬)
 */
	public void stepForward() {
		if(debug){
			System.out.println("keyboard: Step Forward");
		}
		ztrans += 0.1;
		
	}

	public void stepBackward() {
		if(debug){
			System.out.println("keyboard: Step Backward");
		}
		ztrans += -0.1;
	}  
	
	
	public void ambientUp(){
		ambient += 0.1f;
	}
	public void ambientDown(){
		ambient += -0.1f;
	}
	public void diffuseUp(){
		diffuse += 0.1f;
	}
	public void diffuseDown(){
		diffuse += -0.1f;
	}
	public void specularUp(){
		specular += 0.1f;
	}
	public void specularDown(){
		specular += -0.1f;
	}


	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub

	}

}
