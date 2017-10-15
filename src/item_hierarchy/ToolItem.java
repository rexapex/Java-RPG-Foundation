package item_hierarchy;

import game_world.GameItems;

import java.awt.image.BufferedImage;

import main.GameStartClass;

public class ToolItem extends UsableItem
{
	private EToolType toolType;
	private EToolGrade toolGrade;
	
	public ToolItem(BufferedImage itemImage, String name, String examineText, int basePrice, EToolType toolType, EToolGrade toolGrade)
	{
		super(itemImage, name, examineText, basePrice);
		
		this.toolType = toolType;
		this.toolGrade = toolGrade;
	}
	
	public EToolType getType()
	{
		return toolType;
	}
	
	public EToolGrade getGrade()
	{
		return toolGrade;
	}
	
	public void onItemUse(GameStartClass gameClass, int indexX, int indexY)
	{
		Item itemToAddToInv = null;
		switch(toolType)
		{
		case NONE:
			itemToAddToInv = null;
			break;
		case AXE:
			itemToAddToInv = gameClass.bottomRightWindowSelector.toolkit.getAxe();
			gameClass.bottomRightWindowSelector.toolkit.setAxe(this);
			break;
		case PICK:
			itemToAddToInv = gameClass.bottomRightWindowSelector.toolkit.getPick();
			gameClass.bottomRightWindowSelector.toolkit.setPick(this);
			break;
		case HAMMER:
			itemToAddToInv = gameClass.bottomRightWindowSelector.toolkit.getHammer();
			gameClass.bottomRightWindowSelector.toolkit.setHammer(this);
			break;
		case FISHING_ROD:
			itemToAddToInv = gameClass.bottomRightWindowSelector.toolkit.getFishingRod();
			gameClass.bottomRightWindowSelector.toolkit.setFishingRod(this);
			break;
		}
		
		gameClass.bottomRightWindowSelector.invent.removeItemNotToFloor(indexX, indexY);
		
		if(itemToAddToInv != null && itemToAddToInv != GameItems.none)
		{
			gameClass.bottomRightWindowSelector.invent.addItem(itemToAddToInv);
		}
	}
}
