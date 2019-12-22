package core.DataBreak.Tile_Types.UI_Tiles;
import core.Tile_Engine.Tile_System.Components.FillColor;
import core.Tile_Engine.Tile_System.Components.TextDisplay;
import core.Tile_Engine.Tile_System.Tile;
import processing.core.PApplet;
import processing.core.PConstants;

public class ButtonTile extends Tile {

    public FillColor fillColor;
    public TextDisplay textDisplay;

    private String info;

    public ButtonTile(int col, int row, int size, PApplet p)
    {
        super(col, row, size, p);
        info = "0";
        this.fillColor = new FillColor(this, new int[] {(int)parent.random(120, 220), (int)parent.random(120, 220), (int)parent.random(120, 220)}, true);
        this.textDisplay = new TextDisplay(this, info, 25, new int[] {255, 255, 255}, PConstants.CENTER, PConstants.CENTER);
    }

    @Override
    public void Render() {
        CompUpdate();
    }
}
