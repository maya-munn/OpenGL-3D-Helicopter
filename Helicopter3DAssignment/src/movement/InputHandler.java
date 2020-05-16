package movement;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import scene.Ground;
import sceneview.Camera;

/**
 * 
 * @author Papaya
 *
 */
public class InputHandler implements KeyListener {

	//Reference to ground object for toggling wireframe or filled view mode
	Ground groundReference;
	//Reference to class that controls helicopter movement (rotation and translation)
	MovementController moveController;
	//Reference to camera class to reset camera rotation view
	Camera cameraRef;
	
	public void setGroundRef(Ground groundRef) {
		this.groundReference = groundRef; 
	}
	public void setCameraRef(Camera cameraRef) {
		this.cameraRef = cameraRef;
	}
	
	
	public InputHandler(MovementController mc) {
		this.moveController = mc;
		
		//Print out controls when instantiated
		this.printControls();
	}
	
	public void printControls() {
		//Prints key mappings to user
		System.out.println("KEY MAPPINGS:");
		System.out.println();
		System.out.println(" R: Reset camera view");
		System.out.println(" L: Toggle wireframe view");
		System.out.println(" Left mouse drag: Rotate camera");
		System.out.println(" Right mouse drag: Zoom camera");
	}
	
	//*****************************************//
	
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
				//TODO
			break;
		//RIGHT KEY - Turn right
			case KeyEvent.VK_RIGHT:
				//TODO
			break;
		//W KEY - Move forward
			case KeyEvent.VK_W:
				//TODO
			break;
		//S KEY - Move backward
			case KeyEvent.VK_S:
				//TODO
			break;
		//A KEY - Strafe left
			case KeyEvent.VK_A:
				//TODO
				break;
		//D KEY - Strafe right
			case KeyEvent.VK_D:
				//TODO
			break;
		//R KEY - Reset camera view
			case KeyEvent.VK_R:
				cameraRef.resetCameraRotation();
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
	public void keyReleased(KeyEvent arg0) { }

	@Override
	public void keyTyped(KeyEvent arg0) { }

}
