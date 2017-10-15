package game_world_entities;

import item_hierarchy.EToolGrade;
import item_hierarchy.EToolType;
import item_hierarchy.Item;
import item_hierarchy.ToolItem;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import player_data.Skills;

import main.GameStartClass;

public class Entity
{
	protected GameStartClass gameClass;
	
	private BufferedImage aliveImage, deadImage;
	protected int[] xpos, ypos, width, height;
	protected int xGrabRange, yGrabRange;
	protected boolean[] alive;
	
	private int[] health;
	private int baseHealth;
	
	private int respawnTime;
	protected long[] deadAtTime;
	
	protected Item returnedItem;
	protected int rewardedXp;
	
	protected EToolType requiredItem;
	protected EToolGrade requiredGrade;
	
	private Class<Skills> skillClass = player_data.Skills.class;
	private Method rewardMethod;
	
	public Entity(BufferedImage aliveImage, BufferedImage deadImage, int rewardedXp, String rewardMethodName, Item returnedItem, EToolType requiredItem, EToolGrade requiredGrade,
			int respawnTimeSeconds, int health , int[] xpos, int[] ypos, int[] width, int[] height, int xGrabRange, int yGrabRange,
			GameStartClass gameClass)
	{
		this.gameClass = gameClass;
		
		this.aliveImage = aliveImage;
		this.deadImage = deadImage;
		
		this.returnedItem = returnedItem;
		this.rewardedXp = rewardedXp;
		
		this.requiredItem = requiredItem;
		this.requiredGrade = requiredGrade;
		
		this.respawnTime = respawnTimeSeconds;
		
		this.xpos = xpos;
		this.ypos = ypos;
		this.width = width;
		this.height = height;
		
		this.xGrabRange = xGrabRange;
		this.yGrabRange = yGrabRange;
		
		baseHealth = health;
		this.health = new int[xpos.length];
		for(int i = 0; i < this.health.length; i++)
		{
			this.health[i] = health;
		}
		
		deadAtTime = new long[xpos.length];
		
		alive = new boolean[xpos.length];
		for(int i = 0; i < alive.length; i++)
			alive[i] = true;
		
		if(rewardMethodName != null)
		{
			try {
				rewardMethod = skillClass.getMethod(rewardMethodName, long.class);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				System.exit(1);
			} catch (SecurityException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	protected int i = 0;
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
				health[i] -= itemToUse.getGrade().getHealthReduction();
				if(health[i] <= 0 && !gameClass.bottomRightWindowSelector.invent.isFull())
					return onItemInteraction();
			} else if(!alive[i])
			{
				if(TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()-deadAtTime[i]) >= respawnTime)
				{
					alive[i] = true;
					health[i] = baseHealth;
				}
			}
		}
		return null;
	}
	
	public void paint(Graphics g, int camerax, int cameray)
	{
		for(int i = 0; i < xpos.length; i++)
		{
			if(alive[i])
				g.drawImage(aliveImage, xpos[i]+camerax, ypos[i]+cameray, width[i], height[i], null);
			else
				g.drawImage(deadImage, xpos[i]+camerax, ypos[i]+cameray, width[i], height[i], null);
		}
	}
	
	public void paintSingle(Graphics g, int camerax, int cameray, int element)
	{
		if(alive[element])
			g.drawImage(aliveImage, xpos[element]+camerax, ypos[element]+cameray, width[element], height[element], null);
		else
			g.drawImage(deadImage, xpos[element]+camerax, ypos[element]+cameray, width[element], height[element], null);
	}
	
	public Item onItemInteraction()
	{
		alive[i] = false;
		deadAtTime[i] = System.nanoTime();
		try {
			rewardMethod.invoke(gameClass.skills, rewardedXp);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return returnedItem;
	}
	
	public EToolType getRequiredToolType()
	{
		return requiredItem;
	}
	
	public int[] getX()
	{
		return xpos;
	}
	
	public int[] getY()
	{
		return ypos;
	}
	
	public int[] getWidth()
	{
		return width;
	}
	
	public int[] getHeight()
	{
		return height;
	}
	
	public int getAmountOf()
	{
		return xpos.length;
	}
}
