package core.Tile_Engine.Tile_System.Components;

import core.Tile_Engine.Tile_System.Component;
import core.Tile_Engine.Tile_System.EmptyTile;
import core.Tile_Engine.Tile_System.Tile;

//Component which allows tiles to extract information about a tile in the same position but in a different layer
public class InterlayerCollider extends Component {

    //Pointer to the tile in the "other" layer, allows the tile with this component to interact with this tile
    public Tile collideType;
    private boolean collided;

    public InterlayerCollider(Tile t)
    {
        super(t);
        //Prevents NullpointerException
        Tile e = new EmptyTile(1, 1, 1, t.parent);
        setCollided(false, e);
    }

    @Override
    protected void Update() {
        //This component is used as a tag for the collision system, as a tile cant interact with other tiles directly it prevents this update from being useful
    }

    //Used by the collision system to relay the information to this component
    public void setCollided(boolean collided, Tile collideType) {
        this.collided = collided;
        this.collideType = collideType;
    }

    //Accesses the metadata from the pointer tile to return true when it matches the information the tile is requesting, uses a string so the game can use any tilenames.
    public boolean collidesWith(String tileType)
    {
        if (this.collideType.getClass().getSimpleName().equals(tileType))
        {
            return collided;
        }
        else
        {
            return false;
        }
    }
}
