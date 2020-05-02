package resources;

/**
 * Contains colour constants to be used without having to declare them each
 * usage.
 * 
 * @author Maya Ashizumi-Munn | ID: 17978640
 */
public enum Colours {
	RED (1, 0, 0),
	GREEN (0, 1, 0),
	BLUE (0, 0, 1),
	YELLOW (1, 1, 0),
	CYAN (0, 1, 1),
	MAGENTA (1, 0, 1),
	WHITE (1, 1, 1),
	BLACK (0, 0, 0);
	
	//********************************//
	
	private double r, g, b;
	
	private Colours(double r, double g, double b) {
		this.setR(r);
		this.setG(g);
		this.setB(b);
	}
	
	public double getR() {
		return this.r;
	}
	public double getG() {
		return this.g;
	}
	public double getB() {
		return this.b;
	}
	public double[] getRGB() {
		return new double[] { getR(), getG(), getB() };
	}
	
	private void setR(double r) {
		this.r = r;
	}
	private void setG(double g) {
		this.g = g;
	}
	private void setB(double b) {
		this.b = b;
	}
}
