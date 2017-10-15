package spell_hierarchy;

import java.awt.Image;

import main.GameStartClass;

public abstract class Spell
{
	private int[] castString;
	
	protected Image animation;
	
	protected float castTimeSecs;
	
	private Image spellNameRunic, spellNameEnglish;

	public Spell(int[] castString, Image animation, float castTimeSecs, Image spellNameRunic, Image spellNameEnglish)
	{
		this.castString = castString;
		this.animation = animation;
		this.castTimeSecs = castTimeSecs;
		
		this.spellNameRunic = spellNameRunic;
		this.spellNameEnglish = spellNameEnglish;
	}
	
	public int[] getCastString()
	{
		return castString;
	}
	
	public void setAnimation(Image animation)
	{
		this.animation = animation;
	}
	
	public float getCastTime()
	{
		return castTimeSecs;
	}
	
	public Image getSpellNameRunic()
	{
		return spellNameRunic;
	}

	public Image getSpellNameEnglish()
	{
		return spellNameEnglish;
	}
	
	public abstract void startCasting(int mousex, int mousey, GameStartClass gameClass);
	public abstract void cast(int playerX, int playerY, GameStartClass gameClass);
}
