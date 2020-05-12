package helihierarchy.heliparts;

/**
 * A generic scene graph tree node.
 *
 * @author Jacqueline Whalley
 */

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.glu.GLU;

import com.jogamp.opengl.GL2;

public abstract class TreeNode {
	
	protected double transX, transY, transZ;
		
	// list of children nodes
	private List<TreeNode> children = new ArrayList<TreeNode>();
	
	// Adds a child to the tree node
	public void addChild(TreeNode newChild)
	{
		children.add(newChild);
	}
	  
	//GLU reference
	GLU glu;
	
	 // drawing code for this branch of the tree
	 public void draw(GL2 gl, com.jogamp.opengl.glu.GLU glu2){
		 this.glu = glu2;
	
		 gl.glPushMatrix();
		
		 // make the transformation for this branch of the tree
		 transformNode(gl);
	
		 // draw the current node
		 drawNode(gl);
	
		 // iterate through all the children
		 for ( TreeNode child : children)
		 {
			 // go depth first into the tree
			 child.draw(gl, glu2);
		 }
		
		 gl.glPopMatrix();
	 }
	
	 //Implement this method to do the initialising of display list
	 abstract void initialiseDisplayList(GL2 gl, GLU glu);
	  
	 // Implement this method for transforming the node relative to its parent
	 abstract void transformNode(GL2 gl);
	
	 // Implement this method to do the actual drawing of the node
	 abstract void drawNode(GL2 gl);
}