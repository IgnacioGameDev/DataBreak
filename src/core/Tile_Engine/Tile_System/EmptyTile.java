package core.Tile_Engine.Tile_System;
import processing.core.PApplet;

//Tile that holds information but does and interacts with nothing, almost any game idea demands "empty space" which this provides
public class EmptyTile extends Tile {

    public EmptyTile(int col, int row, int size, PApplet p)
    {
        super(col, row, size, p);
    }

    @Override
    public void Render() { }
}
