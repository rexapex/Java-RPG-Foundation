package ui_manager;

import item_hierarchy.Item;
import item_hierarchy.UsableItem;

import java.awt.Color;
import java.awt.Graphics;

import main.GameStartClass;

public class Inventory
{
	private int x, y, width, height;
	private int itemWidth, itemHeight;
	private Item[][] invent = new Item[5][6];
	
	private GameStartClass gameClass;
	
	public Inventory(int x, int y, int width, int height, GameStartClass gameClass)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.gameClass = gameClass;
		
		this.itemWidth = width / 8;
		this.itemHeight = height / 9;
	}
	
	public void update(boolean leftMouseButtonPressed, boolean previousLeftMouseButtonPressed, boolean rightMouseButtonPressed, int mousex, int mousey)
	{
		for(int i = 0; i < invent.length; i++)
		{
			for(int j = 0; j < invent[0].length; j++)
			{
				if(invent[i][j] != null && mousex >= x+(itemWidth/2*(i+1))+(itemWidth*i) && mousex <= x+(itemWidth/2*(i+1))+(itemWidth*i)+itemWidth
						&& mousey >= y+(itemHeight/2*(j+1))+(itemHeight*j) && mousey <= y+(itemHeight/2*(j+1))+(itemHeight*j)+itemHeight)
				{
					if(leftMouseButtonPressed && !previousLeftMouseButtonPressed)
					{
						if(invent[i][j] instanceof UsableItem)
						{
							((UsableItem) invent[i][j]).onItemUse(gameClass, i, j);
						} else
						{
							gameClass.console.addText(invent[i][j].examine());
						}
					} else if(rightMouseButtonPressed)
					{
						removeItem(i, j);
					}
				}
			}
		}
	}
	
	public void paint(Graphics g)
	{
		/**Draw the bag*/
		g.setColor(Color.gray);
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		
		/**Draw the contents of the bag*/
		for(int i = 0; i < invent[0].length; i++)
		{
			for(int j = 0; j < invent.length; j++)
			{
				if(invent[j][i] != null)
				{
					if(invent[j][i] != null)
						g.drawImage(invent[j][i].getImage(), x+(itemWidth/2*(j+1))+(itemWidth*j), y+(itemHeight/2*(i+1))+(itemHeight*i),
								itemWidth, itemHeight, null);
					else
						g.fillRect(x+(itemWidth/2*(j+1))+(itemWidth*j), y+(itemHeight/2*(i+1))+(itemHeight*i),
								itemWidth, itemHeight);
				}
			}
		}
	}
	
	public boolean isFull()
	{
		for(int i = 0; i < invent[0].length; i++)
		{
			for(int j = 0; j < invent.length; j++)
			{
				if(invent[j][i] == null)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public void addItem(Item itemToAdd)
	{
		outerloop:
		for(int i = 0; i < invent[0].length; i++)
		{
			for(int j = 0; j < invent.length; j++)
			{
				if(invent[j][i] == null)
				{
					invent[j][i] = itemToAdd;
					break outerloop;
				}
			}
		}
	}
	
	public void removeItem(int indexX, int indexY)
	{
		gameClass.chunkList.get(0).droppedItems.addDroppedItem(invent[indexX][indexY], (int)gameClass.player1.getPermanentMiddleXPos()-itemWidth/2-gameClass.camerax,
				(int)gameClass.player1.getPermanentYFeetPos()-gameClass.cameray);
		invent[indexX][indexY] = null;
	}
	
	public void removeItemNotToFloor(int indexX, int indexY)
	{
		invent[indexX][indexY] = null;
	}
	
	public int getItemWidth()
	{
		return itemWidth;
	}
	
	public int getItemHeight()
	{
		return itemHeight;
	}
	
	public Item[][] getContents()
	{
		return invent;
	}
}
