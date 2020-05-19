package sceneview;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;

/**
 * Basic Camera class 
 * 
 * Implementation of this class has been heavily inspired by Camera.java and TrackBallCamera.java
 * in projections and camera movement from example code.
 * 
 * @author Jacqueline Whalley
 * Modified by Maya Ashizumi-Munn | 17978640
 */
public class Camera implements MouseListener, MouseMotionListener {
	
	//Window size
	double windowWidth      = 1;
	double windowHeight     = 1;
	
	//Camera rotation variables
	private double angleX = 90;
	private double angleY = 10;
	
	//Mouse variables
	private Point oldMousePos;
	private int whichMouseButton; 		//Stores if left, middle or right mouse button
	
	//Camera positioning variables
	private final double MIN_DIST = 15; 	//Minimum distance of camera from center pos
	private final double MAX_DIST = 40;
	private double distanceToOrigin = MIN_DIST;
	private final double FOV = 45; 			//Field of view
	
	//Helicopter position
	private double[] lookAtPos = new double[3];
	
	//GL drawing functionalities
	GL2 gl;
	GLU glu;
	
	//**********************************************//
	 
	public Camera(GLCanvas canvas) {
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
	}

	public void draw(GL2 gl) {
		// set up projection first
		this.gl = gl;
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu = new GLU();
        glu.gluPerspective(FOV, (float) windowWidth / (float) windowHeight, 0.1, 100);
        // set up the camera position and orientation
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        
        //Let rotation revolve around the look at center point
    	double distance = distanceToOrigin * Math.acos(Math.toRadians(angleY));
    	double eyeZ = distance * Math.cos(Math.toRadians(angleX));
    	double eyeX = distance * Math.sin(Math.toRadians(angleX));
    	double eyeY = distanceToOrigin * Math.sin(Math.toRadians(angleY));
        
        //Set look at based on rotation, zoom
        glu.gluLookAt(eyeX + lookAtPos[0], eyeY + lookAtPos[1] + 5, eyeZ + lookAtPos[2], 	//Eye
			  		  lookAtPos[0],        lookAtPos[1],            lookAtPos[2], 			//Look at
				      0,                   1,                       0);						//Up
	}
	
	//**********************************************//
	
	//Returns the current camera Y angle
	public double getAngleY() {
		return this.angleY;
	}
	
	/**
     * Sets up the lookAt point - could be a specified object's location
     * This is called in display from main class
     */
    public void setLookAt(double x, double y, double z) {
        //Set look at based on helicopter position
    	this.lookAtPos = new double[] {x, y, z};
    }
 
    public double getDistance() {
    	return this.distanceToOrigin;
    }
    
    public void setDistance(double distance) {
    	distanceToOrigin = distance;
    	this.limitDistance();
    	
    }
    
    public void limitDistance() {
    	if (distanceToOrigin < MIN_DIST) {
    		//Set distance to minimum distance
    		distanceToOrigin = MIN_DIST;
    	}
    	else if (distanceToOrigin > MAX_DIST) {
    		//Set distasnce to maximum distance
    		distanceToOrigin = MAX_DIST;
    	}
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
    
    //Resets camera rotation
    public void resetCameraRotation(boolean resetDistance) {
    	angleX = 90; angleY = 10;
    	if (resetDistance) {
    		distanceToOrigin = MIN_DIST; //Reset to default
    	}
    	oldMousePos = null;
    }
    
    //Called when left or right arrow key pressed
    public void changeHorizontalCameraRotation(double angle) {
    	//Reset camera
    	boolean resetDistance = false;
    	this.resetCameraRotation(resetDistance);
    	//Add changed angle to angleX
    	angleX += angle;
    }
    
    //**********************************************//

    //Change the camera view when the mouse is dragged
	@Override
	public void mouseDragged(MouseEvent arg0) {
		Point mousePoint = arg0.getPoint();
		
		if (oldMousePos != null) {
			//Do different actions depending on which mouse was pressed
			switch (whichMouseButton) {
				case MouseEvent.BUTTON1:
					//ROTATE CAM
					angleX -= mousePoint.getX() - oldMousePos.getX();
					angleY += mousePoint.getY() - oldMousePos.getY();
				break;
				case MouseEvent.BUTTON3:
					//CHANGE CAM DISTANCE
					distanceToOrigin += 0.1 * (mousePoint.y - oldMousePos.y);
					this.limitDistance();
				break;
			}
		}
		
		oldMousePos = mousePoint;
	}
	
	//Stores which mouse button has been pressed to determine mouseDragged function
	@Override
	public void mousePressed(MouseEvent arg0) {
		oldMousePos = arg0.getPoint(); //Stores where the mouse has been pressed
		whichMouseButton = arg0.getButton();
	}

	//Reset mouse variables
	@Override
	public void mouseReleased(MouseEvent arg0) {
		oldMousePos = null;
		whichMouseButton = 0;
	}
	
	//******* Redundant mouse methods **************//

	@Override
	public void mouseMoved(MouseEvent arg0) { }
	@Override
	public void mouseClicked(MouseEvent arg0) { } 
	@Override
	public void mouseEntered(MouseEvent arg0) { }
	@Override
	public void mouseExited(MouseEvent arg0) { }
}
