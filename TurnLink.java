package hw4;

import api.Crossable;
import api.Path;
import api.Point;
import api.PositionVector;

/**
 * Models a fixed link with three paths
 * Endpoints 1, 2, and 3 form the connection
 * A > C
 * B > A
 * C > A
 * 
 * @author Ethan Griesman 5/4/2023
 */
public class TurnLink extends StraightTurnLink {
	/**
	 * Constructor for a turnlink with three fixed paths
	 * @param endpointA
	 * @param endpointB
	 * @param endpointC
	 */
	public TurnLink (Point endpointA, Point endpointB, Point endpointC) {
		// Execute parent class StraightTurnLink given the following parameters
		super(endpointA, endpointB, endpointC);
	}
	/**
	 *  Model the exact same process in calculating connectedPoint from 
	 *  StraightLink but replace A to B with A to C
	 */
	@Override
	public Point getConnectedPoint(Point point) {
		// Initialize connectedPoint to null
	    Point connectedPoint = null;
	    // Check if the input point is equal to the first endpoint of the link
	    if (point.equals(getEndPoint1())) {
	    	// If so, set connectedPoint to endpoint 3
	        connectedPoint = getEndPoint3();
	    } else if (point.equals(getEndPoint2()) || point.equals(getEndPoint3())) {
	    	// Otherwise, if the input point is equal to endpoint 2 or endpoint 3, set connectedPoint to endpoint 1
	        connectedPoint = getEndPoint1();
	    }
	    // Return the connectedPoint
	    return connectedPoint;
	}
}
