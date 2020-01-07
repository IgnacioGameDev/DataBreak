package core.DataBreak;
import core.DataBreak.Tile_Types.Level_Tiles.*;
import core.Tile_Engine.Collision_Systems.LayerCollisionSystem;
import core.Tile_Engine.Collision_Systems.ProxyCollisionSystem;
import core.Tile_Engine.Data_Management.DataManager;
import core.Tile_Engine.Data_Management.Serializable;
import core.Tile_Engine.Tile_System.Components.Directions;
import core.Tile_Engine.Tile_System.EmptyTile;
import core.Tile_Engine.Tile_System.Tile;
import core.Tile_Engine.Tile_System.TileMap;
import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;
import java.util.ArrayList;

//This class handles saving and loading the tiles inside an entire level
//It also handles the addition and removal of individual tiles independent of layers
//Tile centric class, NOT dependant of tilemap
//Very "game specific" class, uses but also deviates from engine methods
public class LevelManager implements Serializable {

    private PApplet parent;

    //String used to reference type of tiles being instantiated
    private String tileType;

    //Arraylists that handle tiles, sorting and prioritizing some over others
    private ArrayList<Tile> loadedTiles;
    private ArrayList<Tile> loadedSpecialTiles;
    private ArrayList<PlayerTile> players;
    private ArrayList<Tile> dirTiles;

    //Classes used to serialize information and to manipulate tiles with collider components
    private DataManager dataManager;
    private LayerCollisionSystem layerCollisionSystem;

    //The only level to be saved currently is the custom user made level (level 10)
    private String infoLocation = "src/core/DataBreak/Level_Data/level10info.json";

    //Information specific to a level that is serialized independent of its tiles (number and types of player, number of objectives)
    private int objectiveNum;
    private int playerUp;
    private int playerDown;
    private int playerRight;
    private int playerLeft;

    //Initializing the class referencing level10 for custom level editing
    public LevelManager(PApplet parent, LayerCollisionSystem layerCollisionSystem)
    {
        this.parent = parent;
        this.layerCollisionSystem = layerCollisionSystem;
        dataManager = new DataManager(this.parent, "level10.json");
        dataManager.load();
        players = new ArrayList<>();
        dirTiles = new ArrayList<>();
    }

    //Used to put information about the level into strings in an external file
    @Override
    public JSONObject serializeToJSON() {
        JSONObject levelData = new JSONObject();
        levelData.setInt("objectiveNum", objectiveNum);
        levelData.setInt("playerUp", playerUp);
        levelData.setInt("playerDown", playerDown);
        levelData.setInt("playerLeft", playerLeft);
        levelData.setInt("playerRight", playerRight);
        return levelData;
    }

    //Returns strings in an external file into variables used by the class
    @Override
    public void loadJSONObject(JSONObject json) {
        objectiveNum = json.getInt("objectiveNum");
        playerUp = json.getInt("playerUp");
        playerDown = json.getInt("playerDown");
        playerLeft = json.getInt("playerLeft");
        playerRight = json.getInt("playerRight");
    }

    //Saves a layer of tiles and the level info in the editor into an external file
    public void SaveLevel(TileMap layer, String levelName)
    {
        ArrayList<Tile> allTiles = layer.getInitialTiles();
        for (int i = 0; i < layer.getAddedTiles().size(); i++)
        {
            allTiles.add(layer.getAddedTiles().get(i));
        }
        parent.saveJSONObject(this.serializeToJSON(), infoLocation);
        dataManager.save(allTiles, levelName);
        System.out.println("save");
    }

    //Used to request and queue a level to be loaded using an integer, level files must be numbered
    public void SetLoadLevel(int levelNum)
    {
        dataManager.setLoadGameFile("level"+String.valueOf(levelNum)+".json");
        dataManager.load();
        infoLocation = "src/core/DataBreak/Level_Data/level" + String.valueOf(levelNum) + "info.json";
    }

