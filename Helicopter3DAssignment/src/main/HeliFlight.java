package main;

import helihierarchy.CreateHelicopter;
import helihierarchy.heliparts.CoordinateSystem;
import helihierarchy.heliparts.TreeNode;
import origin.Origin;
import resources.Colours;
import scene.Ground;
import sceneview.Camera;
import sceneview.Lighting;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.GL2;


/**
 * Starting class for Assignment 2 - Project Part I (2016)
 * Sets up prospective, a basic static camera and lighting
 * Renders a Utah Teapot at the origin
 * 
 * Implementation of reshape class has been taken from 
 * PAGE 12 of COMP612 lecture slides - Unit 11 - Camera Positioning
 * 
 * @author Jacqueline Whalley
 *
 */
public class HeliFlight implements GLEventListener, KeyListener {

	private static GLCanvas canvas;
	
	//scene objects
	private Camera camera;
	private Lighting lights;
	private Origin origin;
	private Ground ground;
	private TreeNode heliRoot;
	
	//Drawing functionality libraries
	GL2 gl;
	GLU glu;
	
	public static void main(String[] args) {
		
		Frame frame = new Frame("A2 3D Helicopter - Maya Ashizumi-Munn");	//Changed frame title
		canvas = new GLCanvas();
		HeliFlight app = new HeliFlight();
		canvas.addGLEventListener(app);
		canvas.addKeyListener(app); //Key listener
		
		//Print out key mappings to user
		printKeyMappings();

		frame.add(canvas);
		frame.setSize(1000, 500);
		final FPSAnimator animator = new FPSAnimator(canvas, 60);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// Run this on another thread than the AWT event queue to
				// make sure the call to Animator.stop() completes before
				// exiting
				new Thread(new Runnable() {

					@Override
					public void run() {
						animator.stop();
						System.exit(0);
					}
				}).start();
			}
		});
		// Center frame
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		animator.start();
	}
	
	private static void printKeyMappings() {
		//Prints key mappings to user
		System.out.println("KEY MAPPINGS:");
		System.out.println(" L: Toggle wireframe view");
	}



	@Override
	public void init(GLAutoDrawable drawable) {
		gl = drawable.getGL().getGL2();
		glu = new GLU();
		
		// Enable VSync
		gl.setSwapInterval(1);
		
		// Setup the drawing area and shading mode
		gl.glEnable (GL2.GL_BLEND);
		gl.glBlendFunc (GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		
		//Background colour (sky colour)
		float[] bgColour = Colours.SKY_BLUE.getFloatRGB();
		gl.glClearColor(bgColour[0], bgColour[1], bgColour[2], 0.9f);
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glEnable(GL2.GL_DEPTH_TEST);

		//Initialise scene objects
		initScene();
		
		//Initialise helicopter objects (create world root node)
		heliRoot = new CoordinateSystem(2, 1);
		//Gets the treenode object of the helicopter capsule and adds it as roots child
		heliRoot.addChild(new CreateHelicopter().drawHeliParts());
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {

		//Do animations first - TODO
		
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
		
		//Default (not coded by me)
		camera.draw(gl);		
		lights.draw(gl);
		
		//Added by myself for assignment
		//Calling display lists to be drawn for scene objects
		origin.draw();
		ground.draw(); 
		
		//Calling helicopter display lists to be drawn
		heliRoot.draw(gl, glu);
		
		// Flush all drawing operations to the graphics card
		gl.glFlush();
	}
	
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		camera.newWindowSize(width, height);
	}
	
	//**********************************************//
	
	private void initScene() {
		//Initialise scene objects
		camera = new Camera(canvas);
		lights = new Lighting(gl);
		origin = new Origin(gl, glu);
		//Ground try-catch for odd number exception
		try {
			ground = new Ground(gl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Call display lists to compile
		origin.precompileDisplayList();
		ground.precompileDisplayList();
	}
	
	//***************************************************// 

		@Override
		public void keyPressed(KeyEvent arg0) {
			int key = arg0.getKeyCode();
			
			if (key == KeyEvent.VK_L) {
				//L key toggles drawing mode change
				ground.toggleDrawMode();
			}
		}
		
		//******* Redundant KeyListener methods *************//

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dispose(GLAutoDrawable drawable) {
			// TODO Auto-generated method stub
			
		}
	
}
