package core.DataBreak;

import core.Tile_Engine.Tile_System.TileMap;
import processing.core.PApplet;

public class DataBreak {

    private PApplet parent;
    private Gamemode gamemode;
    private LevelEditor levelEditor;
    private TileMap levelSelect;

    public DataBreak(PApplet parent)
    {
        this.parent = parent;
        gamemode = Gamemode.MAINMENU;
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
                break;
            case GAMEOVER:
                GameOver();
                break;
            case VICTORY:
                Victory();
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

    }
}
