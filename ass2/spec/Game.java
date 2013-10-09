package ass2.spec;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;

import ass2.spec.LevelIO;

/**
 * COMMENT: Comment Game
 * 
 * @author malcolmr
 */
public class Game extends JFrame{

	private Terrain myTerrain;

	public Game(Terrain terrain) {
		myTerrain = terrain;
	}

	/**
	 * Run the game.
	 * 
	 */
	public void run() {
		// setup camera
		// build a mesh
		Camera camera = new Camera(myTerrain);
		System.out.println(myTerrain.getGridAltitude(1, 1));
		GLProfile glprofile = GLProfile.getDefault();
		GLCapabilities glcapabilities = new GLCapabilities(glprofile);
		GLJPanel panel = new GLJPanel(glcapabilities);
		
		panel.addGLEventListener(camera);
		panel.addKeyListener(new Keyboard(camera));
		panel.setFocusable(true);
		panel.requestFocus();

		// Add an animator to call 'display' at 60fps
		FPSAnimator animator = new FPSAnimator(60);
		animator.add(panel);
		animator.start();

		getContentPane().add(panel, BorderLayout.CENTER);
		setSize(800, 800);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * Load a level file and display it.
	 * 
	 * @param args
	 *            - The first argument is a level file in JSON format
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Terrain terrain = LevelIO.load(new File(args[0]));
		System.out.println(args[0]);
		Game game = new Game(terrain);
		game.run();
	}
}
