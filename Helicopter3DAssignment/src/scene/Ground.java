package scene;

import com.jogamp.opengl.GL2;

import resources.Colours;
import shapes.StaticShape;

/**
 * Draws a grid of GL_Quads on X-Z plane centered at origin.
 * Size of grid is adjustable and ground view can be changed to wireframe or filled
 * 
 * @author Maya Ashizumi-Munn | ID: 17978640
 */
public class Ground extends StaticShape {
	
	GL2 gl;	//Graphics library reference
	
	//Indices of display-lists
	int groundDisplayList;
	
	//Grid attributes
	double tileSize = 2;								//Sets each grid tile length and width
	//Width and length must be even number
	int gridWidth = 	100;								//Sets number of panels width-way
	int gridLength = 	100;								//Sets number of panels length-way
	GroundDrawMode drawMode = GroundDrawMode.FILLED;	//Change to FILLED or WIREFRAME
	
	public Ground(GL2 gl2) throws Exception {
		this.gl = gl2;
		
		//Creating display lists
		groundDisplayList = gl2.glGenLists(1);
		
		//Check for even number width and length
		//If not even number, draw algorithm wont work
		if (gridWidth % 2 == 1 || gridLength % 2 == 1) {
			throw new Exception("Grid width and length must be even numbers!");
		}
	}
	
	//***************************************************//
	
	@Override
	public void draw() {
		//Call display list dependent on GroundDrawMode settings
		if (drawMode == GroundDrawMode.FILLED) {
			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL); //Fill mode
			gl.glCallList(groundDisplayList);
			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
		}
		else {
			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE); //Line mode
			gl.glCallList(groundDisplayList);
			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
		}
	}
	
	@Override
	public void precompileDisplayList() {
		//Precompile ground display list
		this.compileGrounds(this.groundDisplayList);
	}
	
	private void compileGrounds(int displayList) {
		//Create ground display list
		gl.glNewList(this.groundDisplayList, GL2.GL_COMPILE);
		//Set colour
		double[] groundColour = Colours.GRASS_GREEN.getRGB();
		gl.glColor3d(groundColour[0], groundColour[1], groundColour[2]);
			//Begin setting grid tiles vertices
			gl.glBegin(GL2.GL_QUADS);
				this.createGroundGrid();
			gl.glEnd();
		gl.glEndList();
	}
	
	//***** Methods used to draw entire grid ************//
	
	//Main method for ground vertice definition
	private void createGroundGrid() {
		//Get X edge point of grid's left side
		double edgePointVertice = 0 - ((gridWidth / 2) * tileSize);	//Negation because left side edge X vertice
		
		//Draw all upper rows
		this.drawRows(edgePointVertice, true);
		//Draw all lower rows
		this.drawRows(edgePointVertice, false);
	}
	
	//Draws one tile
	private void drawTile(double upperLeftX, double upperLeftZ) {
		double x = upperLeftX;
		double z = upperLeftZ;
		
		//Sets vertices for this tile in the grid
		gl.glVertex3d(x, 0, z);									//Left top
		gl.glVertex3d(x + this.tileSize, 0, z); 				//Right top
		gl.glVertex3d(x + this.tileSize, 0, z + this.tileSize); //Right bottom
		gl.glVertex3d(x, 0, z + this.tileSize); 				//Left bottom
	}
	
	/**
	 * Draws all tiles in all rows in one direction from centre point
	 * 
	 * @param xEdgePoint
	 * @param goUp	If true, iterate through upper rows
	 */
	private void drawRows(double xEdgePoint, boolean goUp) {
		int iterations = this.gridLength / 2;	//Draw half of the grid
		double zStartPointUp = 0 - this.tileSize;	//Always start up rows 1 tile length up
		double zStartPointDown = 0;
		
		//Find all row start points in one direction
		for (int row = 0; row < iterations; row++) {
			if (goUp) {
				drawRow(xEdgePoint, zStartPointUp);
				zStartPointUp -= this.tileSize;
			}
			else {
				drawRow(xEdgePoint, zStartPointDown);
				zStartPointDown += this.tileSize;
			}
		}
	}
	
	//Draws row tiles from left to right
	private void drawRow(double xStartPoint, double zStartPoint) {
		//Draw tiles equal to number of columns (gridWidth)
		double x = xStartPoint;
		double z = zStartPoint;
		
		for (int tile = 0; tile < this.gridWidth; tile++) {
			drawTile(x, z);
			//Move x over by tile size amount to draw another tile
			x += tileSize;
		}
	}
	
	//***************************************************//
	
	public void toggleDrawMode() {
		//Change draw mode to one that is not selected
		if (drawMode == GroundDrawMode.FILLED) {
			drawMode = GroundDrawMode.WIREFRAME;
		}
		else {
			drawMode = GroundDrawMode.FILLED;
		}
	}
}
