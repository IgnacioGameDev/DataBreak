package core.DataBreak.Tile_Types.Level_Tiles;
import core.Tile_Engine.Tile_System.Components.*;
import core.Tile_Engine.Tile_System.Tile;
import processing.core.PApplet;

import java.awt.*;

//This tile contains almost all functionality for the gameplay, interacting with all other tiles in the game
//Uses both types of collider types to gather information about tiles around it
//Contains booleans that control the game state and other game events (eg. game over, flipping the switch)
//Makes use of movement component
public class PlayerTile extends Tile {

    public FillColor fillColor;
    public Movement movement;
    public InterlayerCollider interlayerCollider;
    public ProximityCollider proximityCollider;

    private boolean gameOver;
    private int victory;
    private boolean hasCollected;
    private Directions specialDir;
    private boolean flipIt;

    public PlayerTile(int col, int row, int size, PApplet p, Directions dir)
    {
        super(col, row, size, p);
        this.fillColor = new FillColor(this, new Color(242, 31, 12), false);
        this.movement = new Movement(this, dir);
        this.interlayerCollider = new InterlayerCollider(this);
        this.proximityCollider = new ProximityCollider(this);
        hasCollected = false;
        flipIt = false;
        gameOver = false;
        victory = 0;
    }

    @Override
    public void Render() {
        //Colliding with directional tiles in a different layer changes the direction accordingly
        if (this.interlayerCollider.collidesWith("DirUpTile"))
        {
            this.movement.setDir(Directions.UP);
        }
        else if (this.interlayerCollider.collidesWith("DirDownTile"))
        {
            this.movement.setDir(Directions.DOWN);

        }
        else if (this.interlayerCollider.collidesWith("DirRightTile"))
        {
            this.movement.setDir(Directions.RIGHT);
        }
        else if (this.interlayerCollider.collidesWith("DirLeftTile"))
        {
            this.movement.setDir(Directions.LEFT);
        }

        //Colliding with a slider tile triggers its movement component to travel with it
        else if (this.interlayerCollider.collidesWith("SliderTile"))
        {
            ChangeMove(this.interlayerCollider.collideType, this.movement.getDir());
        }

        //Colliding with the target tile if the data is already collected game over is triggered, if it hasnt been collected it takes it
        //Makes use of component specific methods like setHue to change color
        //Makes use of collideType variable to change the tile it is colliding with
        else if (this.interlayerCollider.collidesWith("TargetTile"))
        {
            if (hasCollected && ((TargetTile) this.interlayerCollider.collideType).value)
            { gameOver = true; }
            else if (((TargetTile)this.interlayerCollider.collideType).value)
            {
                hasCollected = true;
                this.fillColor.setHue(new Color(242, 203, 5));
                ((TargetTile) this.interlayerCollider.collideType).deValue();
            }
        }

        //Hits the flip switch
        else if (this.interlayerCollider.collidesWith("FlipTile"))
        {
            if (!this.flipIt)
            {
                this.flipIt = true;
                this.movement.setDir(Directions.STATIC);
                this.fillColor.setHue(Color.WHITE);
            }
        }

        //If the player has collected the data it will check the winning condition when arriving at the exit tile
        //The reason victory variable is an integer and not boolean is there might be multiple objectives in a single level, these can be added up instead of converted
        else if (this.interlayerCollider.collidesWith("ExitTile"))
        {
            if (hasCollected)
            {
                victory = 1;
                this.movement.setDir(Directions.STATIC);
                this.fillColor.setHue(Color.WHITE);
            }
            else
            { gameOver = true; }
        }

        //When the player exits the level it triggers a game over
        else if (this.interlayerCollider.collidesWith("EmptyTile"))
        {
            this.gameOver = true;
            this.Destroy();
        }

        //Special conditions using the proximity collider for diagonal collisions with other players
        //Used as a failsafe for the hard coded collisions in the level manager
        if (this.proximityCollider.collidesWithNorth("PlayerTile") && this.movement.getDir() == Directions.UP)
        {
            if (((PlayerTile)this.proximityCollider.NearbyTiles().get(3)).getSpecialDir() == Directions.DOWN)
            {
                this.gameOver = true;
                this.Destroy();
            }
        }
        else if (this.proximityCollider.collidesWithSouth("PlayerTile") && this.movement.getDir() == Directions.DOWN)
        {
            if (((PlayerTile)this.proximityCollider.NearbyTiles().get(5)).getSpecialDir() == Directions.UP)
            {
                this.gameOver = true;
                this.Destroy();
            }
        }
        else if (this.proximityCollider.collidesWithEast("PlayerTile") && this.movement.getDir() == Directions.RIGHT)
        {
            if (((PlayerTile)this.proximityCollider.NearbyTiles().get(7)).getSpecialDir() == Directions.LEFT)
            {
                this.gameOver = true;
                this.Destroy();
            }
        }
        else if (this.proximityCollider.collidesWithWest("PlayerTile") && this.movement.getDir() == Directions.LEFT)
        {
            if (((PlayerTile)this.proximityCollider.NearbyTiles().get(1)).getSpecialDir() == Directions.RIGHT)
            {
                this.gameOver = true;
                this.Destroy();
            }
        }

        this.CompUpdate();
        specialDir = this.movement.getDir();
    }

    //Fetches the movement component of a tile and changes its direction, used for the slider tile interaction with the player
    private void ChangeMove(Tile t, Directions dir)
    {
        for (int i = 0; i < t.componentList.size(); i++)
        {
            if (t.componentList.get(i) instanceof Movement)
            {
                ((Movement) t.componentList.get(i)).setDir(dir);
            }
        }
    }

    //Variable fetching methods
    public int getVictory() {
        return victory;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean hasCollected() {
        return hasCollected;
    }

    public Directions getSpecialDir() { return specialDir; }

    public boolean isFlipIt() { return flipIt; }
}
