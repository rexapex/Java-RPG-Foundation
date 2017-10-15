package main;

/**Handles scaling of screen*/
public class FrameManager
{
	private float ratioWidth;
	
	public FrameManager(int baseWidth, int baseHeight, int actualScreenWidth, int actualScreenHeight)
	{
		if(actualScreenWidth < actualScreenHeight)
		{
			this.ratioWidth = (float)actualScreenWidth / (float)baseWidth;
		} else
		{
			this.ratioWidth = (float)actualScreenHeight / (float)baseHeight;
		}
	}
	
	public float scaleWidthOrX(int varToScale)
	{
		return (float)varToScale * ratioWidth;
	}
	
	public float scaleHeightOrY(int varToScale)
	{
		return (float)varToScale * ratioWidth;
	}
	
	public float getScaleFactor()
	{
		return ratioWidth;
	}
}
