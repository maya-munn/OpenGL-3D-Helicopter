package shapes;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

import resources.Colours;

/**
 * Cube
 * Draws a cube
 * Unusued in Assignment 2 
 * 
 * @author Maya Ashizumi-Munn | 17978640
 */
public class Cube {
	
	//Display list index
	int displayList;
	
	GLUT glut = new GLUT();
	
	//Cube colour
	private Colours cubeColour;
	private double[] colourArray;
	
	//Scaling variables of the cube
	private double scaleX, scaleY, scaleZ = 0;
	
	//**************************************//
	
	public Cube(Colours cubeColour, double scaleX, double scaleY, double scaleZ) {
		displayList = -1;
		
		this.cubeColour = cubeColour;
		this.colourArray = this.cubeColour.getRGB();
		
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.scaleZ = scaleZ;
	}
	
	//**************************************//
	
	public void draw(GL2 gl) {
		//If display list not initialised, do now
		if (displayList < 0) {
			initDisplayList(gl);
		}
		
		//Call display list to be drawn
		gl.glPushMatrix();
		gl.glScaled(scaleX, scaleY, scaleZ);
			gl.glCallList(displayList);
		gl.glPopMatrix();
	}
	
	//Initialise cube display list
	public void initDisplayList(GL2 gl2) {

		//Create display list
		displayList = gl2.glGenLists(1);
		
		gl2.glNewList(displayList, GL2.GL_COMPILE);
			gl2.glColor3d(colourArray[0], colourArray[1], colourArray[2]);
			glut.glutSolidCube(1);
		gl2.glEndList();
	}
}
