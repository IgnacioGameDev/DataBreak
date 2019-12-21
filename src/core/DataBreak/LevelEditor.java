package core.DataBreak;
import core.DataBreak.Tile_Types.Level_Tiles.BackgroundTile;
import core.DataBreak.Tile_Types.UI_Tiles.MarkerTile;
import core.Tile_Engine.Collision_Systems.LayerCollisionSystem;
import core.Tile_Engine.Tile_System.Components.Directions;
import core.Tile_Engine.Tile_System.EmptyTile;
import core.Tile_Engine.Tile_System.TileMap;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;

public class LevelEditor {

    private PApplet parent;
    private TileMap layer1, layer2, layer3;
    private LayerCollisionSystem layerCollisionSystem;
    private LevelManager levelManager;

    private boolean isPlaying;
    private boolean gameOver;
    private boolean victory;
    private Directions playerDir;

    private ArrayList<MarkerTile> markerTiles;


    public LevelEditor(PApplet parent)
    {
        this.parent = parent;
        layer1 = new TileMap(16,16, 50, new BackgroundTile(1, 1, 1, this.parent), parent);
        layer2 = new TileMap(16,16, 50, new EmptyTile(1, 1, 1, this.parent), parent);
        layer3 = new TileMap(16, 16, 50, new EmptyTile(1, 1, 1, this.parent), parent);
        layerCollisionSystem = new LayerCollisionSystem(layer2, layer3, parent);
        levelManager = new LevelManager(this.parent, this.layerCollisionSystem, layer3.getProxyCollisionSystem());
        isPlaying = true;
        markerTiles = new ArrayList<>();
    }

    public void Update()
    {
        if (isPlaying)
        {
            levelManager.CheckPlayerSwitch(layer3);
            layerCollisionSystem.CheckCollisions();
            layer1.Update();
            layer2.Update();
            layer3.Update();
        }
        for (MarkerTile markerTile : markerTiles)
        {
            markerTile.Render();
        }
    }

    public void LevelInfo()
    {
        parent.fill(0, 0, 0);
        parent.textSize(20);
        parent.textAlign(PConstants.CENTER, PConstants.CENTER);
        parent.text("UP  " + levelManager.getPlayerUp(), 100, 30);
        parent.text("DOWN  " + levelManager.getPlayerDown(), 300, 30);
        parent.text("LEFT  " + levelManager.getPlayerLeft(), 500, 30);
        parent.text("RIGHT  " + levelManager.getPlayerRight(), 700, 30);
    }

    public void keyReleased(char key, int keyCode)
    {
        switch(key)
        {
            case '1' :
                levelManager.setTileType("PathTile");
                levelManager.AddTile(layer3, layer3.getLinePixel(parent.mouseX), layer3.getLinePixel(parent.mouseY), layer3.getTileSize());
                break;
            case 'w' :
                levelManager.setTileType("DirUpTile");
                levelManager.AddTile(layer3, layer3.getLinePixel(parent.mouseX), layer3.getLinePixel(parent.mouseY), layer3.getTileSize());
                break;
            case 's' :
                levelManager.setTileType("DirDownTile");
                levelManager.AddTile(layer3, layer3.getLinePixel(parent.mouseX), layer3.getLinePixel(parent.mouseY), layer3.getTileSize());
                break;
            case 'd' :
                levelManager.setTileType("DirRightTile");
                levelManager.AddTile(layer3, layer3.getLinePixel(parent.mouseX), layer3.getLinePixel(parent.mouseY), layer3.getTileSize());
                break;
            case 'a' :
                levelManager.setTileType("DirLeftTile");
                levelManager.AddTile(layer3, layer3.getLinePixel(parent.mouseX), layer3.getLinePixel(parent.mouseY), layer3.getTileSize());
                break;
            case 'e' :
                levelManager.setTileType("FlipTile");
                levelManager.AddTile(layer3, layer3.getLinePixel(parent.mouseX), layer3.getLinePixel(parent.mouseY), layer3.getTileSize());
                break;
            case 'f' :
                levelManager.setTileType("SliderTile");
                levelManager.AddTile(layer3, layer3.getLinePixel(parent.mouseX), layer3.getLinePixel(parent.mouseY), layer3.getTileSize());
                break;
            case '2' :
                levelManager.setTileType("TargetTile");
                levelManager.AddTile(layer3, layer3.getLinePixel(parent.mouseX), layer3.getLinePixel(parent.mouseY), layer3.getTileSize());
                break;
            case '3' :
                levelManager.setTileType("ExitTile");
                levelManager.AddTile(layer3, layer3.getLinePixel(parent.mouseX), layer3.getLinePixel(parent.mouseY), layer3.getTileSize());
                break;
            case 'r' :
                for (int i = 0; i < layer3.getEveryTile().length; i++)
                {
                    layer3.getEveryTile()[i].Destroy();
                }
                break;
            case 'z' :
                levelManager.SaveLevel(layer3, "level1");
                break;
            case 'q' :
                layer3.getTile(layer3.getLinePixel(parent.mouseX), layer3.getLinePixel(parent.mouseY)).Destroy();
                break;
            case 'x' :
                levelManager.LoadLevel(layer3,"level1");
                break;
        }
    }

    public void mouseEdit(int x, int y)
    {
        if (parent.mouseButton == parent.LEFT)
        {
            if (x < 200)
            {
                levelManager.setPlayerUp(levelManager.getPlayerUp() + 1);
            }
            else if (x < 400)
            {
                levelManager.setPlayerDown(levelManager.getPlayerDown() + 1);
            }
            else if (x < 600)
            {
                levelManager.setPlayerLeft(levelManager.getPlayerLeft() + 1);
            }
            else if (x < 800)
            {
                levelManager.setPlayerRight(levelManager.getPlayerRight() + 1);
            }
        }
        else if (parent.mouseButton == parent.RIGHT)
        {
            if (x < 200)
            {
                levelManager.setPlayerUp(levelManager.getPlayerUp() - 1);
            }
            else if (x < 400)
            {
                levelManager.setPlayerDown(levelManager.getPlayerDown() - 1);
            }
            else if (x < 600)
            {
                levelManager.setPlayerLeft(levelManager.getPlayerLeft() - 1);
            }
            else if (x < 800)
            {
                levelManager.setPlayerRight(levelManager.getPlayerRight() - 1);
            }
        }
    }

    public void mousePlay(int x, int y)
    {
        if (y < 120)
        {
            if (x < 200)
            {
                playerDir = Directions.UP;
            }
            else if (x < 400)
            {
                playerDir = Directions.DOWN;
            }
            else if (x < 600)
            {
                playerDir = Directions.LEFT;
            }
            else if (x < 800)
            {
                playerDir = Directions.RIGHT;
            }
        }
        if (y > 120 && y < 700)
        {
            if (layer3.getTile(layer3.getLinePixel(x), layer3.getLinePixel(y)).getClass().getSimpleName().equals("PathTile"))
            {
                levelManager.AddPlayer(layer2, layer2.getLinePixel(x), layer2.getLinePixel(y), layer2.getTileSize(), playerDir);
                markerTiles.add(new MarkerTile(layer2.getLinePixel(x), layer2.getLinePixel(y), layer2.getTileSize(), parent));
            }
            else
            {
                levelManager.notAllowed();
            }
        }
        if (y > 700 && x < 200)
        {
            setPlaying(true);
            markerTiles.clear();
        }
    }

    public boolean checkGameOver()
    {
        return levelManager.CheckPlayersGameOver();
    }

    public boolean checkVictory()
    {
        return levelManager.CheckPlayersVictory(levelManager.getObjectiveNum());
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
