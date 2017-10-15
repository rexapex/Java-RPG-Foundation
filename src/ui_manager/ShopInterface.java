package ui_manager;

import game_world_entities.Shop;
import item_hierarchy.Item;

import java.awt.Color;
import java.awt.Graphics;

import main.GameStartClass;
import player_data.Bank;

public class ShopInterface extends UserInterface
{
	private Item[] itemList;
	private int[] itemAmountList;
	
	private int itemWidth, itemHeight;
	
	public ShopInterface(GameStartClass gameClass, Item[] itemList, int[] itemAmountList, int screenWidth, int screenHeight,
			int itemWidth, int itemHeight)
	{
		super(gameClass, screenWidth, screenHeight);
		
		this.itemWidth = itemWidth;
		this.itemHeight = itemHeight;

		this.itemList = itemList;
		this.itemAmountList = itemAmountList;
	}
	
	public void update(int mousex, int mousey, boolean leftMouseButtonPressed, boolean previousLeftMouseButtonPressed)
	{
		super.update(mousex, mousey, leftMouseButtonPressed, previousLeftMouseButtonPressed);
		
		int xShift = 0;
		int yShift = 0;
		for(int i = 0; i < itemList.length; i++)
		{
			if(x+(xShift+1)*itemWidth+(xShift*itemWidth)+itemWidth >= x+width/2)
			{
				xShift = 0;
				yShift++;
			}
			if(mousex > x+(xShift+1)*itemWidth+(xShift*itemWidth) && mousex < x+(xShift+1)*itemWidth+(xShift*itemWidth)+itemWidth &&
					mousey > y+itemHeight/2+(itemHeight*yShift*3) && mousey < y+itemHeight/2+(itemHeight*yShift*3)+itemHeight)
			{
				if(leftMouseButtonPressed && !previousLeftMouseButtonPressed)
				{
					if(itemList[i].getBasePrice() <= Bank.money && itemAmountList[i] != 0 && !gameClass.bottomRightWindowSelector.invent.isFull())
					{
						Bank.money -= itemList[i].getBasePrice();
						if(itemAmountList[i] > 0)
							itemAmountList[i]--;
						gameClass.bottomRightWindowSelector.invent.addItem(itemList[i]);
						if(itemAmountList[i] == 0)
						{
							//Add remove stuff here
							removeItemFromShop(itemList[i]);
						}
					} else if(itemList[i].getBasePrice() > Bank.money)
						gameClass.console.addText("Not enough money.");
					actionDone = true;
				}
				break;
			}
			xShift++;
		}
		if(!actionDone)
		{
			outerloop:
			for(int i = 0; i < gameClass.bottomRightWindowSelector.invent.getContents().length; i++)
			{
				for(int j = 0; j < gameClass.bottomRightWindowSelector.invent.getContents()[0].length; j++)
				{
					if(gameClass.bottomRightWindowSelector.invent.getContents()[i][j] != null && mousex >= x+width/2+(itemWidth/2*(i+1))+(itemWidth*i)
							&& mousex <= x+width/2+(itemWidth/2*(i+1))+(itemWidth*i)+itemWidth&& mousey >= y+(itemHeight/2*(j+1))+(itemHeight*j)
							&& mousey <= y+(itemHeight/2*(j+1))+(itemHeight*j)+itemHeight)
					{
						if(leftMouseButtonPressed && !previousLeftMouseButtonPressed)
						{
							addItemToShop(gameClass.bottomRightWindowSelector.invent.getContents()[i][j]);
							Bank.money += gameClass.bottomRightWindowSelector.invent.getContents()[i][j].getBasePrice();
							gameClass.bottomRightWindowSelector.invent.removeItemNotToFloor(i, j);
							actionDone = true;
						}
						break outerloop;
					}
				}
			}
		}
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		
		/**Draw divider line*/
		g.drawLine(x+width/2, y, x+width/2, y+height);
		
		int xShift = 0;
		int yShift = 0;
		for(int i = 0; i < itemList.length; i++)
		{
			g.setColor(Color.BLACK);
			if(x+(xShift+1)*itemWidth+(xShift*itemWidth)+itemWidth >= x+width/2)
			{
				xShift = 0;
				yShift++;
			}
			g.drawImage(itemList[i].getImage(), x+(xShift+1)*itemWidth+(xShift*itemWidth), y+itemHeight/2+(itemHeight*yShift*3), itemWidth, itemHeight, null);
			g.drawString((itemAmountList[i] >= 0 ? itemAmountList[i]+" in stock" : "Infinite"), x+(xShift+1)*itemWidth+(xShift*itemWidth)-itemWidth/3,
					y+itemHeight/2+itemHeight+itemHeight/2+(itemHeight*yShift*3));
			g.drawString(itemList[i].getBasePrice()+" coins", x+(xShift+1)*itemWidth+(xShift*itemWidth)-itemWidth/3,
					y+itemHeight/2+itemHeight*2+(itemHeight*yShift*3));
			xShift++;
		}
		
		for(int i = 0; i < gameClass.bottomRightWindowSelector.invent.getContents()[0].length; i++)
		{
			for(int j = 0; j < gameClass.bottomRightWindowSelector.invent.getContents().length; j++)
			{
				if(gameClass.bottomRightWindowSelector.invent.getContents()[j][i] != null)
				{
					if(gameClass.bottomRightWindowSelector.invent.getContents()[j][i] != null)
					{
						g.drawImage(gameClass.bottomRightWindowSelector.invent.getContents()[j][i].getImage(),
								x+width/2+(itemWidth/2*(j+1))+(itemWidth*j), y+(itemHeight/2*(i+1))+(itemHeight*i), itemWidth, itemHeight, null);
						g.drawString(gameClass.bottomRightWindowSelector.invent.getContents()[j][i].getBasePrice()+" coins",
								x+width/2+(itemWidth/2*(j+1))+(itemWidth*j), y+(itemHeight/2*(i+1))+(itemHeight*i)+(int)(itemHeight*1.35));
					}
				}
			}
		}
		g.drawString(Bank.money+" coins" ,x+width-100, y+20);
	}
	
