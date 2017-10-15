package sprite_hierarchy;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import main.GameStartClass;
import main.I2DGame;

public abstract class Sprite2D
{
	protected GameStartClass gameClass;
	protected BufferedImage[] stoodStillImages;
	protected float xpos, ypos;
	protected int width, height;
	
	public Sprite2D(BufferedImage[] stoodStillImages, float xpos, float ypos, int width, int height, GameStartClass gameClass)
	{
		this.stoodStillImages = stoodStillImages;
		this.xpos = xpos;
		this.ypos = ypos;
		this.width = width;
		this.height = height;
		this.gameClass = gameClass;
	}
	
	public void paint(Graphics g)
	{
		g.drawImage(stoodStillImages[0], (int)xpos, (int)ypos, width, height, null);
	}

	public float getXpos()
	{
		return xpos;
	}

	public void setXpos(float xpos)
	{
		this.xpos = xpos;
	}

	public float getYpos()
	{
		return ypos;
	}

	public void setYpos(int ypos)
	{
		this.ypos = ypos;
	}

	public float getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public float getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}
}
