package core.DataBreak.Tile_Types.Level_Tiles;

import core.Tile_Engine.Tile_System.Components.Sprite;
import core.Tile_Engine.Tile_System.Tile;
import processing.core.PApplet;

public class ExitTile extends Tile {

    public Sprite sprite;

    public ExitTile(int col, int row, int size, PApplet p)
    {
        super(col, row, size, p);
        sprite = new Sprite(this, "src/core/DataBreak/Assets/Exit.png");
    }

    @Override
    public void Render() { CompUpdate(); }
}
