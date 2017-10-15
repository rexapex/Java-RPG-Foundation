package ui_manager;

import java.awt.Graphics;
import java.awt.Image;

import main.GameStartClass;

public class MagicReadInterface extends UserInterface
{
	private Image[] spellNamesRunic, spellNamesEnglish, castOrders;
	private int firstImageX, firstImageY, imageWidth, imageHeight;
	
	public MagicReadInterface(GameStartClass gameClass, int screenWidth, int screenHeight, Image[] spellNamesRunic,
			Image[]spellNamesEnglish, Image[] castOrders)
	{
		super(gameClass, screenWidth, screenHeight);
		
		this.spellNamesEnglish = spellNamesEnglish;
		this.spellNamesRunic = spellNamesRunic;
		this.castOrders = castOrders;
		
		this.firstImageX = 
		this.firstImageY = 
		this.imageWidth = 
		this.imageHeight = 0;
	}
	
	public void update(int mousex, int mousey, boolean leftMouseButtonPressed, boolean previousLeftMouseButtonPressed)
	{
		super.update(mousex, mousey, leftMouseButtonPressed, previousLeftMouseButtonPressed);
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		
		for(int i = 0; i < spellNamesRunic.length; i++)
		{
			g.drawImage(spellNamesRunic[i], firstImageX, firstImageY+(imageHeight*i*3), imageWidth, imageHeight, null);
			g.drawImage(spellNamesEnglish[i], firstImageX, firstImageY+(imageHeight*(i+1)), imageWidth, imageHeight, null);
			g.drawImage(castOrders[i], firstImageX+imageWidth, firstImageY+(imageHeight*i*3), imageWidth, imageHeight, null);
		}
	}
	
	public void onClose()
	{
		gameClass.bottomRightWindowSelector.magic.setUi(false);
	}
}
