/**
 * A basic event that is sent off to registered listeners
 * @
 *
 * PUBLIC FEATURES:
 * // Constructors
 *    public CarUpdateEvent(Object source)
 *
 * // Methods
 *
 * COLLABORATORS:
 *
 * @version 1.0, 16 Oct 2004
 * @author Adam Black
 */
public class CarUpdateEvent extends java.util.EventObject
{
	/**
	 * @param source source object of event
	 */
	public CarUpdateEvent(Object source)
	{
		super(source);
	}
}
