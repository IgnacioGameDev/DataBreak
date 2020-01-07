package core.DataBreak.Tile_Types.Level_Tiles;
import core.Tile_Engine.Tile_System.Components.Sprite;
import core.Tile_Engine.Tile_System.Tile;
import processing.core.PApplet;
import java.awt.*;

//This tile rotates all directional tiles 180 degrees when interacted with
public class FlipTile extends Tile {

    public Sprite sprite;

    public FlipTile(int col, int row, int size, PApplet p)
    {
        super(col, row, size, p);
        sprite = new Sprite(this, "src/core/DataBreak/Assets/Flip.png", new Color(173, 48, 255));
    }

    @Override
    public void Render() {
        CompUpdate();
    }
}
