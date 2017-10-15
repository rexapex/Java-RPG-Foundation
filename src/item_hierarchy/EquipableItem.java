package item_hierarchy;

import java.awt.image.BufferedImage;

import main.GameStartClass;

public class EquipableItem extends UsableItem
{
	public EquipableItem(BufferedImage itemImage, String name, String examineText, int basePrice)
	{
		super(itemImage, name, examineText, basePrice);
	}
	
	public void onItemUse(GameStartClass gameClass, int indexX, int indexY)
	{
		
	}
}
