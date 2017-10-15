package sprite_generic;

import java.awt.image.BufferedImage;

public class Animator
{
	private BufferedImage[] frames;
	private BufferedImage sprite;
	private int currentFrame = 0;
	private int speed = 0;
	
	public Animator(BufferedImage[] frames, int speed)
	{
		this.frames = frames;
		this.speed = speed;
	}
	
	public BufferedImage update()
	{
		currentFrame++;
		
		if(currentFrame == frames.length)
		{
			currentFrame = 0;
		}
		
		sprite = frames[currentFrame];
		
		return sprite;
	}
}