	public void onClose()
	{
		for(int i = 0; i < gameClass.chunkList.size(); i++)
		{
			for(int e = 0; e < gameClass.chunkList.get(i).getEntityArray().length; e++)
			if(gameClass.chunkList.get(i).getEntityArray()[e] instanceof Shop)
			{
				Shop thisShop = (Shop)gameClass.chunkList.get(i).getEntityArray()[e];
				thisShop.setItems(itemList, itemAmountList);
				thisShop.setShop(false);
			}
		}
	}

	private void addItemToShop(Item itemToAdd)
	{
		for(int i = 0; i < itemList.length; i++)
		{
			if(itemList[i] == itemToAdd)
			{
				if(itemAmountList[i] != -1)
					itemAmountList[i]++;
				return;
			}
		}
		
		int itemListLength = itemList.length;
		Item[] newItemList = new Item[itemListLength+1];
		for(int i = 0; i < itemList.length; i++)
		{
			newItemList[i] = itemList[i];
		}
		newItemList[newItemList.length-1] = itemToAdd;
		
		int itemAmountListLength = itemAmountList.length;
		int[] newItemAmountList = new int[itemAmountListLength+1];
		for(int i = 0; i < itemAmountList.length; i++)
		{
			newItemAmountList[i] = itemAmountList[i];
		}
		newItemAmountList[newItemList.length-1] = 1;
		
		itemList = newItemList;
		itemAmountList = newItemAmountList;
	}
	
	private void removeItemFromShop(Item itemToRemove)
	{
		Item[] newItemList = new Item[itemList.length-1];
		int minusBy = 0;
		for(int i = 0; i < itemList.length; i++)
		{
			if(itemToRemove != itemList[i])
			{
				newItemList[i-minusBy] = itemList[i];
			} else
				minusBy = 1;
		}
		minusBy = 0;
		int[] newItemAmountList = new int[itemAmountList.length-1];
		for(int i = 0; i < itemAmountList.length; i++)
		{
			if(itemToRemove != itemList[i])
			{
				newItemAmountList[i-minusBy] = itemAmountList[i];
			} else
				minusBy = 1;
		}
		itemList = newItemList;
		itemAmountList = newItemAmountList;
	}
}
