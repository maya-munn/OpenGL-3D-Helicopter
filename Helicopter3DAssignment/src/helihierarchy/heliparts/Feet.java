package helihierarchy.heliparts;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import resources.Colours;
import shapes.Cylinder;

/**
 * Draws the feet (landing skids) of the helicopter 
 * 
 * @author Maya Ashizumi-Munn | 17978640
 *
 */
public class Feet extends TreeNode {

	//Display list index
	private int displayList;
	
	//Helicopter feet colour
	Colours feetColour = Colours.BLACK;
	
	//What side this foot will be positioned
	Side footSide;
	
	//Length of foot
	double length;
	
	public Feet(Side footSide) {
		displayList = -1;
		this.footSide = footSide;
		length = 6;
	}
	
	public enum Side { //Used in createHelicopter to determine which side it will be drawn
		LEFT,
		RIGHT;
	}
	
	public double getLength() {
		return this.length;
	}
	
	//**************************************//
	
	@Override
	void initialiseDisplayList(GL2 gl, GLU glu) {
		
		//Create display list
		displayList = gl.glGenLists(1);
		
		//Compile data in display list
		gl.glNewList(displayList, GL2.GL_COMPILE);
			//Set feet colour
			double[] tailColours = feetColour.getRGB();
			gl.glColor3d(tailColours[0], tailColours[1], tailColours[2]);
			
			//Set feet cylinder properties
			double radius = 0.2;
			int slicesStacks = 15;

			//Draw feet cylinder
			gl.glPushMatrix();
				//Rotate 90 degrees on Y axis
				gl.glRotated(90, 0, 1, 0);
				//Draw cylinder
				try {
					Cylinder feetCylinder = new Cylinder(gl, glu, radius, radius, length, slicesStacks, slicesStacks, GLU.GLU_FILL);
					feetCylinder.drawCylinder();
				} catch (Exception e) { e.printStackTrace(); }
				
			gl.glPopMatrix();
			
			drawConnectorCylinders(length, radius, gl, glu);
			
		gl.glEndList();
	}
	
	private void drawConnectorCylinders(double feetLength, double feetRadius, GL2 gl, GLU glu) {
		//Draw connector cylinders
		double radius = 0.1;
		//Save feet plank height for later use
		int height = 1;
		int slicesStacks = 15;
		
		//First connector cylinder
		gl.glPushMatrix();
			//Rotate -90degrees on x axis to be on top
			gl.glRotated(-90, 1, 0, 0);
			//Move this connector down on x axis by quarter of feet cylinder height 
			gl.glTranslated(feetLength / 4, 0, 0);
			//Draw cylinder
			try {
				Cylinder frontConnector = new Cylinder(gl, glu, radius, radius, height, slicesStacks, slicesStacks, GLU.GLU_FILL);
				frontConnector.drawCylinder();
			} catch (Exception e) { e.printStackTrace(); }
		gl.glPopMatrix();
		
		//Second connector cylinder
		gl.glPushMatrix();
			//Rotate -90degrees on x axis to be on top
			gl.glRotated(-90, 1, 0, 0);
			//Move this connector up on x axis by quarter of feet cylinder height
			gl.glTranslated(feetLength - ((feetLength / 4)), 0, 0);
			//Draw cylinder
			try {
				Cylinder backConnector = new Cylinder(gl, glu, radius, radius, height, slicesStacks, slicesStacks, GLU.GLU_FILL);
				backConnector.drawCylinder();
			} catch (Exception e) { e.printStackTrace(); }
		gl.glPopMatrix();
	}

	@Override
	void drawNode(GL2 gl) {
		//If display list not initialised, do now
		if (displayList < 0) {
			initialiseDisplayList(gl, glu);
		}
		
		//Call display list to be drawn
		gl.glPushMatrix();
			gl.glCallList(displayList);
		gl.glPopMatrix();
	}
}
