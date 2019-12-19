package core.DataBreak;
import core.DataBreak.Tile_Types.Level_Tiles.*;
import core.Tile_Engine.Collision_Systems.LayerCollisionSystem;
import core.Tile_Engine.Collision_Systems.ProxyCollisionSystem;
import core.Tile_Engine.Data_Management.DataManager;
import core.Tile_Engine.Tile_System.EmptyTile;
import core.Tile_Engine.Tile_System.Tile;
import core.Tile_Engine.Tile_System.TileMap;
import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;
import java.util.ArrayList;

public class LevelManager {

    private PApplet parent;
    private String tileType;
    private ArrayList<Tile> loadedTiles;
    private ArrayList<Tile> loadedSpecialTiles;
    private DataManager dataManager;
    private LayerCollisionSystem layerCollisionSystem;
    private ProxyCollisionSystem proxyCollisionSystem;

    public LevelManager(PApplet parent, LayerCollisionSystem layerCollisionSystem, ProxyCollisionSystem proxyCollisionSystem)
    {
        this.parent = parent;
        this.layerCollisionSystem = layerCollisionSystem;
        this.proxyCollisionSystem = proxyCollisionSystem;
        dataManager = new DataManager(this.parent);
        dataManager.load();
    }

    public void SaveTiles(TileMap layer, String levelName)
    {
        ArrayList<Tile> allTiles = layer.getInitialTiles();
        for (int i = 0; i < layer.getAddedTiles().size(); i++)
        {
            allTiles.add(layer.getAddedTiles().get(i));
        }
        dataManager.save(allTiles, levelName);
        System.out.println("save");
    }

    public void LoadTiles(TileMap layer, String levelName)
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
        System.out.println("load");
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
                tile = dirUpTile;
                break;
            case "DirDownTile" :
                Tile dD =  layer.getTile(col, row);
                DirDownTile dirDownTile = new DirDownTile(col, row, size, this.parent);
                layer.getInitialTiles().set(dD.getCell(), dirDownTile);
                layer.getInitialTiles().get(dD.getCell()).setCell(((dD.getRow()) * layer.getColNum()) + (dD.getCol()+1));
                layerCollisionSystem.UpdateList(dirDownTile, layerCollisionSystem.colliderListB);
                tile = dirDownTile;
                break;
            case "DirLeftTile" :
                Tile dL =  layer.getTile(col, row);
                DirLeftTile dirLeftTile = new DirLeftTile(col, row, size, this.parent);
                layer.getInitialTiles().set(dL.getCell(), dirLeftTile);
                layer.getInitialTiles().get(dL.getCell()).setCell(((dL.getRow()) * layer.getColNum()) + (dL.getCol()+1));
                layerCollisionSystem.UpdateList(dirLeftTile, layerCollisionSystem.colliderListB);
                tile = dirLeftTile;
                break;
            case "DirRightTile" :
                Tile dR =  layer.getTile(col, row);
                DirRightTile dirRightTile = new DirRightTile(col, row, size, this.parent);
                layer.getInitialTiles().set(dR.getCell(), dirRightTile);
                layer.getInitialTiles().get(dR.getCell()).setCell(((dR.getRow()) * layer.getColNum()) + (dR.getCol()+1));
                layerCollisionSystem.UpdateList(dirRightTile, layerCollisionSystem.colliderListB);
                tile = dirRightTile;
                break;
            case "SliderTile" :
                SliderTile sliderTile = new SliderTile(col, row, size, this.parent);
                layer.getAddedTiles().add(sliderTile);
                proxyCollisionSystem.UpdateList(sliderTile, proxyCollisionSystem.proxyList);
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
//            case "RedTile" :
//                RedTile redTile = new RedTile(col, row, size, this.parent);
//                layer.getAddedTiles().add(redTile);
//                layerCollisionSystem.UpdateList(redTile, layerCollisionSystem.colliderListA);
//                tile = redTile;
//                break;
        }
        return tile;
    }

    public void setTileType(String tileType) { this.tileType = tileType; }
}
