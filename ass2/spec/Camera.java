package ass2.spec;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;

public class Camera implements GLEventListener{
	final public boolean debug = true;
	double xtrans = 0.0;
	double ytrans = 0.0;
	double ztrans = 0.0;
	int yrotate = 0;
	Terrain myTerrain;


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
		gl.glTranslated(xtrans-2.0, ytrans+1, ztrans-20); 
        gl.glRotatef(30, 1.0f, 0.0f, 0.0f); 
        gl.glRotatef(-30+yrotate, 0.0f, 1.0f, 0.0f); 
        gl.glPushMatrix();
        //现在GL在地图应在位置
		drawShape(gl);
	}

	
	
	//真正的画图的方法。由于terrian内部含有tree之类的方法，所以只要载入terrian就好了
	private void drawShape(GL2 gl){
		//myTerrain.triangleTest(gl);
		myTerrain.draw(gl);
	}


	@Override
	public void init(GLAutoDrawable arg0) {   //获取GL对象  
		GL2 gl = arg0.getGL().getGL2();  
		gl.glLoadIdentity();
		GLU glu = new GLU();
		
		
		
		
		//启用阴影平滑  
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
		glu.gluPerspective(45.0f, h, 1.0, 40.0); //--眼睛睁开的角度 ，比例，近，远
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


	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub

	}

}
