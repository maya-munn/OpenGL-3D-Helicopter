package helihierarchy.heliparts;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import resources.Colours;
import shapes.Sphere;

/**
 * Draws the tail motor at the end of the tail of the helicopter 
 * 
 * @author Maya Ashizumi-Munn | 17978640
 *
 */
public class TailMotor extends TreeNode {

	//Pointer to quadric object (display list)
	private int displayList;
	
	//Capsule customisation variables
	Colours tailColour = Colours.YELLOW;
	Colours tailMotorColour = Colours.BLACK;
	
	//Spheres radius
	double radius;
	
	//Initialise tail motor
	public TailMotor() {
		displayList = -1;
		radius = 0.75;
	}
	
	public double getRadius() {
		return this.radius;
	}
	
	@Override
	void initialiseDisplayList(GL2 gl, GLU glu) {
		//Create display list
		displayList = gl.glGenLists(1);
		
		//Compile data in display list
		gl.glNewList(displayList, GL2.GL_COMPILE);
			//Set sphere colour
			double[] tailColours = tailColour.getRGB();
			gl.glColor3d(tailColours[0], tailColours[1], tailColours[2]);
			
			//Set sphere properties
			int slices = 10;
			int stacks = 10;
			
			//Draw cylinder
			gl.glPushMatrix();
				//Make a new sphere
				try {
					Sphere tailRotorSphere = new Sphere(gl, glu, radius, slices, stacks, GLU.GLU_FILL);
					tailRotorSphere.drawSphere();
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
