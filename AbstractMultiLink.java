package hw4;

import api.Crossable;
import api.Path;
import api.Point;
import api.PointPair;
import api.PositionVector;

/**
 * Models an abstract multi link class for MultiSwitchLink and MultiFixedLink 
 * Extends AbstractLink which itself implements Crossable interface
 * 
 * @author Ethan Griesman 5/4/2023
 *
 */
public abstract class AbstractMultiLink extends AbstractLink {
	// An array of PointPairs that represent the paths between two points.
	private PointPair[] connections;
	/**
	 * Constructor initializes AbstractMultiLink with an array of PointPairs.
	 * @param connections An array of PointPairs that represent the paths between two points.
	 */
	public AbstractMultiLink (PointPair[] connections) {
		// protected helper method to set the connections 
		connectionsSet(connections);
	}
	/**
	 * Helper method to set connections of link to a new array of PointPairs.
	 * @param connections An array of PointPairs that represent the paths between two points.
	 */
	protected void connectionsSet(PointPair[] connections) {
		this.connections = connections;
	}
	/**
	 * Method to get the connections of this link.
	 * @return An array of PointPairs that represent the paths between two points.
	 */
	protected PointPair[] connectionsGet() {
		return connections;
	}
	/**
     * Method to shift the position vector to the next connected point.
     * Inherited by child classes MultiSwitchLink and MultiFixedLink 
     * @param positionVector the train position vector to be shifted
     */
    @Override
    public Point getConnectedPoint(Point point) {
    	// Iterate through all connections of this link
    	for (PointPair connection : connections) {
    		// Get the i-th connection
    		// Check if the input point is equal to the first point of the connection
            if (point.equals(connection.getPointA())) {
            	// If it is, return the second point of the connection
                return connection.getPointB();
            } 
            // Otherwise, check if the input point is equal to the second point of the connection
            else if (point.equals(connection.getPointB())) {
            	// If it is, return the first point of the connection
                return connection.getPointA();
            }
        }
    	// If the input point is not part of any connection, return null
        return null;
    } 
    
    /**
     *  The number of paths is equal to the length of connections array
     */
	@Override
	public int getNumPaths() {
		return connections.length;
	}

	
}
