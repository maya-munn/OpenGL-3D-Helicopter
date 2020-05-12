package shapes;

import com.jogamp.opengl.GL2;

import resources.Colours;

/**
 * Draws a 3D line
 * 
 * @author Maya Ashizumi-Munn | ID: 17978640
 */
public class Line {
	
	double[] colour;
	double[] startPoints;
	double[] endPoints;
	double lineLength;
	
	//Used for a single line
	public Line(double[] colour, double[] startPoints, double[] endPoints) {
		this.colour = colour;
		this.startPoints = startPoints;
		this.endPoints = endPoints;
	}
	
	//Used for draw coordinate lines method
	public Line(double lineLength) {
		this.lineLength = lineLength;
	}
	
	//*********************************//
	
	public void drawLine(GL2 gl) {
		gl.glColor3d(colour[0], colour[1], colour[2]);
		gl.glBegin(GL2.GL_LINE);
			gl.glVertex3d(startPoints[0], startPoints[1], startPoints[2]); //Start point
			gl.glVertex3d(endPoints[0], endPoints[1], endPoints[2]);
		gl.glEnd();
	}
	
	public void drawCoordinateLines(GL2 gl) {
		//Set line width and line length
		gl.glLineWidth(2.0f);
		gl.glBegin(GL2.GL_LINES);
		int lineLength = 2;
			//Get colours
			double[] xColour = Colours.RED.getRGB();
			double[] yColour = Colours.GREEN.getRGB();
			double[] zColour = Colours.BLUE.getRGB();
			
			//Set red colour and x vertices
			gl.glColor3d(xColour[0], xColour[1], xColour[2]);
			gl.glVertex3d(0, 0, 0);
			gl.glVertex3d(lineLength, 0, 0);
			//Set green colour and y vertices
			gl.glColor3d(yColour[0], yColour[1], yColour[2]);
			gl.glVertex3d(0, 0, 0);
			gl.glVertex3d(0, lineLength, 0);
			//Set blue colour and z vertices
			gl.glColor3d(zColour[0], zColour[1], zColour[2]);
			gl.glVertex3d(0, 0, 0);
			gl.glVertex3d(0, 0, lineLength);
		gl.glEnd();
	}
}
