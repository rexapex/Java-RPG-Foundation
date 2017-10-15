package main;

import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public interface I2DGame extends Runnable, MouseListener, MouseMotionListener, KeyListener
{
	public void loadContent();
	public void init();
	public void sync(int fps);
	public void paint(Graphics g);
	public void paintComponent(Graphics g);
}
