package main;

import game_world.Chunk;
import game_world.GameItems;
import game_world.UsedImages;
import game_world_entities.Entity;
import game_world_entities.Shop;
import item_hierarchy.EToolGrade;
import item_hierarchy.EToolType;
import item_hierarchy.Item;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import player_data.Skills;
import sprite_generic.BufferedImageLoader;
import sprite_generic.SpriteSheet;
import sprite_hierarchy.Monster;
import sprite_hierarchy.Player;
import ui_manager.BottomRightWindowSelector;
import ui_manager.Console;
import ui_manager.UserInterface;

/**Game and graphics by James Sugden*/
public class GameStartClass extends JFrame implements I2DGame
{
	public static GameState gameState = GameState.LOADING;
	public static FrameManager frameManager;
	
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	public ArrayList<UserInterface> userInterfacesOpen = new ArrayList<>();
	
	public GameStartClass()
	{
		/**Set up display*/
		super.setTitle("Unamed 2D RPG Game");
		super.setSize((int)toolkit.getScreenSize().getWidth(), (int)toolkit.getScreenSize().getHeight());
		//super.setSize(1366, 768);
		//super.setSize(683, 384);
		super.setResizable(false);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		super.setUndecorated(true);
		super.setVisible(true);
		
		/**Add event listeners*/
		super.addMouseListener(this);
		super.addMouseMotionListener(this);
		super.addKeyListener(this);
		
		/**Goes before initialization because it is needed in load content method*/
		frameManager = new FrameManager(1366, 768, this.getWidth(), this.getHeight());
		/**Load content/resources and then initialize the game world*/
		loadContent();
		init();
		
		/**Content loaded and game initialized, move to login phase*/
		//gameState = GameState.LOGIN;

		gameState = GameState.INGAME;
		setBackground(Color.BLACK);
	}
	
	
	
	private ArrayList<BufferedImage> playerWalkingLeftImages = new ArrayList<>();
	private ArrayList<BufferedImage> playerWalkingRightImages = new ArrayList<>();
	private ArrayList<BufferedImage> playerStoodStillImages = new ArrayList<>();
	
	private ArrayList<BufferedImage> mapTileImages = new ArrayList<>();
	private BufferedImage emptyTile;
	
