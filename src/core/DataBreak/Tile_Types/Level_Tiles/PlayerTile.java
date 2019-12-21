package core.DataBreak.Tile_Types.Level_Tiles;
import core.Tile_Engine.Tile_System.Components.*;
import core.Tile_Engine.Tile_System.Tile;
import processing.core.PApplet;

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
        this.fillColor = new FillColor(this, new int[] {255, 0, 0}, false);
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
        else if (this.interlayerCollider.collidesWith("SliderTile"))
        {
            ChangeMove(this.interlayerCollider.collideType, this.movement.getDir());
        }
        else if (this.interlayerCollider.collidesWith("TargetTile"))
        {
            if (hasCollected && ((TargetTile) this.interlayerCollider.collideType).value)
            { gameOver = true; }
            else if (((TargetTile)this.interlayerCollider.collideType).value)
            {
                hasCollected = true;
                this.fillColor.setHue(new int[] {255, 255, 0});
                ((TargetTile) this.interlayerCollider.collideType).deValue();
            }
        }
        else if (this.interlayerCollider.collidesWith("FlipTile"))
        {
            if (!this.flipIt)
            {
                this.flipIt = true;
                this.movement.setDir(Directions.STATIC);
                this.fillColor.setHue(new int[] {0, 255, 0});
            }
        }
        else if (this.interlayerCollider.collidesWith("ExitTile"))
        {
            if (hasCollected)
            {
                victory = 1;
                this.movement.setDir(Directions.STATIC);
                this.fillColor.setHue(new int[] {0, 255, 0});
            }
            else
            { gameOver = true; }
        }
        else if (this.interlayerCollider.collidesWith("EmptyTile"))
        {
            this.gameOver = true;
            this.Destroy();
        }

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
