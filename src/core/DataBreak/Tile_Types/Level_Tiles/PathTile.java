package core.DataBreak.Tile_Types.Level_Tiles;
import core.DataBreak.DataBreak;
import core.Tile_Engine.Tile_System.Components.Sprite;
import core.Tile_Engine.Tile_System.Tile;
import processing.core.PApplet;

//Basic tile, used as a starting point for player tiles
public class PathTile extends Tile {

    public Sprite sprite;

    public PathTile(int col, int row, int size, PApplet p)
    {
        super(col, row, size, p);
        this.sprite = new Sprite(this, "src/core/DataBreak/Assets/Path.png", DataBreak.defaultTileColor);
    }

    @Override
    public void Render() { this.CompUpdate(); }
}
