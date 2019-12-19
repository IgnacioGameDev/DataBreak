package core.Tile_Engine.Tile_System.Components;

import core.Tile_Engine.Tile_System.Component;
import core.Tile_Engine.Tile_System.Tile;

import java.util.ArrayList;

//Component that allows a tile to fetch information about all the tiles around it in the same layer
public class ProximityCollider extends Component {

    public int x, y;
    private ArrayList<Tile> proxys;

    //Pointers to all tiles around the parent tile
    private Tile collidedNorth;
    private Tile collidedSouth;
    private Tile collidedWest;
    private Tile collidedEast;
    private Tile collidedNorthEast;
    private Tile collidedSotuhEast;
    private Tile collidedSouthWest;
    private Tile collidedNorthWest;

    public ProximityCollider(Tile t)
    {
        super(t);
        proxys = new ArrayList<>();
        setCollidedNorth(t);
        setCollidedSouth(t);
        setCollidedWest(t);
        setCollidedEast(t);
    }

    @Override
    protected void Update() {
        this.x = t.getCol();
        this.y = t.getRow();
    }

    public void setProxys(ArrayList<Tile> proxys) { this.proxys = proxys; }

    //Method used by the proxy collision system to update the pointers to the tiles around the parent tile
    public void UpdateProxys()
    {
        if (!proxys.isEmpty())
        {
            setCollidedNorth(proxys.get(3));
            setCollidedSouth(proxys.get(5));
            setCollidedWest(proxys.get(1));
            setCollidedEast(proxys.get(7));
            setCollidedNorthEast(proxys.get(6));
            setCollidedSotuhEast(proxys.get(8));
            setCollidedSouthWest(proxys.get(2));
            setCollidedNorthWest(proxys.get(0));
        }
    }

    //Can be used to collectively look at information of all the tiles by looping through the arraylist
    public ArrayList<Tile> NearbyTiles()
    {
        return proxys;
    }

    //Eight methods named after the cardinal system for the parent tile to check the metadata of tiles around it, uses Strings for versatility
    //Any uses the default empty tile as the exception for returning true, used for tiles that can collide with any other user made tiles, to prevent repetition
    public boolean collidesWithNorth(String tileType)
    {
        if (tileType.equals("any")) { if (!this.collidedNorth.getClass().getSimpleName().equals("EmptyTile")) { return true; } }
        if (this.collidedNorth.getClass().getSimpleName().equals(tileType))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean collidesWithSouth(String tileType)
    {
        if (tileType.equals("any")) { if (!this.collidedSouth.getClass().getSimpleName().equals("EmptyTile")) { return true; } }
        if (this.collidedSouth.getClass().getSimpleName().equals(tileType))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean collidesWithWest(String tileType)
    {
        if (tileType.equals("any")) { if (!this.collidedWest.getClass().getSimpleName().equals("EmptyTile")) { return true; } }
        if (this.collidedWest.getClass().getSimpleName().equals(tileType))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean collidesWithEast(String tileType)
    {
        if (tileType.equals("any")) { if (!this.collidedEast.getClass().getSimpleName().equals("EmptyTile")) { return true; } }
        if (this.collidedEast.getClass().getSimpleName().equals(tileType))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean collidesWithNorthEast(String tileType)
    {
        if (tileType.equals("any")) { if (!this.collidedNorthEast.getClass().getSimpleName().equals("EmptyTile")) { return true; } }
        if (this.collidedNorthEast.getClass().getSimpleName().equals(tileType))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean collidesWithSouthEast(String tileType)
    {
        if (tileType.equals("any")) { if (!this.collidedSotuhEast.getClass().getSimpleName().equals("EmptyTile")) { return true; } }
        if (this.collidedSotuhEast.getClass().getSimpleName().equals(tileType))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean collidesWithSouthWest(String tileType)
    {
        if (tileType.equals("any")) { if (!this.collidedSouthWest.getClass().getSimpleName().equals("EmptyTile")) { return true; } }
        if (this.collidedSouthWest.getClass().getSimpleName().equals(tileType))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean collidesWithNorthWest(String tileType)
    {
        if (tileType.equals("any")) { if (!this.collidedNorthWest.getClass().getSimpleName().equals("EmptyTile")) { return true; } }
        if (this.collidedNorthWest.getClass().getSimpleName().equals(tileType))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void setCollidedNorth(Tile collidedNorth) { this.collidedNorth = collidedNorth; }

    public void setCollidedSouth(Tile collidedSouth) { this.collidedSouth = collidedSouth; }

    public void setCollidedWest(Tile collidedWest) { this.collidedWest = collidedWest; }

    public void setCollidedEast(Tile collidedEast) { this.collidedEast = collidedEast; }

    public void setCollidedNorthEast(Tile collidedNorthEast) { this.collidedNorthEast = collidedNorthEast; }

    public void setCollidedSotuhEast(Tile collidedSotuhEast) { this.collidedSotuhEast = collidedSotuhEast; }

    public void setCollidedSouthWest(Tile collidedSouthWest) { this.collidedSouthWest = collidedSouthWest; }

    public void setCollidedNorthWest(Tile collidedNorthWest) { this.collidedNorthWest = collidedNorthWest; }
}
