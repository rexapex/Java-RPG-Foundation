package item_hierarchy;

import java.awt.image.BufferedImage;

import spell_hierarchy.Spell;

import main.GameStartClass;

public class SpellBookItem extends UsableItem
{
	private Spell[] spellList;
	
	public SpellBookItem(BufferedImage itemImage, String name, String examineText, int basePrice, Spell[] spellList)
	{
		super(itemImage, name, examineText, basePrice);
		
		this.spellList = spellList;
	}
	
	public void onItemUse(GameStartClass gameClass, int indexX, int indexY)
	{
		SpellBookItem oldItem = null;
		if(gameClass.bottomRightWindowSelector.magic.getSpellBook() != null)
			oldItem = gameClass.bottomRightWindowSelector.magic.getSpellBook();
		gameClass.bottomRightWindowSelector.magic.setSpellBook((SpellBookItem)gameClass.bottomRightWindowSelector.invent.getContents()[indexX][indexY]);
		gameClass.bottomRightWindowSelector.invent.getContents()[indexX][indexY] = null;
		gameClass.bottomRightWindowSelector.invent.addItem(oldItem);
	}
	
	public Spell[] getSpellList()
	{
		return spellList;
	}
}
