package core.DataBreak;
import core.DataBreak.Tile_Types.UI_Tiles.ButtonTile;
import core.Tile_Engine.GameManager;
import core.Tile_Engine.Tile_System.TileMap;
import processing.core.PApplet;
import processing.core.PConstants;

public class DataBreak {

    private PApplet parent;
    private Gamemode gamemode;
    private GameManager gameManager;
    private LevelEditor levelEditor;
    private TileMap levelButtons;

    private int wait;
    private boolean start;
    private int currentLevel;

    public DataBreak(PApplet parent)
    {
        this.parent = parent;
        gamemode = Gamemode.MAINMENU;
        gameManager = new GameManager(60, 30, parent);
        levelEditor = new LevelEditor(parent);
        levelButtons = new TileMap(5, 2, 100, new ButtonTile(1, 1, 1, parent), parent);
        start = true;
    }

    public void Update()
    {
        switch (gamemode)
        {
            case MAINMENU:
                MainMenu();
                break;
            case SELECT:
                LevelSelect();
                break;
            case EDITOR:
                levelEditor.Update();
                levelEditor.LevelInfo();
                levelEditor.EditOptions();
                break;
            case PLAY :
                gameManager.Run();
                levelEditor.setPassedTime(parent.millis() - levelEditor.getSavedTime());
                levelEditor.notAllowed();
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
            case GAMEOVER:
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
            case VICTORY:
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
                break;
            case GAMEOVER :
                break;
            case VICTORY :
                break;
        }
    }

    public void MousePressed(int x, int y)
    {
        switch (gamemode)
        {
            case MAINMENU :
                MainMenuClick(x, y);
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

    private void mousePlay2(int x, int y)
    {
        if (x > 550 && y > 710)
        {
            levelEditor = new LevelEditor(parent);
            gamemode = Gamemode.MAINMENU;
        }
    }

    private void mouseEdit2(int x, int y)
    {
        if (y > 645 && y < 700 && x > 600)
        {
            levelEditor.LoadLevelFromInt(10);
            gamemode = Gamemode.PLAY;
        }
    }

    private void MainMenu()
    {
        parent.clear();
        parent.background(255, 255, 255);
        parent.textSize(80);
        parent.textAlign(PConstants.CENTER, PConstants.CENTER);
        parent.fill(0, 0, 0);
        parent.text("Databreak", 400, 200);
        parent.textSize(30);
        parent.text("Level Select", 400, 500);
        parent.text("Editor", 400, 600);
    }

    private void MainMenuClick(int x, int y)
    {
        parent.clear();
        if (y < 580)
        {
            gamemode = Gamemode.SELECT;
        }
        else
        {
            gamemode = Gamemode.EDITOR;
        }
    }

    private void LevelSelect()
    {
        if (start)
        {
            int index = 1;
            for (int i = 0; i < levelButtons.getEveryTile().length; i++)
            {
                ((ButtonTile) levelButtons.getEveryTile()[i]).textDisplay.setInfo(String.valueOf(index));
                System.out.println(index);
                index++;
            }
            start = false;
        }
        parent.pushMatrix();
        parent.translate(150, 250);
        levelButtons.Update();
        parent.popMatrix();
    }

    private void LevelClick(int x, int y)
    {
        int w = x - 150;
        int h = y - 250;
        currentLevel = Integer.parseInt(((ButtonTile)levelButtons.getTilePixel(w, h)).textDisplay.getInfo());
        levelEditor.LoadLevelFromInt(currentLevel);
        gamemode = Gamemode.PLAY;
    }

    private void GameOver()
    {
        parent.clear();
        parent.background(0, 0, 0);
        parent.textSize(50);
        parent.textAlign(PConstants.CENTER, PConstants.CENTER);
        parent.fill(255, 255, 255);
        parent.text("GAME OVER!", 400, 300);
        parent.text("Try Again", 400, 450);
    }

    public void GameOverClick()
    {
        levelEditor = new LevelEditor(parent);
        levelEditor.LoadLevelFromInt(currentLevel);
        gamemode = Gamemode.PLAY;
    }

    private void Victory()
    {
        parent.clear();
        parent.background(255, 255, 255);
        parent.textSize(50);
        parent.textAlign(PConstants.CENTER, PConstants.CENTER);
        parent.fill(parent.random(0, 255), parent.random(0, 255), parent.random(0, 255));
        parent.text("YOU WON!!", 400, 400);
        parent.fill(parent.random(0, 255), parent.random(0, 255), parent.random(0, 255));
        parent.text("YOU WON!!", parent.random(100, 700), parent.random(50, 450));
        parent.fill(parent.random(0, 255), parent.random(0, 255), parent.random(0, 255));
        parent.text("YOU WON!!", parent.random(100, 700), parent.random(50, 450));
        parent.fill(parent.random(0, 255), parent.random(0, 255), parent.random(0, 255));
        parent.text("YOU WON!!", parent.random(100, 700), parent.random(50, 450));
        parent.fill(parent.random(0, 255), parent.random(0, 255), parent.random(0, 255));
        parent.text("YOU WON!!", parent.random(100, 700), parent.random(50, 450));
        if (currentLevel < 9)
        {
            parent.fill(0, 0, 0);
            parent.text("Next Level", 200, 600);
        }
        parent.fill(0, 0, 0);
        parent.text("Main Menu", 600, 600);
    }

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
