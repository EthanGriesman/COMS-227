package hw4;

import api.CardinalDirection;
import api.Crossable;
import api.Path;
import api.PositionVector;
import api.Train;
import api.Point;

/**
 * Models a link that connects a single path to nothing. 
 * getConnectedPoint() returns null and shiftPoints() does nothing
 * A > | 
 * 
 * @author Ethan Griesmaan 5/4/2023
 */
public class DeadEndLink extends AbstractLink {
	    @Override
	    public void shiftPoints(PositionVector positionVector) {
	    	// is dead end, does nothing
	    }
	    @Override
	    public Point getConnectedPoint(Point point) {
	    	// is dead end, no point to return
	    	return null;
	    }
	    @Override
	    public void trainEnteredCrossing() {
	        // do nothing, as there is no crossing for a dead end link
	    }
	    @Override
	    public void trainExitedCrossing() {
	        // do nothing, as there is no crossing for a dead end link
	    }
		@Override
		public int getNumPaths() {
			// only one path in a dead end path
			return 1;
		}
}
