package core.DataBreak.Tile_Types.Level_Tiles;

import core.Tile_Engine.Tile_System.Components.Sprite;
import core.Tile_Engine.Tile_System.Tile;
import processing.core.PApplet;

public class TargetTile extends Tile {

    public Sprite sprite;
    public boolean value;

    public TargetTile(int col, int row, int size, PApplet p)
    {
        super(col, row, size, p);
        sprite = new Sprite(this, "src/core/DataBreak/Assets/Target.png");
        sprite.setTint(new int[] {0, 0, 255});
        value = true;
    }

    @Override
    public void Render() {
        if (!value)
        {
            this.sprite.setTint(new int[] {0, 0, 0});
        }
        CompUpdate();
    }

    public void deValue() {
        this.value = false;
    }
}
