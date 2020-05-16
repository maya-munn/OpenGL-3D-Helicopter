package movement;

import helihierarchy.heliparts.Body;
import helihierarchy.heliparts.Motor;

/**
 * Controls movement of the helicopter
 * 
 * @author Maya Ashizumi-Munn
 *
 */
public class MovementController {

	double speed = 0;
	double angle = 0; //Angle of rotors
	final int MAX_SPEED;
	double groundYPos; //Y position where body is on the ground
	
	Body heliBody;
	Motor mainMotor;
	Motor leftTailMotor;
	Motor rightTailMotor;
	
	public MovementController() {
		//Instantiating the maximum speed
		MAX_SPEED = 2;
	}
	
	//Getting reference of helicopter body from main class
	public void setHeliBody(Body heliBody) {
		this.heliBody = heliBody;
		groundYPos = 3.6; //This is the Y pos of heli where the feet touch the ground perfectly
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
	public void move() {
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
	
	public void moveForwards() {
		
	}
	public void moveBackwards() {
		
	}
	
	public void moveSidewardsLeft() {
		
	}
	public void moveSidewardsRight() {
		
	}
}