	private BufferedImage entityTreeAliveImage, entitiyTreeDeadImage, entityCopperOreAliveImage, entityOreDeadImage;
	private BufferedImage inventImage, equipedItemsImage, toolkitImage, magicImage, questImage;
	private BufferedImage readImage;
	private Image roman1, roman2, roman3, roman4;
	public void loadContent()
	{
		BufferedImageLoader imageLoader = new BufferedImageLoader();
		
		try {
			loadingIcon = new ImageIcon("res/images/titles/Loading_Animation.gif");
			repaint();
			
			/**Player animation image loading*/
			BufferedImage playerSpriteSheetImage = imageLoader.loadImage("res/images/Warrior.png");
			SpriteSheet playerSpriteSheet = new SpriteSheet(playerSpriteSheetImage);
			for(int j = 0; j < 8; j++)
				playerWalkingLeftImages.add(playerSpriteSheet.grabSprite(j*60, 0, 60, 60));
			for(int j = 0; j < 8; j++)
				playerWalkingRightImages.add(playerSpriteSheet.grabSprite(j*60, 60, 60, 60));
			for(int j = 0; j < 2; j++)
				playerStoodStillImages.add(playerSpriteSheet.grabSprite(j*60, 120, 60, 60));
			
			
			
			/**Item Images*/
				/**Resources*/
					/**Logs*/
					GameItems.logOak.setImage(imageLoader.loadImage("res/images/items/log.png"));
					/**Ores*/
					GameItems.oreCopper.setImage(imageLoader.loadImage("res/images/items/ore_copper.png"));
				/**Tools*/
					/**Axes*/
					GameItems.axeBronze.setImage(imageLoader.loadImage("res/images/items/axe_bronze.png"));
					GameItems.axeIron.setImage(imageLoader.loadImage("res/images/items/axe_iron.png"));
					GameItems.axeSteel.setImage(imageLoader.loadImage("res/images/items/axe_steel.png"));
					/**Picks*/
					GameItems.pickBronze.setImage(imageLoader.loadImage("res/images/items/pick_bronze.png"));
					GameItems.pickIron.setImage(imageLoader.loadImage("res/images/items/pick_iron.png"));
				/**Spell Books*/
					GameItems.spellBookNoviceFire.setImage(imageLoader.loadImage("res/images/magic/spell_books/spell_book_fire_novice.png"));
					
			/**Spell Animations*/
					GameItems.spellFireBall.setAnimation(imageLoader.loadImage("res/images/magic/spells/spell_ball_fire.png"));
					GameItems.spellFireStrike.setAnimation(imageLoader.loadImage("res/images/magic/spells/spell_strike_fire.png"));
			
			/**Icons*/
				/**UI*/
				inventImage = imageLoader.loadImage("res/images/icons/invent.png");
				equipedItemsImage = imageLoader.loadImage("res/images/icons/equiped_items.png");
				toolkitImage = imageLoader.loadImage("res/images/icons/toolkit.png");
				magicImage = imageLoader.loadImage("res/images/icons/magic.png");
				questImage = imageLoader.loadImage("res/images/icons/quest.png");
				/**Other*/
				readImage = imageLoader.loadImage("res/images/icons/magic_read.png");
				roman1 = imageLoader.loadImage("res/images/icons/roman_one.png");
				roman2 = imageLoader.loadImage("res/images/icons/roman_two.png");
				roman3 = imageLoader.loadImage("res/images/icons/roman_three.png");
				roman4 = imageLoader.loadImage("res/images/icons/roman_four.png");
			
			
			entityTreeAliveImage = imageLoader.loadImage("res/images/entities/tree_alive.png");
			entitiyTreeDeadImage = imageLoader.loadImage("res/images/entities/tree_dead.png");
			entityOreDeadImage = imageLoader.loadImage("res/images/entities/ore_dead.png");
			entityCopperOreAliveImage = imageLoader.loadImage("res/images/entities/copper_ore_alive.png");
			
			/**Map tile image loading*/
			emptyTile = imageLoader.loadImage("res/images/map_tiles/emptyTile.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Player player1;
	public Skills skills;
	public BottomRightWindowSelector bottomRightWindowSelector;
	public Console console;
	public ProjectileHandler projectileHandler;
	public void init()
	{
		BufferedImage[] playerStoodStillImagesArray = new BufferedImage[playerStoodStillImages.size()];
			playerStoodStillImagesArray = playerStoodStillImages.toArray(playerStoodStillImagesArray);
		BufferedImage[] playerWalkingRightImagesArray = new BufferedImage[playerWalkingRightImages.size()];
			playerWalkingRightImagesArray = playerWalkingRightImages.toArray(playerWalkingRightImagesArray);
		BufferedImage[] playerWalkingLeftImagesArray = new BufferedImage[playerWalkingLeftImages.size()];
			playerWalkingLeftImagesArray = playerWalkingLeftImages.toArray(playerWalkingLeftImagesArray);
		
		/**Call frame managers scale methods for setting the variables to match the screen size*/
		player1 = new Player(playerStoodStillImagesArray, playerWalkingRightImagesArray, playerWalkingLeftImagesArray, (int)frameManager.scaleWidthOrX(100),
				(int)frameManager.scaleHeightOrY(100), (int)frameManager.scaleWidthOrX(100), (int)frameManager.scaleHeightOrY(100),
				frameManager.scaleWidthOrX(1), frameManager.scaleHeightOrY(1), frameManager.scaleWidthOrX(5), frameManager.scaleHeightOrY(5), this);
		
		/**Create a new instance of skills which will hold the players level and exp*/
		skills = new Skills(this);
		
		
		/**Create a window in the bottom right of the screen for the inventory etc.*/
		float windowSelectorHeight = (float)this.getWidth() / 4.25f;
		bottomRightWindowSelector = new BottomRightWindowSelector(this.getWidth()-this.getWidth()/5,
				(int)(this.getHeight()-windowSelectorHeight), this.getWidth()/5,(int)windowSelectorHeight, inventImage, equipedItemsImage,
				toolkitImage, magicImage, questImage, readImage, this);
		
		/**Create a console which will go in the bottom left corner*/
		console = new Console(0, this.getHeight()-this.getHeight()/3, this.getWidth()/3, this.getHeight()/3, (int)frameManager.scaleWidthOrX(12), this);
		
		
		projectileHandler = new ProjectileHandler(roman1, roman2, roman3, roman4, this);
		
		
		//Test
		int[] x = new int[]{(int)frameManager.scaleWidthOrX(50), (int)frameManager.scaleWidthOrX(600), (int)frameManager.scaleWidthOrX(900)};
		int[] y = new int[]{(int)frameManager.scaleHeightOrY(450), (int)frameManager.scaleHeightOrY(300), (int)frameManager.scaleHeightOrY(200)};
		int[] w = new int[]{(int)frameManager.scaleWidthOrX(128), (int)frameManager.scaleWidthOrX(128), (int)frameManager.scaleWidthOrX(128)};
		int[] h = new int[]{(int)frameManager.scaleHeightOrY(256), (int)frameManager.scaleHeightOrY(256), (int)frameManager.scaleHeightOrY(256)};
		trees = new Entity(entityTreeAliveImage, entitiyTreeDeadImage, 7, "rewardWoodCuttingXp", GameItems.logOak, EToolType.AXE, EToolGrade.BRONZE, 15, 3, x, y, w, h, (int)frameManager.scaleWidthOrX(50),
				(int)frameManager.scaleHeightOrY(50), this);
		
		//Test Two
		shop = new Shop(playerStoodStillImages.get(0), playerStoodStillImages.get(0), 0, null, null, EToolType.NONE, EToolGrade.NONE, 10, 5,
				new int[]{(int)frameManager.scaleHeightOrY(120)}, new int[]{(int)frameManager.scaleHeightOrY(180)},
				new int[]{(int)frameManager.scaleHeightOrY(100)}, new int[]{(int)frameManager.scaleHeightOrY(100)},
				(int)frameManager.scaleWidthOrX(50), (int)frameManager.scaleHeightOrY(50),
				new Item[]{GameItems.pickBronze, GameItems.axeIron, GameItems.spellBookNoviceFire}, new int[]{-1, 10, 1}, this);
		
		//Test Three
		//monster = new Monster(playerStoodStillImagesArray, playerWalkingRightImagesArray, playerWalkingLeftImagesArray, (int)frameManager.scaleWidthOrX(100),
		//		(int)frameManager.scaleHeightOrY(700), (int)frameManager.scaleWidthOrX(100), (int)frameManager.scaleHeightOrY(100),
		//		frameManager.scaleWidthOrX(1), frameManager.scaleHeightOrY(1), frameManager.scaleWidthOrX(5), frameManager.scaleHeightOrY(5), this);
		
		//Test Four
		ores = new Entity(entityCopperOreAliveImage, entityOreDeadImage, 13, "rewardMiningXp", GameItems.oreCopper, EToolType.PICK, EToolGrade.BRONZE, 20, 5,
				new int[]{(int)frameManager.scaleHeightOrY(1024), (int)frameManager.scaleHeightOrY(765)}, new int[]{(int)frameManager.scaleHeightOrY(760), (int)frameManager.scaleHeightOrY(779)},
				new int[]{(int)frameManager.scaleHeightOrY(96), (int)frameManager.scaleHeightOrY(96)}, new int[]{(int)frameManager.scaleHeightOrY(96),
				(int)frameManager.scaleHeightOrY(96)}, (int)frameManager.scaleWidthOrX(50), (int)frameManager.scaleHeightOrY(50), this);
		
		
		mapImages = new UsedImages();
		
		loadMapFile("res/maps/map/src/map.txt");
		loadImagesFile("res/maps/map/src/images.txt", "res/maps/map/src");
	}
	//Tests
	public Entity trees;
	public Shop shop;
	//public Monster monster;
	public Entity ores;
	
	private ImageIcon loadingIcon = null;
	
	public int camerax = 0, cameray = 0;
	public void run()
	{
		bottomRightWindowSelector.invent.addItem(GameItems.axeBronze);
		
		ArrayList<Integer> uisToRemove = new ArrayList<>();
		
		int fps = 30;
		while(true)
		{
			player1.update(leftKeyPressed, rightKeyPressed, upKeyPressed, downKeyPressed, camerax, cameray);
			
			bottomRightWindowSelector.update(leftMouseButtonPressed, previousLeftMouseButtonPressed, rightMouseButtonPressed, mousex, mousey);
			
			for(Chunk c : chunkList)
				c.update(camerax, cameray, mousex, mousey, leftMouseButtonPressed, previousLeftMouseButtonPressed);
			
			for(int i = 0; i < userInterfacesOpen.size(); i++)
			{
				if(userInterfacesOpen.get(i).isSetForRemoval())
					uisToRemove.add(i);
				else
					userInterfacesOpen.get(i).update(mousex, mousey, leftMouseButtonPressed, previousLeftMouseButtonPressed);
			}
			for(int i : uisToRemove)
			{
				userInterfacesOpen.remove(i);
			}
			uisToRemove.clear();
			
			//monster.update();
						
			projectileHandler.update(oneKey, twoKey, threeKey, fourKey, leftMouseButtonPressed, previousLeftMouseButtonPressed, mousex, mousey);
						
			repaint();
			
			previousLeftMouseButtonPressed = leftMouseButtonPressed;
			
			sync(fps);
		}
	}
	
	
	
	/**Timing method which syncs the display to the desired framerate*/
	private long lastTime = System.nanoTime();
	public void sync(int fps)
	{
		Thread.yield();
        long gapTo = 1000000000L / fps + lastTime;
		long currentTime = System.nanoTime();
		try {
			while(currentTime < gapTo)
			{
				Thread.sleep((gapTo - currentTime) / 2000000L);
				currentTime = System.nanoTime();
			}
		} catch (InterruptedException e) {e.printStackTrace();}
		
		lastTime = currentTime;
	}
	
	private Graphics dbg;
	private Image dbi;
	public void paint(Graphics g)
	{
		dbi = createImage(this.getWidth(), this.getHeight());
		dbg = dbi.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbi, 0, 0, this);
	}
	
	public void paintComponent(Graphics g)
	{
		switch(gameState)
		{
		case INGAME:
			for(Chunk c : chunkList)
				c.paint(g, camerax, cameray);
			projectileHandler.paintEntities(g, (Graphics2D)g);
			//monster.paint(g);
			bottomRightWindowSelector.paint(g);
			console.paint(g);
			for(UserInterface ui : userInterfacesOpen)
				ui.paint(g);
			projectileHandler.paintMagicString(g);
			break;
		case LOADING:
			if(loadingIcon != null)
				g.drawImage(loadingIcon.getImage(), this.getWidth()/2-128, this.getHeight()/2-64, 256, 128, this);
			break;
		}
		g.drawString("In-Dev Build v.2014.02.27", 20, 20);
	}
	
	public ArrayList<Chunk> chunkList = new ArrayList<Chunk>();
	private UsedImages mapImages;
	public void loadMapFile(String mapFile)
	{
		File f = new File(mapFile);
		FileReader fr;
		try {
			fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			
			String line1 = br.readLine();
			String mapWidthStr = line1.split(" ")[1];
			String mapHeightStr = line1.split(" ")[2];
			String tileWidthStr = line1.split(" ")[3];
			String tileHeightStr = line1.split(" ")[4];
			int mapWidth = Integer.parseInt(mapWidthStr);
			int mapHeight = Integer.parseInt(mapHeightStr);
			int tileWidth = Integer.parseInt(tileWidthStr);
			int tileHeight = Integer.parseInt(tileHeightStr);
			
			
			int[][] layer = new int[mapWidth][mapHeight];
			
			String[][] stringLayer = new String[mapWidth][mapHeight];
			
			ArrayList<Integer> tileProperties = new ArrayList<>();
			ArrayList<Integer> tilePropertiesX = new ArrayList<>();
			ArrayList<Integer> tilePropertiesY = new ArrayList<>();
			
			for(int i = 0; i < mapHeight; i++)
			{
				String line = br.readLine();
				String[] splitLine = line.split(" ");
				for(int j = 0; j < mapWidth; j++)
				{
					if(splitLine[j].contains("-"))
					{
						String[] secondSplitLine;
						secondSplitLine = splitLine[j].split("-");
						for(int e = 1; e < secondSplitLine.length; e++)
						{
							tileProperties.add(Integer.parseInt(secondSplitLine[e]));
							tilePropertiesX.add(i);
							tilePropertiesY.add(j);
						}
						
						splitLine[j] = secondSplitLine[0];
					}
					stringLayer[j][i] = splitLine[j];
				}
			}
			
			for(int i = 0; i < mapHeight; i++)
			{
				for(int j = 0; j < mapWidth; j++)
				{
					layer[j][i] = Integer.parseInt(stringLayer[j][i]);
				}
			}
			
			chunkList.clear();
			Chunk newLayer = new Chunk(layer, (int)frameManager.scaleWidthOrX(tileWidth), (int)frameManager.scaleHeightOrY(tileHeight),
					mapImages, new Entity[]{trees, shop, ores}, this);
			newLayer.setProperties(tileProperties);
			newLayer.setPropertiesX(tilePropertiesX);
			newLayer.setPropertiesY(tilePropertiesY);
			chunkList.add(newLayer);
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadImagesFile(String imagesFilePath, String srcDirectory)
	{
		try {
			File f = new File(imagesFilePath);
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			
			ArrayList<String> allImagePaths = new ArrayList<String>();
			String line;
			while((line = br.readLine()) != null)
			{
				allImagePaths.add(srcDirectory + line);
			}
			
			mapImages.clear();
			
			BufferedImageLoader imageLoader = new BufferedImageLoader();
			mapImages.addImage(emptyTile);
			for(int i = 0; i < allImagePaths.size(); i++)
			{
				mapImages.addImage(imageLoader.loadImage(allImagePaths.get(i)));
			}
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int[] convertIntegers(List<Integer> integers)
	{
	    int[] ret = new int[integers.size()];
	    Iterator<Integer> iterator = integers.iterator();
	    for (int i = 0; i < ret.length; i++)
	    {
	        ret[i] = iterator.next().intValue();
	    }
	    return ret;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	private boolean leftMouseButtonPressed, rightMouseButtonPressed;
	private boolean previousLeftMouseButtonPressed;
	public void mousePressed(MouseEvent e) {
		mousex = e.getX();
		mousey = e.getY();
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			leftMouseButtonPressed = true;
		} else if(e.getButton() == MouseEvent.BUTTON3)
		{
			rightMouseButtonPressed = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			leftMouseButtonPressed = false;
		} else if(e.getButton() == MouseEvent.BUTTON3)
		{
			rightMouseButtonPressed = false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	private int mousex, mousey;
	public void mouseMoved(MouseEvent e)
	{
		mousex = e.getX();
		mousey = e.getY();
	}

	private boolean rightKeyPressed, leftKeyPressed, upKeyPressed, downKeyPressed;
	private boolean oneKey, twoKey, threeKey, fourKey;
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_RIGHT:
			rightKeyPressed = true;
			break;
		case KeyEvent.VK_LEFT:
			leftKeyPressed = true;
			break;
		case KeyEvent.VK_UP:
			upKeyPressed = true;
			break;
		case KeyEvent.VK_DOWN:
			downKeyPressed = true;
			break;
		case KeyEvent.VK_1:
			oneKey = true;
			break;
		case KeyEvent.VK_2:
			twoKey = true;
			break;
		case KeyEvent.VK_3:
			threeKey = true;
			break;
		case KeyEvent.VK_4:
			fourKey = true;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_RIGHT:
			rightKeyPressed = false;
			break;
		case KeyEvent.VK_LEFT:
			leftKeyPressed = false;
			break;
		case KeyEvent.VK_UP:
			upKeyPressed = false;
			break;
		case KeyEvent.VK_DOWN:
			downKeyPressed = false;
			break;
		case KeyEvent.VK_1:
			oneKey = false;
			break;
		case KeyEvent.VK_2:
			twoKey = false;
			break;
		case KeyEvent.VK_3:
			threeKey = false;
			break;
		case KeyEvent.VK_4:
			fourKey = false;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[]args)
	{
		new Thread(new GameStartClass()).start();
	}
}
