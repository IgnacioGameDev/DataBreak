package core.Tile_Engine.Tile_System.Components;
import core.Tile_Engine.Tile_System.Component;
import core.Tile_Engine.Tile_System.Tile;
import processing.core.PImage;

//Component that displays GIF, JPEG or PNG files, resizing them to the parent tile
//Uses the processing library PImage methods
public class Sprite extends Component {

    //Tiles with a single sprite can change the image with a method call
    private PImage img;

    //Optionally tiles with a Sprite can use transparency and tint to change the image internally instead of using a separate file
    private int[] tint;
    private int transparency;

    public Sprite(Tile t, String fileName)
    {
        super(t);
        img = t.parent.loadImage(fileName);
        tint = new int[] {255, 255, 255};
        transparency = 255;
    }

    @Override
    protected void Update() {
        img.resize(t.getSize(), t.getSize());
        t.parent.tint(tint[0], tint[1], tint[2], transparency);
        t.parent.image(img, t.getX(), t.getY());
    }

    public void setImg(String fileName) {
        this.img = t.parent.loadImage(fileName);
    }

    public void setTint(int[] tint) { this.tint = tint; }

    public void setTransparency(int transparency) { this.transparency = transparency; }
}
