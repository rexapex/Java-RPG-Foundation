package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import spell_hierarchy.Spell;
import utils.Utils;

public class ProjectileHandler
{
	private GameStartClass gameClass;
	
	private ArrayList<Integer> currentMagicString = new ArrayList<>();
	
	public ProjectileHandler(Image roman1, Image roman2, Image roman3, Image roman4, GameStartClass gameClass)
	{
		this.roman1 = roman1;
		this.roman2 = roman2;
		this.roman3 = roman3;
		this.roman4 = roman4;
		
		this.gameClass = gameClass;
		
		this.channelBarY = (int)(gameClass.getHeight()-GameStartClass.frameManager.scaleHeightOrY(100));
		this.channelBarWidth = (int)GameStartClass.frameManager.scaleHeightOrY(450);
		this.channelBarStartWidth = channelBarWidth;
		this.channelBarHeight = (int)GameStartClass.frameManager.scaleHeightOrY(50);
		
		magicImageWidthHeight = (int)GameStartClass.frameManager.scaleHeightOrY(64);
		magicImageStringStartX = (int)GameStartClass.frameManager.scaleHeightOrY(500);
	}
	
	private boolean prevOneKey, prevTwoKey, prevThreeKey, prevFourKey;
	public void update(boolean oneKey, boolean twoKey, boolean threeKey, boolean fourKey, boolean leftMouseButtonPressed,
			boolean previousLeftMouseButtonPressed, int mousex, int mousey)
	{
		/**Check if a spell is still casting and if not, reset it*/
		checkIfCast(mousex, mousey);
		
		/**Magic String Updating*/
		if(currentMagicString.size() < 4)
		{
			if(oneKey && !prevOneKey)
				currentMagicString.add(new Integer(1));
			else if(twoKey && !prevTwoKey)
				currentMagicString.add(new Integer(2));
			else if(threeKey && !prevThreeKey)
				currentMagicString.add(new Integer(3));
			else if(fourKey && !prevFourKey)
				currentMagicString.add(new Integer(4));
		}
		if(leftMouseButtonPressed && !previousLeftMouseButtonPressed)
		{
			if(gameClass.bottomRightWindowSelector.magic.getSpellBook() != null)
			{
				for(Spell spell : gameClass.bottomRightWindowSelector.magic.getSpellBook().getSpellList())
				{
					boolean identical = true;
					if(spell.getCastString().length == currentMagicString.size())
					{
						for(int i = 0; i < spell.getCastString().length; i++)
						{
							if(spell.getCastString()[i] != currentMagicString.get(i))
							{
								identical = false;
							}
						}
					} else
						identical = false;
					if(identical)
					{
						spell.startCasting(mousex, mousey, gameClass);
					}
				}
				currentMagicString.clear();
			} else
				currentMagicString.clear();
		}
		prevOneKey = oneKey;
		prevTwoKey = twoKey;
		prevThreeKey = threeKey;
		prevFourKey = fourKey;
	}
	
	private Image roman1, roman2, roman3, roman4;
	private int magicImageStringStartX, magicImageWidthHeight;
	public void paintMagicString(Graphics g)
	{
		for(int i = 0; i < currentMagicString.size(); i++)
		{
			Image imageToDraw = null;
			switch(currentMagicString.get(i))
			{
			case 1:
				imageToDraw = roman1;
				break;
			case 2:
				imageToDraw = roman2;
				break;
			case 3:
				imageToDraw = roman3;
				break;
			case 4:
				imageToDraw = roman4;
				break;
			}
			if(imageToDraw != null)
			{
				g.drawImage(imageToDraw, magicImageStringStartX+magicImageWidthHeight*i*2, gameClass.getHeight()-magicImageWidthHeight, magicImageWidthHeight, magicImageWidthHeight, null);
			}
		}
		
		if(casting)
		{
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(magicImageStringStartX, channelBarY, channelBarWidth, channelBarHeight);
			g.setColor(Color.BLACK);
			g.drawRect(magicImageStringStartX, channelBarY, channelBarStartWidth, channelBarHeight);
		}
	}
	private int channelBarY, channelBarWidth, channelBarHeight;
	private final int channelBarStartWidth;
	
	private ArrayList<Projectile> projectileList = new ArrayList<>();
	public void paintEntities(Graphics g, Graphics2D g2d)
	{
		for(int i = 0; i < projectileList.size(); i++)
		{
			projectileList.get(i).x += projectileList.get(i).xVelocity;
			projectileList.get(i).y += projectileList.get(i).yVelocity;
			
			g2d.rotate(Math.toRadians(90)+projectileList.get(i).angle, projectileList.get(i).x+gameClass.camerax, projectileList.get(i).y+gameClass.cameray);
			
			g2d.drawImage(projectileList.get(i).animation, (int)projectileList.get(i).x+gameClass.camerax, (int)projectileList.get(i).y+gameClass.cameray, null);
			
			g2d.rotate(-(Math.toRadians(90)+projectileList.get(i).angle), projectileList.get(i).x+gameClass.camerax, projectileList.get(i).y+gameClass.cameray);
			
			if(TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()-projectileList.get(i).spawnTime) >= projectileList.get(i).lifeTime)
			{
				projectileList.remove(i);
			}
		}
	}
	
	private Spell spellBeingCast;
	private long castStartTime;
	private boolean casting;
	public void setSpellBeingCast(Spell spell)
	{
		spellBeingCast = spell;
		casting = true;
		castStartTime = System.nanoTime();
	}
	
	public boolean isCasting()
	{
		return casting;
	}
	
	private void checkIfCast(int mousex, int mousey)
	{
		if(spellBeingCast != null)
		{
			if(TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()-castStartTime) >= spellBeingCast.getCastTime())
			{
				spellBeingCast.cast(gameClass.player1.getPermanentMiddleXPos(), gameClass.player1.getPermanentMiddleYPos(), gameClass);
				casting = false;
				spellBeingCast = null;
			} else
			{
				channelBarWidth = (int)Utils.scale(channelBarStartWidth, ((double)System.nanoTime()-(double)castStartTime)/1000000000, spellBeingCast.getCastTime());
			}
		}
	}
	
	public void addEntity(Image animation, int lifeTime, long spawnTime, double velocityX, double velocityY, double x, double y, float angle)
	{
		projectileList.add(new Projectile(animation, x, y, velocityX, velocityY, lifeTime, spawnTime, angle));
	}
	
	private class Projectile
	{
		public Image animation;
		public int lifeTime;
		public double xVelocity, yVelocity, x, y;
		public long spawnTime;
		public float angle;
		
		private Projectile(Image animation, double x, double y, double xVelocity, double yVelocity, int lifeTime, long spawnTime, float angle)
		{
			this.animation = animation;
			this.x = x;
			this.y = y;
			this.xVelocity = xVelocity;
			this.yVelocity = yVelocity;
			this.lifeTime = lifeTime;
			this.spawnTime = spawnTime;
			this.angle = angle;
		}
	}
}
