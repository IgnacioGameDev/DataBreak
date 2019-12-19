package core.Tile_Engine.Tile_System.Components;
import core.Tile_Engine.Tile_System.Component;
import core.Tile_Engine.Tile_System.Tile;

//Component that displays strings as a text element on tiles
public class TextDisplay extends Component {

    private String info;
    private int font;

    //Text can be alligned using an existing enum in the processing library
    private int textAlignX;
    private int textAlignY;

    //COLORED TEXT! YAY
    private int[] textColor;

    public TextDisplay(Tile t, String info, int font, int[] textColor, int textAlignX, int textAlignY)
    {
        super(t);
        this.info = info;
        this.font = font;
        this.textAlignX = textAlignX;
        this.textAlignY = textAlignY;
        this.textColor = textColor;
    }

    @Override
    protected void Update() {
        t.parent.textAlign(textAlignX, textAlignY);
        t.parent.textSize(font);
        t.parent.fill(textColor[0], textColor[1], textColor[2]);
        t.parent.text(info, t.getX() + t.getSize()/2, t.getY() + t.getSize()/2);
    }

    public void setInfo(String info) { this.info = info; }

    public void setFont(int font) { this.font = font; }

    public void setTextColor(int[] textColor) { this.textColor = textColor; }

    public void setTextAlignX(int textAlignX) { this.textAlignX = textAlignX; }

    public void setTextAlignY(int textAlignY) { this.textAlignY = textAlignY; }
}
