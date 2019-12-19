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
        this.fillColor = new FillColor(this, new int[] {255, 0, 0}, true);
        this.textDisplay = new TextDisplay(this, info, 12, new int[] {255, 255, 255}, PConstants.CENTER, PConstants.CENTER);
    }

    @Override
    public void Render() {
        CompUpdate();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
