package helihierarchy.heliparts;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import resources.Colours;
import shapes.Cylinder;

public class Tail extends TreeNode {

	//Pointer to quadric object (display list)
	private int displayList;
	
	//Capsule customisation variables
	Colours tailColour = Colours.YELLOW;
	
	//Length of tail
	double length;
	
	//Initialise tail
	public Tail() {
		displayList = -1;
		length = 7;
	}
	
	public double getLength() {
		return this.length;
	}
	
	@Override
	void initialiseDisplayList(GL2 gl, GLU glu) {
		//Create display list
		displayList = gl.glGenLists(1);
		
		//Compile data in display list
		gl.glNewList(displayList, GL2.GL_COMPILE);
			//Set cylinder colour
			double[] tailColours = tailColour.getRGB();
			gl.glColor3d(tailColours[0], tailColours[1], tailColours[2]);
			
			//Set cylinder properties
			double baseRadius = 1;
			double topRadius = 0.5;
			int slices = 20;
			int stacks = 20;
			
			//Draw cylinder
			gl.glPushMatrix();
				//Rotate 90 degrees on y axis
				gl.glRotated(90, 0, 90, 0);
				Cylinder tailCylinder;
				try {
					tailCylinder = new Cylinder(gl, glu, baseRadius, topRadius, length, slices, stacks, GLU.GLU_FILL);
					tailCylinder.drawCylinder();
				} catch (Exception e) {
					e.printStackTrace();
				}
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
