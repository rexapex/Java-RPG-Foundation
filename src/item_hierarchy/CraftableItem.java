package item_hierarchy;

import java.awt.image.BufferedImage;

public abstract class CraftableItem extends UsableItem
{
	public CraftableItem(BufferedImage itemImage, String name, String examineText, int basePrice)
	{
		super(itemImage, name, examineText, basePrice);
	}
	
	public abstract Item onItemCraft(CraftableItem itemToCraft);
}
