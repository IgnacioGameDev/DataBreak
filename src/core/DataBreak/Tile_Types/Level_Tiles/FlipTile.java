package core.DataBreak.Tile_Types.Level_Tiles;

import core.Tile_Engine.Tile_System.Components.Sprite;
import core.Tile_Engine.Tile_System.Tile;
import processing.core.PApplet;

public class FlipTile extends Tile {

    public Sprite sprite;
    private boolean lever;

    public FlipTile(int col, int row, int size, PApplet p)
    {
        super(col, row, size, p);
        sprite = new Sprite(this, "src/core/DataBreak/Assets/Flip.png");
    }

    @Override
    public void Render() {
        CompUpdate();
    }

    public void setLever(boolean lever) { this.lever = lever; }
}
