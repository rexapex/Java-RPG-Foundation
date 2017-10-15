package sprite_generic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader
{
	public BufferedImage loadImage(String pathRelativeToThis) throws IOException
	{
		BufferedImage image = ImageIO.read(new File(pathRelativeToThis));
		return image;
	}
}
