package hw4;

import api.Crossable;
import api.Path;
import api.Point;
import api.PointPair;
import api.PositionVector;
import java.util.List;
// just 
/**
 * This class represents a switchable link that connects two points with multiple paths.
 * A MultiSwitchLink object can have 2 to 6 paths.
 * 
 * @author Ethan Griesman 5/4/2023
 */
public class MultiSwitchLink extends AbstractMultiLink implements Crossable {
	
	/**
	 * Constructor initializing MultiSwitchLink object with array of PointPairs
	 * @param connections An array of PointPairs that represent the paths between two points.
	 */
    public MultiSwitchLink(PointPair[] connections) {
    	// Execute the parent class AbstractMultiLink
        super(connections);
    }
    
    /**
     * Method to switch the connection at the specified index to a new PointPair.
     * @param pointPair The new PointPair to switch the connection to.
     * @param index The new PointPair to switch the connection to.
     */
    public void switchConnection(PointPair pointPair, int index) {
        // If the train is currently crossing the link, do nothing
        if (isTrainInCrossing()) {
            return;
        }
        // Update the connection at the given index
        connectionsGet()[index] = pointPair;
    }
}