package hw4;

import api.Crossable;
import api.Path;
import api.Point;
import api.PointPair;
import api.PositionVector;

/**
 * Models an abstract link for all child classes.
 * Implements Crossable interface, which defines methods related to train crossings.
 * All child classes inherit from this class.
 * 
 * @author Ethan Griesman 5/4/2023
 */
public abstract class AbstractLink implements Crossable {
    
    // Boolean indicating if a train is currently in the crossing
    private boolean isTrainInCrossing;
    
    /**
     * Method that shifts position vector to next connected point.
     * Inherited by all child classes.
     * @param positionVector the position vector to be shifted
     */
    public void shiftPoints(PositionVector positionVector) {
        // Get the current endpoint and the next connected point
        Point end = positionVector.getPointB();
        Point next = getConnectedPoint(end);
        // Get the Path object of the next point and its index
        Path p = next.getPath();
        int idx = next.getPointIndex();
        // Adjust the index accordingly if it is out of bounds
        if (idx + 1 >= p.getNumPoints()) {
            idx--;
        } 
        else if (idx -1 < 0) {
            idx++;
        }
        positionVector.setPointA(next);
        // Set the PositionVector's endpoint B to be the neighboring point at the updated index
        positionVector.setPointB(p.getPointByIndex(idx));
    }
    
    /**
     * Returns the point connected to the given point.
     * Inherited by all child classes.
     * @param point the point to find the connected point for
     * @return the connected point
     */
    @Override
    public Point getConnectedPoint(Point point) {
        return null;
    }
    
    /**
     * Sets that a train has entered the crossing.
     * This method is inherited by all child classes.
     */
    @Override
    public void trainEnteredCrossing() {
    	setTrainInCrossing(true);
    }
    
    /**
     * Sets that a train has exited the crossing.
     * This method is inherited by all child classes.
     */
    @Override
    public void trainExitedCrossing() {
        setTrainInCrossing(false);
    }
    
    /**
     * Returns the number of paths for this link.
     * This method is abstract and must be inherited by all child classes.
     * @return the number of paths for this link
     */
    @Override
    public abstract int getNumPaths();
    /**
     * Protected boolean testing if the train is in the crossing
     * @return isTrainInCrossing true if in crossing, false otherwise
     */
	protected boolean isTrainInCrossing() {
		return isTrainInCrossing;
	}
	/**
	 * Protected setter method to set train as being in the crossing
	 * @param isTrainInCrossing true if in crossing, false otherwise
	 */
	protected void setTrainInCrossing(boolean isTrainInCrossing) {
		this.isTrainInCrossing = isTrainInCrossing;
	}
}
