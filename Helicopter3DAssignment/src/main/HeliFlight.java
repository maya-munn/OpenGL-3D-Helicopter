package main;

import helihierarchy.CreateHelicopter;
import helihierarchy.heliparts.Body;
import helihierarchy.heliparts.Motor;
import movement.InputHandler;
import movement.MovementController;
import origin.Origin;
import resources.Colours;
import scene.Ground;
import sceneview.Camera;
import sceneview.Lighting;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.GL2;


/**
 * Main class derived from: Starting class for Assignment 2 - Project Part I (2016)
 * Sets up prospective, a basic static camera and lighting
 * 
 * Renders a helicopter object in a scene
 * 
 * Implementation of reshape class has been taken from 
 * PAGE 12 of COMP612 lecture slides - Unit 11 - Camera Positioning
 * 
 * @author Jacqueline Whalley
 * Modified by Maya Ashizumi-Munn | 17978640
 *
 */
public class HeliFlight implements GLEventListener {

	private static GLCanvas canvas;
	
	//Scene objects
	private Camera camera;
	private Lighting lights;
	private Origin origin;
	private static Ground ground;
	private Body heliBody;
	
	//Drawing functionality libraries
	GL2 gl;
	GLU glu;
	
	//Movement controller and input handler for helicopter
	private static MovementController moveController;
	private static InputHandler inputHandler;
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to Maya's Helicopter Simulator");
		Frame frame = new Frame("A2 3D Helicopter - Maya Ashizumi-Munn");	//Changed frame title
		canvas = new GLCanvas();
		
		HeliFlight app = new HeliFlight();
		canvas.addGLEventListener(app);
		
		//Handling input and helicopter movement
		moveController = new MovementController();
		inputHandler = new InputHandler(moveController);
		canvas.addKeyListener(inputHandler); //Key listener

		frame.add(canvas);
		frame.setSize(1000, 500);
		final FPSAnimator animator = new FPSAnimator(canvas, 120);
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
		heliBody = new Body();
		moveController.setHeliBody(heliBody, gl);
		
		//Sets all the other helicopter parts as heli bodys child
		CreateHelicopter heliCreator = new CreateHelicopter(gl);
		heliCreator.createHeliParts(heliBody);

		//Send reference of rotor motors to movement controller
		Motor[] motors = heliCreator.getMotors();
		moveController.setMotors(motors[0], motors[1], motors[2]);
		
		//Send reference of ground and camera to input handler
		inputHandler.setGroundRef(ground);
		inputHandler.setCameraRef(camera);
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {
		
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
		
		//Set camera location relative to helicopter position
		camera.setLookAt(heliBody.getX(), heliBody.getY(), heliBody.getZ());
		camera.draw(gl);		
		
		//Calling display lists to be drawn for scene objects
		origin.draw();
		ground.draw(); 
		
		//Calling helicopter display lists to be drawn
		heliBody.draw(gl, glu);
		
		//Call movement methods
		moveController.updateMovement();
		
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
		lights.enable();
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

	//Unimplemented dispose method
	@Override
	public void dispose(GLAutoDrawable drawable) { }
}
