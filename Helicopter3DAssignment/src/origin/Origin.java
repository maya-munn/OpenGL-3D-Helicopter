package origin;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import resources.Colours;
import shapes.Line;
import shapes.Sphere;

/**
 * Draws a ball at the origin and three lines representing the unit vector X Y Z directions
 * 
 * @author Maya Ashizumi-Munn | ID: 17978640
 */
public class Origin {
	
	GL gl;
	GLU glu = new GLU();
	
	//Indices of display-lists
	int ballDisplayList;
	int vectorDisplayList;
	
	/**
	 * Constructor for Origin class
	 * @param gl graphics library reference for drawing
	 * @param glu graphics library reference for drawing (3D)
	 */
	public Origin(GL gl, GLU glu) {
		this.gl = gl;
		this.glu = glu;
		
		//Creating display-list objects
		ballDisplayList = gl.glGenLists(2);
		vectorDisplayList = ballDisplayList + 1;
	}
	
	//*********************************//
	
	public void draw() {
		gl.glCallList(ballDisplayList);
		gl.glCallList(vectorDisplayList);
	}
	
	public void precompileDisplayList() {
		//Pre-compile commands in the two lists
		this.compileBall();
		this.compileVectors();
	}
	
	private void compileBall() {
		//Pre-compile ball draw commands into display list
		gl.glNewList(ballDisplayList, GL.GL_COMPILE);
			
			//Set sphere details
			double ballRadius = 0.3;
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
		gl.glNewList(vectorDisplayList, GL.GL_COMPILE);
		double lineLength = 0.05;
		double[] startPoints = {0, 0, 0};
			
		//Draw lines
			//Draw X
			double[] xColour = Colours.RED.getRGB();
			double[] xEndPoints = {lineLength, 0, 0};
			Line xLine = new Line(xColour, startPoints, xEndPoints);
			xLine.drawLine(gl);
			
			//Draw Y
			double[] yColour = Colours.GREEN.getRGB();
			double[] yEndPoints = {0, lineLength, 0};
			Line yLine = new Line(yColour, startPoints, yEndPoints);
			yLine.drawLine(gl);
			
			//Draw Z
			double[] zColour = Colours.BLUE.getRGB();
			double[] zEndPoints = {0, 0, lineLength};
			Line zLine = new Line(zColour, startPoints, zEndPoints);
			zLine.drawLine(gl);
	}
}
