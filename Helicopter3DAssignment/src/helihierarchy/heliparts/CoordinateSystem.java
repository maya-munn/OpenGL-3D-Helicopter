package helihierarchy.heliparts;

import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.GL2;

/**
 * Class for a coordinate system
 *
 * @author Jacqueline Whalley
 */
public class CoordinateSystem extends TreeNode {

	@SuppressWarnings("unused")
	private double size;
	@SuppressWarnings("unused")
	private double lineWidth;
	
	//*********************************//
	
	public CoordinateSystem(double size, double lineWidth) {
		this.size = size;
		this.lineWidth = lineWidth;
	}
	
	@Override
	void transformNode(GL2 gl) {
		// TODO Auto-generated method stub
		//Implement transform later on
	}

	@Override
	void drawNode(GL2 gl) {
		// TODO Auto-generated method stub
		//Invisible coordinate system, origin already draws coordinate lines
	}

	@Override
	void initialiseDisplayList(GL2 gl, GLU glu) {
		// TODO Auto-generated method stub
		
	}

}
