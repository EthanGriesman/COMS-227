package hw4;

import api.Crossable;
import api.Path;
import api.Point;
import api.PositionVector;

/**
 * Models a fixed link with three paths
 * Endpoints 1, 2, and 3 form the connection
 * A > B
 * B > A
 * C > A
 * 
 * @author Ethan Griesman 5/4/2023
 */
public class StraightLink extends StraightTurnLink {	
	/**
	 * Constructor of a straight link with 3 fixed paths.
	 * @param endpointA the first endpoint of the straight link
	 * @param endpointB the second endpoint of the straight link
	 * @param endpointC the third endpoint of the straight link
	 */
    public StraightLink(Point endpointA, Point endpointB, Point endpointC) {
    	// Execute parent class StraightTurnLink given the following parameters
    	super(endpointA, endpointB, endpointC);
    }
    /**
     * Gets the connected point to the given point on the straight link.
     * @param point the point for which to find the connected point
     * @return the connected point to the given point, or null if there is no connected point
     */
    @Override
    public Point getConnectedPoint(Point point) {
    	// If the given point is the first endpoint of the straight link, return the second endpoint
    	if (point.equals(getEndPoint1())) {
            return getEndPoint2();
        }
    	// If the given point is the second endpoint of the straight link, return the first endpoint
        else if (point.equals(getEndPoint2())) {
            return getEndPoint1();
        }
    	// If the given point is the third endpoint of the straight link, return the first endpoint
        else if (point.equals(getEndPoint3())) {
            return getEndPoint1();
        }
    	// If the given point is not an endpoint of the straight link, return null
        else {
            return null;
        }
    }
}
