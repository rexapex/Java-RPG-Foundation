package ui_manager;

import java.awt.Color;
import java.awt.Graphics;

import item_hierarchy.EquipableItem;

public class EquipedItems
{
	private int x, y, width, height;
	
	private EquipableItem head;
	private EquipableItem body;
	private EquipableItem legs;
	private EquipableItem feet;
	private EquipableItem mainHand;
	private EquipableItem offHand;
	
	public EquipedItems(int x, int y, int width, int height,
			EquipableItem head, EquipableItem body, EquipableItem legs, EquipableItem feet, EquipableItem mainHand, EquipableItem offHand)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.head = head;
		this.body = body;
		this.legs = legs;
		this.feet = feet;
		this.mainHand = mainHand;
		this.offHand = offHand;
		
		this.boxHeight = height/9;
		this.boxWidth = boxHeight;
	}
	
	public void update(boolean leftMouseButtonPressed, boolean rightMouseButtonPressed, int mousex, int mousey)
	{
		
	}
	
	private int boxWidth, boxHeight;
	public void paint(Graphics g)
	{
		/**Draw the bag*/
		g.setColor(Color.gray);
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		
		g.drawRect(x+((width/2)-(boxWidth/2)), y+(height/9), boxWidth, boxHeight);
		g.drawRect(x+((width/2)-(boxWidth/2)), y+(height/9*3), boxWidth, boxHeight);
		g.drawRect(x+((width/2)-(boxWidth/2)), y+(height/9*4+(height/9)), boxWidth, boxHeight);
		g.drawRect(x+((width/2)-(boxWidth/2)), y+(height/9*7), boxWidth, boxHeight);
		
		g.drawRect(x+(width/4)-boxWidth/2, y+(height/9*3), boxWidth, boxHeight);
		g.drawRect(x+(width/4*3)-boxWidth/2, y+(height/9*3), boxWidth, boxHeight);
	}
	
	public void setHead(EquipableItem head)
	{
		this.head = head;
	}
	
	public void setBody(EquipableItem body)
	{
		this.body = body;
	}
	
	public void setLegs(EquipableItem legs)
	{
		this.legs = legs;
	}
	
	public void setFeet(EquipableItem feet)
	{
		this.feet = feet;
	}
	
	public void setMainHand(EquipableItem mainHand)
	{
		this.mainHand = mainHand;
	}
	
	public void setOffHand(EquipableItem offHand)
	{
		this.offHand = offHand;
	}
}
