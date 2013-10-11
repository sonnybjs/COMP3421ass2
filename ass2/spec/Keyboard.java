package ass2.spec;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Keyboard implements KeyListener{


	Camera myCamera;
	/**
	 * 控制键盘，上下左右分别代表：前进 后退 左平移 右平移
	 */
	public Keyboard (Camera camera){
		myCamera = camera;
	}
	
	/**
	 * 代码来自teapot.class
	 */
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		 switch (e.getKeyCode()) {
	        case KeyEvent.VK_SPACE:
	           // myWireframe = !myWireframe;
	            break;

	        case KeyEvent.VK_LEFT:
	        	myCamera.stepLeft();
	            break;

	        case KeyEvent.VK_RIGHT:
	        	myCamera.stepRight();
	            break;

	        case KeyEvent.VK_UP:
	        	myCamera.stepForward();
	            break;

	        case KeyEvent.VK_DOWN:
	        	myCamera.stepBackward();
	            break;
	        
			case KeyEvent.VK_PAGE_DOWN:
				myCamera.rotateLeft();
	        	break;
	    
			case KeyEvent.VK_PAGE_UP:
				myCamera.rotateRight();
				break;
				
			case KeyEvent.VK_Q:
				myCamera.ambientUp();
				break;
			case KeyEvent.VK_W:
				myCamera.ambientDown();
				break;
			case KeyEvent.VK_E:
				myCamera.diffuseUp();
				break;
			case KeyEvent.VK_R:
				myCamera.diffuseDown();
				break;
			case KeyEvent.VK_T:
				myCamera.specularUp();
				break;
			case KeyEvent.VK_Y:
				myCamera.specularDown();
				break;
		 }		
	}
}
