package shapes;

import com.jogamp.opengl.GL2;

import resources.Colours;

/**
 * Cube
 * Draws a cube
 * Some code has come from Cube class by JIER
 * 
 * @author Papaya
 */
public class Cube {
	
	int displayList;
	
	private Colours cubeColour = Colours.WHITE;
	private double[] colourArray;
	
	private double scaleX, scaleY, scaleZ = 0;
	
	public Cube(Colours cubeColour, double scaleX, double scaleY, double scaleZ) {
		displayList = -1;
		
		this.cubeColour = cubeColour;
		this.colourArray = this.cubeColour.getRGB();
		
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.scaleZ = scaleZ;
	}
	
	//**************************************//
	
	final double[][] vertices = {
			{-1, -1, -1}, 
			{+1, -1, -1}, 
			{+1, +1, -1}, 
			{-1, +1, -1}, 
			{-1, -1, +1},
		    {+1, -1, +1}, 
		    {+1, +1, +1}, 
		    {-1, +1, +1}};

	final int[][] faces = {
			{0, 1, 2, 3}, 
			{4, 5, 6, 7}, 
			{0, 1, 5, 4}, 
			{3, 2, 6, 7}, 
			{1, 5, 6, 2},
		    {0, 4, 7, 3}};
	
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
	
	public void initDisplayList(GL2 gl2) {

		//Create display list
		displayList = gl2.glGenLists(1);
		
		gl2.glNewList(displayList, GL2.GL_COMPILE);
		
			gl2.glBegin(GL2.GL_QUADS);
				for (int[] face : faces) {
					for (int vertex : face) {
						gl2.glColor3d(colourArray[0], colourArray[1], colourArray[2]);
						gl2.glVertex3d(vertices[vertex][0], vertices[vertex][1], vertices[vertex][2]);
					}
				}
			gl2.glEnd();
			
		gl2.glEndList();
	}
}
