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
import javax.swing.*;

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

	private static JLabel label;
	
	/**
	 * Run the game.
	 * 
	 */
	public void run() {
		// setup camera
		// build a mesh
		label = new JLabel();
		label.setForeground(new java.awt.Color(204, 204, 255));
		label.setText("<HTML>THE Label<P><P>Sample Output</HTML>");
		Camera camera = new Camera(myTerrain,label);
		System.out.println(myTerrain.getGridAltitude(1, 1));
		GLProfile glprofile = GLProfile.getDefault();
		GLCapabilities glcapabilities = new GLCapabilities(glprofile);
		GLJPanel panel = new GLJPanel(glcapabilities);
		
		panel.addGLEventListener(camera);
		panel.addKeyListener(new Keyboard(camera));
		panel.setFocusable(true);
		panel.requestFocus();
		//panel.add(label);
		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
        panel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addComponent(label, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(551, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)
                    .addContainerGap())
            );
		
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
