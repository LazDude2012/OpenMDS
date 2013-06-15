package OpenMDS.api;

/**
 * This interface is used for the Defence Computer. I may implement higher tier defence computers (or lower-tier ones?) in the future.
 */
public interface IAttunementStorage
{
	/**
	 * Self-explanatory, this is where the attunements are stored.
	 */
	public int[] attunements = {};

	/**
	 * Gets the attunement stored in the priority requested.
	 * @param priority The priority, as an integer.
	 * @return The matching attunement.
	 */
	public int GetAttunementFromPriority(int priority);

	/**
	 * Sets the attunement stored in the priority requested.
	 * @param attunement The attunement, in its integer form.
	 * @param priority The priority, as an integer.
	 */
	public void SetAttunementForPriority(int attunement,int priority);

	/**
	 * Gets the name of a given priority.
	 * @param priority The priority to identify.
	 * @return
	 */
	public String GetPriorityName(int priority);
}