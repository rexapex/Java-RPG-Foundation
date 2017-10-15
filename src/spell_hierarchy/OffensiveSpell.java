package spell_hierarchy;

import java.awt.Image;

public abstract class OffensiveSpell extends Spell
{
	protected int baseDamage, lifeTime;
	protected int castAtX, castAtY;
	
	public OffensiveSpell(int[] castString, int baseDamage, int lifeTime, Image animation, float castTimeSecs, Image spellNameRunic,
			Image spellNameEnglish)
	{
		super(castString, animation, castTimeSecs, spellNameRunic, spellNameEnglish);
		
		this.baseDamage = baseDamage;
		this.lifeTime = lifeTime;
	}
}
