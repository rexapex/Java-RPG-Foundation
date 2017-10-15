package item_hierarchy;

import java.awt.image.BufferedImage;

import main.GameStartClass;

public abstract class UsableItem extends Item
{
	public UsableItem(BufferedImage itemImage, String name, String examineText, int basePrice)
	{
		super(itemImage, name, examineText, basePrice);
	}
	
	public abstract void onItemUse(GameStartClass gameClass, int indexX, int indexY);
}
