package core.DataBreak.Tile_Types.Level_Tiles;
import core.DataBreak.DataBreak;
import core.Tile_Engine.Tile_System.Components.Sprite;
import core.Tile_Engine.Tile_System.Tile;
import processing.core.PApplet;

//Some tiles, including all directional tiles and others use a transparent png as a sprite so tiles from other layers can overlap visually
//Directional tiles redirect the player tiles in a specific direction
//Player tiles are destroyed if over the background
public class DirDownTile extends Tile {

    public Sprite sprite;

    public DirDownTile(int col, int row, int size, PApplet p)
    {
        super(col, row, size, p);
        this.sprite = new Sprite(this, "src/core/DataBreak/Assets/ArrowDown.png", DataBreak.specialTileColor);
    }

    @Override
    public void Render() {
        this.CompUpdate();
    }
}
