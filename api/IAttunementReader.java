package OpenMDS.api;

public interface IAttunementReader
{
	public int[] attunements = {};
	public int GetAttunementFromPriority(int priority);
	public void SetAttunementForPriority(int attunement,int priority);
	public String GetPriorityName(int priority);
}