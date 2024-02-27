package hw4;

import api.Crossable;
import api.Path;
import api.Point;
import api.PositionVector;

/**
 * Models a switchable link with three paths
 * Endpoints 1, 2, and 3 form the connection
 * 
 * @author Ethan Griesman 5/4/2023
 *
 */
public class SwitchLink extends StraightTurnLink {
	// private member variable to store the turn state
	private boolean turn;
	
	/**
	 * Constructor for a switchable link with three fixed paths
	 * @param endpointA
	 * @param endpointB
	 * @param endpointC
	 */
	public SwitchLink (Point endpointA, Point endpointB, Point endpointC) {
		// Execute parent class StraightTurnLink given the following parameters
		super(endpointA, endpointB, endpointC);
	}
	/**
	 * Updates the link to turn or go straight
	 * @param turn
	 */
	public void setTurn(boolean turn) {
		// if there is no train in the crossing, update the turn state
		if(!isTrainInCrossing()) {
			this.turn = turn;
		}
	}
	/**
     * Gets the connected point to the given point on the switch link.
     * @param point the point for which to find the connected point
     * @return the connected point to the given point, or null if there is no connected point
     */
	@Override
	public Point getConnectedPoint(Point point) {
		if (point.equals(getEndPoint1())) {
	        if (turn) {
	            return getEndPoint3();	// If turn is true, return end point 3
	        } else {
	            return getEndPoint2();	// Otherwise, return end point 2
	        }
	    } else if (point.equals(getEndPoint2())) {
	        return getEndPoint1();	// If input point is end point 2, return end point 1
	    } else if (point.equals(getEndPoint3())) {
	        return getEndPoint1();	// If input point is end point 3, return end point 1
	    } else {
	        return null;	// If input point is not part of any connection, return null
	    }
    }

}
