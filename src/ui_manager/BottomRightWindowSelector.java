package ui_manager;

import java.awt.Graphics;
import java.awt.Image;

import main.GameStartClass;

public class BottomRightWindowSelector
{
	private int x, y, width, height;
	
	private int selectorX, selectorWidth, selectorHeight;
	
	private int windowSelected;
	public Inventory invent;
	public EquipedItems equipedItems;
	public ToolKit toolkit;
	public MagicDisplay magic;
	public QuestLog quests;
	
	private Image inventImage, equipedItemsImage, toolkitImage, magicImage, questImage;
	
	public BottomRightWindowSelector(int x, int y, int width, int height, Image inventI, Image equipedItemsI, Image toolkitI, Image magicI,
			Image questI, Image readImage, GameStartClass gameClass)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.inventImage = inventI;
		this.equipedItemsImage = equipedItemsI;
		this.toolkitImage = toolkitI;
		this.magicImage = magicI;
		this.questImage = questI;
		
		selectorHeight = height/5;
		selectorWidth = selectorHeight;
		selectorX = x - selectorWidth;
		
		/**Create the players inventory based of the screen width and height*/
		invent = new Inventory(x, y, width, height, gameClass);
		equipedItems = new EquipedItems(x, y, width, height, null, null, null, null, null, null);
		toolkit = new ToolKit(x, y, width, height, gameClass);
		magic = new MagicDisplay(x, y, width, height, readImage, gameClass);
		quests = new QuestLog(x, y, width, height, gameClass);
	}
	
	public void update(boolean leftMouseButtonPressed, boolean previousLeftMouseButtonPressed, boolean rightMouseButtonPressed, int mousex, int mousey)
	{
		if(leftMouseButtonPressed)
		{
			if(mousex > selectorX && mousex < selectorX+selectorWidth)
			{
				if(mousey > y && mousey < y+selectorHeight)
					windowSelected = 0;
				else if(mousey > y+selectorHeight && mousey < y+(selectorHeight*2))
					windowSelected = 1;
				else if(mousey > y+selectorHeight*2 && mousey < y+selectorHeight*3)
					windowSelected = 2;
				else if(mousey > y+selectorHeight*3 && mousey < y+selectorHeight*4)
					windowSelected = 3;
				else if(mousey > y+selectorHeight*4 && mousey < y+selectorHeight*5)
					windowSelected = 4;
			}
		}
		
		switch(windowSelected)
		{
		case 0:
			invent.update(leftMouseButtonPressed, previousLeftMouseButtonPressed, rightMouseButtonPressed, mousex, mousey);
			break;
		case 1:
			equipedItems.update(leftMouseButtonPressed, rightMouseButtonPressed, mousex, mousey);
			break;
		case 2:
			toolkit.update(leftMouseButtonPressed, previousLeftMouseButtonPressed, rightMouseButtonPressed, mousex, mousey);
			break;
		case 3:
			magic.update(leftMouseButtonPressed, previousLeftMouseButtonPressed, rightMouseButtonPressed, mousex, mousey);
			break;
		case 4:
			quests.update(leftMouseButtonPressed, previousLeftMouseButtonPressed, rightMouseButtonPressed, mousex, mousey);
			break;
		}
	}
	
	public void paint(Graphics g)
	{
		g.drawImage(inventImage, selectorX, y, selectorWidth, selectorHeight, null);
		g.drawRect(selectorX, y, selectorWidth, selectorHeight);
		g.drawImage(equipedItemsImage, selectorX, y+selectorHeight, selectorWidth, selectorHeight, null);
		g.drawRect(selectorX, y+selectorHeight, selectorWidth, selectorHeight);
		g.drawImage(toolkitImage, selectorX, y+(selectorHeight*2), selectorWidth, selectorHeight, null);
		g.drawRect(selectorX, y+(selectorHeight*2), selectorWidth, selectorHeight);
		g.drawImage(magicImage, selectorX, y+(selectorHeight*3), selectorWidth, selectorHeight, null);
		g.drawRect(selectorX, y+(selectorHeight*3), selectorWidth, selectorHeight);
		g.drawImage(questImage, selectorX, y+(selectorHeight*4), selectorWidth, selectorHeight, null);
		g.drawRect(selectorX, y+(selectorHeight*4), selectorWidth, selectorHeight);
		
		switch(windowSelected)
		{
		case 0:
			invent.paint(g);
			break;
		case 1:
			equipedItems.paint(g);
			break;
		case 2:
			toolkit.paint(g);
			break;
		case 3:
			magic.paint(g);
			break;
		case 4:
			quests.paint(g);
			break;
		}
	}
}
