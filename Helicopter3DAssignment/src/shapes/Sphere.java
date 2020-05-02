package shapes;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

/**
 * Draws a custom sphere
 * 
 * @author Maya Ashizumi-Munn | ID: 17978640
 */
public class Sphere {
	
	GL gl;
	GLU glu;
	double radius;
	int slices;
	int stacks;
	int drawStyle;
	
	GLUquadric sphere;
	
	//************************************//
	
	public Sphere(GL gl, GLU glu, double radius, int slices, int stacks, int drawStyle) throws Exception {
		this.setGL(gl, glu);
		this.setRadius(radius);
		this.setSlices(slices);
		this.setStacks(stacks);
		//Checking if draw style has been properly configured
		switch (drawStyle) {
			case GLU.GLU_POINT: case GLU.GLU_LINE: case GLU.GLU_FILL:
				this.setDrawStyle(drawStyle);
				break;
			default:
				throw new Exception("Draw style not valid!");
		}
		
	}
	
	
	public void drawSphere() {
		//Create quadric
		sphere = glu.gluNewQuadric();
		//Set sphere draw style
		glu.gluQuadricDrawStyle(sphere, drawStyle);
		//Draw sphere in specified draw style and dimensions
		glu.gluSphere(sphere, this.getRadius(), this.getSlices(), this.getStacks());
	}
	
	public void deleteSphere() {
		//Delete sphere when done with it
		glu.gluDeleteQuadric(sphere);
	}
	
	//************************************//
	
	private void setGL(GL gl, GLU glu) {
		this.gl = gl;
		this.glu = glu;
	}
	
	private void setRadius(double radius) {
		this.radius = radius;
	}
	
	private void setSlices(int slices) {
		this.slices = slices;
	}
	
	private void setStacks(int stacks) {
		this.stacks = stacks;
	}
	
	private void setDrawStyle(int drawStyle) {
		this.drawStyle = drawStyle;
	}
	
	//************************************//
	
	private double getRadius() {
		return this.radius;
	}
	
	private int getSlices() {
		return this.slices;
	}
	
	private int getStacks() {
		return this.stacks;
	}
}
