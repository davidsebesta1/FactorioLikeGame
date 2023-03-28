package engine.time;

public class DeltaTime {
	private static double delta = 0;
	
	private DeltaTime() {};
	
	public static void updateDeltaTime(double delta) {
		DeltaTime.delta = delta;
	}
	
	public static double getDeltaTime() {
		return delta;
	}
}
