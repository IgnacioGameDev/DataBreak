package core.DataBreak.Tile_Types.Level_Tiles;
import core.DataBreak.DataBreak;
import core.Tile_Engine.Tile_System.Components.FillColor;
import core.Tile_Engine.Tile_System.Tile;
import processing.core.PApplet;

//Used to draw the background using tiles instead of the default processing background method
//Only coloured tile has no functionality
public class BackgroundTile extends Tile {

    public FillColor fillColor;

    public BackgroundTile(int col, int row, int size, PApplet p)
    {
        super(col, row, size, p);
        this.fillColor = new FillColor(this, DataBreak.backgroundColor, false);
    }

    @Override
    public void Render() {
        this.CompUpdate();
    }
}
