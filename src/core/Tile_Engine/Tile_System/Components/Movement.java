package core.Tile_Engine.Tile_System.Components;
import core.Tile_Engine.Tile_System.Component;
import core.Tile_Engine.Tile_System.Tile;

//Component used for basic tile movement horizontally and vertically, this is especially delicate and complicated as it ruins the tile's column and row reference
//When overlaping tiles on the same layer tiles that move are granted priority when fetching information about that column/row position
public class Movement extends Component {

    //Uses enum for the direction variable
    Directions directions;

    //Tile has initial direction and requires method setDir to change direction
    public Movement(Tile t, Directions directions)
    {
        super(t);
        this.directions = directions;
    }

    @Override
    protected void Update() {
        switch(directions)
        {
            case RANDOM:
                directions = Directions.values()[(int)t.parent.random(0, Directions.values().length)];
                break;
            case STATIC:
                break;
            case UP:
                t.setRow(t.getRow()-1);
                break;
            case DOWN:
                t.setRow(t.getRow()+1);
                break;
            case LEFT:
                t.setCol(t.getCol()-1);
                break;
            case RIGHT:
                t.setCol(t.getCol()+1);
                break;
        }
    }

    public Directions getDir() {
        return directions;
    }

    public void setDir(Directions directions) {
        this.directions = directions;
    }
}
