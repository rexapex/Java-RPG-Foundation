package game_world_entities;

import item_hierarchy.EToolGrade;
import item_hierarchy.EToolType;
import item_hierarchy.Item;
import item_hierarchy.ToolItem;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

import ui_manager.ShopInterface;
import ui_manager.UserInterface;

import main.GameStartClass;

public class Shop extends Entity
{
	private boolean shopOpen = false;
	
	private Item[] shopItems;
	private int[] shopItemAmounts;
	
	public Shop(BufferedImage aliveImage, BufferedImage deadImage, int xpRewarded, String rewardMethod, Item returnedItem, EToolType reqToolType, EToolGrade reqToolGrade,
			int respawnTimeSeconds, int health, int[] xpos, int[] ypos, int[] width,
			int[] height, int xGrabRange, int yGrabRange, Item[] shopItems, int[] shopItemAmounts, GameStartClass gameClass)
	{
		super(aliveImage, deadImage, xpRewarded, rewardMethod, returnedItem, reqToolType, reqToolGrade, respawnTimeSeconds, health, xpos, ypos, width, height, xGrabRange, yGrabRange, gameClass);
		
		this.shopItems = shopItems;
		this.shopItemAmounts = shopItemAmounts;
	}
	
	public Item update(boolean leftMouseButtonPressed, boolean previousLeftMouseButtonPressed, boolean rightMouseButtonPressed,
			ToolItem itemToUse, int mousex, int mousey, int camerax, int cameray)
	{
		for(i = 0; i < xpos.length; i++)
		{
			if(mousex > xpos[i]+camerax && mousex < xpos[i]+width[i]+camerax && gameClass.player1.getPermanentMiddleXPos() > xpos[i]-xGrabRange+camerax
					&& gameClass.player1.getPermanentMiddleXPos() < xpos[i]+width[i]+xGrabRange+camerax && mousey > ypos[i]+cameray &&
					mousey < ypos[i]+height[i]+cameray &&
					gameClass.player1.getPermanentYFeetPos() > ypos[i]-yGrabRange+cameray && gameClass.player1.getPermanentYFeetPos() < ypos[i]+height[i]+yGrabRange
					+cameray && leftMouseButtonPressed && alive[i] && !previousLeftMouseButtonPressed && itemToUse.getType().getID()>=requiredItem.getID() &&
					itemToUse.getGrade().getID()>=requiredGrade.getID())
			{
				return onItemInteraction();
			}
		}
		return null;
	}
	
	@Override
	public Item onItemInteraction()
	{
		if(!shopOpen)
		{
			gameClass.userInterfacesOpen.add(new ShopInterface(gameClass, shopItems, shopItemAmounts, gameClass.getWidth(), gameClass.getHeight(),
					gameClass.bottomRightWindowSelector.invent.getItemWidth(), gameClass.bottomRightWindowSelector.invent.getItemHeight()));
			shopOpen = true;
		}
		
		return null;
	}
	
	
	public boolean isShopOpen()
	{
		return shopOpen;
	}
	
	public void setShop(boolean open)
	{
		shopOpen = open;
	}
	
	public void setItems(Item[] itemList, int[] itemAmountsList)
	{
		this.shopItems = itemList;
		this.shopItemAmounts = itemAmountsList;
	}
}
