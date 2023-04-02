package engine.sprites.structures.conveyors;

public enum ConveyorBeltDirection {
	 	UP(0, -1),
	    LEFT(1, 0),
	    DOWN(0, 1),
	    RIGHT(-1, 0);

	    private final int deltaX;
	    private final int deltaY;

	    private ConveyorBeltDirection(int deltaX, int deltaY) {
	        this.deltaX = deltaX;
	        this.deltaY = deltaY;
	    }

	    public int getDeltaX() {
	        return deltaX;
	    }

	    public int getDeltaY() {
	        return deltaY;
	    }
}
