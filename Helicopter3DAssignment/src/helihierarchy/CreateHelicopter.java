package helihierarchy;

import com.jogamp.opengl.GL2;

import helihierarchy.heliparts.Feet;
import helihierarchy.heliparts.Motor;
import helihierarchy.heliparts.Rotor;
import helihierarchy.heliparts.Tail;
import helihierarchy.heliparts.TailMotor;
import helihierarchy.heliparts.TreeNode;

/**
 * Called from main classes init method, this instantiates the helicopter
 * 
 * @author Maya Ashizumi-Munn | 17978640
 */
public class CreateHelicopter {
	
	GL2 gl; //Reference to gl drawing library
	
	//Default helicopter positions
	double heliXPos;
	double heliYPos;
	double heliZPos;
	double heliOnGroundYPos = 3.6;
	
	//Helicopter parts
	Motor motor;
	Rotor rotors;
	
	Feet leftFoot;
	Feet rightFoot;
	
	Tail tail;
	TailMotor tailMotor;
	Motor leftTailMotor;
	Motor rightTailMotor;
	Rotor leftTailRotor;
	Rotor rightTailRotor;

	public CreateHelicopter(GL2 gl) {
		//Set default helicopter position
		heliXPos = 0;
		heliYPos = heliOnGroundYPos; //Y position where the helicopter feet are on the ground
		heliZPos = 0;
		
		this.gl = gl;
	}
	
	//To sent references of motors
	public Motor[] getMotors() {
		Motor[] motors = new Motor[] {motor, leftTailMotor, rightTailMotor };
		return motors;
	}
	
	//*************************************//
	
	//This 
	public TreeNode createHeliParts(TreeNode heliBody) {
		gl.glPushMatrix();
			gl.glPushMatrix();
			
			//Draw top motor and rotor
				motor = new Motor(true); //True to do scale 
				rotors = new Rotor();
				rotors.setTranslation(-rotors.getLength() / 4, 0, 0);
				motor.setTranslation(0, 1.6, 0);
				
				motor.addChild(rotors);
				heliBody.addChild(motor);
				
			gl.glPopMatrix();
			
			//Draw helicopter feet
			gl.glPushMatrix();
				gl.glPushMatrix();
					leftFoot = new Feet(Feet.Side.LEFT); 	
					leftFoot.setTranslation(-1.5, -1.7, -0.7);
					heliBody.addChild(leftFoot);
				gl.glPopMatrix();
				gl.glPushMatrix();
					rightFoot = new Feet(Feet.Side.RIGHT);
					rightFoot.setTranslation(-1.5, -1.7, 0.7);
					heliBody.addChild(rightFoot);
				gl.glPopMatrix();
			gl.glPopMatrix();
			
			//Draw helicopter tail
			gl.glPushMatrix();
				tail = new Tail();
				tail.setTranslation(2, 0, 0);
				
				tailMotor = new TailMotor();
				//Move tailMotor up the length of tail
				tailMotor.setTranslation(tail.getLength() / 2, 0, 0);
				tail.addChild(tailMotor);
				
				//Add motor and rotor to each side of tailMotor
				gl.glPushMatrix();
					leftTailMotor = new Motor(false); //False to not scale
					//Move left
					tailMotor.addChild(leftTailMotor);
					leftTailMotor.setTranslation(0, 0, tailMotor.getRadius() / 2);
				
					gl.glPushMatrix();
						//Add rotors
						leftTailRotor = new Rotor(1); //Length of blades
						leftTailRotor.setRotation(45, 1, 0, 0);
						leftTailRotor.setTranslation(-leftTailRotor.getLength() / 4, 0, 0);
						leftTailMotor.addChild(leftTailRotor);
					gl.glPopMatrix();
				gl.glPopMatrix();
				
				gl.glPushMatrix();
					rightTailMotor = new Motor(false); //False to not scale
					//Move left
					tailMotor.addChild(rightTailMotor);
					rightTailMotor.setTranslation(0, 0, -tailMotor.getRadius() / 2);
					
					gl.glPushMatrix();
						//Add rotors
						rightTailRotor = new Rotor(1); //Length of blades
						rightTailRotor.setRotation(45, 1, 0, 0);
						rightTailRotor.setTranslation(-rightTailRotor.getLength() / 4, 0, 0);
						rightTailMotor.addChild(rightTailRotor);
					gl.glPopMatrix();
				gl.glPopMatrix();
				
				heliBody.addChild(tail);
			gl.glPopMatrix();
			
		gl.glPopMatrix();
		
		//Move helicopter to its default position
		heliBody.setTranslation(heliXPos, heliYPos, heliZPos);
		return heliBody;
	}
}
