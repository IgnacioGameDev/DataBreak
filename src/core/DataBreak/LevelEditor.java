package core.DataBreak;
import core.DataBreak.Tile_Types.Level_Tiles.BackgroundTile;
import core.Tile_Engine.Collision_Systems.LayerCollisionSystem;
import core.Tile_Engine.Tile_System.EmptyTile;
import core.Tile_Engine.Tile_System.TileMap;
import processing.core.PApplet;

public class LevelEditor {

    private PApplet parent;
    private TileMap layer1, layer2, layer3;
    private LayerCollisionSystem layerCollisionSystem;
    private LevelManager levelManager;

    public LevelEditor(PApplet parent)
    {
        this.parent = parent;
        layer1 = new TileMap(16,16, 50, new BackgroundTile(1, 1, 1, this.parent), parent);
        layer2 = new TileMap(16,16, 50, new EmptyTile(1, 1, 1, this.parent), parent);
        layer3 = new TileMap(16, 16, 50, new EmptyTile(1, 1, 1, this.parent), parent);
        layerCollisionSystem = new LayerCollisionSystem(layer2, layer3, parent);
        levelManager = new LevelManager(this.parent, this.layerCollisionSystem, layer3.getProxyCollisionSystem());
        layerCollisionSystem.CheckCollisions();
    }

    public void Update()
    {
        layer1.Update();
        layer2.Update();
        layer3.Update();
    }

    public void keyReleased(char key, int keyCode)
    {
        switch(key)
        {
            case '1' :
                levelManager.setTileType("PathTile");
                levelManager.AddTile(layer3, layer3.getLinePixel(parent.mouseX), layer3.getLinePixel(parent.mouseY), layer3.getTileSize());
                break;
            case '2' :
                levelManager.setTileType("DirUpTile");
                levelManager.AddTile(layer3, layer3.getLinePixel(parent.mouseX), layer3.getLinePixel(parent.mouseY), layer3.getTileSize());
                break;
            case '3' :
                levelManager.setTileType("DirDownTile");
                levelManager.AddTile(layer3, layer3.getLinePixel(parent.mouseX), layer3.getLinePixel(parent.mouseY), layer3.getTileSize());
                break;
            case '4' :
                levelManager.setTileType("DirRightTile");
                levelManager.AddTile(layer3, layer3.getLinePixel(parent.mouseX), layer3.getLinePixel(parent.mouseY), layer3.getTileSize());
                break;
            case '5' :
                levelManager.setTileType("DirLeftTile");
                levelManager.AddTile(layer3, layer3.getLinePixel(parent.mouseX), layer3.getLinePixel(parent.mouseY), layer3.getTileSize());
                break;
            case '6' :
                levelManager.setTileType("SliderTile");
                levelManager.AddTile(layer3, layer3.getLinePixel(parent.mouseX), layer3.getLinePixel(parent.mouseY), layer3.getTileSize());
                break;
            case '7' :
                levelManager.setTileType("TargetTile");
                levelManager.AddTile(layer3, layer3.getLinePixel(parent.mouseX), layer3.getLinePixel(parent.mouseY), layer3.getTileSize());
                break;
            case 'r' :
                for (int i = 0; i < layer3.getEveryTile().length; i++)
                {
                    layer3.getEveryTile()[i].Destroy();
                }
                break;
            case 's' :
                levelManager.SaveTiles(layer3, "level1");
                break;
            case 'd' :
                layer3.getTile(layer3.getLinePixel(parent.mouseX), layer3.getLinePixel(parent.mouseY)).Destroy();
//            case '9' :
//                levelManager.LoadTiles(layer3,"level1");
//                break;
        }
    }
}
