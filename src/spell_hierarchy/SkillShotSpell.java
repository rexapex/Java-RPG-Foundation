package spell_hierarchy;

import java.awt.Image;

import utils.Utils;

import main.GameStartClass;

public class SkillShotSpell extends OffensiveSpell
{
	private float velocity;
	
	public SkillShotSpell(int[] castString, int baseDamage, Image animation, float velocity, int lifeTime, float castTimeSecs, Image spellNameRunic, Image spellNameEnglish)
	{
		super(castString, baseDamage, lifeTime, animation, castTimeSecs, spellNameRunic, spellNameEnglish);
		
		this.velocity = velocity;
	}
	
	public void startCasting(int mousex, int mousey, GameStartClass gameClass)
	{
		if(!gameClass.projectileHandler.isCasting())
			gameClass.projectileHandler.setSpellBeingCast(this);
		castAtX = mousex;
		castAtY = mousey;
	}
	
	public void cast(int playerX, int playerY, GameStartClass gameClass)
	{
		double adj = castAtX-playerX;
		double opp = castAtY-playerY;
		double origOpp = opp;
		//opp = Utils.findSmallestRatio(opp, adj, true);
		//adj = Utils.findSmallestRatio(origOpp, adj, false);
		System.out.println(Utils.findSmallestRatio(origOpp, adj, false)+" "+Utils.findSmallestRatio(origOpp, adj, true));
		System.out.println(adj*velocity+" "+opp*velocity);
		gameClass.projectileHandler.addEntity(animation, lifeTime, System.nanoTime(), adj*velocity, opp*velocity,
				playerX-gameClass.camerax, playerY-gameClass.cameray, Utils.trigForAngle(opp, adj));
	}
}
