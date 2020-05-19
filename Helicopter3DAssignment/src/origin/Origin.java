package origin;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import resources.Colours;
import shapes.Line;
import shapes.Sphere;
import shapes.StaticShape;

/**
 * Draws a ball at the origin and three lines representing the unit vector X Y Z directions
 * 
 * @author Maya Ashizumi-Munn | ID: 17978640
 */
public class Origin extends StaticShape {
	
	GL2 gl;
	GLU glu;
	
	//Indices of display-lists
	int ballDisplayList;
	int vectorDisplayList;
	
	/**
	 * Constructor for Origin class
	 * @param gl2 graphics library reference for drawing
	 * @param glu2 graphics library reference for drawing (3D)
	 */
	public Origin(GL2 gl2, GLU glu2) {
		this.gl = gl2;
		this.glu = glu2;
		
		//Creating display-list objects
		ballDisplayList = gl2.glGenLists(2);
		vectorDisplayList = ballDisplayList + 1;
	}
	
	//*********************************//
	
	@Override
	public void draw() {
		//Call display lists to be drawn in display()
		gl.glCallList(ballDisplayList);
		gl.glCallList(vectorDisplayList);
	}
	
	@Override
	public void precompileDisplayList() {
		//Pre-compile commands in the two lists in init()
		this.compileBall();
		this.compileVectors();
	}
	
	private void compileBall() {
		//Pre-compile ball draw commands into display list
		gl.glNewList(ballDisplayList, GL2.GL_COMPILE);
			
			//Set sphere details
			double ballRadius = 0.12;
			int ballSlices = 30;
			int ballStacks = 30;
			int drawStyle = GLU.GLU_FILL;
			//Set colour for the ball
			double[] ballColour = Colours.WHITE.getRGB();
			gl.glColor3d(ballColour[0], ballColour[1], ballColour[2]);
			
			//Create sphere
			Sphere ball = null;
			try {
				ball = new Sphere(gl, glu, ballRadius, ballSlices, ballStacks, drawStyle);
				ball.drawSphere();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		gl.glEndList();
	}
	
	private void compileVectors() {
		//Pre-compile XYZ vector drawing into display list
		gl.glNewList(vectorDisplayList, GL2.GL_COMPILE);
		double lineLength = 2;
			
		//Draw lines
			Line line = new Line(lineLength);
			line.drawCoordinateLines(gl);
			
		gl.glEndList();
	}
}
