package helihierarchy.heliparts;

import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.GL2;

import resources.Colours;
import shapes.Sphere;

/**
 * Draws the body of the helicopter 
 * 
 * @author Maya Ashizumi-Munn
 *
 */
public class Body extends TreeNode {

	//Pointer to quadric object (display list)
	private int displayList;
	
	//TODO - rotation parameters
	
	//Capsule customisation variables
	Colours capsuleColour = Colours.MAGENTA;
	
	//Initialise capsule
	public Body() {
		displayList = -1;
	}
	
	//***************************************//
	
	@Override
	void initialiseDisplayList(GL2 gl, GLU glu) {
		//Create display list
		displayList = gl.glGenLists(1);
		
		//Compile data in display list
		gl.glNewList(displayList, GL2.GL_COMPILE);
			//Set sphere colour
			double[] capColours = capsuleColour.getRGB();
			gl.glColor3d(capColours[0], capColours[1], capColours[2]);
			
			//Set sphere properties
			double radius = 3.0f;
			int slices = 20;
			int stacks = 40;
			
			//Draw sphere
			gl.glPushMatrix();
				try {
					//Scale on x axis to make oval shape
					gl.glScaled(1.5, 1, 1);
					Sphere bodySphere = new Sphere(gl, glu, radius, slices, stacks, GLU.GLU_FILL);
					bodySphere.drawSphere();
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
