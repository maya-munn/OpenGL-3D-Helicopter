package helihierarchy.heliparts;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import resources.Colours;

public class Feet extends TreeNode {

	private int displayList;
	
	Colours feetColour = Colours.BLACK;
	
	public Feet() {
		displayList = -1;
	}
	
	//**************************************//
	
	@Override
	void initialiseDisplayList(GL2 gl, GLU glu) {
		//Create quadric
		GLUquadric feetQuad = glu.gluNewQuadric();	//Feet base plank
		GLUquadric connectorQuad = glu.gluNewQuadric(); //Connector 
		
		//Create display list
		displayList = gl.glGenLists(1);
		
		//Compile data in display list
		gl.glNewList(displayList, GL2.GL_COMPILE);
			//Quadric rendered as filled cylinder object
			double[] tailColours = feetColour.getRGB();
			gl.glColor3d(tailColours[0], tailColours[1], tailColours[2]);
			glu.gluQuadricDrawStyle(feetQuad, GLU.GLU_FILL);
			glu.gluQuadricDrawStyle(connectorQuad, GLU.GLU_FILL);
			
			//Set feet cylinder properties
			double radius = 0.2;
			double height = 5; //Length of feet
			int slicesStacks = 15;

			//Draw feet cylinder
			gl.glPushMatrix();
				//Rotate 90 degrees on Y axis
				gl.glRotated(90, 0, 1, 0);
				glu.gluCylinder(feetQuad, radius, radius, height, slicesStacks, slicesStacks);
			gl.glPopMatrix();
			
			drawConnectorCylinders(height, radius, gl, connectorQuad);
			
		gl.glEndList();
	}
	
	private void drawConnectorCylinders(double feetLength, double feetRadius, GL2 gl, GLUquadric connectorQ) {
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
			glu.gluCylinder(connectorQ, radius, radius, height, slicesStacks, slicesStacks);
		gl.glPopMatrix();
		
		//Second connector cylinder
		gl.glPushMatrix();
			//Rotate -90degrees on x axis to be on top
			gl.glRotated(-90, 1, 0, 0);
			//Move this connector up on x axis by quarter of feet cylinder height
			gl.glTranslated(feetLength - ((feetLength / 4)), 0, 0);
			glu.gluCylinder(connectorQ, radius, radius, height, slicesStacks, slicesStacks);
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
	
	//******** Translation/Rotation *********//
	
	@Override
	void transformNode(GL2 gl) {
		//Do translation relative to parent
		gl.glTranslated(transX, transY, transZ);
	}
	
	public void setTranslation(double x, double y, double z) {
		transX = x;
		transY = y;
		transZ = z;
	}
}
