package core.Tile_Engine.Tile_System.Components;
import core.Tile_Engine.Tile_System.Component;
import core.Tile_Engine.Tile_System.Tile;

//Component that draws a square over the tile
public class FillColor extends Component {

    //Controlable by the user are the square's color and if it has a stroke
    //Size is always adjusted to the tile this is on
    private int[] hue;
    private boolean stroke;

    public FillColor(Tile t, int[] hue, boolean stroke)
    {
        super(t);
        this.hue = hue;
        this.stroke = stroke;
    }

    @Override
    protected void Update() {
        t.parent.fill(hue[0], hue[1], hue[2]);
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

    public void setHue(int[] hue) {
        this.hue = hue;
    }

    public void setStroke(boolean stroke) { this.stroke = stroke; }
}
