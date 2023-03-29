package math;

public class MathUtilities {

	private MathUtilities() {}
	
	public static int clamp(int value, int least, int max) {
		if(value >= max) return max;
		if(value <= least) return least;
		return value;
	}
	
	public static double clamp(double value, double least, double max) {
		if(value >= max) return max;
		if(value <= least) return least;
		return value;
	}
}
