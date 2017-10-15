package ui_manager;

import item_hierarchy.SpellBookItem;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import main.GameStartClass;
import spell_hierarchy.Spell;

public class MagicDisplay
{
	private int x, y, width, height;
	
	private SpellBookItem spellBook = null;
	
	private Image readImage;
	
	private boolean uiOpen = false;
	
	private GameStartClass gameClass;
	
	public MagicDisplay(int x, int y, int width, int height, Image readImage, GameStartClass gameClass)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.readImage = readImage;
		
		this.gameClass = gameClass;
		
		SPELL_BOOK_DRAWX_POS = x+width/2-width/6;
		SPELL_BOOK_DRAWY_POS = y+height/2-width/6;
		SPELL_BOOK_DRAW_WIDTH = width/6*2;
		SPELL_BOOK_DRAW_HEIGHT = width/6*2;
		
		READ_DRAWX_POS = x+width/2-width/7;
		READ_DRAWY_POS = y+height/2-width/6+width/6*2+height/20;
		READ_DRAW_WIDTH = width/7*2;
		READ_DRAW_HEIGHT = width/10;
	}
	
	public void update(boolean leftMouseButtonPressed, boolean previousLeftMouseButtonPressed, boolean rightMouseButtonPressed,
			int mousex, int mousey)
	{
		if(mousex >= SPELL_BOOK_DRAWX_POS && mousex <= SPELL_BOOK_DRAWX_POS+SPELL_BOOK_DRAW_WIDTH && mousey >= SPELL_BOOK_DRAWY_POS &&
				mousey <= SPELL_BOOK_DRAWY_POS+SPELL_BOOK_DRAW_HEIGHT && !gameClass.bottomRightWindowSelector.invent.isFull() && leftMouseButtonPressed)
		{
			gameClass.bottomRightWindowSelector.invent.addItem(spellBook);
			spellBook = null;
		} else if(mousex >= READ_DRAWX_POS && mousex <= READ_DRAWX_POS+READ_DRAW_WIDTH && mousey >= READ_DRAWY_POS &&
				mousey <= READ_DRAWY_POS+READ_DRAW_HEIGHT && leftMouseButtonPressed && !previousLeftMouseButtonPressed &&
				spellBook != null && !uiOpen)
		{
			/**ArrayList<Image> spellNamesRunic = new ArrayList<>(), spellNamesEnglish = new ArrayList<>();
			ArrayList<Image[]> castOrders = new ArrayList<>();
			for(Spell spell : spellBook.getSpellList())
			{
				spellNamesRunic.add(spell.getSpellNameRunic());
				spellNamesEnglish.add(spell.getSpellNameEnglish());
				int[] castString = spell.getCastString();
				for(int i = 0; i < castString.length; i++)
				{
					switch(castString[i])
					{
					case 0:
					
						break;
					}
				}
			}
			gameClass.userInterfacesOpen.add(new MagicReadInterface(gameClass, gameClass.getWidth(), gameClass.getHeight(),
					(Image[])spellNamesRunic.toArray(), (Image[])spellNamesEnglish.toArray(), (Image[])castOrders.toArray()));
			uiOpen = true;*/
		}
	}
	
	private final int SPELL_BOOK_DRAWX_POS, SPELL_BOOK_DRAWY_POS, SPELL_BOOK_DRAW_WIDTH, SPELL_BOOK_DRAW_HEIGHT;
	private final int READ_DRAWX_POS, READ_DRAWY_POS, READ_DRAW_WIDTH, READ_DRAW_HEIGHT;
	
	public void paint(Graphics g)
	{
		/**Draw the bag*/
		g.setColor(Color.gray);
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		
		/**Draw the spell book*/
		if(spellBook != null)
			g.drawImage(spellBook.getImage(), SPELL_BOOK_DRAWX_POS, SPELL_BOOK_DRAWY_POS, SPELL_BOOK_DRAW_WIDTH, SPELL_BOOK_DRAW_HEIGHT, null);
		g.drawRect(SPELL_BOOK_DRAWX_POS, SPELL_BOOK_DRAWY_POS, SPELL_BOOK_DRAW_WIDTH, SPELL_BOOK_DRAW_HEIGHT);
		/**Draw the read icon*/
		g.drawImage(readImage, READ_DRAWX_POS, READ_DRAWY_POS, READ_DRAW_WIDTH, READ_DRAW_HEIGHT, null);
		g.drawRect(READ_DRAWX_POS, READ_DRAWY_POS, READ_DRAW_WIDTH, READ_DRAW_HEIGHT);
	}
	
	public void setUi(boolean open)
	{
		this.uiOpen = open;
	}
	
	public SpellBookItem getSpellBook()
	{
		return spellBook;
	}
	
	public void setSpellBook(SpellBookItem newSpellBook)
	{
		spellBook = newSpellBook;
	}
}
