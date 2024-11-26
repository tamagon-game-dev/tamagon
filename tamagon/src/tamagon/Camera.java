package tamagon;

public class Camera {
	
	//Camera offset
	static int x,y;
	
	/**
	 * Make the camera stay on the level's bonds
	 * @param current
	 * @param minimum
	 * @param maximum
	 * @return
	 */
	static int clampCamera(int current, int minimum, int maximum) {
		
		if(current < minimum) {
			current = minimum;
		}
		
		if(current > maximum) {
			current = maximum;
		}
		
		return current;
	}
}
