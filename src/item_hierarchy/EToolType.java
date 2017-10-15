package item_hierarchy;

public enum EToolType
{
	NONE(-1), AXE(0), PICK(1), HAMMER(2), FISHING_ROD(3);
	
	private int typeID;
	EToolType(int typeID)
	{
		this.typeID = typeID;
	}
	
	public int getID()
	{
		return typeID;
	}
}
