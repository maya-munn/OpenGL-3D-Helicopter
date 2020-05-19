package movement;

import com.jogamp.opengl.GL2;

import helihierarchy.heliparts.Body;
import helihierarchy.heliparts.Motor;

/**
 * Controls movement of the helicopter based on input
 * from InputHandler
 * 
 * @author Maya Ashizumi-Munn | 17978640
 *
 */
public class MovementController {

	//Movement variables
	double speed = 0;
	double angle = 0; //Angle of rotors
	final int MAX_SPEED;
	double groundYPos; //Y position where body is on the ground
	
	//Objects that will be moved 
	Body heliBody;
	Motor mainMotor;
	Motor leftTailMotor;
	Motor rightTailMotor;
	
	GL2 gl;
	
	public MovementController() {
		//Instantiating the maximum speed
		MAX_SPEED = 2;
	}
	
	//Getting reference of helicopter body from main class
	public void setHeliBody(Body heliBody, GL2 gl) {
		this.heliBody = heliBody;
		groundYPos = 3.6; //This is the Y pos of heli where the feet touch the ground perfectly
		this.gl = gl;
	}
	
	public void setMotors(Motor main, Motor leftTail, Motor rightTail) {
		this.mainMotor 		= main;
		this.leftTailMotor 	= leftTail;
		this.rightTailMotor = rightTail;
	}
	
	
	//*************************************//
	
	/**
	 * Called from main class draw() method to continuously update 
	 * movement
	 */
	public void updateMovement() {
		//If speed has been set by user and not less or equal to 0, start rotating motors
		if (speed > 0) {
			rotateMotors();
		}
		
	}
	
	//*************************************//
	
	
	
	private void rotateMotors() {
		//Rotate all motors based on speed
		double currentTime = System.currentTimeMillis();
		angle = (currentTime * speed) % 360;
		
		//Main motor vertical - rotate around Y axis, other motors 
		mainMotor.setRotation(angle, 0, 1, 0);
		leftTailMotor.setRotation(angle, 0, 0, 1);
		rightTailMotor.setRotation(angle, 0, 0, 1);
	}
	
	public void increaseAltitude() {
		//Dont increase speed over max speed value (so speed max will be 299)
		if (speed < MAX_SPEED) {
			speed += 0.02;
		}
		else {
			//Now speed has reached max speed, start lifting by 0.1 on y axis
			heliBody.setTranslation(heliBody.getX(), heliBody.getY() + 0.1, heliBody.getZ());
		}
	}
	public void decreaseAltitude() {
		//If helicopter is still above ground point and speed is over 0
		if (heliBody.getY() > groundYPos && speed > 0) { 
			//Move helicopter down by 0.1 each call made
			heliBody.setTranslation(heliBody.getX(), heliBody.getY() - 0.1, heliBody.getZ());
		}
		//If helicopter is at ground level, decrease speed
		else if (speed > 0) {
			speed -= 0.05; //Normal decrease rate
		}
	}
	
	//*************************************//
	
	private double heliPosX;
	private double heliPosZ;
	
	public void moveForwards(double angleY) {
		//Only move forward if in the air
		if (heliBody.getY() > this.groundYPos) {
			//Set positions based on current position
			heliPosX = heliBody.getX();
			heliPosZ = heliBody.getZ();
			//Increment positions based on helicopter rotation
			heliPosX -= Math.cos(Math.toRadians(angleY)) * 1;
			heliPosZ += Math.sin(Math.toRadians(angleY)) * 1;
			
			heliBody.setTranslation(heliPosX, heliBody.getY(), heliPosZ);
		}
	}
	public void moveBackwards(double angleY) {
		//Only move forward if in the air
		if (heliBody.getY() > this.groundYPos) {
			//Set positions based on current position
			heliPosX = heliBody.getX();
			heliPosZ = heliBody.getZ();
			//Increment positions based on helicopter rotation
			heliPosX += Math.cos(Math.toRadians(angleY)) * 1;
			heliPosZ -= Math.sin(Math.toRadians(angleY)) * 1;
			
			heliBody.setTranslation(heliPosX, heliBody.getY(), heliPosZ);
		}
	}
	
	public void strafeLeft(double angleY) {
		//Only move left if in the air
		if (heliBody.getY() > this.groundYPos) {
			//Set positions based on current position
			heliPosX = heliBody.getX();
			heliPosZ = heliBody.getZ();
			//Increment positions based on helicopter rotation
			heliPosX += Math.cos(Math.toRadians(angleY - 90)) * 1;
			heliPosZ -= Math.sin(Math.toRadians(angleY - 90)) * 1;
			
			heliBody.setTranslation(heliPosX, heliBody.getY(), heliPosZ);
		}
	}
	public void strafeRight(double angleY) {
		//Only move right if in the air
		if (heliBody.getY() > this.groundYPos) {
			//Set positions based on current position
			heliPosX = heliBody.getX();
			heliPosZ = heliBody.getZ();
			//Increment positions based on helicopter rotation
			heliPosX += Math.cos(Math.toRadians(angleY + 90)) * 1;
			heliPosZ -= Math.sin(Math.toRadians(angleY + 90)) * 1;
			
			heliBody.setTranslation(heliPosX, heliBody.getY(), heliPosZ);
		}
	}
	
	//*************************************//
	
	private double turnAngle = 0;
	
	public double turnLeft() {
		//Rotate body on Y axis by 1 degree for every key press
		//But only if off the ground
		if (heliBody.getY() > groundYPos) {
			turnAngle += -1;
			if (turnAngle > 360) {
				turnAngle = 0;
			}
			gl.glPushMatrix();
				heliBody.setRotation(turnAngle, 0, 1, 0);
				heliBody.rotateNode(gl);
			gl.glPopMatrix();
		}
		
		return turnAngle;
	}
	public double turnRight() {
		//Rotate body on Y axis by 1 degree for every key press
		//But only if off the ground
		if (heliBody.getY() > groundYPos) {
			turnAngle += 1;
			if (turnAngle > 360) {
				turnAngle = 0;
			}
			heliBody.setRotation(turnAngle, 0, 1, 0);
			heliBody.rotateNode(gl);
		}
		
		return turnAngle;
	}
}
