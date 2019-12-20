package core.DataBreak.Tile_Types.Level_Tiles;
import core.Tile_Engine.Tile_System.Components.*;
import core.Tile_Engine.Tile_System.Tile;
import processing.core.PApplet;

public class PlayerTile extends Tile {

    public FillColor fillColor;
    public Movement movement;
    public InterlayerCollider interlayerCollider;

    private boolean gameOver;
    private int victory;
    private boolean hasCollected;

    public PlayerTile(int col, int row, int size, PApplet p, Directions dir)
    {
        super(col, row, size, p);
        this.fillColor = new FillColor(this, new int[] {255, 0, 0}, false);
        this.movement = new Movement(this, dir);
        this.interlayerCollider = new InterlayerCollider(this);
        hasCollected = false;
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
        else if (this.interlayerCollider.collidesWith("ExitTile"))
        {
            if (hasCollected)
            {
                victory = 1;
                this.movement.setDir(Directions.STATIC);
            }
            else
            { gameOver = true; }
        }
        else if (this.interlayerCollider.collidesWith("EmptyTile"))
        {
            this.Destroy();
        }
        this.CompUpdate();
    }

    private void ChangeSprite(Tile t)
    {
        for (int i = 0; i < t.componentList.size(); i++)
        {
            if (t.componentList.get(i) instanceof Sprite)
            {
                ((Sprite) t.componentList.get(i)).setImg("ArrowRight.png");
            }
        }
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
}
