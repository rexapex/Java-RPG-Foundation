package sprite_hierarchy;

import java.awt.image.BufferedImage;

import sprite_generic.Animator;

import main.GameStartClass;
import main.I2DGame;

public abstract class MovableSprite2D extends Sprite2D
{
	protected Animator moveLeftAnimator, moveRightAnimator;
	protected BufferedImage[] moveRightImages, moveLeftImages;
	protected float speedIncrX, speedIncrY, xSpeed, ySpeed, topSpeedX, topSpeedY;
	
	public MovableSprite2D(BufferedImage[] stoodStillImages, BufferedImage[] moveRightImages, BufferedImage[] moveLeftImages,
			float xpos, float ypos, int width, int height, float speedIncrX, float speedIncrY, float topSpeedX, float topSpeedY, GameStartClass gameClass)
	{
		super(stoodStillImages, xpos, ypos, width, height, gameClass);
		
		this.moveRightImages = moveRightImages;
		this.moveLeftImages = moveLeftImages;
		
		this.topSpeedX = topSpeedY;
		this.topSpeedY = topSpeedY;
		this.speedIncrX = speedIncrX;
		this.speedIncrY = speedIncrY;
		
		this.moveRightAnimator = new Animator(moveRightImages, 0);
		this.moveLeftAnimator = new Animator(moveLeftImages, 0);
	}
}
