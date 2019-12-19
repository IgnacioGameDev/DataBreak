import core.DataBreak.LevelEditor;
import processing.core.PApplet;

public class Main extends PApplet {

    public LevelEditor levelEditor;

    public static void main(String args[]) {PApplet.main("Main");}

    public void settings() {size(800, 800);}

    public void setup()
    {
        levelEditor = new LevelEditor(this);
    }

    public void draw() {
        levelEditor.Update();
    }

    public void mouseReleased() {}

    public void keyReleased() {
        levelEditor.keyReleased(key, keyCode);
    }
}
