package helihierarchy;

import helihierarchy.heliparts.Body;
import helihierarchy.heliparts.CoordinateSystem;
import helihierarchy.heliparts.Feet;
import helihierarchy.heliparts.Tail;
import helihierarchy.heliparts.TreeNode;

public class CreateHelicopter {
	//To be called from init 
	
	/*
	 * Example
	 * // upper arm of the Robot
        upperArm = new RobotPart(UPPER_ARM_RADIUS, UPPER_ARM_HEIGHT, RobotPart.RotationAxis.Z);
        upperArm.setTranslation(0, LOWER_ARM_HEIGHT, 0);
        upperArm.addChild(new CoordinateSystem(1, 1));

        // Lower arm of robot
        lowerArm = new RobotPart(LOWER_ARM_RADIUS, LOWER_ARM_HEIGHT, RobotPart.RotationAxis.Z);
        lowerArm.addChild(upperArm);
        lowerArm.setTranslation(0, BASE_HEIGHT, 0);
        lowerArm.addChild(new CoordinateSystem(1, 1));

        // Base of the Robot
        robotBase = new RobotPart(BASE_RADIUS, BASE_HEIGHT, RobotPart.RotationAxis.Y);
        robotBase.setTranslation(-1, 0, 0);
        robotBase.addChild(lowerArm);
        robotBase.addChild(new CoordinateSystem(2, 1));
	 */
	
	
	
	public TreeNode drawHeliParts() {
		
		double middleYPos = 5;
		
		Feet leftFoot = new Feet();
		Feet rightFoot = new Feet();
		Tail heliTail = new Tail();
		Body heliBody = new Body();
		
		//ROTOR
		//Rotor blade x2
		//Rotor motor stick
		
		//FEET
		//Move left
		leftFoot.setTranslation(0, 0, -2.4);
		leftFoot.addChild(new CoordinateSystem(1, 1));
		
		//Move right and up Y axis and backwards on X axis
		rightFoot.setTranslation(-2, -3.4, 1.2);
		rightFoot.addChild(leftFoot);
		rightFoot.addChild(new CoordinateSystem(1, 1));
		//connectors x4
		//Feet blade x2
		
		//TAIL
		//Translate on x axis to move to end of capsule
		heliTail.setTranslation(4, 0, 0);
		heliTail.addChild(new CoordinateSystem(1, 1));
		
		//BODY
		//add child, feet and rotor objects
		heliBody.addChild(heliTail);
		heliBody.addChild(rightFoot);
		//Move up off the ground
		heliBody.setTranslation(0, middleYPos, 0);
		heliBody.addChild(new CoordinateSystem(1, 1));
		
		return heliBody;
	}
}
