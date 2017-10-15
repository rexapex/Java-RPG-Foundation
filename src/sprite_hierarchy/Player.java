package sprite_hierarchy;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.GameStartClass;

public class Player extends MovableSprite2D
{
	private BufferedImage currentSpriteImage;
	
	private float middleXPos, middleYPos, feetYPos;
	private int permanentX, permanentY, permanentFeetYPos;
	
	public Player(BufferedImage[] stoodStillImages, BufferedImage[] moveRightImages, BufferedImage[] moveLeftImages,
			float xpos, float ypos, int width, int height, float speedIncrX, float speedIncrY, float topSpeedX, float topSpeedY, GameStartClass gameClass)
	{
		super(stoodStillImages, moveRightImages, moveLeftImages, xpos, ypos, width, height, speedIncrX, speedIncrY, topSpeedX, topSpeedY, gameClass);
		
		currentSpriteImage = stoodStillImages[0];
		
		permanentX = (gameClass.getWidth()/2)-width/2;
		permanentY = (gameClass.getHeight()/2)-height/2;
		
		middleXPos = xpos + (float)width/2;
		middleYPos = ypos + (float)height/2;
		
		feetYPos = (ypos+height)-GameStartClass.frameManager.scaleHeightOrY(13);
		
		permanentFeetYPos = (int)((permanentY+height)-GameStartClass.frameManager.scaleHeightOrY(13));
	}
	
	public void update(boolean leftKeyPressed, boolean rightKeyPressed, boolean upKeyPressed, boolean downKeyPressed, int camerax, int cameray)
	{
		if(leftKeyPressed && xSpeed > -topSpeedX)
		{
			xSpeed -= speedIncrX;
			currentSpriteImage = moveLeftAnimator.update();
		}
		else if(rightKeyPressed && xSpeed < topSpeedX)
		{
			xSpeed += speedIncrX;
			currentSpriteImage = moveRightAnimator.update();
		}
		else
		{
			if(xSpeed > 0)
			{
				xSpeed -= speedIncrX;
				if(xSpeed == 0 && ySpeed == 0)
					currentSpriteImage = stoodStillImages[0];
			}
			else if(xSpeed < 0)
			{
				xSpeed += speedIncrX;
				if(xSpeed == 0 && ySpeed == 0)
					currentSpriteImage = stoodStillImages[1];
			}
		}
		if(downKeyPressed && ySpeed < topSpeedY)
			ySpeed += speedIncrY;
		else if(upKeyPressed && ySpeed > -topSpeedY)
			ySpeed -= speedIncrY;
		else
		{
			if(ySpeed > 0)
				ySpeed -= speedIncrY;
			else if(ySpeed < 0)
				ySpeed += speedIncrY;
		}
		
		middleXPos = xpos + (float)width/2;
		middleYPos = ypos + (float)height/2;
		
		boolean applyMovement = true;
		outerloop:
		for(int i = 0; i < gameClass.chunkList.size(); i++)
		{
			for(int k = 0; k < gameClass.chunkList.get(i).getProperties().size(); k++)
			{
				if(gameClass.chunkList.get(i).getProperties().get(k) == 1)
				{
					if(gameClass.player1.getPermanentMiddleXPos()+xSpeed >= (gameClass.chunkList.get(i).getTileDimension().width*2*gameClass.chunkList.get(i).getPropertiesX().get(k))
								-(gameClass.chunkList.get(i).getPropertiesY().get(k)*gameClass.chunkList.get(i).getTileDimension().width)+camerax &&
							gameClass.player1.getPermanentMiddleXPos()+xSpeed < (gameClass.chunkList.get(i).getTileDimension().width*2*gameClass.chunkList.get(i).getPropertiesX().get(k))
								-(gameClass.chunkList.get(i).getPropertiesY().get(k)*gameClass.chunkList.get(i).getTileDimension().width)+camerax+gameClass.chunkList.get(i).getTileDimension().width*3 &&
							gameClass.player1.getPermanentYFeetPos()+ySpeed >= (gameClass.chunkList.get(i).getTileDimension().height*gameClass.chunkList.get(i).getPropertiesY().get(k))
								+cameray &&
							gameClass.player1.getPermanentYFeetPos()+ySpeed < (gameClass.chunkList.get(i).getTileDimension().height*gameClass.chunkList.get(i).getPropertiesY().get(k))
								+cameray+gameClass.chunkList.get(i).getTileDimension().height)
					{
						xSpeed = 0;
						ySpeed = 0;
						applyMovement = false;
						break outerloop;
					}
				}
			}
		}
		if(applyMovement)
			applyMovement();
		
		feetYPos = ypos+height-GameStartClass.frameManager.scaleHeightOrY(8);
	}
	
	public void applyMovement()
	{
		xpos += xSpeed;
		ypos += ySpeed;
		gameClass.camerax -= xSpeed;
		gameClass.cameray -= ySpeed;
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.drawImage(currentSpriteImage, permanentX, permanentY, width, height, null);
	}
	
	public float getFeetYPos()
	{
		return feetYPos;
	}
	
	public int getPermanentXPos()
	{
		return permanentX;
	}
	
	public int getPermanentYPos()
	{
		return permanentY;
	}
	
	public int getPermanentYFeetPos()
	{
		return permanentFeetYPos;
	}
	
	public int getPermanentMiddleXPos()
	{
		return permanentX+width/2;
	}
	
	public int getPermanentMiddleYPos()
	{
		return permanentY+height/2;
	}
	
	public float getMiddleXPos()
	{
		return middleXPos;
	}
	
	public float getMiddleYPos()
	{
		return middleYPos;
	}
	
	public void setXSpeed(int xSpeed)
	{
		this.xSpeed = xSpeed;
	}
	
	public void setYSpeed(int ySpeed)
	{
		this.ySpeed = ySpeed;
	}
}
