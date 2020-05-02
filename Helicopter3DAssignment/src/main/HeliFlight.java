package main;

/**
 * Chopper - yeah.
 * 
 * @author Jacqueline Whalley
 */

import com.sun.opengl.util.FPSAnimator;

import origin.Origin;
import scene.Camera;
import scene.Lighting;

import java.awt.Frame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

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
public class HeliFlight implements GLEventListener {

	private static GLCanvas canvas;
	
	//scene objects
	private Camera camera;
	private Lighting lights;
	private Origin origin;
	
	//Drawing functionality
	GL gl;
	GLU glu;
	
	
	public static void main(String[] args) {
		
		Frame frame = new Frame("A2 3D Helicopter - Maya Ashizumi-Munn");	//Changed frame title
		canvas = new GLCanvas();
		HeliFlight app = new HeliFlight();
		canvas.addGLEventListener(app);
		

		frame.add(canvas);
		frame.setSize(1000, 500);
		final FPSAnimator animator = new FPSAnimator(canvas,60);
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
		gl = drawable.getGL();
		glu = new GLU();
		
		// Enable VSync
		gl.setSwapInterval(1);
		
		// Setup the drawing area and shading mode
		gl.glEnable (GL.GL_BLEND);
		gl.glBlendFunc (GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glClearColor(0.1f, 0.1f, 0.6f, 0.8f);
		gl.glShadeModel(GL.GL_SMOOTH);
		gl.glEnable(GL.GL_DEPTH_TEST);

		
		camera = new Camera(canvas);
		lights = new Lighting(gl);
		origin = new Origin(gl, glu);
		//Call origins display lists
		origin.precompileDisplayList();
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {

		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glEnable(GL.GL_COLOR_MATERIAL);
		
		//Default (not coded by me)
		camera.draw(gl);		
		lights.draw(gl);
		//Added
		origin.draw(); //Calls display lists
		
		//Removed teapot GLUT
		
		// Flush all drawing operations to the graphics card
		gl.glFlush();
	}
	
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		camera.newWindowSize(width, height);
	}


	//methods not used
	@Override
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}
	
}
