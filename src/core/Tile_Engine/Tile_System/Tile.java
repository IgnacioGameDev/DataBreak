package core.Tile_Engine.Tile_System;
import core.Tile_Engine.Data_Management.Serializable;
import processing.core.PApplet;
import processing.data.JSONObject;

import java.util.ArrayList;

//Base class which holds all the information needed for a tile based system
//Especially important are the column and row values which represent the position in a grid
//boolean isActive is used for garbage collection and removal of tiles

public abstract class Tile implements Serializable {

    public PApplet parent;

    public ArrayList<Component> componentList = new ArrayList<>();

    private int col;
    private int row;
    private int size;
    private int x, y;
    private int cell;
    private boolean isActive;

    public Tile(int col, int row, int size, PApplet p)
    {
        this.parent = p;
        this.col = col;
        this.row = row;
        this.size = size;
        isActive = true;
    }

    //Abstract methods are used to give an idea of what the object should fundamentally do ie. every tile will need to be Rendered or visually represented in a game
    public abstract void Render();

    //Method that cycles and updates all components
    public void CompUpdate()
    {
        for(Component c : this.componentList)
        {
            c.Update();
        }
    }

    public void addComponentList(Component c) { componentList.add(c); }

    public int getCol() { return col; }

    public int getRow() { return row; }

    public void setCol(int col) { this.col = col; }

    public void setRow(int row) { this.row = row; }

    public int getSize() { return size; }

    public void setSize(int size) { this.size = size; }

    //x and y are the pixel positions of each tile
    public int getX() { x = col*size; return x; }

    public int getY() { y = row*size; return y; }

    public int getCell() { return cell; }

    //The cell variable is a numbering system to reference a tile's position within a grid with a single variable instead of two
    public void setCell(int cell) { this.cell = cell; }

    public boolean isActive() { return isActive; }

    //Stops calling any methods from the tile, triggering garbage collection, object is deleted
    public void Destroy() { this.isActive = false; }

    //Information about this class or any of its subclasses, including its metadata can be saved into a JSON file
    //This information can then be restored ingame by loading and feeding it back into a constructor function or other methods, restoring a previous game state
    @Override
    public JSONObject serializeToJSON()
    {
        JSONObject tileData = new JSONObject();
        tileData.setInt("col", this.col);
        tileData.setInt("row", this.row);
        tileData.setInt("size", this.size);
        tileData.setString("tileType", this.getClass().getSimpleName());
        return tileData;
    }

    @Override
    public void loadJSONObject(JSONObject json) {
        this.col = json.getInt("col");
        this.row = json.getInt("row");
    }
}
