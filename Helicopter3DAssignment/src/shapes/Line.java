package shapes;

import javax.media.opengl.GL;

/**
 * Draws a 3D line
 * 
 * @author Maya Ashizumi-Munn | ID: 17978640
 */
public class Line {
	
	double[] colour;
	double[] startPoints;
	double[] endPoints;
	
	public Line(double[] colour, double[] startPoints, double[] endPoints) {
		this.colour = colour;
		this.startPoints = startPoints;
		this.endPoints = endPoints;
	}
	
	public void drawLine(GL gl) {
		gl.glColor3d(colour[0], colour[1], colour[2]);
		gl.glBegin(GL.GL_LINE);
			gl.glVertex3d(startPoints[0], startPoints[1], startPoints[2]); //Start point
			gl.glVertex3d(endPoints[0], endPoints[1], endPoints[2]);
		gl.glEnd();
	}
}
