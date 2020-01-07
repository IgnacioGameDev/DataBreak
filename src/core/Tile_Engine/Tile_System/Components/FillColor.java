package core.Tile_Engine.Tile_System.Components;
import core.Tile_Engine.Tile_System.Component;
import core.Tile_Engine.Tile_System.Tile;

import java.awt.*;

//Component that draws a square over the tile
public class FillColor extends Component {

    //Controlable by the user are the square's color and if it has a stroke
    //Size is always adjusted to the tile this is on
    //Uses Java Color class
    private Color hue;
    private boolean stroke;

    public FillColor(Tile t, Color hue, boolean stroke)
    {
        super(t);
        this.hue = hue;
        this.stroke = stroke;
    }

    @Override
    protected void Update() {
        t.parent.fill(hue.getRed(), hue.getGreen(), hue.getBlue());
        if (stroke)
        {
            t.parent.stroke(0);
        }
        else
        {
            t.parent.noStroke();
        }
        t.parent.rect(t.getX(), t.getY(), t.getSize(), t.getSize());
    }

    public void setHue(Color hue) {
        this.hue = hue;
    }

    public void setStroke(boolean stroke) { this.stroke = stroke; }
}
