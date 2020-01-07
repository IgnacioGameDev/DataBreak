package core.DataBreak;
import core.DataBreak.Tile_Types.Level_Tiles.BackgroundTile;
import core.DataBreak.Tile_Types.UI_Tiles.MarkerTile;
import core.Tile_Engine.Collision_Systems.LayerCollisionSystem;
import core.Tile_Engine.Tile_System.Components.Directions;
import core.Tile_Engine.Tile_System.EmptyTile;
import core.Tile_Engine.Tile_System.TileMap;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import java.util.ArrayList;

//Class used to handle player input during gameplay and editing
//Class also handles gameplay UI and info display
//Only class to handle all the game-play layers, essentially all tiles in the game are contained within this class
//Having all the layers also means it makes use of the layercollisionsystem
public class LevelEditor {

    private PApplet parent;
    private TileMap layer1, layer2, layer3;
    private LayerCollisionSystem layerCollisionSystem;
    private LevelManager levelManager;

    private boolean isPlaying;
    private Directions playerDir;

    //Variables used for the java timer to display information for a determined amount of seconds
    private boolean notAllowed;
    private int savedTime;
    private int passedTime;

    private ArrayList<MarkerTile> markerTiles;


    public LevelEditor(PApplet parent)
    {
        this.parent = parent;
        layer1 = new TileMap(16,16, 50, new BackgroundTile(1, 1, 1, this.parent), parent);
        layer2 = new TileMap(16,16, 50, new EmptyTile(1, 1, 1, this.parent), parent);
        layer3 = new TileMap(16, 16, 50, new EmptyTile(1, 1, 1, this.parent), parent);
        layerCollisionSystem = new LayerCollisionSystem(layer2, layer3, parent);
        levelManager = new LevelManager(this.parent, this.layerCollisionSystem);
        isPlaying = true;
        markerTiles = new ArrayList<>();
        playerDir = Directions.UP;
        savedTime = parent.millis();
        notAllowed = false;
    }

    //Updates layers and collisions when the game isn't paused, displays the marker tiles
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

    //Draws level UI and interactable buttons
    public void LevelInfo()
    {
        parent.fill(DataBreak.backgroundColor.getRGB());
        parent.rect(0, 0, 800, 130);

        //Available players and current selected player
        parent.fill(DataBreak.defaultTextColor.getRGB());
        switch (playerDir)
        {
            case UP :
                parent.rect(90, 60, 20, 20);
                break;
            case DOWN :
                parent.rect(290, 60, 20, 20);
                break;
            case LEFT :
                parent.rect(490, 60, 20, 20);
                break;
            case RIGHT :
                parent.rect(690, 60, 20, 20);
                break;
        }
        parent.textSize(20);
        parent.textAlign(PConstants.CENTER, PConstants.CENTER);
        parent.text("UP  " + levelManager.getPlayerUp(), 100, 30);
        parent.text("DOWN  " + levelManager.getPlayerDown(), 300, 30);
        parent.text("LEFT  " + levelManager.getPlayerLeft(), 500, 30);
        parent.text("RIGHT  " + levelManager.getPlayerRight(), 700, 30);

        //Play/Pause/Menu buttons
        PImage playButton = parent.loadImage("src/core/DataBreak/Assets/Play.png");
        PImage pauseButton = parent.loadImage("src/core/DataBreak/Assets/Pause.png");
        playButton.resize(100, 100);
        pauseButton.resize(110, 110);
        parent.image(playButton, 30, 670);
        //Pause button and feature disabled, it worked but broke the game design (the player can pause and cheat, easier to remove feature altogether)
        //parent.image(pauseButton, 150, 665);
        //Replace with reset button to start the level over
        parent.textSize(38);
        parent.text("Reset", 220, 720);
        parent.textSize(45);
        parent.text("Main Menu", 660, 740);
    }

