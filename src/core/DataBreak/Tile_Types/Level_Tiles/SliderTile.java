package core.DataBreak.Tile_Types.Level_Tiles;
import core.Tile_Engine.Tile_System.Components.Directions;
import core.Tile_Engine.Tile_System.Components.Movement;
import core.Tile_Engine.Tile_System.Components.ProximityCollider;
import core.Tile_Engine.Tile_System.Components.Sprite;
import core.Tile_Engine.Tile_System.Tile;
import processing.core.PApplet;

public class SliderTile extends Tile {

    public Sprite spirte;
    public Movement movement;
    public ProximityCollider proximityCollider;

    public SliderTile(int col, int row, int size, PApplet p)
    {
        super(col, row, size, p);
        this.spirte = new Sprite(this, "src/core/DataBreak/Assets/Slider.png");
        this.movement = new Movement(this, Directions.STATIC);
        this.proximityCollider = new ProximityCollider(this);
    }

    @Override
    public void Render() {
        if (this.movement.getDir().equals(Directions.RIGHT) && this.proximityCollider.collidesWithEast("any"))
        {
            this.movement.setDir(Directions.STATIC);
        }
        if (this.movement.getDir().equals(Directions.DOWN) && this.proximityCollider.collidesWithSouth("any"))
        {
            this.movement.setDir(Directions.STATIC);
        }
        if (this.movement.getDir().equals(Directions.UP) && this.proximityCollider.collidesWithNorth("any"))
        {
            this.movement.setDir(Directions.STATIC);
        }
        if (this.movement.getDir().equals(Directions.LEFT) && this.proximityCollider.collidesWithWest("any"))
        {
            this.movement.setDir(Directions.STATIC);
        }
        CompUpdate();
    }
}
