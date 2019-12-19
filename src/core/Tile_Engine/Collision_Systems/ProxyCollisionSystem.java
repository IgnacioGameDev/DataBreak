package core.Tile_Engine.Collision_Systems;

import core.Tile_Engine.Tile_System.Components.ProximityCollider;
import core.Tile_Engine.Tile_System.Tile;
import core.Tile_Engine.Tile_System.TileMap;

import java.util.ArrayList;

//This object when inside a TileMap filters tiles that have ProximityCollider and feeds information about the tiles around them
public class ProxyCollisionSystem {

    TileMap tileMap;
    public ArrayList<Tile> proxyList;

    public ProxyCollisionSystem(TileMap tileMap)
    {
        this.tileMap = tileMap;
        proxyList = new ArrayList<>();
        for (int i = 0; i < tileMap.getEveryTile().length; i++) {
            UpdateList(tileMap.getEveryTile()[i], proxyList);
        }
    }

    //Checks individual tiles for the collider
    public void UpdateList(Tile t, ArrayList layerList)
    {
        for (int i = 0; i < t.componentList.size(); i++)
        {
            if (t.componentList.get(i) instanceof ProximityCollider)
            {
                layerList.add(t);
            }
        }
    }

    //Checks all the tiles around one tile
    private ArrayList<Tile> CheckProxy(Tile t)
    {
        ArrayList<Tile> l = new ArrayList<>();
        for (int o = -1; o <  2; o++)
        {
            for (int p = -1; p < 2; p++)
            {
                l.add(tileMap.getTile(t.getCol()+o, t.getRow()+p));
            }
        }
        //Lines 39 to 45 are a replacement for the code below, using a for loop inside a for loop to prevent repetition
//        l.add(tileMap.getTile(t.getCol(), t.getRow()-1));
//        l.add(tileMap.getTile(t.getCol(), t.getRow()+1));
//        l.add(tileMap.getTile(t.getCol()-1, t.getRow()));
//        l.add(tileMap.getTile(t.getCol()+1, t.getRow()));
//        l.add(tileMap.getTile(t.getCol()+1, t.getRow()-1));
//        l.add(tileMap.getTile(t.getCol()+1, t.getRow()+1));
//        l.add(tileMap.getTile(t.getCol()-1, t.getRow()+1));
//        l.add(tileMap.getTile(t.getCol()-1, t.getRow()-1));
        return l;
    }

    //Checks and updates the pointers of all the tiles in the list with all the tiles around each one
    public void CheckCollisions()
    {
        for (int i = 0; i < proxyList.size(); i++)
        {
            for (int j = 0; j < proxyList.get(i).componentList.size(); j++)
            {
                if (proxyList.get(i).componentList.get(j) instanceof ProximityCollider)
                {
                    ((ProximityCollider) proxyList.get(i).componentList.get(j)).setProxys(CheckProxy(proxyList.get(i)));
                    ((ProximityCollider) proxyList.get(i).componentList.get(j)).UpdateProxys();
                }
            }
        }
    }
}
