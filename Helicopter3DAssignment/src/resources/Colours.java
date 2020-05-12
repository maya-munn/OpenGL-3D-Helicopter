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
	BLACK (0, 0, 0),
	
	//Custom colours
	GRASS_GREEN(0.6, 0.8, 0.196078),
	SKY_BLUE(0.196078, 0.7, 1);
	
	//********************************//
	
	//Variables for RGB values
	private double r, g, b;
	
	//Constructor to set RGB values for a colour
	private Colours(double r, double g, double b) {
		this.setR(r);
		this.setG(g);
		this.setB(b);
	}
	
	//Getter methods for individual colour values or the whole set of RGB
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
	//Sometimes float values are needed for colours, convert from double to float
	public float[] getFloatRGB() {
		float[] rgbFloats = new float[3];
		double[] rgbDoubles = this.getRGB();
		for (int f = 0; f < rgbFloats.length; f++) {
			rgbFloats[f] = (float) rgbDoubles[f];
		}
		
		return rgbFloats;
	}
	
	//Setter methods for RGB values
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
