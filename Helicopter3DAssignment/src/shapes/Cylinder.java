package shapes;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

/**
 * Draws a custom cylinder
 * 
 * @author Maya Ashizumi-Munn
 */
public class Cylinder {
	
	//GL and GLU library references
	GL2 gl;
	GLU glu;
	
	//Cylinder variables
	double baseR;
	double topR;
	double height;
	int slices;
	int stacks;
	int drawStyle;
	
	GLUquadric cylinder;
	
	//************************************//
	
	/**
	 * Instantiates a custom cylinder
	 */
	public Cylinder(GL2 gl2, GLU glu, double baseR, double topR, double height,
					int slices, int stacks, int drawStyle) throws Exception {
		this.setGL(gl2, glu);
		this.setBaseRadius(baseR);
		this.setTopRadius(topR);
		this.setHeight(height);
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
	
	
	public void drawCylinder() {
		//Create quadric
		cylinder = glu.gluNewQuadric();
		//Set sphere draw style
		glu.gluQuadricDrawStyle(cylinder, drawStyle);
		//Draw sphere in specified draw style and dimensions
		glu.gluCylinder(cylinder, this.getBaseRadius(), this.getTopRadius(),
						this.getHeight(), this.getSlices(), this.getStacks());
		this.deleteQuadric();
	}
	
	private void deleteQuadric() {
		//Delete cylinder when done with it
		glu.gluDeleteQuadric(cylinder);
	}
	
	//**** Setter methods ****************//
	
	private void setGL(GL2 gl2, GLU glu) {
		this.gl = gl2;
		this.glu = glu;
	}
	
	private void setBaseRadius(double baseR) {
		this.baseR = baseR;
	}
	
	private void setTopRadius(double topR) {
		this.topR = topR;
	}
	
	private void setHeight(double height) {
		this.height = height;
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
	
	//**** Getter methods ****************//
	
	private double getBaseRadius() {
		return this.baseR;
	}
	
	private double getTopRadius() {
		return this.topR;
	}
	
	private double getHeight() {
		return this.height;
	}
	
	private int getSlices() {
		return this.slices;
	}
	
	private int getStacks() {
		return this.stacks;
	}
}
