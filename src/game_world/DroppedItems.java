package game_world;

import item_hierarchy.Item;

import java.awt.Graphics;
import java.util.ArrayList;

import main.GameStartClass;

public class DroppedItems
{
	private ArrayList<Item> droppedItemsList = new ArrayList<>();
	private ArrayList<Integer> droppedItemsX = new ArrayList<>();
	private ArrayList<Integer> droppedItemsY = new ArrayList<>();
	
	private int itemWidth, itemHeight, xGrabRange, yGrabRange;
	
	private GameStartClass gameClass;
	
	public DroppedItems(GameStartClass gameClass, int itemWidth, int itemHeight, int xGrabRange, int yGrabRange)
	{
		this.gameClass = gameClass;
		this.itemWidth = itemWidth;
		this.itemHeight = itemHeight;
		this.xGrabRange = xGrabRange;
		this.yGrabRange = yGrabRange;
	}
	
	public void update(boolean leftMouseButtonPressed, int mousex, int mousey, int camerax, int cameray)
	{
		for(int i = 0; i < droppedItemsList.size(); i++)
		{
			if(mousex > droppedItemsX.get(i)+camerax && mousex < droppedItemsX.get(i)+itemWidth+camerax && mousey > droppedItemsY.get(i)+cameray &&
					mousey < droppedItemsY.get(i)+itemHeight+cameray && leftMouseButtonPressed &&
					(int)gameClass.player1.getPermanentMiddleXPos()-gameClass.camerax > droppedItemsX.get(i)-xGrabRange &&
					(int)gameClass.player1.getPermanentMiddleXPos()-gameClass.camerax < droppedItemsX.get(i)+itemWidth+xGrabRange &&
					(int)gameClass.player1.getPermanentYFeetPos()-gameClass.cameray > droppedItemsY.get(i)-yGrabRange &&
					(int)gameClass.player1.getPermanentYFeetPos()-gameClass.cameray < droppedItemsY.get(i)+itemHeight+yGrabRange)
			{
				
				removeDroppedItem(i);
				break;
			}
		}
	}
	
	public void paint(Graphics g, int camerax, int cameray)
	{
		for(int i = 0; i < droppedItemsList.size(); i++)
		{
			if(droppedItemsList.get(i).getImage() != null)
				g.drawImage(droppedItemsList.get(i).getImage(), droppedItemsX.get(i)+camerax, droppedItemsY.get(i)+cameray, itemWidth, itemHeight, null);
			else
				g.fillRect(droppedItemsX.get(i)+camerax, droppedItemsY.get(i)+cameray, itemWidth, itemHeight);
		}
	}
	
	public void addDroppedItem(Item item, int x, int y)
	{
		droppedItemsList.add(item);
		droppedItemsX.add(x);
		droppedItemsY.add(y);
	}
	
	private void removeDroppedItem(int index)
	{
		if(!gameClass.bottomRightWindowSelector.invent.isFull())
		{
			gameClass.bottomRightWindowSelector.invent.addItem(droppedItemsList.get(index));
			droppedItemsList.remove(index);
			droppedItemsX.remove(index);
			droppedItemsY.remove(index);
		}
	}
}
