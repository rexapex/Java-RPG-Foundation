package ui_manager;

import java.awt.Color;
import java.awt.Graphics;

import main.GameStartClass;

public abstract class UserInterface
{
	protected GameStartClass gameClass;
	protected int x, y, width, height;
	protected boolean actionDone = false;
	
	public UserInterface(GameStartClass gameClass, int screenWidth, int screenHeight)
	{
		this.gameClass = gameClass;
		
		this.width = screenWidth-(screenWidth/8);
		this.height = screenHeight-(screenHeight/8);
		this.x = screenWidth/16;
		this.y = screenHeight/16;
	}
	
	public void update(int mousex, int mousey, boolean leftMouseButtonPressed, boolean previousLeftMouseButtonPressed)
	{
		actionDone = false;
		if(!actionDone && mousex >= x+width-45 && mousex <=x+width+5 && mousey >= y-5 && mousey <= y+35 && leftMouseButtonPressed &&
				!previousLeftMouseButtonPressed)
		{
			setForRemoval();
			onClose();
		}
	}
	
	public void paint(Graphics g)
	{
		/**Background*/
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		
		/**Close Button*/
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x+width-45, y-5, 50, 50);
		g.setColor(Color.black);
		g.drawRect(x+width-45, y-5, 50, 50);
		g.setColor(Color.red);
		g.drawLine(x+width-40, y, x+width, y+35);
		g.drawLine(x+width-40, y+35, x+width, y);
		g.setColor(Color.BLACK);
	}

	public abstract void onClose();
	
	private boolean remove = false;
	private void setForRemoval()
	{
		remove = true;
	}
	
	public boolean isSetForRemoval()
	{
		return remove;
	}
}
