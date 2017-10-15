package player_data;

import main.GameStartClass;

public class Skills
{
	private GameStartClass gameClass;
	
	private long woodCuttingXp = 0;
	private int woodCuttingLevel = 1;
	
	private long miningXp = 0;
	private int miningLevel = 1;
	
	public Skills(GameStartClass gameClass)
	{
		this.gameClass = gameClass;
	}
	
	public void rewardWoodCuttingXp(long xp)
	{
		woodCuttingXp += xp;
		int methodInstanceLevel = woodCuttingLevel;
		woodCuttingLevel = level(woodCuttingXp, woodCuttingLevel);
		if(methodInstanceLevel != woodCuttingLevel)
			gameClass.console.addText("You have reached level " + woodCuttingLevel + " woodcutting!");
	}
	
	public void rewardMiningXp(long xp)
	{
		miningXp += xp;
		int methodInstanceLevel = miningLevel;
		miningLevel = level(miningXp, miningLevel);
		if(methodInstanceLevel != miningLevel)
			gameClass.console.addText("You have reached level " + miningLevel + " mining!");
	}
	
	private int level(long xp, int level)
	{
		if((long)xp >= Math.floor(level + (50*level) * Math.pow(2, level / 7.)))
			return ++level;
		else
			return level;
	}
}
