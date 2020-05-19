package helihierarchy.heliparts;

import com.jogamp.opengl.glu.GLU;

import resources.Colours;
import shapes.Cylinder;

import com.jogamp.opengl.GL2;

/**
 * Draws the rotors of the helicopter
 * 
 * @author Maya Ashizumi-Munn | 17978640
 *
 */
public class Rotor extends TreeNode {

	//Display list index
	private int displayList;
	
	//Colour of rotors
	Colours rotorColour = Colours.BLACK;
	
	//Length of rotor blades
	double length;
	
	//Default rotor
	public Rotor() {
		displayList = -1;
		length = 10;
	}
	
	//Custom rotor
	public Rotor(double length) {
		displayList = -1;
		this.length = length;
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
			//Set rotor colour
			double[] tailColours = rotorColour.getRGB();
			gl.glColor3d(tailColours[0], tailColours[1], tailColours[2]);
			//Quadric rendered as filled cylinder object
			
			//Set rotor cylinder properties
			double radius = 0.2;
			int slicesStacks = 15;

			//Draw rotor cylinders
			gl.glPushMatrix();
				gl.glRotated(90, 0, 1, 0); //Rotate 90 degrees on Y axis
				gl.glScaled(length / 4, (this.length / 10) / 2, 1); //Flatten
				//Draw vertical cylinder
				try {
					Cylinder verticalRotor = new Cylinder(gl, glu, radius, radius, length, slicesStacks, slicesStacks, GLU.GLU_FILL);
					verticalRotor.drawCylinder();
				} catch (Exception e) { e.printStackTrace(); }
			gl.glPopMatrix();
			
			gl.glPushMatrix();
				gl.glTranslated(length / 2, 0, (length / 2) * -1); //Move it to intersect with above rotor in the middle
				gl.glScaled(length / 4, (this.length / 10) / 2, 1); //Flatten
				//Draw horizontal cylinder
				try {
					Cylinder horizontalRotor = new Cylinder(gl, glu, radius, radius, length, slicesStacks, slicesStacks, GLU.GLU_FILL);
					horizontalRotor.drawCylinder();
				} catch (Exception e) { e.printStackTrace(); }
			gl.glPopMatrix();
			
		gl.glEndList();
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
