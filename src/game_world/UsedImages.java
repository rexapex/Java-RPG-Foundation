package game_world;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

public class UsedImages
{
	private ArrayList<Image> imageList = new ArrayList<Image>();
	
	public ArrayList<Image> getImageList()
	{
		return imageList;
	}
	
	public void clear()
	{
		imageList.clear();
	}
	
	public void deleteImage(int id)
	{
		imageList.remove(id);
	}
	
	public void deleteImage(Image i)
	{
		imageList.remove(i);
	}
	
	public void addImage(Image i)
	{
		imageList.add(i);
	}
}
