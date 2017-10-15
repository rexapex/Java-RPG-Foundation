package item_hierarchy;

import java.awt.image.BufferedImage;

public class ArmourItem extends EquipableItem
{
	private int armourRating;
	private int type;
	
	public ArmourItem(BufferedImage itemImage, String name, String examineText, int basePrice, int armourRating, int type)
	{
		super(itemImage, name, examineText, basePrice);
		
		this.armourRating = armourRating;
		this.type = type;
	}
	
	public int getArmourRating()
	{
		return armourRating;
	}
	
	public int getType()
	{
		return type;
	}
}
