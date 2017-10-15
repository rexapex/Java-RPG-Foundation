package sprite_generic;

import java.awt.image.BufferedImage;

public class SpriteSheet
{
private BufferedImage spriteSheet;
	
	public SpriteSheet(BufferedImage spriteSheet)
	{
		this.spriteSheet = spriteSheet;
	}
	
	public BufferedImage grabSprite(int x, int y, int width, int height)
	{
		BufferedImage sprite = spriteSheet.getSubimage(x, y, width, height);
		return sprite;
	}
}
