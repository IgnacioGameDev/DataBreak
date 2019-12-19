package core.DataBreak.Tile_Types.Level_Tiles;
import core.Tile_Engine.Tile_System.Components.Sprite;
import core.Tile_Engine.Tile_System.Tile;
import processing.core.PApplet;

public class DirRightTile extends Tile {

    public Sprite sprite;

    public DirRightTile(int col, int row, int size, PApplet p)
    {
        super(col, row, size, p);
        this.sprite = new Sprite(this, "src/core/DataBreak/Assets/ArrowRight.png");
    }

    @Override
    public void Render() { CompUpdate(); }
}