    //Sets level variables using the saved level info and instantiates serialized tiles into a selected layer
    public void LoadLevel(TileMap layer, String levelName)
    {
        loadedTiles = new ArrayList<>();
        loadedSpecialTiles = new ArrayList<>();
        JSONArray levelTiles = dataManager.gameData.getJSONArray(levelName);
        for (int i = 0; i < levelTiles.size(); i++)
        {
            JSONObject tileData = (JSONObject) levelTiles.get(i);
            tileType = tileData.getString("tileType");
            if (tileType.equals("SliderTile"))
            {
                loadedSpecialTiles.add(AddTile(layer, tileData.getInt("col"), tileData.getInt("row"), tileData.getInt("size")));
            }
            else
            {
                loadedTiles.add(AddTile(layer, tileData.getInt("col"), tileData.getInt("row"), tileData.getInt("size")));
            }
        }
        layer.setInitialTiles(loadedTiles);
        layer.setAddedTiles(loadedSpecialTiles);
        this.loadJSONObject(parent.loadJSONObject(infoLocation));
        System.out.println("load");
    }

    //Uses a string variable to execute specific constructors for tile subclasses, organizing them into necessary lists
    //Sensibly it can only initialize tiles with a default constructor, unless extra parameters are irrelevant
    //Using a string is important for serialization
    //Can also be used for level editing and individual tiles
    //Creates tiles in a specific layer
    public Tile AddTile(TileMap layer, int col, int row, int size)
    {
        Tile tile = null;
        switch (tileType)
        {
            case "EmptyTile" :
                Tile nT = layer.getTile(col, row);
                EmptyTile emptyTile = new EmptyTile(col, row, size, this.parent);
                layer.getInitialTiles().set(nT.getCell(), emptyTile);
                layer.getInitialTiles().get(nT.getCell()).setCell(((nT.getRow()) * layer.getColNum()) + (nT.getCol()+1));
                layerCollisionSystem.UpdateList(emptyTile, layerCollisionSystem.colliderListB);
                tile = emptyTile;
                break;
            case "BackgroundTile" :
                BackgroundTile backgroundTile = new BackgroundTile(col, row, size, this.parent);
                tile = backgroundTile;
                break;
            case "PathTile" :
                Tile pT =  layer.getTile(col, row);
                PathTile pathTile = new PathTile(col, row, size, this.parent);
                layer.getInitialTiles().set(pT.getCell(), pathTile);
                layer.getInitialTiles().get(pT.getCell()).setCell(((pT.getRow()) * layer.getColNum()) + (pT.getCol()+1));
                layerCollisionSystem.UpdateList(pathTile, layerCollisionSystem.colliderListB);
                tile = pathTile;
                break;
            case "DirUpTile" :
                Tile dU =  layer.getTile(col, row);
                DirUpTile dirUpTile = new DirUpTile(col, row, size, this.parent);
                layer.getInitialTiles().set(dU.getCell(), dirUpTile);
                layer.getInitialTiles().get(dU.getCell()).setCell(((dU.getRow()) * layer.getColNum()) + (dU.getCol()+1));
                layerCollisionSystem.UpdateList(dirUpTile, layerCollisionSystem.colliderListB);
                dirTiles.add(dirUpTile);
                tile = dirUpTile;
                break;
            case "DirDownTile" :
                Tile dD =  layer.getTile(col, row);
                DirDownTile dirDownTile = new DirDownTile(col, row, size, this.parent);
                layer.getInitialTiles().set(dD.getCell(), dirDownTile);
                layer.getInitialTiles().get(dD.getCell()).setCell(((dD.getRow()) * layer.getColNum()) + (dD.getCol()+1));
                layerCollisionSystem.UpdateList(dirDownTile, layerCollisionSystem.colliderListB);
                dirTiles.add(dirDownTile);
                tile = dirDownTile;
                break;
            case "DirLeftTile" :
                Tile dL =  layer.getTile(col, row);
                DirLeftTile dirLeftTile = new DirLeftTile(col, row, size, this.parent);
                layer.getInitialTiles().set(dL.getCell(), dirLeftTile);
                layer.getInitialTiles().get(dL.getCell()).setCell(((dL.getRow()) * layer.getColNum()) + (dL.getCol()+1));
                layerCollisionSystem.UpdateList(dirLeftTile, layerCollisionSystem.colliderListB);
                dirTiles.add(dirLeftTile);
                tile = dirLeftTile;
                break;
            case "DirRightTile" :
                Tile dR =  layer.getTile(col, row);
                DirRightTile dirRightTile = new DirRightTile(col, row, size, this.parent);
                layer.getInitialTiles().set(dR.getCell(), dirRightTile);
                layer.getInitialTiles().get(dR.getCell()).setCell(((dR.getRow()) * layer.getColNum()) + (dR.getCol()+1));
                layerCollisionSystem.UpdateList(dirRightTile, layerCollisionSystem.colliderListB);
                dirTiles.add(dirRightTile);
                tile = dirRightTile;
                break;
            case "SliderTile" :
                SliderTile sliderTile = new SliderTile(col, row, size, this.parent);
                layer.getAddedTiles().add(sliderTile);
                layer.getProxyCollisionSystem().UpdateList(sliderTile, layer.getProxyCollisionSystem().proxyList);
                layerCollisionSystem.UpdateList(sliderTile, layerCollisionSystem.colliderListB);
                tile = sliderTile;
                break;
            case "TargetTile" :
                Tile tT =  layer.getTile(col, row);
                TargetTile targetTile = new TargetTile(col, row, size, this.parent);
                layer.getInitialTiles().set(tT.getCell(), targetTile);
                layer.getInitialTiles().get(tT.getCell()).setCell(((tT.getRow()) * layer.getColNum()) + (tT.getCol()+1));
                layerCollisionSystem.UpdateList(targetTile, layerCollisionSystem.colliderListB);
                tile = targetTile;
                break;
            case "ExitTile" :
                Tile eT =  layer.getTile(col, row);
                ExitTile exitTile = new ExitTile(col, row, size, this.parent);
                layer.getInitialTiles().set(eT.getCell(), exitTile);
                layer.getInitialTiles().get(eT.getCell()).setCell(((eT.getRow()) * layer.getColNum()) + (eT.getCol()+1));
                layerCollisionSystem.UpdateList(exitTile, layerCollisionSystem.colliderListB);
                objectiveNum++;
                tile = exitTile;
                break;
            case "FlipTile" :
                Tile fT =  layer.getTile(col, row);
                FlipTile flipTile = new FlipTile(col, row, size, this.parent);
                layer.getInitialTiles().set(fT.getCell(), flipTile);
                layer.getInitialTiles().get(fT.getCell()).setCell(((fT.getRow()) * layer.getColNum()) + (fT.getCol()+1));
                layerCollisionSystem.UpdateList(flipTile, layerCollisionSystem.colliderListB);
                tile = flipTile;
                break;
        }
        return tile;
    }

