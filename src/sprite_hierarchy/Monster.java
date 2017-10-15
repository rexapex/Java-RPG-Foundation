package sprite_hierarchy;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.GameStartClass;

public class Monster extends MovableSprite2D
{
	private BufferedImage currentSpriteImage;
	
	private float middleXPos, middleYPos;
	
	public Monster(BufferedImage[] stoodStillImages, BufferedImage[] moveRightImages, BufferedImage[] moveLeftImages, float xpos, float ypos,
			int width, int height, float speedIncrX, float speedIncrY, float topSpeedX, float topSpeedY, GameStartClass gameClass)
	{
		super(stoodStillImages, moveRightImages, moveLeftImages, xpos, ypos, width, height, speedIncrX, speedIncrY, topSpeedX, topSpeedY, gameClass);
	
		middleXPos = xpos + (float)width/2;
		middleYPos = ypos + (float)height/2;
	}
	
	public void update()
	{
		if(xpos+gameClass.camerax < gameClass.player1.getPermanentMiddleXPos() && xSpeed < topSpeedX)
		{
			xSpeed += speedIncrX;
			currentSpriteImage = moveRightAnimator.update();
		} else if(xpos+gameClass.camerax > gameClass.player1.getPermanentMiddleXPos() && xSpeed > -topSpeedX)
		{
			xSpeed -= speedIncrX;
			currentSpriteImage = moveLeftAnimator.update();
		} else
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
		if(ypos+gameClass.cameray < gameClass.player1.getPermanentYPos() && ySpeed < topSpeedY)
			ySpeed += speedIncrY;
		else if(ypos+gameClass.cameray > gameClass.player1.getPermanentYPos() && ySpeed > -topSpeedY)
			ySpeed -= speedIncrY;
		else
		{
			if(ySpeed > 0)
				ySpeed -= speedIncrY;
			else if(ySpeed < 0)
				ySpeed += speedIncrY;	
		}
		
		boolean applyMovement = true;
		outerloop:
		for(int i = 0; i < gameClass.chunkList.size(); i++)
		{
			for(int k = 0; k < gameClass.chunkList.get(i).getProperties().size(); k++)
			{
				if(gameClass.chunkList.get(i).getProperties().get(k) == 1)
				{
					if(xpos+xSpeed >= (gameClass.chunkList.get(i).getTileDimension().width*2*gameClass.chunkList.get(i).getPropertiesX().get(k))
							-(gameClass.chunkList.get(i).getPropertiesY().get(k)*gameClass.chunkList.get(i).getTileDimension().width) &&
							xpos+xSpeed < (gameClass.chunkList.get(i).getTileDimension().width*2*gameClass.chunkList.get(i).getPropertiesX().get(k))
								+gameClass.chunkList.get(i).getTileDimension().width*2 &&
							ypos+ySpeed >= (gameClass.chunkList.get(i).getTileDimension().height*gameClass.chunkList.get(i).getPropertiesY().get(k)) &&
							ypos+ySpeed < (gameClass.chunkList.get(i).getTileDimension().height*gameClass.chunkList.get(i).getPropertiesY().get(k))
								+gameClass.chunkList.get(i).getTileDimension().height)
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
	}
	
	public void applyMovement()
	{
		xpos += xSpeed;
		ypos += ySpeed;
	}
	
	public void paint(Graphics g)
	{
		g.drawImage(currentSpriteImage, (int)xpos+gameClass.camerax, (int)ypos+gameClass.cameray, width, height, null);
	}
}
