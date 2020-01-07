package core.DataBreak.Tile_Types.UI_Tiles;
import core.Tile_Engine.Tile_System.Components.FillColor;
import core.Tile_Engine.Tile_System.Tile;
import processing.core.PApplet;
import java.awt.*;

//Used as UI to indicate the player where they have placed the player tiles
public class MarkerTile extends Tile {

    public FillColor fillColor;

    public MarkerTile(int col, int row, int size, PApplet p)
    {
        super(col, row, size, p);
        this.fillColor = new FillColor(this, new Color(242, 31, 12), false);
    }

    @Override
    public void Render() {
        CompUpdate();
    }
}
