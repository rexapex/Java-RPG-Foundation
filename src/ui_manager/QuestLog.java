package ui_manager;

import java.awt.Color;
import java.awt.Graphics;

import main.GameStartClass;

public class QuestLog
{
	private int x, y, width, height;
	
	private GameStartClass gameClass;
	
	public QuestLog(int x, int y, int width, int height, GameStartClass gameClass)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.gameClass = gameClass;
	}
	
	public void update(boolean leftMouseButtonPressed, boolean previousLeftMouseButtonPressed, boolean rightMouseButtonPressed,
			int mousex, int mousey)
	{
		
	}
	
	public void paint(Graphics g)
	{
		/**Draw the bag*/
		g.setColor(Color.gray);
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
	}
}
