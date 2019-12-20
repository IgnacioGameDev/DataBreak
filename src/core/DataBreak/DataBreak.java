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
                    gameManager.setTick(false);
                    if (levelEditor.checkVictory())
                    {
                        gamemode = Gamemode.VICTORY;
                    }
                }
                break;
            case GAMEOVER:
                GameOver();
                break;
            case VICTORY:
                Victory();
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

    }

    private void Victory()
    {
        parent.clear();
        parent.background(255, 255, 255);
        parent.textSize(50);
        parent.textAlign(PConstants.CENTER, PConstants.CENTER);
        parent.fill(0, 0, 0);
        parent.text("YOU WON!!", 400, 400);
        gameManager.Run();
        if (gameManager.tick())
        {

            gameManager.setTick(false);
        }


    }
}
