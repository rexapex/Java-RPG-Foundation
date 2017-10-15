package game_world;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import game_world_entities.Entity;
import item_hierarchy.ToolItem;
import main.GameStartClass;

public class Chunk
{
	private UsedImages chunkImages;
	private int[][] layer;
	private int tileWidth, tileHeight;
	
	private Entity[] entityArray;
	
	private ArrayList<Integer> tileProperties = new ArrayList<>();
	private ArrayList<Integer> tilePropertiesX = new ArrayList<>();
	private ArrayList<Integer> tilePropertiesY = new ArrayList<>();
	
	public DroppedItems droppedItems;
	
	private GameStartClass gameClass;
	
	public Chunk(int[][] layer, int tileWidth, int tileHeight, UsedImages chunkImages, Entity[] entityArray, GameStartClass gameClass)
	{
		this.gameClass = gameClass;
		this.chunkImages = chunkImages;
		this.layer = layer;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		
		this.entityArray = entityArray;
		
		this.droppedItems = new DroppedItems(gameClass, (int)GameStartClass.frameManager.scaleWidthOrX(32), (int)GameStartClass.frameManager.scaleHeightOrY(32),
				(int)GameStartClass.frameManager.scaleWidthOrX(75), (int)GameStartClass.frameManager.scaleHeightOrY(75));
	}
	
	public void update(int camerax, int cameray, int mousex, int mousey, boolean leftMouseButtonPressed, boolean previousLeftMouseButtonPressed)
	{
		droppedItems.update(leftMouseButtonPressed, mousex, mousey, camerax, cameray);
		
		for(Entity e : entityArray)
		{
			ToolItem reqItem = null;
			if(e.getRequiredToolType() != null)
			{
				switch(e.getRequiredToolType())
				{
				case AXE:
					reqItem = gameClass.bottomRightWindowSelector.toolkit.getAxe();
					break;
				case PICK:
					reqItem = gameClass.bottomRightWindowSelector.toolkit.getPick();
					break;
				case HAMMER:
					reqItem = gameClass.bottomRightWindowSelector.toolkit.getHammer();
					break;
				case FISHING_ROD:
					reqItem = gameClass.bottomRightWindowSelector.toolkit.getFishingRod();
					break;
				default:
					reqItem = (ToolItem) GameItems.none;
					break;
				}
			}
			gameClass.bottomRightWindowSelector.invent.addItem(e.update(leftMouseButtonPressed, previousLeftMouseButtonPressed,
					false, reqItem, mousex, mousey, camerax, cameray));
		}
	}
	
	public void paint(Graphics g, int camerax, int cameray)
	{
		int startX;
		if((startX = -camerax/(tileWidth*3)) < 0)
				startX = 0;
		int endX;
		if((endX = (-camerax+gameClass.getWidth())/(tileWidth*3)) >= layer.length);
			endX = layer.length-1;
		int startY;
		if((startY = -cameray/tileHeight) < 0)
			startY = 0;
		int endY;
		if((endY = (-cameray+gameClass.getHeight()+tileHeight*3)/tileHeight) >= layer[0].length)
			endY = layer[0].length-1;
		for(int j = startY; j < endY; j++)
		{
			for(int i = startX; i < endX; i++)
			{
				/**Draw the image got from the index of the images list which matches the value in this index of the array-list*/
				boolean painted = false;
				for(int k = 0; k < tileProperties.size(); k++)
				{
					if(tilePropertiesX.get(k) == i && tilePropertiesY.get(k) == j && tileProperties.get(k) == 2)
					{
						g.drawImage(chunkImages.getImageList().get(layer[i][j]), (tileWidth*2*i)-(j*tileWidth)+camerax, (tileHeight*j)-(tileHeight*3)+cameray,
							tileWidth*3, tileHeight*4, null);
						painted = true;
					}
				}
				if(!painted)
					g.drawImage(chunkImages.getImageList().get(layer[i][j]), (tileWidth*2*i)-(j*tileWidth)+camerax, (tileHeight*j)+cameray,
							tileWidth*3, tileHeight, null);
				for(Entity e : entityArray)
				{
					for(int l = 0; l < e.getAmountOf(); l++)
					{
						if((int)e.getX()[l]+e.getWidth()[l] > (tileWidth*2*i)-(j*tileWidth) && (int)e.getX()[l]+e.getWidth()[l] < (tileWidth*2*i)-(j*tileWidth)+tileWidth*3 &&
								(int)e.getY()[l]+e.getHeight()[l] > (tileHeight*j) && (int)e.getY()[l]+e.getHeight()[l] < (tileHeight*j)+tileHeight)
						{
							e.paintSingle(g, camerax, cameray, l);
						}
					}
				}
				if((int)(gameClass.player1.getPermanentXPos()+gameClass.player1.getWidth()) >= (tileWidth*2*i)-(j*tileWidth)+camerax &&
						(int)(gameClass.player1.getPermanentXPos()+gameClass.player1.getWidth()) < (tileWidth*2*i)-(j*tileWidth)+(tileWidth*3)+camerax &&
						(int)gameClass.player1.getPermanentYFeetPos() >= (tileHeight*j)+cameray &&
						(int)gameClass.player1.getPermanentYFeetPos() < (tileHeight*j)+tileHeight+cameray)
				{
					gameClass.player1.paint(g);
				}
			}
		}
		droppedItems.paint(g, camerax, cameray);
	}
	
	public Entity[] getEntityArray()
	{
		return entityArray;
	}
	
	public int[][] getLayer()
	{
		return layer;
	}
	
	public ArrayList<Integer> getProperties()
	{
		return tileProperties;
	}
	
	public ArrayList<Integer> getPropertiesX()
	{
		return tilePropertiesX;
	}
	
	public ArrayList<Integer> getPropertiesY()
	{
		return tilePropertiesY;
	}
	
	public void setProperties(ArrayList<Integer> properties)
	{
		this.tileProperties = properties;
	}
	
	public void setPropertiesX(ArrayList<Integer> propertiesX)
	{
		this.tilePropertiesX = propertiesX;
	}
	
	public void setPropertiesY(ArrayList<Integer> propertiesY)
	{
		this.tilePropertiesY = propertiesY;
	}
	
	public Dimension getTileDimension()
	{
		return new Dimension(tileWidth, tileHeight);
	}
	
	public void setFullLayer(int tileType)
	{
		for(int i = 0; i < layer.length; i++)
		{
			for(int j = 0; j < layer[0].length; j++)
			{
				layer[i][j] = tileType;
			}
		}
	}
}
