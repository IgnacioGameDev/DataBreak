package core.DataBreak.Tile_Types.Level_Tiles;
import core.Tile_Engine.Tile_System.Components.FillColor;
import core.Tile_Engine.Tile_System.Tile;
import processing.core.PApplet;

public class BackgroundTile extends Tile {

    public FillColor fillColor;

    public BackgroundTile(int col, int row, int size, PApplet p)
    {
        super(col, row, size, p);
        this.fillColor = new FillColor(this, new int[] {205, 240, 220}, false);
    }

    @Override
    public void Render() {
        this.CompUpdate();
    }
}
