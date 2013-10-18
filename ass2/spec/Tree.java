package ass2.spec;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.jogamp.opengl.util.texture.Texture;

/**
 * COMMENT: Comment Tree
 * 
 * @author malcolmr
 */
public class Tree {
	final public boolean debug = Game.debug;
	private double[] myPos;
	Terrain myTerrain;
	GLU glu = new GLU();
	float treeHeight = 0.8f;
	float treeRadius = 0.1f;

	public void draw(GL2 gl, Texture treeTexture) {
		if (debug) {
			System.out.println("tree's coordinate: x,heigh,z "
					+ getPosition()[0] + " " + getPosition()[1] + " "
					+ getPosition()[2]);
		}
		gl.glPushMatrix();
		gl.glTranslated(getPosition()[0], getPosition()[1], getPosition()[2]);
		gl.glRotatef(-90, 1.0f, 0.0f, 0.0f); // angle,x,y,z 由于glucylinder是在z轴上画的
		/**
		 * 纹理的用法 gl.glColor3f(1.0f, 1.0f, 1.0f); //白色
		 * gl.glEnable(GL.GL_TEXTURE_2D); //使用纹理
		 * gl.glBindTexture(GL.GL_TEXTURE_2D, textureArr[1]);//
		 */
		gl.glColor3f(102 / 255f, 51 / 255f, 0.0f); 
		// Enables this texture's target in the current GL context's state.
		treeTexture.enable(gl); // same as gl.glEnable(texture.getTarget());
		// gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE,
		// GL.GL_REPLACE);
		// Binds this texture to the current GL context.
		treeTexture.bind(gl); // same as gl.glBindTexture(texture.getTarget(),
		// texture.getTextureObject());

		GLUquadric cylinder = glu.gluNewQuadric();
		glu.gluQuadricTexture(cylinder, true);
		glu.gluQuadricNormals(cylinder, GLU.GLU_SMOOTH);
		glu.gluCylinder(cylinder, treeRadius, treeRadius, treeHeight, 5, 10);
		gl.glNormal3f(0.0f, 0.0f, 1.0f); // front face points out of the screen
		// on z.
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f); // Bottom Left Of The Texture and
		// Quad
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f); // Bottom Right Of The Texture and
		// Quad
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f); // Top Right Of The Texture and Quad
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f); // Top Left Of The Texture and Quad
		gl.glEnd();

		gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
		gl.glTranslated(0.0f, treeRadius + treeHeight, 0.0f);
		glu.gluQuadricTexture(cylinder, false);
		// 球体,位置在radius+height的地方(中心位置)
		gl.glColor3f(51 / 255f, 102 / 255f, 0.0f);
		glu.gluSphere(cylinder, 0.4, 25, 25); // ？？没有NORMAL？没有texture？？
		gl.glPopMatrix();
	}

	public Tree(double x, double y, double z, Terrain terrain) {
		myTerrain = terrain;
		myPos = new double[3];
		myPos[0] = x;
		myPos[1] = y;
		myPos[2] = z;
	}

	public double[] getPosition() {
		return myPos;
	}

}
