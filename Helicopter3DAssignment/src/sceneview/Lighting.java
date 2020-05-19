package sceneview;

import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import com.jogamp.opengl.GL2;

/**
 * Basic Lighting setup for scene
 * Consists of global ambient lighting and 
 * 
 * @author Jacqueline Whalley
 * Modified by Maya Ashizumi-Munn | 17978640
 */
public class Lighting {
	public GLU glu;
	public GL2 gl;
	public GLUquadric quadric;
	float globalAmbient[] = { 0.4f, 0.4f, 0.4f, 1 }; 	// global light properties
	
	public float[] lightPosition = { 5.0f, 5.0f, 5.0f, 1.0f }; //point light
	public float[] ambientLight = { 0, 0, 0, 1 };
	public float[] diffuseLight = { 1, 1, 1, 1 };
	public float[] specularLight = { 1, 1, 1, 1 };

	/**
	 * Creates and enables scene's lights using default light0 position
	 * @param gl
	 */
	public Lighting(GL2 gl) {
		glu = new GLU();
		this.gl = gl;
		// set the global ambient light level
	    gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, globalAmbient, 0);
	    
		// setup the light 0 properties
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specularLight, 0);
		
		// normalize the normals
	    gl.glEnable(GL2.GL_NORMALIZE);
	    
	   // position the light
	    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition, 0);
	}

	/**
	 * Creates and enables scene's light and specifies a position for light 0
	 * @param position light0's position
	 * @param gl
	 */
	public Lighting(float[] position, GL2 gl) {
		//To be implemented when scenes light pos needs to be changed
	}
	
	/**
	 * enable both global ambient and light0
	 */
	public void enable(){
		// enable lighting
	    gl.glEnable(GL2.GL_LIGHTING);
	    // enable light 0
	    gl.glEnable(GL2.GL_LIGHT0);
	}
	
	/**
	 * disables light0 only
	 * global ambient light remains enabled
	 */
	public void disable(){
		gl.glDisable(GL2.GL_LIGHT0); //keep ambient light
	}
}