package core.Tile_Engine;
import processing.core.PApplet;

//Acts as an ingame clock which can adjust framerate and very easily time events every few determined frames
public class GameManager {

    PApplet parent;
    private int freq;
    private boolean tick;

    public GameManager(int frameRate, int freq, PApplet p)
    {
        tick = false;
        this.parent = p;
        this.freq = freq;
        parent.frameRate(frameRate);
    }

    //Uses a boolean as a switch used to trigger other methods every "freq" frames
    public void Run()
    {
        if (parent.frameCount % freq == 0)
        {
            tick = true;
        }
    }

    public boolean tick() { return tick; }

    public void setTick(boolean tick) { this.tick = tick; }

    public int getFreq() { return freq; }

    public void setFreq(int freq) { this.freq = freq; }
}