    //Displays information on level editing
    public void EditInfo()
    {
        //Tile type key
        parent.textAlign(PConstants.LEFT, PConstants.CENTER);
        parent.textSize(28);
        parent.text("Keys", 0, 140);
        parent.text("Delete r", 0, 638);
        parent.textAlign(PConstants.CENTER, PConstants.CENTER);
        parent.text("1", 75, 188);
        parent.text("2", 75, 238);
        parent.text("3", 75, 288);
        parent.text("w", 75, 338);
        parent.text("a", 75, 388);
        parent.text("s", 75, 438);
        parent.text("d", 75, 488);
        parent.text("e", 75, 538);
        parent.text("f", 75, 588);
        parent.image(parent.loadImage("src/core/DataBreak/Assets/Path.png"), 0, 170);
        parent.image(parent.loadImage("src/core/DataBreak/Assets/Target.png"), 0, 220);
        parent.image(parent.loadImage("src/core/DataBreak/Assets/Exit.png"), 0, 270);
        parent.image(parent.loadImage("src/core/DataBreak/Assets/ArrowUp.png"), 0, 320);
        parent.image(parent.loadImage("src/core/DataBreak/Assets/ArrowLeft.png"), 0, 370);
        parent.image(parent.loadImage("src/core/DataBreak/Assets/ArrowDown.png"), 0, 420);
        parent.image(parent.loadImage("src/core/DataBreak/Assets/ArrowRight.png"), 0, 470);
        parent.image(parent.loadImage("src/core/DataBreak/Assets/Flip.png"), 0, 520);
        parent.image(parent.loadImage("src/core/DataBreak/Assets/Slider.png"), 0, 570);

        //Save/Load buttons
        parent.fill(DataBreak.defaultTextColor.getRGB());
        parent.textSize(40);
        parent.text("Save", 730, 600);
        parent.text("Load", 730, 670);

        //Additional instructions
        parent.textSize(20);
        parent.text("Left/Right click - Add/Remove available viruses", 400, 100);
    }

    //Editing player input using the level manager to add tiles to the corresponding layers
    //Controlled by string variable
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
                //Reset editor by deleting every tile
                //Can't reset/delete slider tiles
                for (int i = 0; i < layer3.getEveryTile().length; i++)
                {
                    levelManager.setTileType("EmptyTile");
                    levelManager.AddTile(layer3, layer3.getEveryTile()[i].getCol(), layer3.getEveryTile()[i].getRow(), layer3.getTileSize());
                }
                break;
            case 'z' :
                //Save level shortcut
                levelManager.SaveLevel(layer3, "level1");
                break;
            case 'q' :
                //Deleting single tiles
                levelManager.setTileType("EmptyTile");
                levelManager.AddTile(layer3, layer3.getLinePixel(parent.mouseX), layer3.getLinePixel(parent.mouseY), layer3.getTileSize());
                break;
        }
    }

    //Clicking on edit mode adds and removes players available for the level being edited
    //Also used to press buttons like save/load
    public void mouseEdit(int x, int y)
    {
        if (parent.mouseButton == parent.LEFT)
        {
            if (y < 200)
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
            else if (y > 580 && y < 625 && x > 600)
            {
                levelManager.SaveLevel(layer3, "level1");
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

    //During play mode mouse is used to select player direction, place players down and play/pause the game
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
        if (y > 120 && y < 650)
        {
            //Only allowed to add player if game is paused, and it is on allowed tiles
            if (layer3.getTile(layer3.getLinePixel(x), layer3.getLinePixel(y)).getClass().getSimpleName().equals("PathTile") && levelManager.canAddPlayer(playerDir) && !isPlaying)
            {
                levelManager.AddPlayer(layer2, layer2.getLinePixel(x), layer2.getLinePixel(y), layer2.getTileSize(), playerDir);
                markerTiles.add(new MarkerTile(layer2.getLinePixel(x), layer2.getLinePixel(y), layer2.getTileSize(), parent));
            }
            else
            {
                savedTime = parent.millis();
                notAllowed = true;
            }
        }

        if (y > 650 && x < 140)
        {
            setPlaying(true);
            markerTiles.clear();
        }
    }

    //Can use the keys instead of the mouse to select player directions
    public void keyPlay(char key, int keycode)
    {
        switch (key)
        {
            case '1' :
                playerDir = Directions.UP;
                break;
            case '2' :
                playerDir = Directions.DOWN;
                break;
            case '3' :
                playerDir = Directions.LEFT;
                break;
            case '4' :
                playerDir = Directions.RIGHT;
                break;
        }
    }

    public void setPassedTime(int passedTime) {
        this.passedTime = passedTime;
    }

    public int getSavedTime() {
        return savedTime;
    }

    //Message displayed when attempting things that arent allowed, uses the processing millis method for a timer when the game is not in paying state
    public void notAllowed()
    {
        if (passedTime > 1000 && notAllowed)
        {
            notAllowed = false;
            parent.fill(DataBreak.backgroundColor.getRGB());
            parent.rect(320, 80, 160, 100);
        }
        else if (notAllowed)
        {
            parent.textSize(25);
            parent.textAlign(PConstants.CENTER, PConstants.CENTER);
            parent.fill(255, 0, 0);
            parent.text("Not Allowed", 400, 100);
        }
    }

    //Loads numbered levels from an integer using levelmanager variables, then displays it and pauses the game
    public void LoadLevelFromInt(int levelNum)
    {
        levelManager.SetLoadLevel(levelNum);
        levelManager.LoadLevel(layer3,"level1");
        Update();
        setPlaying(false);
    }

    //Checks for the game state using specific level information
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
