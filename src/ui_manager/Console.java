package ui_manager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import main.GameStartClass;

public class Console
{
	private ArrayList<String> text = new ArrayList<String>();
	private int textY;
	private int x, y, width, height;
	private Font font;
	private int textSpacing;
	
	public Console(int x, int y, int width, int height, int fontSize, GameStartClass gameClass)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.textY = (int)(y+GameStartClass.frameManager.scaleHeightOrY(25));
		
		this.font = new Font("Helvetica", Font.PLAIN, fontSize);
		textSpacing = gameClass.getGraphics().getFontMetrics(font).getHeight();
		
		this.text.add("Welcome!");
		
		gameClass.revalidate();
		gameClass.repaint();
	}
	
	public void addText(String newText)
	{
		if(textY+textSpacing*text.size()-1 >= y+height)
			text.remove(0);
		text.add(newText);
	}
	
	public void paint(Graphics g)
	{
		g.setColor(Color.gray);
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		
		g.setFont(font);
		
		for(int i = 0; i < text.size(); i++)
			g.drawString(text.get(i), 10, textY+textSpacing*i);
	}
}
