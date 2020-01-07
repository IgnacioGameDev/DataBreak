package core.Tile_Engine.Tile_System.Components;
import core.Tile_Engine.Tile_System.Component;
import core.Tile_Engine.Tile_System.Tile;
import processing.core.PImage;

import java.awt.*;

//Component that displays GIF, JPEG or PNG files, resizing them to the parent tile
//Uses the processing library PImage methods
public class Sprite extends Component {

    //Tiles with a single sprite can change the image with a method call
    private PImage img;

    //Optionally tiles with a Sprite can use transparency and tint to change the image internally instead of using a separate file
    private Color tint;
    private int transparency;

    public Sprite(Tile t, String fileName, Color tint)
    {
        super(t);
        img = t.parent.loadImage(fileName);
        this.tint = tint;
        transparency = 255;
    }

    @Override
    protected void Update() {
        img.resize(t.getSize(), t.getSize());
        t.parent.tint(tint.getRed(), tint.getGreen(), tint.getBlue(), transparency);
        t.parent.image(img, t.getX(), t.getY());
    }

    public void setImg(String fileName) {
        this.img = t.parent.loadImage(fileName);
    }

    public void setTint(Color tint) { this.tint = tint; }

    public void setTransparency(int transparency) { this.transparency = transparency; }
}
