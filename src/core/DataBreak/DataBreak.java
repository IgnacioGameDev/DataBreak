package core.DataBreak;
import core.DataBreak.Tile_Types.UI_Tiles.ButtonTile;
import core.Tile_Engine.GameManager;
import core.Tile_Engine.Tile_System.TileMap;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import java.awt.*;

//This class handles menus and brings them together with the levelEditor and gameManager to run the game
public class DataBreak {

    private PApplet parent;
    private Gamemode gamemode;

    private GameManager gameManager;
    private LevelEditor levelEditor;

    //4 colors easily editable and integrated to quickly change and test the core colour scheme in the game
    public static Color backgroundColor = new Color(13, 24, 38);
    public static Color defaultTextColor = new Color( 242, 31, 12);
    public static Color defaultTileColor = new Color( 33, 85, 191);
    public static Color specialTileColor = new Color(48, 242, 205);

    //Grid of button tiles for the level select
    private TileMap levelButtons;

    //Images to display the tutorial
    private int activeTutorial;
    private PImage[] tutorials;

    //Timer for the UI elements
    private int wait;
    private boolean start;

    //Integer controlling what level is being played
    private int currentLevel;

    public DataBreak(PApplet parent)
    {
        this.parent = parent;
        gamemode = Gamemode.MAINMENU;
        gameManager = new GameManager(60, 30, parent);
        levelEditor = new LevelEditor(parent);
        levelButtons = new TileMap(5, 2, 100, new ButtonTile(1, 1, 1, parent), parent);
        start = true;
        activeTutorial = 0;
        tutorials = new PImage[] {parent.loadImage("src/core/DataBreak/Assets/HowToPlay2.png"),
                parent.loadImage("src/core/DataBreak/Assets/HowToPlay3.png"),
                parent.loadImage("src/core/DataBreak/Assets/HowToPlay4.png")};
    }

    //Happens once per frame dependant on gamemode
    public void Run()
    {
        switch (gamemode)
        {
            case MAINMENU :
                MainMenu();
                break;
            case HOWTOPLAY :
                HowToPlay();
                break;
            case SELECT :
                LevelSelect();
                break;
            case EDITOR :
                levelEditor.Update();
                levelEditor.LevelInfo();
                levelEditor.EditInfo();
                break;
            case PLAY :
                gameManager.Run();
                levelEditor.setPassedTime(parent.millis() - levelEditor.getSavedTime());
                levelEditor.notAllowed();
                //Certain things are handled by the gameManager timer, also controls play/pause
                if (gameManager.tick())
                {
                    levelEditor.Update();
                    levelEditor.LevelInfo();
                    if (levelEditor.checkGameOver())
                    {
                        wait = 2;
                        gamemode = Gamemode.GAMEOVER;
                    }
                    else if (levelEditor.checkVictory())
                    {
                        wait = 2;
                        gamemode = Gamemode.VICTORY;
                    }
                    gameManager.setTick(false);
                }
                break;
            //Game over and victory game states have a timer of 2 seconds before triggering, so the visual triggers for those game states are more clearly shown
            //Eg. When two player tiles collide they trigger a game over but the wait gives the player visual feedback this is happening before changing game state
            case GAMEOVER :
                gameManager.Run();
                if (gameManager.tick())
                {
                    levelEditor.Update();
                    levelEditor.LevelInfo();
                    wait--;
                    gameManager.setTick(false);
                }
                if (wait < 0) { GameOver(); }
                break;
            case VICTORY :
                gameManager.Run();
                if (gameManager.tick())
                {
                    levelEditor.Update();
                    levelEditor.LevelInfo();
                    wait--;
                    gameManager.setTick(false);
                    if (wait < 0)
                    {
                        Victory();
                        gameManager.setTick(false);
                    }
                }
                break;
        }
    }

    //What pressing keys does depending on gamemode
    public void KeyPressed(char key, int keyCode)
    {
        switch (gamemode)
        {
            case MAINMENU :
                break;
            case SELECT :
                break;
            case EDITOR :
                levelEditor.keyReleased(key, keyCode);
                if (key == 'x')
                {
                    levelEditor.LoadLevelFromInt(10);
                    gamemode = Gamemode.PLAY;
                }
                break;
            case PLAY :
                levelEditor.keyPlay(key, keyCode);
                break;
            case GAMEOVER :
                break;
            case VICTORY :
                break;
        }
    }

    //What clicking mice does depending on gamemode
    public void MousePressed(int x, int y)
    {
        switch (gamemode)
        {
            case MAINMENU :
                MainMenuClick(x, y);
                break;
            case HOWTOPLAY :
                HowToPlayClick();
                break;
            case SELECT :
                LevelClick(x, y);
                break;
            case EDITOR :
                levelEditor.mouseEdit(x ,y);
                mouseEdit2(x, y);
                mousePlay2(x, y);
                break;
            case PLAY :
                levelEditor.mousePlay(x, y);
                mousePlay2(x, y);
                break;
            case GAMEOVER :
                GameOverClick();
                break;
            case VICTORY :
                VictoryClick(x, y);
                break;
        }
    }

