package hw4;

import api.CardinalDirection;
import api.Crossable;
import api.Path;
import api.Point;
import api.PositionVector;
import api.Train;
import api.Traversable;

/**
 * Models a fixed link between exactly two paths
 * Endpoints 1 and 2 form the connection
 * A > B
 * @author Ethan Griesman 5/4/2023
 */
public class CouplingLink extends StraightTurnLink implements Crossable {
	
	/**
	 * Constructor for a CouplingLink with 2 fixed paths
	 * @param endPoint1 start of path
	 * @param endPoint2 end of path
	 */
    public CouplingLink(Point endPoint1, Point endPoint2) {
    	// Execute parent class StraightTurnLink given the following parameters
    	// Will override constructor in abstract class and set new points
    	super(endPoint1, endPoint2);
    }
    
    /**
     * Given one of the endpoints of this link, return the other endpoint.
     * If the given point is not an endpoint of this link, return null.
     * @param point one of the endpoints of this link
     * @return the other endpoint of this link, or null if the given point is not an endpoint of this link
     */
    @Override
    public Point getConnectedPoint(Point point) {
    	// If the given point is equal to the endpoint 1
		if (point.equals(getEndPoint1())) {
			// return end point 2
	        return getEndPoint2();
	    } 
		else if (point.equals(getEndPoint2())) {
			// If the given point is equal to the endpoint 2, return 1
	        return getEndPoint1();
	    } 
		else {
			// else return null
	        return null;
	    }
    }
    
    /**
     * // A coupling link has two paths, one leading to the low end and one leading to the high end
     */
    @Override
    public int getNumPaths() {
        return 2;
    }
}
    
