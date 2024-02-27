package hw4;

import api.CardinalDirection;
import api.Crossable;
import api.Path;
import api.Point;
import api.PointPair;
import api.PositionVector;

/**
 * Models a MultiFixedLink with a minimum of 2 to a maximum of 6 fixed paths.
 * Turns cannot be modified. Inherits from parent class AbstractMultiLink
 * 
 * @author Ethan Griesman 5/4/2023
 */
public class MultiFixedLink extends AbstractMultiLink implements Crossable {
	
	/**
	 * Constructor that initializes MultiFixedLink object with array of PointPairs.
	 * @param connections An array of PointPairs that represent the paths between two points.
	 */
	public MultiFixedLink(PointPair[] connections) {
		// Execute parent class AbstractMultiLink
		super(connections);
	}
	
}
