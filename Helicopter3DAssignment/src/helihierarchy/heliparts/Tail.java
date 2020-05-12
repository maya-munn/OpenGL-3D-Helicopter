package helihierarchy.heliparts;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import resources.Colours;

public class Tail extends TreeNode {

	//Pointer to quadric object (display list)
	private int displayList;
	
	//TODO - rotation parameters
	
	//Capsule customisation variables
	Colours tailColour = Colours.YELLOW;
	
	//Initialise tail
	public Tail() {
		displayList = -1;
	}
	
	@Override
	void initialiseDisplayList(GL2 gl, GLU glu) {
		//Create quadric
		GLUquadric tailQuad = glu.gluNewQuadric();
		
		//Create display list
		displayList = gl.glGenLists(1);
		
		//Compile data in display list
		gl.glNewList(displayList, GL2.GL_COMPILE);
			//Quadric rendered as filled cylinder object
			double[] tailColours = tailColour.getRGB();
			gl.glColor3d(tailColours[0], tailColours[1], tailColours[2]);
			glu.gluQuadricDrawStyle(tailQuad, GLU.GLU_FILL);
			
			//Set cylinder properties
			double baseRadius = 1;
			double topRadius = 0.5;
			double height = 7; //Length of tail
			int slices = 20;
			int stacks = 20;
			
			//Draw cylinder
			gl.glPushMatrix();
				//Rotate 90 degrees on y axis
				gl.glRotated(90, 0, 90, 0);
				glu.gluCylinder(tailQuad, baseRadius, topRadius, height, slices, stacks);
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
