package core.DataBreak;

import core.Tile_Engine.GameManager;
import core.Tile_Engine.Tile_System.TileMap;
import processing.core.PApplet;
import processing.core.PConstants;

public class DataBreak {

    private PApplet parent;
    private Gamemode gamemode;
    private GameManager gameManager;
    private LevelEditor levelEditor;
    private TileMap levelSelect;

    private int wait;


    public DataBreak(PApplet parent)
    {
        this.parent = parent;
        gamemode = Gamemode.EDITOR;
        gameManager = new GameManager(60, 30, parent);
        levelEditor = new LevelEditor(parent);
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
                break;
            case PLAY :
                gameManager.Run();
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
                break;
            case PLAY :
                break;
            case GAMEOVER :
                break;
            case VICTORY :
                break;
        }
        if (key == 'n')
        {
            gamemode = Gamemode.PLAY;
            levelEditor.setPlaying(false);
        }
    }

    public void MousePressed(int x, int y)
    {
        switch (gamemode)
        {
            case MAINMENU :
                break;
            case SELECT :
                break;
            case EDITOR :
                levelEditor.mouseEdit(x ,y);
                break;
            case PLAY :
                levelEditor.mousePlay(x, y);
                break;
            case GAMEOVER :
                break;
            case VICTORY :
                break;
        }
    }

    private void MainMenu()
    {

    }

    private void LevelSelect()
    {

    }

    private void GameOver()
    {
        parent.clear();
        parent.background(0, 0, 0);
        parent.textSize(50);
        parent.textAlign(PConstants.CENTER, PConstants.CENTER);
        parent.fill(255, 255, 255);
        parent.text("GAME OVER!", 400, 400);
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
        parent.text("YOU WON!!", parent.random(100, 700), parent.random(100, 700));
        parent.fill(parent.random(0, 255), parent.random(0, 255), parent.random(0, 255));
        parent.text("YOU WON!!", parent.random(100, 700), parent.random(100, 700));
        parent.fill(parent.random(0, 255), parent.random(0, 255), parent.random(0, 255));
        parent.text("YOU WON!!", parent.random(100, 700), parent.random(100, 700));
        parent.fill(parent.random(0, 255), parent.random(0, 255), parent.random(0, 255));
        parent.text("YOU WON!!", parent.random(100, 700), parent.random(100, 700));
    }
}