    //Example of tile with non default constructor with a relevant extra parameter (direction)
    public void AddPlayer(TileMap layer, int col, int row, int size, Directions dir)
    {
        PlayerTile playerTile = new PlayerTile(col, row, size, parent, dir);
        layer.getAddedTiles().add(playerTile);
        layer.getProxyCollisionSystem().UpdateList(playerTile, layer.getProxyCollisionSystem().proxyList);
        layerCollisionSystem.UpdateList(playerTile, layerCollisionSystem.colliderListA);
        players.add(playerTile);
        subtractPlayer(dir);
    }

    //References level data integers using direction variable
    //Example : How many players are left with the UP direction?
    private int dirToInt(Directions directions)
    {
        int result = 0;
        switch (directions)
        {
            case UP :
                result = playerUp;
                break;
            case DOWN :
                result = playerDown;
                break;
            case LEFT :
                result = playerLeft;
                break;
            case RIGHT :
                result = playerRight;
                break;
        }
        return result;
    }

    //Boolean to check if there are available players of specific directions
    public boolean canAddPlayer(Directions dir)
    {
        if (dirToInt(dir) > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //Checks if players are triggering a game over game state or if they are overlapping
    public boolean CheckPlayersGameOver()
    {
        boolean result = false;
        for (int i = 0; i < players.size(); i++)
        {
            if (players.get(i).isGameOver())
            {
                result = true;
            }
            for (int j = i+1; j < players.size(); j++)
            {
                //When players collide they stop moving and game over is triggered
                if (players.get(i).getCol() == players.get(j).getCol() && players.get(i).getRow() == players.get(j).getRow())
                {
                    players.get(i).movement.setDir(Directions.STATIC);
                    players.get(j).movement.setDir(Directions.STATIC);
                    result = true;
                }
            }
        }
        return result;
    }

    //Checks if players are triggering a victory game state
    //This happens when all players have a true victory variable
    public boolean CheckPlayersVictory(int objective)
    {
        boolean result = false;
        int victcheck = 0;
        for (int i = 0; i < players.size(); i++)
        {
            victcheck += players.get(i).getVictory();
            if (victcheck == objective)
            {
                result = true;
            }
        }
        return result;
    }

    //Checks if players have flipped the switch
    //This functionality is on the player and not the flip switch because that would require putting switches in another Arraylist or searching for them in existing ones
    public void CheckPlayerSwitch(TileMap layer)
    {
        for (int i = 0; i < players.size(); i++)
        {
            if (players.get(i).isFlipIt())
            {
                FlipDirection(layer);
                players.remove(i);
            }
        }
    }

    //Flips all the directional tiles in a layer (Effectively rotates them 180 degrees)
    //This is done by temporarily creating a clone of the directional tiles arraylist
    //so that the original tiles can be changed while still gathering information from it
    private void FlipDirection(TileMap layer)
    {
        ArrayList<Tile> temporary = (ArrayList<Tile>) dirTiles.clone();
        for (int i = 0; i < temporary.size(); i++)
        {
            if (temporary.get(i) instanceof DirUpTile)
            {
                tileType = "DirDownTile";
                AddTile(layer, temporary.get(i).getCol(), temporary.get(i).getRow(), layer.getTileSize());
            }
            else if (temporary.get(i) instanceof DirDownTile)
            {
                tileType = "DirUpTile";
                AddTile(layer, temporary.get(i).getCol(), temporary.get(i).getRow(), layer.getTileSize());
            }
            else if (temporary.get(i) instanceof DirLeftTile)
            {
                tileType = "DirRightTile";
                AddTile(layer, temporary.get(i).getCol(), temporary.get(i).getRow(), layer.getTileSize());
            }
            else if (temporary.get(i) instanceof DirRightTile)
            {
                tileType = "DirLeftTile";
                AddTile(layer, temporary.get(i).getCol(), temporary.get(i).getRow(), layer.getTileSize());
            }
        }
    }

    //Variable referencing
    public void setTileType(String tileType) { this.tileType = tileType; }

    public int getObjectiveNum() { return objectiveNum; }

    public int getPlayerUp() { return playerUp; }

    public int getPlayerDown() { return playerDown; }

    public int getPlayerLeft() { return playerLeft; }

    public int getPlayerRight() { return playerRight; }

    public void setObjectiveNum(int objectiveNum) { this.objectiveNum = objectiveNum; }

    //Variables with bounds
    public void setPlayerUp(int playerUp) {
        if (playerUp < 0)
        { this.playerUp = 0; }
        else
        { this.playerUp = playerUp; }
    }

    public void setPlayerDown(int playerDown) {
        if (playerDown < 0)
        { this.playerDown = 0; }
        else
        { this.playerDown = playerDown; }
    }

    public void setPlayerLeft(int playerLeft) {
        if (playerLeft < 0)
        { this.playerLeft = 0; }
        else
        { this.playerLeft = playerLeft; }
    }

    public void setPlayerRight(int playerRight) {
        if (playerRight < 0)
        { this.playerRight = 0; }
        else
        { this.playerRight = playerRight; }
    }

    public void subtractPlayer(Directions dir)
    {
        switch (dir)
        {
            case UP :
                playerUp--;
                break;
            case DOWN :
                playerDown--;
                break;
            case LEFT :
                playerLeft--;
                break;
            case RIGHT :
                playerRight--;
                break;
        }
    }
}
