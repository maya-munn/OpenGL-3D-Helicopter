package movement;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import scene.Ground;
import sceneview.Camera;

/**
 * Input Handler - Calls methods from other classes based on user input
 * 
 * @author Maya Ashizumi-Munn | 17978640
 *
 */
public class InputHandler implements KeyListener {

	//Class references passed in from main class
	Ground groundReference;
	MovementController moveController;
	Camera cameraRef;
	
	public void setGroundRef(Ground groundRef) {
		this.groundReference = groundRef; 
	}
	public void setCameraRef(Camera cameraRef) {
		this.cameraRef = cameraRef;
	}
	
	//*****************************************//
	
	public InputHandler(MovementController mc) {
		this.moveController = mc; //Reference for movement controller class
		
		//Print out controls when instantiated
		this.printControls();
	}
	
	public void printControls() {
		//Prints key mappings to user
		System.out.println("KEY MAPPINGS:");
		System.out.println("*****************");
		System.out.println(" UP/DOWN ARROWS: Increase/decrease altitude");
		System.out.println(" LEFT/RIGHT ARROWS: Turn left/right");
		System.out.println(" W/S: Move forward/backward");
		System.out.println(" A/D: Strafe left/right");
		System.out.println("*****************");
		System.out.println(" L: Toggle wireframe view");
		System.out.println(" Left mouse drag: Rotate camera");
		System.out.println(" Right mouse drag: Zoom camera");
		System.out.println(" R: Reset camera view");
	}
	
	//*****************************************//
	
	//Y angle of helicopter
	double angle;
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		int key = arg0.getKeyCode(); //What key has been pressed
		
		switch (key) {
		//L KEY - Toggle between filled or wireframe mode
			case KeyEvent.VK_L:
				groundReference.toggleDrawMode();
			break;
		//UP KEY - Increase altitude
			case KeyEvent.VK_UP:
				//Increase speed with up button
				moveController.increaseAltitude();
				break;
		//DOWN KEY - Decrease altitude
			case KeyEvent.VK_DOWN:
				//Decrease speed with down button
				moveController.decreaseAltitude();
			break;
		//LEFT KEY - Turn Left
			case KeyEvent.VK_LEFT:
				angle = moveController.turnLeft();
				cameraRef.changeHorizontalCameraRotation(angle); //Rotate camera with helicopter rotation
			break;
		//RIGHT KEY - Turn right
			case KeyEvent.VK_RIGHT:
				angle = moveController.turnRight();
				cameraRef.changeHorizontalCameraRotation(angle);
			break;
		//W KEY - Move forward
			case KeyEvent.VK_W:
				moveController.moveForwards(angle);
			break;
		//S KEY - Move backward
			case KeyEvent.VK_S:
				moveController.moveBackwards(angle);
			break;
		//A KEY - Strafe left
			case KeyEvent.VK_A:
				moveController.strafeLeft(angle);
				break;
		//D KEY - Strafe right
			case KeyEvent.VK_D:
				moveController.strafeRight(angle);
			break;
		//R KEY - Reset camera view
			case KeyEvent.VK_R:
				boolean resetDistance = true;
				cameraRef.resetCameraRotation(resetDistance);
			break;
		//Any other key... Invalid message
			default:
				System.out.println();
				System.out.println("Invalid input!");
				this.printControls(); //Show user controls again
			break;
		}
			
	}
	
	//***** Redundant key listener methods *****//
	

	@Override
	public void keyTyped(KeyEvent arg0) { }
	@Override
	public void keyReleased(KeyEvent arg0) { }

}
