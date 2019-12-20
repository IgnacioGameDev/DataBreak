import core.DataBreak.DataBreak;
import core.DataBreak.LevelEditor;
import processing.core.PApplet;

public class Main extends PApplet {

    DataBreak dataBreak;

    public static void main(String args[]) {PApplet.main("Main");}

    public void settings() {size(800, 800);}

    public void setup()
    {
        dataBreak = new DataBreak(this);
    }

    public void draw() {
        dataBreak.Update();
    }

    public void mouseReleased() {
        dataBreak.MousePressed(mouseX, mouseY);
    }

    public void keyReleased() {
        dataBreak.KeyPressed(key, keyCode);
    }
}
