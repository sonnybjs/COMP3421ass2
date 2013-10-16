package ass2.spec;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

public class Avatar {
	public double x = 0.0;
	public double y;
	public double z = 0.0;
	public double step = 0.1;
	Terrain terrain;
	GLU glu = new GLU();
	double radius = 0.05;
	double height = 0.5;
	
	public Avatar(Terrain terrain2) {
		terrain = terrain2;
	}

	public void draw(GL2 gl){
		gl.glPushMatrix();
		y = getY();
		//gl.glLoadIdentity();
		gl.glTranslated(x, y, z);
		gl.glRotatef(-90, 1.0f, 0.0f, 0.0f);  //angle,x,y,z 由于glucylinder是在z轴上画的
		gl.glColor3f(1.0f, 1.0f, 1.0f);
		GLUquadric obj = glu.gluNewQuadric();
    	//glu.gluQuadricTexture(cylinder, true);
    	glu.gluQuadricNormals(obj, GLU.GLU_SMOOTH);
		glu.gluCylinder(obj , radius, radius, height, 3, 10);
		
		
		gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
		gl.glTranslated(0.0f,radius	+height, 0.0f);
		glu.gluSphere(obj, radius*2, 25, 25);
		gl.glPopMatrix();
		
	}
	
	public double getY(){
		y = terrain.altitude(x, z);
		return y;
	}
	
	public void stepForawrd(double angle){
		
		double radius = angle * Math.PI / 180.0;
		double newX = x + Math.sin(radius) * step;
		double newZ = z + Math.cos(radius) * step;	
		if( newX < 0.0){
			newX = 0.0;
		}
		
		if( newZ < 0.0){
			newZ = 0.0;
		}
		if(newX > terrain.size().getWidth()-1){
			newX = terrain.size().getWidth() - 1 ;
		}
		if(newZ > terrain.size().getHeight()-1){
			newZ = terrain.size().getHeight() - 1 ;
		}
		if(newX <= terrain.size().getWidth()-1 && newX >=0 && newZ >= 0&& newZ <= terrain.size().getHeight()-1){
			z = newZ;
			x = newX;
			y = getY();
		}
				
	}
	
	public void stepBackward(double angle){
		double radius = (angle+180) * Math.PI / 180.0;
		double newX = x + Math.sin(radius) * step;
		double newZ = z + Math.cos(radius) * step;
		
		
		if( newX < 0.0){
			newX = 0.0;
		}
		
		if( newZ < 0.0){
			newZ = 0.0;
		}
		if(newX > terrain.size().getWidth()-1){
			newX = terrain.size().getWidth() - 1 ;
		}
		if(newZ > terrain.size().getHeight()-1){
			newZ = terrain.size().getHeight() - 1 ;
		}
		
		if( newX <= terrain.size().getWidth()-1 && newX >=0 && newZ >= 0 && newZ <= terrain.size().getHeight()-1){			
			z = newZ;
			x = newX;
			y = getY();
		}
	}
	
}
