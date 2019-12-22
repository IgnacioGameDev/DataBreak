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
import processing.core.PConstants;
import processing.data.JSONArray;
import processing.data.JSONObject;
import java.util.ArrayList;

public class LevelManager implements Serializable {

    private PApplet parent;
    private String tileType;
    private ArrayList<Tile> loadedTiles;
    private ArrayList<Tile> loadedSpecialTiles;
    private DataManager dataManager;
    private LayerCollisionSystem layerCollisionSystem;
    private String infoLocation = "src/core/DataBreak/Level_Data/level10info.json";

    private int objectiveNum;
    private int playerUp;
    private int playerDown;
    private int playerRight;
    private int playerLeft;
    private ArrayList<PlayerTile> players;
    private ArrayList<Tile> dirTiles;

    public LevelManager(PApplet parent, LayerCollisionSystem layerCollisionSystem, ProxyCollisionSystem proxyCollisionSystem)
    {
        this.parent = parent;
        this.layerCollisionSystem = layerCollisionSystem;
        dataManager = new DataManager(this.parent, "level10.json");
        dataManager.load();
        players = new ArrayList<>();
        dirTiles = new ArrayList<>();
    }

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

    public void SetLoadLevel(int levelNum)
    {
        dataManager.setLoadGameFile("level"+String.valueOf(levelNum)+".json");
        dataManager.load();
        infoLocation = "src/core/DataBreak/Level_Data/level" + String.valueOf(levelNum) + "info.json";
    }

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

    @Override
    public void loadJSONObject(JSONObject json) {
        objectiveNum = json.getInt("objectiveNum");
        playerUp = json.getInt("playerUp");
        playerDown = json.getInt("playerDown");
        playerLeft = json.getInt("playerLeft");
        playerRight = json.getInt("playerRight");
    }

    public Tile AddTile(TileMap layer, int col, int row, int size)
    {
        Tile tile = null;
        switch (tileType)
        {
            case "EmptyTile" :
                EmptyTile emptyTile = new EmptyTile(col, row, size, this.parent);
                tile = emptyTile;
                break;
            case "BackgroundTile" :
                BackgroundTile backgroundTile = new BackgroundTile(col, row, size, this.parent);
                tile = backgroundTile;
                break;
            case "PathTile" :
                Tile b =  layer.getTile(col, row);
                PathTile pathTile = new PathTile(col, row, size, this.parent);
                layer.getInitialTiles().set(b.getCell(), pathTile);
                layer.getInitialTiles().get(b.getCell()).setCell(((b.getRow()) * layer.getColNum()) + (b.getCol()+1));
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

    public void AddPlayer(TileMap layer, int col, int row, int size, Directions dir)
    {
        PlayerTile playerTile = new PlayerTile(col, row, size, parent, dir);
        layer.getAddedTiles().add(playerTile);
        layer.getProxyCollisionSystem().UpdateList(playerTile, layer.getProxyCollisionSystem().proxyList);
        layerCollisionSystem.UpdateList(playerTile, layerCollisionSystem.colliderListA);
        players.add(playerTile);
        subtractPlayer(dir);
    }

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

    public void setTileType(String tileType) { this.tileType = tileType; }

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

    public int getObjectiveNum() { return objectiveNum; }

    public int getPlayerUp() { return playerUp; }

    public int getPlayerDown() { return playerDown; }

    public int getPlayerLeft() { return playerLeft; }

    public int getPlayerRight() { return playerRight; }

    public void setObjectiveNum(int objectiveNum) { this.objectiveNum = objectiveNum; }

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
