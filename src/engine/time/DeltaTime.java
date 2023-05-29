package engine.time;

/**
 * Delta time is a class that handles frame rate variation of users, if a value such as movement is multiplied each ftame by delta time, its consistency will be the same on all devices.
 * @author David Šebesta
 *
 */
public class DeltaTime {
	private static double delta = 0;
	
	/**
	 * Private consturctor
	 */
	private DeltaTime() {};
	
	/**
	 * Method to set delta time
	 * @param delta
	 */
	public static void updateDeltaTime(double delta) { 
		DeltaTime.delta = delta;
	}
	
	/**
	 * Get delta time
	 * @return delta time
	 */
	public static double getDeltaTime() {
		return delta;
	}
}
