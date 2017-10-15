package item_hierarchy;

import java.util.Random;

public enum EToolGrade
{
	NONE(-1, 0, 0), BRONZE(0, 0, 2), IRON(1, 0, 4), STEEL(2, 1, 5), SILVER(3, 1, 7), MITHRIL(4, 3, 10), TITANIUM(5, 8, 12), TUNGSTEN(6, 10, 12), IRIDIUM(7, 10, 15);
	
	private int gradeID, minHealthReduc, maxHealthReduc;
	EToolGrade(int gradeID, int minHealthReduc, int maxHealthReduc)
	{
		this.gradeID = gradeID;
		this.minHealthReduc = minHealthReduc;
		this.maxHealthReduc = maxHealthReduc;
	}
	
	public int getID()
	{
		return gradeID;
	}
	
	private Random rand = new Random();
	public int getHealthReduction()
	{
		if(gradeID >= 0)
			return minHealthReduc+rand.nextInt(minHealthReduc+maxHealthReduc);
		else
			return 0;
	}
}
