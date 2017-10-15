package utils;

public class Utils
{
	public static float trigForAngle(double opp, double adj)
	{
		return (float)Math.atan2(opp, adj);
	}
	
	public static double scale(int varToScale, double i, double j)
	{
		return varToScale - varToScale*(i/j);
	}
	
	public static double findSmallestRatio(double i, double j, boolean shouldReturnI)
	{
		double returnI, returnJ;
		if(i < j)
		{
			returnI = 1;
			returnJ = j/i;
		} else
		{
			returnI = i/j;
			returnJ = 1;
		}
		return shouldReturnI ? returnI : returnJ;
	}
}
