package engine.time;

public class DeltaTime {
	private static long lastTime = 0;
	private static double delta = 0;
	
	private DeltaTime() {};
	
	public static void updateDeltaTime() {
		long currentTime = System.nanoTime();
        delta = (currentTime - lastTime) / 1_000_000_000.0; // Convert nanoseconds to seconds
        lastTime = currentTime;
	}
	
	public static double getDeltaTime() {
		return delta;
	}
}
