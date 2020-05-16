package helihierarchy.heliparts;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import resources.Colours;
import shapes.Sphere;

/**
 * Motor that sits on top of helicopter
 * Has rotors attached to it to spin and propel the helicopter
 * 
 * @author Papaya
 *
 */
public class Motor extends TreeNode {

	//Display list index
	private int displayList;
	
	//Colour of rotors
	Colours motorColour = Colours.BLACK;
	
	//Decides whether to scale or not
	boolean isScaled;
	
	//Default motor
	public Motor(boolean isScaled) {
		displayList = -1;
		this.isScaled = isScaled;
	}
	
	//**************************************//
	
	@Override
	void initialiseDisplayList(GL2 gl, GLU glu) {
		
		//Create display list
		displayList = gl.glGenLists(1);
		
		//Compile data in display list
		gl.glNewList(displayList, GL2.GL_COMPILE);
			//Set motor colour
			double[] tailColours = motorColour.getRGB();
			gl.glColor3d(tailColours[0], tailColours[1], tailColours[2]);
			//Quadric rendered as filled cylinder object
			
			//Set motor cylinder properties
			double radius = 0.2;
			int slicesStacks = 15;

			//Draw motor
			gl.glPushMatrix();
				//Draw sphere
				try {
					Sphere motorSphere = new Sphere(gl, glu, radius, slicesStacks, slicesStacks, GLU.GLU_FILL);
					motorSphere.drawSphere();
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
			//Scale sphere to be vertically oblongated IF isScaled == true
			if (isScaled) { gl.glScaled(1, 3, 1); }
			gl.glCallList(displayList);
		gl.glPopMatrix();
	}
}
