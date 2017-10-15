package game_world;

import item_hierarchy.EToolGrade;
import item_hierarchy.EToolType;
import item_hierarchy.Item;
import item_hierarchy.SpellBookItem;
import item_hierarchy.ToolItem;
import spell_hierarchy.CastAtClickSpell;
import spell_hierarchy.SkillShotSpell;
import spell_hierarchy.Spell;

public class GameItems
{
	/**Item Constructor takes (Image image, String name, String examine_text, int base_price)*/
	public static final Item none = new ToolItem(null, "Nothing", "Nothing Here", 0, EToolType.NONE, EToolGrade.NONE);
	
	public static final Item sword = new Item(null, "Sword", "A sword made of stone", 50);
	public static final Item bow = new Item(null, "Bow", "A bow made from cheap materials", 70);
	public static final Item arrow = new Item(null, "Arrow", "Used with a bow", 5);
	
	/**Resources*/
		/**Logs*/
		public static final Item logOak = new Item(null, "Oak Log", "A log from an oak tree", 4);
		public static final Item logBirch = new Item(null, "Birch Log", "A log from a birch tree", 12);
		public static final Item logCedar = new Item(null, "Cedar Log", "A log from a cedar tree", 34);
		public static final Item logMaple = new Item(null, "Maple Log", "A log from a maple tree", 42);
		/**Ores*/
		public static final Item oreCopper = new Item(null, "Copper Ore", "Contains some copper", 16);
		public static final Item oreTin = new Item(null, "Tin Ore", "Contains some tin", 16);
		public static final Item oreIron = new Item(null, "Iron Ore", "Contains some iron", 16);
		
	
	/**Tools*/
		/**Axes*/
		public static final Item axeBronze = new ToolItem(null, "Bronze Axe", "Used to chop down trees", 20, EToolType.AXE, EToolGrade.BRONZE);
		public static final Item axeIron = new ToolItem(null, "Iron Axe", "Used to chop down trees", 40, EToolType.AXE, EToolGrade.IRON);
		public static final Item axeSteel = new ToolItem(null, "Steel Axe", "A slightly better way of chopping down trees", 200, EToolType.AXE, EToolGrade.STEEL);
		/**Picks*/
		public static final Item pickBronze = new ToolItem(null, "Bronze Pick", "Used to mine rocks", 50, EToolType.PICK, EToolGrade.BRONZE);
		public static final Item pickIron = new ToolItem(null, "Iron Pick", "Used to mine rocks", 100, EToolType.PICK, EToolGrade.IRON);
		public static final Item pickSteel = new ToolItem(null, "Steel Pick", "A slightly better way of mining rocks", 450, EToolType.PICK, EToolGrade.STEEL);

	/**Spells*/
		public static final Spell spellFireBall = new SkillShotSpell(new int[]{1, 2}, 5, null, 0.01f, 2, 1.5f, null, null);
		public static final Spell spellFireStrike = new CastAtClickSpell(new int[]{1, 3}, 8, 3, null, 3, null, null);
		
	/**Spell Books*/
		public static final Item spellBookNoviceFire = new SpellBookItem(null, "Novice Fire Spell Book", "A spell book of novice fire spells", 0,
				new Spell[]{spellFireStrike, spellFireBall});
}
