package spell_hierarchy;

import java.awt.Image;

import utils.Utils;

import main.GameStartClass;

public class CastAtClickSpell extends OffensiveSpell
{
	private int cameraStartX, cameraStartY;
	
	public CastAtClickSpell(int[] castString, int baseDamage, int lifeTime, Image animation, float castTimeSecs, Image spellNameRunic, Image spellNameEnglish)
	{
		super(castString, baseDamage, lifeTime, animation, castTimeSecs, spellNameRunic, spellNameEnglish);
	}
	
	public void startCasting(int mousex, int mousey, GameStartClass gameClass)
	{
		if(!gameClass.projectileHandler.isCasting())
			gameClass.projectileHandler.setSpellBeingCast(this);
		castAtX = mousex;
		castAtY = mousey;
		cameraStartX = gameClass.camerax;
		cameraStartY = gameClass.cameray;
	}
	
	public void cast(int playerX, int playerY, GameStartClass gameClass)
	{
		gameClass.projectileHandler.addEntity(animation, lifeTime, System.nanoTime(), 0, 0,
				castAtX-cameraStartX, castAtY-cameraStartY, (float)Math.toRadians(-90));
	}
}
