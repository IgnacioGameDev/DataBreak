package core.DataBreak.Tile_Types.Level_Tiles;
import core.DataBreak.DataBreak;
import core.Tile_Engine.Tile_System.Components.Sprite;
import core.Tile_Engine.Tile_System.Tile;
import processing.core.PApplet;

//Tile contains the objective for the player in each level
//The sprite component in this tile changes color depending on a boolean
public class TargetTile extends Tile {

    public Sprite sprite;
    public boolean value;

    public TargetTile(int col, int row, int size, PApplet p)
    {
        super(col, row, size, p);
        sprite = new Sprite(this, "src/core/DataBreak/Assets/Target.png", DataBreak.specialTileColor);
        value = true;
    }

    @Override
    public void Render() {
        if (!value)
        {
            this.sprite.setTint(DataBreak.defaultTileColor);
        }
        CompUpdate();
    }

    public void deValue() {
        this.value = false;
    }
}
