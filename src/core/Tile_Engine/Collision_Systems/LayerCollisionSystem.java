package core.Tile_Engine.Collision_Systems;
import core.Tile_Engine.Tile_System.Components.InterlayerCollider;
import core.Tile_Engine.Tile_System.Tile;
import core.Tile_Engine.Tile_System.TileMap;
import processing.core.PApplet;

import java.util.ArrayList;

//Object placed with tileMaps which allows InterlayerColliders in two different ones interact
public class LayerCollisionSystem {

    PApplet parent;
    public ArrayList<Tile> colliderListA;
    public ArrayList<Tile> colliderListB;
    private TileMap layerA;
    private TileMap layerB;

    //Constructor points the collision system to the two Tilemaps that will interact
    public LayerCollisionSystem(TileMap layerA, TileMap layerB, PApplet parent)
    {
        this.parent = parent;
        this.layerA = layerA;
        this.layerB = layerB;
        colliderListA = new ArrayList<>();
        colliderListB = new ArrayList<>();
        LayerLoop(layerA, colliderListA);
        LayerLoop(layerB, colliderListB);
    }

    //Method adds tile to an array list ONLY if it has an InterlayerCollider within its components
    public void UpdateList(Tile t, ArrayList layerList)
    {
        for (int i = 0; i < t.componentList.size(); i++)
        {
            if (t.componentList.get(i) instanceof InterlayerCollider)
            {
                layerList.add(t);
            }
        }
    }

    //Filters through every tile in a TileMap using the UpdateList method
    private void LayerLoop(TileMap m, ArrayList colliderList)
    {
        for (int i = 0; i < m.getEveryTile().length; i++)
        {
            UpdateList(m.getEveryTile()[i], colliderList);
        }
    }

    //Checks for and feeds the tiles in the same position as tiles in the colliderList from the "other" TileMap
    private void CheckCollisionsPerList(ArrayList<Tile> layerList, TileMap otherLayer)
    {
        for (int i = 0; i < layerList.size(); i++)
        {
            for (int o = 0; o < layerList.get(i).componentList.size(); o++)
            {
                Tile t = otherLayer.getTile(layerList.get(i).getCol(), layerList.get(i).getRow());
                //Instanceof checks the metadata of the object to get a matching result
                if (layerList.get(i).componentList.get(o) instanceof InterlayerCollider)
                {
                    ((InterlayerCollider) layerList.get(i).componentList.get(o)).setCollided(true, t);
                }
            }
        }
    }

    //Both TileMaps exchanging information
    public void CheckCollisions()
    {
        CheckCollisionsPerList(colliderListA, layerB);
        CheckCollisionsPerList(colliderListB, layerA);
    }
}
