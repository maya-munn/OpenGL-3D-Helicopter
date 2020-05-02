package scene;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.glu.GLU;

/**
 * Basic Camera class 
 * 
 * Implementation of this class has been heavily inspired by Camera.java
 * in projections and camera movement from example code.
 * 
 * @author Jacqueline Whalley
 *
 */
public class Camera implements MouseListener, MouseMotionListener{
	
	private static final double FOV = 45;
	
	double windowWidth      = 1;
	double windowHeight     = 1;
	
	//Camera movement variables
	private double angleX;
	private double angleY;
	private Point oldMousePos;
	
	//GL drawing functionalities
	GL gl;
	GLU glu;
	 
	public Camera(GLCanvas canvas) {
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
	}

	public void draw(GL gl){
		// set up projection first
		this.gl = gl;
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu = new GLU();
        glu.gluPerspective(FOV, (float) windowWidth / (float) windowHeight, 0.1, 100);
        // set up the camera position and orientation
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluLookAt(-10, 2,  -10, 	// eye
                	  0,   0,   0, 		// looking at 
                      0.0, 1.0, 0.0); 	// up   
//        gl.glRotated(angleX, 1, 0, 0);
//        gl.glRotated(angleY, 0, 1, 0);
	}
	
	/**
     * Sets up the lookAt point - could be a specified object's location
     * @param x X coordinate of the lookAt point
     * @param y Y coordinate of the lookAt point
     * @param z Z coordinate of the lookAt point
     */
    public void setLookAt(double x, double y, double z) {
       //TO DO
    }
 
	
	 /**
     * Passes a new window size to the camera.
     * This method should be called from the <code>reshape()</code> method
     * of the main program.
     *
     * @param width the new window width in pixels
     * @param height the new window height in pixels
     */
    public void newWindowSize(int width, int height) {
		this.windowWidth = width;
		this.windowHeight = height;
    }
    
    //**********************************************//

	@Override
	public void mouseDragged(MouseEvent arg0) {
		Point p = arg0.getPoint();
		if (oldMousePos != null) {
			//If there has been a previous mouse position
			angleY -= p.getX() - oldMousePos.getX();
			angleX -= p.getY() - oldMousePos.getY();
		}
		oldMousePos = p;
	}
	
	//******* Redundant mouse methods **************//

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
