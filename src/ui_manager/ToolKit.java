package ui_manager;

import java.awt.Color;
import java.awt.Graphics;

import game_world.GameItems;
import item_hierarchy.Item;
import item_hierarchy.ToolItem;
import main.GameStartClass;

public class ToolKit
{
	private int x, y, width, height;
	private int itemWidth, itemHeight;
	private GameStartClass gameClass;
	
	private ToolItem axe = (ToolItem)GameItems.none, pick= (ToolItem)GameItems.none, hammer = (ToolItem)GameItems.none, fishingRod = (ToolItem)GameItems.none;
	
	public ToolKit(int x, int y, int width, int height, GameStartClass gameClass)
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
		if(leftMouseButtonPressed && !previousLeftMouseButtonPressed && mousex > x+(itemWidth/2*(0+1))+(itemWidth*0) &&
				mousex < x+(itemWidth/2*(0+1))+(itemWidth*0)+itemWidth && mousey > y+(itemHeight/2*(0+1))+(itemHeight*0) &&
				mousey < y+(itemHeight/2*(0+1))+(itemHeight*0)+itemHeight && !gameClass.bottomRightWindowSelector.invent.isFull())
		{
			gameClass.bottomRightWindowSelector.invent.addItem(axe);
			axe = (ToolItem) GameItems.none;
		}
	}
	
	public void paint(Graphics g)
	{
		/**Draw the bag*/
		g.setColor(Color.gray);
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		
		/**Draw the tools*/
		g.drawImage(axe.getImage(), x+(itemWidth/2*(0+1))+(itemWidth*0), y+(itemHeight/2*(0+1))+(itemHeight*0),
				itemWidth, itemHeight, null);
		g.drawString("Axe - "+axe.getName()+" - "+axe.getBasePrice()+" coins", x+(itemWidth/2*(0+1))+(itemWidth+itemWidth/2), y+(itemHeight/2*(0+1))+(itemHeight/2));
		g.drawImage(pick.getImage(), x+(itemWidth/2*(0+1))+(itemWidth*0), y+(itemHeight/2*(0+2))+(itemHeight*1),
				itemWidth, itemHeight, null);
		g.drawString("Pick - "+pick.getName()+" - "+pick.getBasePrice()+" coins", x+(itemWidth/2*(0+1))+(itemWidth+itemWidth/2), y+(itemHeight/2*(0+2))+(itemHeight+itemHeight/2));
		g.drawImage(hammer.getImage(), x+(itemWidth/2*(0+1))+(itemWidth*0), y+(itemHeight/2*(0+3))+(itemHeight*2),
				itemWidth, itemHeight, null);
		g.drawString("Hammer - "+hammer.getName()+" - "+hammer.getBasePrice()+" coins", x+(itemWidth/2*(0+1))+(itemWidth+itemWidth/2), y+(itemHeight/2*(0+3))+(itemHeight*2+itemHeight/2));
	}

	public ToolItem getAxe() {
		return axe;
	}

	public void setAxe(ToolItem axe) {
		this.axe = axe;
	}

	public ToolItem getPick() {
		return pick;
	}

	public void setPick(ToolItem pick) {
		this.pick = pick;
	}

	public ToolItem getHammer() {
		return hammer;
	}

	public void setHammer(ToolItem hammer) {
		this.hammer = hammer;
	}

	public ToolItem getFishingRod() {
		return fishingRod;
	}

	public void setFishingRod(ToolItem fishingRod) {
		this.fishingRod = fishingRod;
	}
}
