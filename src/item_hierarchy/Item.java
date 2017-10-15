package item_hierarchy;

import java.awt.image.BufferedImage;

import main.GameStartClass;

public class Item
{
	protected String name;
	private String examineText;
	private int basePrice;
	private BufferedImage itemImage;
	
	public Item(BufferedImage itemImage, String name, String examineText, int basePrice)
	{
		this.itemImage = itemImage;
		this.name = name;
		this.examineText = examineText;
		this.basePrice = basePrice;
	}
	
	public BufferedImage getImage()
	{
		return itemImage;
	}
	
	public void setImage(BufferedImage itemImage)
	{
		this.itemImage = itemImage;
	}
	
	public String examine()
	{
		return examineText;
	}
	
	public int getBasePrice()
	{
		return basePrice;
	}
	
	public void setBasePrice(int basePrice)
	{
		this.basePrice = basePrice;
	}
	
	public String getName()
	{
		return name;
	}
}
