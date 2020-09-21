package core.Tile_Engine.Tile_System;
import core.Tile_Engine.Collision_Systems.ProxyCollisionSystem;
import processing.core.PApplet;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

//Makes a grid using a "base" tile then allows reference to grid positions and modification of tiles within it
//Games with only one could refer to it as a "grid" but in games with multiple TileMaps we can think of them as "layers"
public class TileMap {
    public PApplet parent;
    private ArrayList<Tile> initialTiles;
    private ArrayList<Tile> addedTiles;
    private ProxyCollisionSystem proxyCollisionSystem;
    private int colNum;
    private int rowNum;
    private int tileSize;

    //Constructor creates a list of empty tiles which is then filled up by tiles ordered neatly by their respective cell value
    //ie index 46 on the tile arraylist will actually be the 46th tile on the grid or the tile with a cell value of 46
    public TileMap(int colNum, int rowNum, int tileSize, Tile baseTile, PApplet p) {
        this.parent = p;
        this.colNum = colNum;
        this.rowNum = rowNum;
        this.tileSize = tileSize;
        initialTiles = new ArrayList<>();
        addedTiles = new ArrayList<>();

        for  (int i = 0; i < (colNum * rowNum) + 1; i++)
        {
            initialTiles.add(new EmptyTile(1, 1, 1, p));
        }

        //To start the grid is made by a single tiletype, the only option could have been the EmptyTile but thanks to this code
        //any subclass of tile which has the same basic parameters in constructor will work
        //Extracts metadata from the constructor variable "baseTile"
        for (int i = 0; i < colNum; i++) {
            for (int j = 0; j < rowNum; j++)
            {
                Tile n = null;
                try
                {
                    n = baseTile.getClass().getConstructor(int.class, int.class, int.class, PApplet.class).newInstance(i, j, tileSize, this.parent);
                }
                //Handles exceptions in case constructors don't match, this is important as there is a "leap of faith" that the user will abide to the constructor parameters
                catch (InstantiationException e) { e.printStackTrace(); }
                catch (InvocationTargetException e) { e.printStackTrace(); }
                catch (NoSuchMethodException e) { e.printStackTrace(); }
                catch (IllegalAccessException e) { e.printStackTrace(); }
                n.setCell(((j) * colNum) + (i+1));
                initialTiles.set(n.getCell(), n);
            }
        }

        //Proximity collisions happen within each grid or "layer" independently
        proxyCollisionSystem = new ProxyCollisionSystem(this);
    }

    //Only calls methods in active tiles, calls methods in the priority Arraylist addedTiles before any other tiles
    public void Update()
    {
        proxyCollisionSystem.CheckCollisions();

        for (int a = 0; a < addedTiles.size(); a++)
        {
            if (addedTiles.get(a).isActive())
            {
                addedTiles.get(a).Render();
            }
            else
            {
                addedTiles.set(a, new EmptyTile(addedTiles.get(a).getCol(), addedTiles.get(a).getRow(), addedTiles.get(a).getSize(), parent));
            }
        }

        for (int i = 0; i < initialTiles.size(); i++)
        {
            if (initialTiles.get(i).isActive())
            {
                initialTiles.get(i).Render();
            }
            else
            {
                initialTiles.set(i, new EmptyTile(initialTiles.get(i).getCol(), initialTiles.get(i).getRow(), initialTiles.get(i).getSize(), parent));
            }
        }
    }

    //Best method in the entire project, being able to reference tiles in the grid, tiles in the addedTiles list take priority
    //Uses column and row position in the grid rather than an index in the arraylist or pixel positions
    public Tile getTile(int col, int row)
    {
        Tile t = null;
        if (!addedTiles.isEmpty())
        {
            for (int p = 0; p < initialTiles.size(); p++)
            {
                if (initialTiles.get(p).getCol() == col && initialTiles.get(p).getRow() == row)
                {
                    t = initialTiles.get(p);
                }
            }
            for (int i = 0; i < addedTiles.size(); i++)
            {
                if (addedTiles.get(i).getCol() == col && addedTiles.get(i).getRow() == row)
                {
                    t = addedTiles.get(i);
                }
            }
        }
        else
        {
            for (int p = 0; p < initialTiles.size(); p++)
            {
                if (initialTiles.get(p).getCol() == col && initialTiles.get(p).getRow() == row)
                {
                    t = initialTiles.get(p);
                }
            }

        }
        return t;
    }

    //Identical to getTile but uses pixel positions to point at tiles instead of column and row, converts them using Math.floordiv method (division without remainder)
    public Tile getTilePixel(int x, int y)
    {
        int gridX = Math.floorDiv(x, tileSize);
        int gridY = Math.floorDiv(y, tileSize);
        Tile t = null;
        if (!addedTiles.isEmpty())
        {
            for (int p = 0; p < initialTiles.size(); p++)
            {
                if (initialTiles.get(p).getCol() == gridX && initialTiles.get(p).getRow() == gridY)
                {
                    parent.print(p);
                    t = initialTiles.get(p);
                }
            }
            for (int i = 0; i < addedTiles.size(); i++)
            {
                if (addedTiles.get(i).getCol() == gridX && addedTiles.get(i).getRow() == gridY)
                {
                    t = addedTiles.get(i);
                }
            }
        }
        else
        {
            for (int p = 0; p < initialTiles.size(); p++)
            {
                if (initialTiles.get(p).getCol() == gridX && initialTiles.get(p).getRow() == gridY)
                {
                    t = initialTiles.get(p);
                }
            }

        }
        return t;
    }

    //Converts a pixel integer into a col/row value
    //Seemed redundant due to getTilePixel but sometimes the value is needed and not the object, which improves performance
    public int getLinePixel(int x)
    {
        int gridX = Math.floorDiv(x, tileSize);
        return gridX;
    }

    //Returns all tiles in the grid as an array
    //Interesting to note it will ignore tiles which lie "below" an added tile as the one on top is granted priority
    public Tile[] getEveryTile()
    {
        int index = 0;
        Tile[] allTiles = new Tile[colNum*rowNum];
        for (int i = 0; i < colNum; i++) {
            for (int j = 0; j < rowNum; j++) {
                allTiles[index] = getTile(i, j);
                index += 1;
            }
        }
        return allTiles;
    }

    //Most other objects require information about the TileMap as a TileMap holds information rather than processing it

    public ArrayList<Tile> getInitialTiles() { return initialTiles; }

    public void setInitialTiles(ArrayList<Tile> initialTiles) { this.initialTiles = initialTiles; }

    public ArrayList<Tile> getAddedTiles() { return addedTiles; }

    public void setAddedTiles(ArrayList<Tile> addedTiles) { this.addedTiles = addedTiles; }

    public int getColNum() { return colNum; }

    public int getRowNum() { return rowNum; }

    public int getTileSize() { return tileSize; }

    public ProxyCollisionSystem getProxyCollisionSystem() { return proxyCollisionSystem; }
}
