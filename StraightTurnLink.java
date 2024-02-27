package hw4;

import api.Crossable;
import api.Path;
import api.Point;

/**
 * 
 * Represents an abstract StraightTurnLink.
 * A Straight Turn Link is a link consisting of three points.
 * This class extends the AbstractLink class and overrides the getNumPaths method to return the number of paths of a Straight Turn Link.
 * The class also contains methods to get and set the three end points of the link.
 *
 * @author Ethan Griesman 5/4/2023
 *
 */
public abstract class StraightTurnLink extends AbstractLink {
	// End of path 1
	protected Point point1;
	// Beginning of path 2
	protected Point point2;
	// Beginning of path 3
	protected Point point3;
	
	/**
	 * Constructs a StraightTurnLink for StraightLink, TurnLink, and SwitchLink child classes
	 * @param endPoint1 the end point of the first path
	 * @param endPoint2 the beginning point of the second path
	 * @param endPoint3 the beginning point of the third path
	 */
	public StraightTurnLink(Point endPoint1, Point endPoint2, Point endPoint3) {
		setEndPoint1(endPoint1);
		setEndPoint2(endPoint2);
		setEndPoint3(endPoint3);
	}
	
	/**
	 * Overrides constructor for CouplingLink class with only 2 points
	 * @param endPoint1 the end point of the first path
	 * @param endPoint2 the beginning point of the second path
	 */
	public StraightTurnLink(Point endPoint1, Point endPoint2) {
		setEndPoint1(endPoint1);
		setEndPoint2(endPoint2);
	}
	
	/**
     * Returns the end point of the first path.
     * @return the end point of the first path
     */
    protected Point getEndPoint1() {
        return point1;
    }
    
    /**
     * Sets the end point of the first path to the given point.
     * @param endPoint1 the end point of the first path
     */
    protected void setEndPoint1(Point endPoint1) {
        point1 = endPoint1;
    }
    
    /**
     * Returns the beginning point of the second path.
     * @return the beginning point of the second path
     */
    protected Point getEndPoint2() {
        return point2;
    }
    
    /**
     * Sets the beginning point of the second path to the given point.
     * @param endPoint2 the beginning point of the second path
     */
    protected void setEndPoint2(Point endPoint2) {
        point2 = endPoint2;
    }
    
    /**
     * Returns the beginning point of the third path.
     * @return the beginning point of the third path
     */
    protected Point getEndPoint3() {
        return point3;
    }
    
    /**
     * Sets the beginning point of the third path to the given point.
     * @param endPoint3 the beginning point of the third path
     */
    protected void setEndPoint3(Point endPoint3) {
        point3 = endPoint3;
    }
    
    /**
     * Returns the number of paths of a Straight Turn Link, which is always 3.
     * @return the number of paths of a Straight Turn Link, which is always 3
     */
    @Override
    public int getNumPaths() {
        return 3;
    }
}