    //Certain methods can not be contained in the levelEditor, mainly things doing with gamemode and game state
    private void mousePlay2(int x, int y)
    {
        //Main Menu
        if (x > 550 && y > 710)
        {
            levelEditor = new LevelEditor(parent);
            gamemode = Gamemode.MAINMENU;
        }
        //Reset
        if (x > 200 && y > 670 && x < 300)
        {
            levelEditor = new LevelEditor(parent);
            levelEditor.LoadLevelFromInt(currentLevel);
            gamemode = Gamemode.GAMEOVER;
            gamemode = Gamemode.PLAY;
            levelEditor.setPlaying(false);
        }
    }

    private void mouseEdit2(int x, int y)
    {
        //Load custom level (set to level 10)
        if (y > 645 && y < 700 && x > 600)
        {
            levelEditor.LoadLevelFromInt(10);
            gamemode = Gamemode.PLAY;
        }
    }

    //Display level buttons
    private void MainMenu()
    {
        parent.clear();
        parent.background(backgroundColor.getRGB());
        parent.textSize(80);
        parent.textAlign(PConstants.CENTER, PConstants.CENTER);
        parent.fill(defaultTextColor.getRGB());
        parent.text("Databreak", 400, 200);
        parent.textSize(30);
        parent.text("How to Play", 400, 400);
        parent.text("Level Select", 400, 500);
        parent.text("Editor", 400, 600);
    }

    //Calculate mouse positions in relation to buttons
    private void MainMenuClick(int x, int y)
    {
        parent.clear();
        if (y < 450)
        {
            gamemode = Gamemode.HOWTOPLAY;
        }
        else if (y < 580)
        {
            gamemode = Gamemode.SELECT;
        }
        else
        {
            gamemode = Gamemode.EDITOR;
        }
    }

    //Tutorial cycles through array of images then goes back to main menu and resets
    private void HowToPlay()
    {
        parent.tint(255, 255, 255);
        parent.image(tutorials[activeTutorial], 0, 0);
    }

    private void HowToPlayClick()
    {
        activeTutorial++;
        if (activeTutorial == 3)
        {
            activeTutorial = 0;
            gamemode = Gamemode.MAINMENU;
        }
    }

    //Level select is made of button tiles
    //Uses matrix to center it on the screen, as all grids start at 0, 0 by default
    private void LevelSelect()
    {
        parent.background(backgroundColor.getRGB());
        if (start)
        {
            int index = 1;
            for (int i = 0; i < levelButtons.getEveryTile().length; i++)
            {
                ((ButtonTile) levelButtons.getEveryTile()[i]).textDisplay.setInfo(String.valueOf(index));
                index++;
            }
            start = false;
        }
        parent.pushMatrix();
        parent.translate(150, 250);
        levelButtons.Update();
        parent.popMatrix();
    }

    //Makes button tiles clickable and loads the corresponding level, takes in account the offset form the translate method
    private void LevelClick(int x, int y)
    {
        int w = x - 150;
        int h = y - 250;
        currentLevel = Integer.parseInt(((ButtonTile)levelButtons.getTilePixel(w, h)).textDisplay.getInfo());
        levelEditor.LoadLevelFromInt(currentLevel);
        gamemode = Gamemode.PLAY;
    }

    //Displays gameover game state
    private void GameOver()
    {
        parent.clear();
        parent.background(backgroundColor.getRGB());
        parent.textSize(50);
        parent.textAlign(PConstants.CENTER, PConstants.CENTER);
        parent.fill(defaultTextColor.getRGB());
        parent.text("GAME OVER!", 400, 300);
        parent.text("Try Again", 400, 450);
    }

    //Goes back to the main menu and resets the level editor
    public void GameOverClick()
    {
        levelEditor = new LevelEditor(parent);
        levelEditor.LoadLevelFromInt(currentLevel);
        gamemode = Gamemode.PLAY;
    }

    //Displays victory game state
    private void Victory()
    {
        parent.clear();
        parent.background(backgroundColor.getRGB());
        parent.textSize(50);
        parent.textAlign(PConstants.CENTER, PConstants.CENTER);
        parent.fill(defaultTextColor.getRGB());
        parent.text("YOU WON!!", 400, 300);
        parent.text("YOU WON!!", parent.random(100, 700), parent.random(50, 450));
        parent.text("YOU WON!!", parent.random(100, 700), parent.random(50, 450));
        parent.text("YOU WON!!", parent.random(100, 700), parent.random(50, 450));
        parent.text("YOU WON!!", parent.random(100, 700), parent.random(50, 450));
        //Doesn't display next level button if there is no next level
        if (currentLevel < 9)
        {
            parent.fill(48, 242, 205);
            parent.text("Next Level", 200, 600);
        }
        parent.fill(48, 242, 205);
        parent.text("Main Menu", 600, 600);
    }

    //Can load the next level if its available, else it only allows to reset the game and return to the menu
    private void VictoryClick(int x, int y)
    {
        levelEditor = new LevelEditor(parent);
        if (currentLevel < 9 && x < 400)
        {
            currentLevel ++;
            levelEditor.LoadLevelFromInt(currentLevel);
            gamemode = Gamemode.PLAY;
        }
        else
        {
            parent.clear();
            gamemode = Gamemode.MAINMENU;
        }
    }
}
